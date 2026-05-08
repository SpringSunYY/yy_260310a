package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseOutboundTypeEnum {

    WAREHOUSE_OUTBOUND_TYPE_0("0", "盘亏出库"),
    WAREHOUSE_OUTBOUND_TYPE_1("1", "领用出库"),
    WAREHOUSE_OUTBOUND_TYPE_2("2", "调拨出库"),
    WAREHOUSE_OUTBOUND_TYPE_3("3", "报废出库");

    private final String value;
    private final String label;

    WarehouseOutboundTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseOutboundTypeEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseOutboundTypeEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseOutboundTypeEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}