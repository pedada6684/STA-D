package com.klpc.stadspring.domain.product_review.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormat {
    public String dateFormat(LocalDateTime time) {
        // 원하는 형식으로 포맷터를 정의합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        // LocalDateTime 객체를 정의된 포맷으로 변환합니다.
        String formattedDate = time.format(formatter);

        return formattedDate;
    }

//    time.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
}
