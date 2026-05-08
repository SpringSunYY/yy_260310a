package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseOrderStatusEnum {

    WAREHOUSE_ORDER_STATUS_0("0", "已下单"),
    WAREHOUSE_ORDER_STATUS_1("1", "已入库"),
    WAREHOUSE_ORDER_STATUS_2("2", "已取消");

    private final String value;
    private final String label;

    WarehouseOrderStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseOrderStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseOrderStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseOrderStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}