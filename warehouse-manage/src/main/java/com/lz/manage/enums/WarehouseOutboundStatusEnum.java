package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseOutboundStatusEnum {

    WAREHOUSE_OUTBOUND_STATUS_0("0", "待出库"),
    WAREHOUSE_OUTBOUND_STATUS_1("1", "已出库"),
    WAREHOUSE_OUTBOUND_STATUS_2("2", "已取消");

    private final String value;
    private final String label;

    WarehouseOutboundStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseOutboundStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseOutboundStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseOutboundStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
