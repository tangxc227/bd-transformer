package com.bd.transformer.domain;

import java.io.Serializable;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 14:06 2018/11/28
 * @Modified by:
 */
public class UserAgentInfo implements Serializable {

    private static final long serialVersionUID = -5458828401747782966L;

    private String browserName;
    private String browserVersion;
    private String osName;
    private String osVersion;

    public UserAgentInfo() {

    }

    public UserAgentInfo(String browserName, String browserVersion, String osName, String osVersion) {
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        this.osName = osName;
        this.osVersion = osVersion;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @Override
    public String toString() {
        return "UserAgentInfo{" +
                "browserName='" + browserName + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                ", osName='" + osName + '\'' +
                ", osVersion='" + osVersion + '\'' +
                '}';
    }

}
