package com.hzxc.chz.server.web;


import com.hzxc.chz.server.annotation.CheckLogin;
import com.hzxc.chz.server.utils.MyX509TrustManager;
import com.hzxc.chz.server.utils.httpPost;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;

@Controller
public class xyJuhePayController {
    // data {"version":"2.0","merchant_id":"80000001","term_id":"86570001","timestamp":"20180409144552","biz_type":"wx.wappay",
    // "biz_content":{"trade_no":"kuake_t001","total_amount":33,"attach":"","body":"","time_expire":"20170809120452","notify_url":"http:\/\/115.236.74.186:9513\/P"},"sign_type":"MD5",
    // "mch_create_ip":"","appid":"","time_expire":"20180409154052"}
    // sign: ebe6734ef7e646c9186f2cad4bda467e

    // TODO 提现需要对提现密码增加随机密码盐
    @CheckLogin
    @RequestMapping(value = "/pay/wappaytest", method = {RequestMethod.POST })
    public @ResponseBody
     String refund(HttpServletRequest request,
                   @RequestBody String data) {

        try {
            BufferedReader br = request.getReader();

            String str, wholeStr = "";
            while ((str = br.readLine()) != null) {
                wholeStr += str;
            }
            System.out.println(wholeStr);
        } catch (Exception e) {}

//        return request();
        return  httpsRequest();
    }

    String request() {
        String ret = "failed";
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .sslSocketFactory(createSSLSocketFactory())
                    .build();
            FormBody.Builder builder = new FormBody.Builder();
            String dataJson = "{\"version\":\"2.0\",\"merchant_id\":\"80000001\",\"term_id\":\"86570001\",\"timestamp\":\"20180409144552\",\"biz_type\":\"wx.wappay\"," +
                     "\"biz_content\":{\"trade_no\":\"kuake_t001\",\"total_amount\":33,\"attach\":\"\",\"body\":\"\",\"time_expire\":\"20170809120452\",\"notify_url\":\"http:\\/\\/115.236.74.186:9513\\/P\"},\"sign_type\":\"MD5\"," +
                     "\"mch_create_ip\":\"\",\"appid\":\"\",\"time_expire\":\"20180409154052\"}";
            String sign = "ebe6734ef7e646c9186f2cad4bda467e";
            System.out.println("data: " + dataJson);
            System.out.println("sign: " + sign);
            builder.add("data", dataJson);
            builder.add("sign", sign);
            okhttp3.RequestBody body = builder.build();
            Request request = new Request.Builder()
                    .url("https://allpaytest.visastandards.com:9902/gclients")
//                    .url("http://localhost:8080/pay/wappaytest")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                StringBuilder sb = new StringBuilder(response.body().string());
                ret = sb.toString();
                System.out.println("服务器响应为: " + ret);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    String httpsRequest()
    {
        String dataJson = "{\"version\":\"2.0\",\"merchant_id\":\"80000001\",\"term_id\":\"86570001\",\"timestamp\":\"20180409144552\",\"biz_type\":\"wx.wappay\"," +
                "\"biz_content\":{\"trade_no\":\"kuake_t001\",\"total_amount\":33,\"attach\":\"\",\"body\":\"\",\"time_expire\":\"20170809120452\",\"notify_url\":\"http:\\/\\/115.236.74.186:9513\\/P\"},\"sign_type\":\"MD5\"," +
                "\"mch_create_ip\":\"\",\"appid\":\"\",\"time_expire\":\"20180409154052\"}";
        String sign = "ebe6734ef7e646c9186f2cad4bda467e";

        String data = "data="+dataJson+"&sign="+sign;

        try {
            dataJson = URLEncoder.encode(dataJson, "UTF-8");
        } catch (Exception e) {}

        return httpPost.HttpsURLConnection_Post("https://allpaytest.visastandards.com:9902/gclients",data.getBytes(), null);
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new MyX509TrustManager()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }
}
