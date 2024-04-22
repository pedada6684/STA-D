package com.klpc.stadspring.domain.contents.detail.service;

import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


public interface ContentDetailService {
    String getVideoUrlById(Long id);
    ResponseEntity<ResourceRegion> streamingPublicVideo(HttpHeaders httpHeaders, String pathStr);
}
