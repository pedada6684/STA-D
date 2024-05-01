package com.klpc.stadspring.domain.option.service;

import com.klpc.stadspring.domain.cart.controller.response.GetCartProductListResponse;
import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.cart.service.command.GetCartProductCommand;
import com.klpc.stadspring.domain.option.controller.response.GetOptionListByProductIdResponse;
import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.option.repository.OptionRepository;
import com.klpc.stadspring.domain.option.service.command.AddOptionCommand;
import com.klpc.stadspring.domain.option.service.command.DeleteOptionCommand;
import com.klpc.stadspring.domain.option.service.command.GetOptionCommand;
import com.klpc.stadspring.domain.option.service.command.GetOptionListCommand;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.product.service.command.AddProductCommand;
import com.klpc.stadspring.domain.product.service.command.DeleteProductCommand;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.repository.ProductTypeRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    /**
     * 옵션 리스트 받아오기
     */

    public GetOptionListByProductIdResponse getOptionList(Long productTypeId) {

        List<ProductOption> optionList = optionRepository.getOptionList(productTypeId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<GetOptionCommand> responseList = new ArrayList<>();

        for(ProductOption productOption : optionList){
            GetOptionCommand response = GetOptionCommand.builder()
                    .name(productOption.getName())
                    .value(productOption.getValue())
                    .build();

            responseList.add(response);
        }
        return GetOptionListByProductIdResponse.builder().list(responseList).build();
    }

    // 상품 등록
    public ProductOption addProductOption(AddOptionCommand command) {
        log.info("AddOptionCommand: "+command);

        ProductType productType = productTypeRepository.findById(command.getProductTypeId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        ProductOption newOption = ProductOption.createNewOption(
                productType,
                command.getName(),
                command.getValue()
        );

        optionRepository.save(newOption);

        return newOption;
    }

    public void deleteOption(Long id) {
        ProductOption productOption = optionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        optionRepository.delete(productOption);
    }
}
