package com.hzxc.chz.server.utils;

import org.apache.commons.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chengzuan on 16/12/14.
 */
public class httpGet {
    // 获取支付配置信息
    public static String HttpURLConnection_Get(String strUrl, int timeout, String channelID, Log logger) {
        String result = null;

        if(timeout == 0)
            timeout = 10000;

        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);

            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();

        } catch (Exception e) {
            if(logger != null)
                logger.error(e.toString() + " url : " + strUrl);
            result = null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    if(logger != null)
                        logger.error(e.toString() + " url : " + strUrl);
                }
            }
        }
        return result;
    }
}

