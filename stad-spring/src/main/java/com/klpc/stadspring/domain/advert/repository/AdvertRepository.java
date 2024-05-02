package com.klpc.stadspring.domain.advert.repository;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert,Long> {

    @Query("""
           SELECT ad FROM Advert ad
           WHERE ad.user = :user
                 AND ad.status=true
           """)
    public List<Advert> findAllByUser(User user);

    @Query("SELECT a.id FROM Advert a WHERE a.status = true")
    public List<Long> findAllAdvertIds();

    @Query("""
           SELECT ad FROM Advert ad
           WHERE :contentId in (SELECT ads.fixedContentId FROM ad.selectedContents ads)
                 AND ad.status = true
           """)
    public List<Advert> findAllByContentId(Long contentId);

    @Query("SELECT a.id FROM Advert a WHERE a.advertCategory = :category AND a.status=true")
    public List<Long> findAdvertIdByCategory(String category);

    @Query("SELECT a.id FROM Advert a WHERE a.advertCategory = :category AND a.status=true ORDER BY FUNCTION('RAND')")
    public List<Long> findRandomAdvertIdByCategory(String category);

    @Query("""
           SELECT a.id FROM Advert a
           WHERE a.advertType = 'NOTPRODUCT'
           ORDER BY FUNCTION('RAND')
           LIMIT 1
           """)
    public Long findRandomNotProductAdvertId();
}
