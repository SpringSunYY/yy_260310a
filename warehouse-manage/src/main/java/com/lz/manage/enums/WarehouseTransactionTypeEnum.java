package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseTransactionTypeEnum {

    WAREHOUSE_TRANSACTION_TYPE_0("0", "入库"),
    WAREHOUSE_TRANSACTION_TYPE_1("1", "出库"),
    WAREHOUSE_TRANSACTION_TYPE_2("2", "盘点");

    private final String value;
    private final String label;

    WarehouseTransactionTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseTransactionTypeEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseTransactionTypeEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseTransactionTypeEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}