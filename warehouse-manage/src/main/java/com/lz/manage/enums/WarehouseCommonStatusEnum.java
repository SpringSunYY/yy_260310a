package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseCommonStatusEnum {

    WAREHOUSE_COMMON_STATUS_0("0", "停用"),
    WAREHOUSE_COMMON_STATUS_1("1", "正常");

    private final String value;
    private final String label;

    WarehouseCommonStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseCommonStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseCommonStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseCommonStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}