package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseQualityStatusEnum {

    WAREHOUSE_QUALITY_STATUS_0("0", "合格"),
    WAREHOUSE_QUALITY_STATUS_1("1", "待检"),
    WAREHOUSE_QUALITY_STATUS_2("2", "不合格");

    private final String value;
    private final String label;

    WarehouseQualityStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseQualityStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseQualityStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseQualityStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}