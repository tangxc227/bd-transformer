package com.bd.transformer.common;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 12:47 2018/11/29
 * @Modified by:
 */
public enum KpiType {

    NEW_INSTALL_USER("new_install_user"),
    BROWSER_NEW_INSTALL_USER("browser_new_install_user");

    public final String name;

    KpiType(String name) {
        this.name = name;
    }

    /**
     * 根据kpiType的名称字符串值，获取对应的kpitype枚举对象
     *
     * @param name
     * @return
     */
    public static KpiType valueOfName(String name) {
        for (KpiType type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new RuntimeException("指定的name不属于该KpiType枚举类：" + name);
    }

}
