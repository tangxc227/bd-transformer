package com.bd.transformer.test.util;

import com.bd.transformer.etl.util.LoggerUtil;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 09:06 2018/11/29
 * @Modified by:
 */
public class LoggerUtilTest {

    public static void main(String[] args) {
        String logText = "121.225.152.174^A1543453859.595^A192.168.100.120^A/BfImg.gif?c_time=1543454178818&oid=orderid456&u_mid=tangxc123&pl=java_server&en=e_cr&sdk=jdk&ver=1";
        logText = "121.225.152.174^A1543459030.394^A192.168.100.120^A/BfImg.gif?en=e_pv&p_url=http%3A%2F%2Flocalhost%3A8080%2Fbd_track_jssdk%2Fdemo2.jsp&p_ref=http%3A%2F%2Flocalhost%3A8080%2Fbd_track_jssdk%2Fdemo.jsp&tt=%E6%B5%8B%E8%AF%95%E9%A1%B5%E9%9D%A22&ver=1&pl=website&sdk=js&u_ud=948AB94A-E1A5-4EED-BBB8-CEDB74B8B4D0&u_sd=9EF5D22F-5CCD-4290-AFCA-641672988F73&c_time=1543458359241&l=zh-CN&b_iev=Mozilla%2F5.0%20(Windows%20NT%206.1%3B%20WOW64)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F46.0.2490.71%20Safari%2F537.36&b_rst=1280*768";
        System.out.println(LoggerUtil.handleLog(logText));
    }

}
