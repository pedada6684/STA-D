package com.klpc.stadspring.domain.selectedContent.entity;

import com.klpc.stadspring.domain.advert.entity.Advert;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class SelectedContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fixedContentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advert_id")
    private Advert advert;

    public static SelectedContent createToSelectedContent(Long contentId){
        SelectedContent result = new SelectedContent();
        result.fixedContentId = contentId;
        return result;
    }

    public void linkAdvert(Advert advert){
        this.advert=advert;
    }

}
