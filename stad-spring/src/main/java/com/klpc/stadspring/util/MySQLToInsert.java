package com.klpc.stadspring.util;

import java.sql.*;

public class MySQLToInsert {

    public static void main(String[] args) {
        // MySQL 데이터베이스 연결 설정
        String url = "jdbc:mysql://localhost:3306/stad";
        String user = "root";
        String password = "1234";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 데이터베이스 연결
            conn = DriverManager.getConnection(url, user, password);

            // 쿼리 실행을 위한 Statement 객체 생성
            stmt = conn.createStatement();

            // 테이블의 데이터를 가져올 쿼리 (예: my_table)
            String query = "SELECT * FROM content_category_relationship";

            // 쿼리 실행
            rs = stmt.executeQuery(query);

            // ResultSet의 메타데이터를 사용하여 열 정보 가져오기
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // 각 행을 `INSERT` 문으로 변환
            while (rs.next()) {
                // `INSERT` 문 시작
                StringBuilder insertStatement = new StringBuilder("(");

                // 열 값 추가 (id를 제외)
                boolean first = true;
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);

                    // 열 이름이 "id"인 경우 건너뜁니다
                    if (!columnName.equalsIgnoreCase("id")) {
                        // 첫 번째 값이 아닌 경우 콤마 추가
                        if (!first) {
                            insertStatement.append(", ");
                        }
                        first = false;

                        // 열 값 가져오기
                        Object value = rs.getObject(i);

                        // 값이 문자열이면 작은따옴표로 감싸기
                        if (value instanceof String) {
                            insertStatement.append("'");
                            insertStatement.append(value.toString().replace("'", "''")); // 작은따옴표 이스케이프
                            insertStatement.append("'");
                        } else {
                            insertStatement.append(value);
                        }
                    }
                }

                // `INSERT` 문 종료
                insertStatement.append("),");

                // `INSERT` 문 출력
                System.out.println(insertStatement.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
