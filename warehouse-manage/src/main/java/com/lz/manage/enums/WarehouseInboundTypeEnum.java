package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseInboundTypeEnum {

    WAREHOUSE_INBOUND_TYPE_0("0", "采购入库"),
    WAREHOUSE_INBOUND_TYPE_1("1", "盘盈入库"),
    WAREHOUSE_INBOUND_TYPE_2("2", "调拨入库"),
    WAREHOUSE_INBOUND_TYPE_3("3", "退货入库");

    private final String value;
    private final String label;

    WarehouseInboundTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseInboundTypeEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseInboundTypeEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseInboundTypeEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}