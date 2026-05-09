package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseWarningTypeEnum {

    WAREHOUSE_WARNING_TYPE_0("0", "库存不足"),
    WAREHOUSE_WARNING_TYPE_1("1", "库存堆积");

    private final String value;
    private final String label;

    WarehouseWarningTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseWarningTypeEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseWarningTypeEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseWarningTypeEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}