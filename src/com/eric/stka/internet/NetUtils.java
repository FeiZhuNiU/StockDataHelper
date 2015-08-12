package com.eric.stka.internet;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class NetUtils {

    private static final String url_pre = "http://table.finance.yahoo.com/table.csv?s=";
    private static final String url_post = ".ss";

    public static final String savePath_pre = "d:/stock/";
    public static final String savePath_post = ".csv";


    /**
     * @param code    the stock code
     * @return false if failed to download data
     */
    public static boolean downloadData(int code) {

        long startTime = System.currentTimeMillis();

        String uri = url_pre + code + url_post;
        String saveDir = savePath_pre + code + savePath_post;

        InputStream is = null;
        FileOutputStream fos = null;
        boolean ret = false;

        try {
            URL url = new URL(uri);

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("cn-proxy.jp.oracle.com", 80));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);

            System.out.println("response code: " + connection.getResponseCode());
//            Map<String,List<String>> map = connection.getHeaderFields();
//
//            for(Map.Entry<String,List<String>> entry : map.entrySet())
//            {
//                System.out.println("key: " + entry.getKey()+ " , Value:" + entry.getValue());
//            }
            is = connection.getInputStream();

            fos = new FileOutputStream(saveDir);

            byte[] buffer = new byte[4096];
            int len;

            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time consumed: (download data " + code + ")" + (endTime - startTime) / 1000.0 + "s");
        return ret;
    }
}
