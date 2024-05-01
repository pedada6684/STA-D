package com.klpc.stadspring.domain.advert.repository;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert,Long> {

    public List<Advert> findAllByUser(User user);

    @Query("SELECT a.id FROM Advert a")
    public List<Long> findAllAdvertIds();

    @Query("""
           SELECT ad FROM Advert ad
           WHERE :contentId in (SELECT ads.fixedContentId FROM ad.selectedContents ads)
           """)
    public List<Advert> findAllByContentId(Long contentId);

    @Query("SELECT a.id FROM Advert a WHERE a.category = :category")
    public List<Long> findAdvertIdByCategory(String category);

    @Query("SELECT a.id FROM Advert a WHERE a.category = :category ORDER BY FUNCTION('RAND')")
    public List<Long> findRandomAdvertIdByCategory(String category);
}
