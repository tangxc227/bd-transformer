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

}
