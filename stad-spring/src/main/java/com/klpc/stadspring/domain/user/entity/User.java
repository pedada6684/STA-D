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
  private LocalDateTime regDate;
  private LocalDateTime delDate;

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
    user.regDate = LocalDateTime.now();
    return user;
  }
}
