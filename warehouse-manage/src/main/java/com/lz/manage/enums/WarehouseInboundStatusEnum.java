package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseInboundStatusEnum {

    WAREHOUSE_INBOUND_STATUS_0("0", "已提交"),
    WAREHOUSE_INBOUND_STATUS_1("1", "已入库"),
    WAREHOUSE_INBOUND_STATUS_2("2", "已取消");

    private final String value;
    private final String label;

    WarehouseInboundStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseInboundStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseInboundStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseInboundStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}