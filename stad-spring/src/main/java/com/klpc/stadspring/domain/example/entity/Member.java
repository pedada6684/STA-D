package com.klpc.stadspring.domain.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;
  @Column(unique = true)
  private String email;
  private String password;
  private String nickname;
  private String username;
  private String profileUrl = null;
  private Long role;
  private LocalDateTime regDate;
  private Long status;
  private LocalDateTime delDate;
//
//  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//  private List<BookmarkPlace> bookmarkPlaces;


  public static Member createNewMember(
          String email,
          String password,
          String nickname,
          String username
  ){
    Member member = new Member();
    member.email = email;
    member.password = password;
    member.nickname = nickname;
    member.username = username;
    member.status = 1L;
    member.regDate = LocalDateTime.now();
    return member;
  }
}
