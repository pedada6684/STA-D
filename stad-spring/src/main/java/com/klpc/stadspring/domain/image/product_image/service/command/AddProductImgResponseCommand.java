package com.klpc.stadspring.domain.image.product_image.service.command;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class AddProductImgResponseCommand {

    List<MultipartFile> imgs;

}
