package com.klpc.stadspring.domain.contents.detail.service;

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

@Service
public class ContentDetailParsingService {

    @Autowired
    private ContentConceptRepository contentConceptRepository;

    @Autowired
    private ContentDetailRepository contentDetailRepository;

    @Transactional
    public void parseAndSaveJson(String filePath) throws Exception {
        // JSONParser를 사용하여 JSON 파일 파싱
        JSONParser parser = new JSONParser();

        try {
            // JSON 파일을 FileReader로 읽기
            FileReader reader = new FileReader(filePath);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            // JSON 배열을 반복하여 각 객체를 처리
            for (Object obj : jsonArray) {
                // JSONObject로 캐스팅
                JSONObject jsonObject = (JSONObject) obj;

                // ContentConcept 객체 생성
                ContentConcept concept = contentConceptRepository.findByIsMovieAndTitle(false,(String) jsonObject.get("title"))
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

                // `episode` 값을 `Long`에서 `Integer`로 변환
                Long episodeLong = (Long) jsonObject.get("episode");
                int episode = episodeLong.intValue();

                // ContentDetail 객체 생성
                ContentDetail newDetail = ContentDetail.createSeriesDetail(
                        concept.getId(),
                        episode,
                        (String) jsonObject.get("video_url"),
                        (String) jsonObject.get("summary"));

                contentDetailRepository.save(newDetail);
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

