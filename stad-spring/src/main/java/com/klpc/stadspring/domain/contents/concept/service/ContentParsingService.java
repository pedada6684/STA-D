package com.klpc.stadspring.domain.contents.concept.service;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.repository.ContentCategoryRepository;
import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;
import com.klpc.stadspring.domain.contents.categoryRelationship.repository.ContentCategoryRelationshipRepository;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.ContentConceptRepository;
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
public class ContentParsingService {

    @Autowired
    private ContentConceptRepository contentConceptRepository;

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
            List<String> existingCategoryNames = contentCategoryRepository.findAll()
                    .stream()
                    .map(ContentCategory::getName)
                    .toList();

            // 새로운 카테고리 이름을 추적하기 위한 Set
            Set<String> newCategoryNames = new HashSet<>();

            // JSON 배열을 반복하여 각 객체를 처리
            for (Object obj : jsonArray) {
                // JSONObject로 캐스팅
                JSONObject jsonObject = (JSONObject) obj;

                // ContentConcept 객체 생성
                ContentConcept newConcept = ContentConcept.createContentConcept(
                        (String) jsonObject.get("audienceAge"),
                        (String) jsonObject.get("playtime"),
                        (String) jsonObject.get("description"),
                        (String) jsonObject.get("cast"),
                        (String) jsonObject.get("creator"),
                        (Boolean) jsonObject.get("is_movie"),
                        (String) jsonObject.get("release_year"),
                        (String) jsonObject.get("thumbnail"),
                        (String) jsonObject.get("title")
                );

                // ContentConcept 엔티티를 데이터베이스에 저장
                contentConceptRepository.save(newConcept);

                // JSON에서 genre 필드를 가져와 ","로 분할
                String[] genres = ((String) jsonObject.get("genre")).split(",");
                for (String genre : genres) {
                    genre = genre.trim(); // 공백 제거

                    // 기존에 존재하지 않는 카테고리 이름을 추적
                    if (!existingCategoryNames.contains(genre)) {
                        newCategoryNames.add(genre);
                    }

                    // ContentCategory 가져오기
                    ContentCategory category = contentCategoryRepository.findByIsMovieAndName((Boolean) jsonObject.get("is_movie"), genre)
                            .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
                    if (category == null) {
                        // ContentCategory가 존재하지 않으면 새로 생성
                        category = ContentCategory.createContentCategory((Boolean) jsonObject.get("is_movie"), genre);
                        contentCategoryRepository.save(category);
                        existingCategoryNames.add(genre); // 기존 카테고리 목록 업데이트
                    }

                    // ContentCategory와 ContentConcept의 관계 생성
                    ContentCategoryRelationship relationship = ContentCategoryRelationship.createRelationship(category, newConcept);
                    // 관계를 데이터베이스에 저장
                    contentCategoryRelationshipRepository.save(relationship);
                }
            }

            // 새로운 카테고리 이름을 ContentCategory에 저장
            for (String categoryName : newCategoryNames) {
                ContentCategory category = ContentCategory.createContentCategory(true, categoryName);
                contentCategoryRepository.save(category);
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

