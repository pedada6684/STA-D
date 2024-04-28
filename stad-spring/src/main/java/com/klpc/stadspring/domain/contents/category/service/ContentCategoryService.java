package com.klpc.stadspring.domain.contents.category.service;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.repository.ContentCategoryRepository;
import com.klpc.stadspring.domain.contents.category.service.command.request.AddCategoryRequestCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.klpc.stadspring.domain.contents.category.entity.QContentCategory.contentCategory;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContentCategoryService {
    private final ContentCategoryRepository repository;

    // id로 카테고리 조회
    public ContentCategory getContentCategoryById(Long id) {
        ContentCategory contentCategory = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return contentCategory;
    }

    // 카테고리명으로 id 조회
    public Long getIdByIsMovieAndName(boolean isMovie, String name) {
        Long id = repository.findIdByIsMovieAndName(isMovie, name)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return id;
    }

    // 시리즈 카테고리 이름 리스트 조회
    public List<String> getSeriesCategories() {
        List<String> categoryList = repository.findNameByIsMovie(false)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return categoryList;
    }

    // 영화 카테고리 이름 리스트 조회
    public List<String> getMovieCategories() {
        List<String> categoryList = repository.findNameByIsMovie(true)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return categoryList;
    }

    // 시리즈 카테고리 id 리스트 조회
    public List<Long> getSeriesCategoriesId() {
        List<String> categoryList = repository.findNameByIsMovie(false)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            Long id = repository.findIdByIsMovieAndName(false, categoryList.get(i))
                    .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
            list.add(id);
        }
        return list;
    }

    // 영화 카테고리 id 리스트 조회
    public List<Long> getMovieCategoriesId() {
        List<String> categoryList = repository.findNameByIsMovie(true)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            Long id = getIdByIsMovieAndName(true, categoryList.get(i));
            System.out.println(id);
            list.add(id);
        }
        return list;
    }

    /**
     * 콘텐츠 카테고리 등록
     */
    public void addCategory(AddCategoryRequestCommand command) {
        log.info("AddCategoryRequestCommand : " + command);

        ContentCategory newCategory = ContentCategory.createContentCategory(
                command.isMovie(),
                command.getName());
        repository.save(newCategory);
    }
}
