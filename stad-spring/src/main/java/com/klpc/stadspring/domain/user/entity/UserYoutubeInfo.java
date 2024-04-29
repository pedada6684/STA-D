package com.klpc.stadspring.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "user_youtube_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserYoutubeInfo {

    @Id
    @GeneratedValue
    @Column(name = "user_youtube_info_id")
    private Long id;
    private String youtubeInfo;

    public static UserYoutubeInfo createNewUserYoutubeInfo(
            String youtubeInfo
    ){
        UserYoutubeInfo userYoutubeInfo = new UserYoutubeInfo();
        userYoutubeInfo.youtubeInfo = youtubeInfo;
        return userYoutubeInfo;
    }
}
