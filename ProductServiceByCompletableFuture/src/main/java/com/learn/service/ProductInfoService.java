package com.learn.service;

import com.learn.domain.ProductInfo;
import com.learn.domain.ProductOption;

import java.util.List;

import static com.learn.util.CommonUtil.delay;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String productId) {
        delay(1000);
        List<ProductOption> productOptions = List.of(new ProductOption(1, "64GB", "Black", 699.99),
                new ProductOption(2, "128GB", "Black", 749.99));
        return ProductInfo.builder().productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
