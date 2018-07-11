package com.hzxc.chz.server.utils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

/**
 * Created by chengzuan on 16/3/25.
 */
public class httpPost {
    public static String HttpURLConnection_Post(String strUrl, byte[] content, HashMap<String, String> httpHead)
    {

        HttpURLConnection urlConn;
        InputStreamReader in;
        String result = null;

        System.out.println( "HttpURLConnection_Post url is :" + strUrl);
        String tmp = "post content is : " + new String(content);
        int strl = tmp.length();
        for(int i = 0; i < strl; )
        {
            int end = strl-i>2014 ? 2014: strl-i;
            String p = tmp.substring(i, i+end);
            i+=end;
            System.out.println("HttpURLConnection_Post" + p);
        }

        try{
            //通过openConnection 连接
            URL url = new URL(strUrl);
            urlConn=(HttpURLConnection)url.openConnection();
            urlConn.setConnectTimeout(10000);
            urlConn.setReadTimeout(10000);

            urlConn.setRequestMethod("POST");

            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConn.setRequestProperty("Content-Length", "" +
                    Integer.toString(content.length));
            urlConn.setRequestProperty("Content-Language", "en-US");

            urlConn.setUseCaches(false);
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);

            // 增加http头
            if(httpHead != null && httpHead.size() > 0) {
                for (String key : httpHead.keySet()) {
                    urlConn.setRequestProperty(key, httpHead.get(key));
                }
            }

            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
            // 要注意的是connection.getOutputStream会隐含的进行connect。
            urlConn.connect();
            //DataOutputStream流
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());

            //将要上传的内容写入流中
            out.write(content);
            //刷新、关闭
            out.flush();
            out.close();

            int response = urlConn.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream istr = urlConn.getInputStream();
                in = new InputStreamReader(istr);

                BufferedReader bufferedReader = new BufferedReader(in);
                StringBuffer strBuffer = new StringBuffer();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    strBuffer.append(line);
                }
                result = strBuffer.toString();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public static String HttpsURLConnection_Post(String strUrl, byte[] content, HashMap<String, String> httpHead)
    {

        HttpsURLConnection urlConn;
        InputStreamReader in;
        String result = null;

        System.out.println( "HttpURLConnection_Post url is :" + strUrl);
        String tmp = "post content is : " + new String(content);
        int strl = tmp.length();
        for(int i = 0; i < strl; )
        {
            int end = strl-i>2014 ? 2014: strl-i;
            String p = tmp.substring(i, i+end);
            i+=end;
            System.out.println("HttpURLConnection_Post" + p);
        }

        try{
            //通过openConnection 连接
            URL url = new URL(strUrl);
            urlConn=(HttpsURLConnection)url.openConnection();
            urlConn.setConnectTimeout(10000);
            urlConn.setReadTimeout(10000);

            urlConn.setRequestMethod("POST");

            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConn.setRequestProperty("Content-Length", "" +
                    Integer.toString(content.length));
            urlConn.setRequestProperty("Content-Language", "en-US");

            urlConn.setUseCaches(false);
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);

            // 增加http头
            if(httpHead != null && httpHead.size() > 0) {
                for (String key : httpHead.keySet()) {
                    urlConn.setRequestProperty(key, httpHead.get(key));
                }
            }

            // 设置ssl拦截
            ignoreSsl();

            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
            // 要注意的是connection.getOutputStream会隐含的进行connect。
            urlConn.connect();
            //DataOutputStream流
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());

            //将要上传的内容写入流中
            out.write(content);
            //刷新、关闭
            out.flush();
            out.close();

            int response = urlConn.getResponseCode();            //获得服务器的响应码
            if(response == HttpsURLConnection.HTTP_OK) {
                InputStream istr = urlConn.getInputStream();
                in = new InputStreamReader(istr);

                BufferedReader bufferedReader = new BufferedReader(in);
                StringBuffer strBuffer = new StringBuffer();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    strBuffer.append(line);
                }
                result = strBuffer.toString();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    static class miTM implements TrustManager, X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }
        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }
        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }
    /**
     * 忽略HTTPS请求的SSL证书，必须在openConnection之前调用
     * 请求时根据url自己判断是否忽略。
     * @throws Exception
     */
    public static void ignoreSsl() throws Exception {
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
}
