package com.klpc.stadspring.domain.contents.concept.service;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.repository.ContentCategoryRepository;
import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;
import com.klpc.stadspring.domain.contents.categoryRelationship.repository.ContentCategoryRelationshipRepository;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.ContentConceptRepository;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.repository.ContentDetailRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContentConceptParsingService {

    @Autowired
    private ContentConceptRepository contentConceptRepository;

    @Autowired
    private ContentDetailRepository contentDetailRepository;

    @Autowired
    private ContentCategoryRepository contentCategoryRepository;

    @Autowired
    private ContentCategoryRelationshipRepository contentCategoryRelationshipRepository;

    @Transactional
    public void parseAndSaveJson(String filePath) throws Exception {
        // JSONParser를 사용하여 JSON 파일 파싱
        JSONParser parser = new JSONParser();

        try {
            // JSON 파일을 FileReader로 읽기
            FileReader reader = new FileReader(filePath);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            // ContentCategory에 저장된 카테고리 이름을 로드
            List<String> existingCategoryNames = contentCategoryRepository.findAllNames()
                    .orElseThrow(()->new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

            // JSON 배열을 반복하여 각 객체를 처리
            for (Object obj : jsonArray) {
                // JSONObject로 캐스팅
                JSONObject jsonObject = (JSONObject) obj;

                ContentConcept newConcept;
                // ContentConcept가 존재하지 않으면 엔티티를 데이터베이스에 저장
                if (!contentConceptRepository.findByIsMovieAndTitle((Boolean) jsonObject.get("is_movie"), (String) jsonObject.get("title")).isPresent()) {
                    // ContentConcept 객체 생성
                    newConcept = ContentConcept.createContentConcept(
                                    (String) jsonObject.get("audience_age"),
                                    (String) jsonObject.get("playtime"),
                                    (String) jsonObject.get("description"),
                                    (String) jsonObject.get("cast"),
                                    (String) jsonObject.get("creator"),
                                    (Boolean) jsonObject.get("is_movie"),
                                    (String) jsonObject.get("release_year"),
                                    (String) jsonObject.get("thumbnail"),
                                    (String) jsonObject.get("title"));
                    contentConceptRepository.save(newConcept);

                    if (newConcept.isMovie()) {
                        ContentDetail newDetail = ContentDetail.createMovieDetail(
                                newConcept.getId(),
                                "https://ssafy-stad.s3.ap-northeast-2.amazonaws.com/AdvertVideo/streamingDummy.mp4");
                        contentDetailRepository.save(newDetail);
                    }
                } else {
                    newConcept = contentConceptRepository.findByIsMovieAndTitle((Boolean) jsonObject.get("is_movie"), (String) jsonObject.get("title"))
                            .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
                }

                // JSON에서 genre 필드를 가져와 ","로 분할
                String[] genres = ((String) jsonObject.get("genre")).split(",");
                for (String genre : genres) {
                    genre = genre.trim(); // 공백 제거

                    ContentCategory category;
                    if (!contentCategoryRepository.findByIsMovieAndName((Boolean) jsonObject.get("is_movie"), genre).isPresent()) {
                        category = ContentCategory.createContentCategory((Boolean) jsonObject.get("is_movie"), genre);
                        contentCategoryRepository.save(category);
                        existingCategoryNames.add(genre); // 기존 카테고리 목록 업데이트
                    } else {
                        category = contentCategoryRepository.findByIsMovieAndName((Boolean) jsonObject.get("is_movie"), genre)
                                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
                    }

                    // ContentCategory와 ContentConcept의 관계 생성
                    ContentCategoryRelationship relationship;
                    if (!contentCategoryRelationshipRepository.findByCategoryAndConcept(category.getId(), newConcept.getId()).isPresent()) {
                        relationship = ContentCategoryRelationship.createRelationship(category, newConcept);
                        // 관계를 데이터베이스에 저장
                        contentCategoryRelationshipRepository.save(relationship);
                    }
                }
            }

            // JSON 파일 읽기 리소스를 닫음
            reader.close();
        } catch (FileNotFoundException e) {
            // 파일을 찾지 못한 예외 처리
            System.err.println("File not found: " + filePath);
            e.printStackTrace();
            throw e;
        } catch (ParseException e) {
            // JSON 파싱 예외 처리
            System.err.println("JSON parsing error: " + filePath);
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            // 파일 입출력 예외 처리
            System.err.println("I/O error occurred while parsing the JSON file: " + filePath);
            e.printStackTrace();
            throw e;
        }
    }
}

