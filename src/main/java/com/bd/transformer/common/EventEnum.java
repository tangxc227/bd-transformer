package com.bd.transformer.common;

/**
 * @Author: tangxc
 * @Description: 时间枚举类，指定事件名称
 * @Date: Created in 11:09 2018/11/28
 * @Modified by:
 */
public enum EventEnum {

    /**
     * launch事件，表示第一次访问
     */
    LAUNCH(1, "launch event", "e_l"),
    /**
     * 页面浏览事件
     */
    PAGEVIEW(2, "pageview event", "e_pv"),
    /**
     * 订单产生事件
     */
    CHARGEREQUEST(3, "charge request event", "e_crt"),
    /**
     * 订单成功支付事件
     */
    CHARGESUCCESS(4, "charge success event", "e_cs"),
    /**
     * 订单退款事件
     */
    CHARGEREFUND(5, "charge refund event", "e_cr"),
    /**
     * 事件
     */
    EVENT(6, "event duration event", "e_e");

    public final int id;
    public final String name;
    public final String alias;

    EventEnum(int id, String name, String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    /**
     * 根据别名查询事件枚举
     *
     * @param ailas
     * @return
     */
    public static EventEnum valuesOfAlias(String ailas) {
        for (EventEnum eventEnum : values() ) {
            if (eventEnum.alias.equals(ailas)) {
                return eventEnum;
            }
        }
        return null;
    }

}
