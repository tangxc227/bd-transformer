package com.bd.transformer.test.util;

import com.bd.transformer.util.IPSeekerExt;

import java.util.List;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 11:43 2018/11/28
 * @Modified by:
 */
public class TestIPSeekerExt {

    public static void main(String[] args) {
        IPSeekerExt ipSeekerExt = new IPSeekerExt();
        IPSeekerExt.RegionInfo info = ipSeekerExt.analyticIp("114.61.94.253");
        System.out.println(info);

        /*List<String> ips = ipSeekerExt.getAllIp();
        for (String ip : ips) {
            System.out.println(ipSeekerExt.analyticIp(ip));
        }*/
    }

}
