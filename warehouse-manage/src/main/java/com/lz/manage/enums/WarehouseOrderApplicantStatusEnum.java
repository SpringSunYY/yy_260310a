package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseOrderApplicantStatusEnum {

    WAREHOUSE_ORDER_APPLICANT_STATUS_0("0", "待审核"),
    WAREHOUSE_ORDER_APPLICANT_STATUS_1("1", "同意"),
    WAREHOUSE_ORDER_APPLICANT_STATUS_2("2", "拒绝");

    private final String value;
    private final String label;

    WarehouseOrderApplicantStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, WarehouseOrderApplicantStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WarehouseOrderApplicantStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<WarehouseOrderApplicantStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}