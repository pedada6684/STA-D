package com.klpc.stadspring.domain.study.entity;

import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import java.util.*;

@Entity
@Getter
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String category;
    String title;

    public static Study addStudy(String category, String title){
        Study study = new Study();
        study.category=category;
        study.title=title;
        return study;
    }

    public static List<Study> jsonToO(ResponseEntity<String> response){
        List<Study> studies = new ArrayList<>();

        JSONParser parser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(response.getBody());
        } catch (ParseException e) {
            throw new CustomException(ErrorCode.JSON_PARSE_IMPOSSIBLE);
        }
        JSONArray items = (JSONArray) object.get("items");

        for (Object arrItem : items) {
            JSONObject item = (JSONObject) arrItem;
            String title = (String) item.get("title");
            title = title.replace("<b>"," ");
            title = title.replace("</b>", " ");
            String category = (String) item.get("category2");
            studies.add(Study.addStudy(category,title));
        }
        return studies;
    }
}
