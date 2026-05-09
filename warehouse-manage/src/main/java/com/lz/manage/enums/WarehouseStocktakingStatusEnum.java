package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseStocktakingStatusEnum {

    WAREHOUSE_STOCKTAKING_STATUS_0("0", "盘点中"),
    WAREHOUSE_STOCKTAKING_STATUS_1("1", "已完成"),
    WAREHOUSE_STOCKTAKING_STATUS_2("2", "已取消");

    private final String value;
    private final String label;

    WarehouseStocktakingStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseStocktakingStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseStocktakingStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseStocktakingStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
