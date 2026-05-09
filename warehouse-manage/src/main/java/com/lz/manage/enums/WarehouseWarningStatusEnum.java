package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseWarningStatusEnum {

    WAREHOUSE_WARNING_STATUS_0("0", "待处理"),
    WAREHOUSE_WARNING_STATUS_1("1", "已处理");

    private final String value;
    private final String label;

    WarehouseWarningStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseWarningStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseWarningStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseWarningStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}