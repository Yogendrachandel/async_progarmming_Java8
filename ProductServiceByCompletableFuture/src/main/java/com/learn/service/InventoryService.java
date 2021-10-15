package com.learn.service;

import com.learn.domain.Inventory;
import com.learn.domain.ProductOption;

import static com.learn.util.CommonUtil.delay;

public class InventoryService {
    public Inventory retrieveInventory(ProductOption productOption) {
        delay(500);
        return Inventory.builder()
                .count(2).build();

    }
}
