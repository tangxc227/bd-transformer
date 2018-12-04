package com.bd.transformer.common;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 14:07 2018/11/29
 * @Modified by:
 */
public enum DateEnum {

    YEAR("year"), SEASON("season"), MONTH("month"),
    WEEK("week"), DAY("day"), HOUR("hour");

    public final String name;

    DateEnum(String name) {
        this.name = name;
    }

    public static DateEnum valueOfName(String name) {
        for (DateEnum dateEnum : values()) {
            if (dateEnum.name.equals(name)) {
                return dateEnum;
            }
        }
        throw new RuntimeException("时间类型不匹配");
    }
}
