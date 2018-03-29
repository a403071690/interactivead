package com.zc.util;


import io.github.elkan1788.ip2region.IPSearcher;
import org.solar.util.IpRegionMapUtil;

public class Main<T> {
    public static void main(String[] args) throws Exception {
        long nt=System.currentTimeMillis();
        IPSearcher iPSearcher=new IPSearcher();
        for (int i = 0; i <100; i++) {

            System.out.println(IpRegionMapUtil.getCountryCity("1.119.129.16"));

        }

        System.out.println(System.currentTimeMillis()-nt);

    }


}
