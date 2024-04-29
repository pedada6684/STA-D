package com.klpc.stadspring.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 예시 필요한 것 추가해서 사용*/
    TEST_NOT_FOUND(HttpStatus.NOT_FOUND, "전달할 메시지"),

    /* S3 업로드 실패 */
    FAIL_TO_UPLOAD_S3(HttpStatus.BAD_GATEWAY, "S3 서버의 업로드를 실패했습니다."),

    /* 패스워드 불일치 */
    PASSWORD_NOT_MATCH(HttpStatus.NOT_ACCEPTABLE, "패스워드가 일치하지 않습니다."),

    /* JWT Token Error */
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "잘못된 토큰"),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),

    /* JSON Parsing 실패 */
    JSON_PARSE_IMPOSSIBLE(HttpStatus.BAD_GATEWAY, "API Parsing 중 오류가 발생했습니다."),

    REQUEST_NOT_FOUND(HttpStatus.BAD_REQUEST, "요청 변수를 확인해주십시오."),

    /* entity 존재하지 않음 */
    ENTITIY_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 정보를 찾을 수 없습니다."),

    /* entity 이미 존재 */
    ENTITIY_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 정보입니다."),

    /* URI */
    URI_SYNTAX_ERROR(HttpStatus.BAD_GATEWAY, "외부 API 호출 중 오류가 발생했습니다."),

    /* encoding UTF-8 에러 */
    ENCODING_UTF_8(HttpStatus.BAD_REQUEST, "UTF-8 변환 중 오류가 발생했습니다."),

    /* S3 파일 오류 */
    AWSS3_ERROR(HttpStatus.BAD_GATEWAY, "AWS S3 호출 중 오류가 발생했습니다."),

    /* 내림차순 정렬 출력 오류 */
    ORDERBYDESC_ERROR(HttpStatus.NOT_FOUND, "DB 내림차순 정렬 출력이 실패하였습니다."),

    /* 재고 수량 오류 */
    QUANTITY_ERROR(HttpStatus.BAD_REQUEST, "재고 수량 보다 주문 수량이 많습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
