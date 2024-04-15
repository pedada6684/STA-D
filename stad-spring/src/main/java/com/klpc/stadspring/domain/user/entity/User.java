package com.klpc.stadspring.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue
  @Column(name = "user_id")
  private Long id;
  private String email;
  private String password;
  private String nickname;
  private String name;
  private String profile = null;
  private Long type;
  private Long status; // 탈퇴 0, 정상 1
  private LocalDateTime regDate;
  private LocalDateTime delDate;
  private String company;
  private String department;
  private String comNo;

  public static User createNewUser(
          String email,
          String password,
          String nickname,
          String username,
          Long type
  ){
    User user = new User();
    user.email = email;
    user.password = password;
    user.nickname = nickname;
    user.name = username;
    user.type = type;
    user.status = 1L;
    user.regDate = LocalDateTime.now();
    return user;
  }

  public void withdraw(){
    this.status = 0L;
    this.delDate = LocalDateTime.now();
  }
}
