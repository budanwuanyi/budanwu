package com.bdw.common.utils.http;

import okhttp3.*;
import okhttp3.Request.Builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class HttpClient {

    OkHttpClient okHttpClient;

    public HttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public void sendJson(String url, String json, Callback callback) {


//MediaType 设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(url)//请求的url
                .post(requestBody)
                .build();

//创建/Call
        Call call = okHttpClient.newCall(request);
//加入队列 异步操作
        call.enqueue(callback);
    }


    private String attachHttpGetParams(String url, Map<String, String> params) {

        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i = 0; i < params.size(); i++) {
            String value = null;
            try {
                value = URLEncoder.encode(values.next(), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            stringBuffer.append(keys.next() + "=" + value);
            if (i != params.size() - 1) {
                stringBuffer.append("&");
            }
        }

        return url + stringBuffer.toString();
    }

    public String get(String url, Map<String, String> params) throws IOException {
        Request request = new Request.Builder().url(attachHttpGetParams(url, params)).build();
        return okHttpClient.newCall(request).execute().body().string();
    }

    public String post(String url, Map<String, String> formValues) throws IOException {

        FormBody.Builder builder = new FormBody.Builder();
        formValues.forEach((k, v) -> {
            builder.add(k, v);
        });

        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        return call.execute().body().string();
    }

    public String postWithHeaderJson(String url, Map<String, String> header, String json) throws IOException {

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Builder builderHeader = new Request.Builder();
        header.forEach((k, v) -> {
            builderHeader.addHeader(k, v);
        });
        Request request = builderHeader.url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        return call.execute().body().string();
    }

    public String postJson(String url, String content) throws IOException {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        return call.execute().body().string();
    }

    public String getWithHeader(String url, Map<String, String> header) throws IOException {
        Builder builderHeader = new Request.Builder();
        header.forEach((k, v) -> {
            builderHeader.addHeader(k, v);
        });
        Request request = builderHeader.url(url).build();
        return okHttpClient.newCall(request).execute().body().string();
    }

    public static String sendPost(String url, String param) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) realUrl.openConnection();
// 打开和URL之间的连接
// 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // POST方法
// 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
// 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
// 发送请求参数
            out.write(param);
// flush输出流的缓冲
            out.flush();
// 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
//使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
