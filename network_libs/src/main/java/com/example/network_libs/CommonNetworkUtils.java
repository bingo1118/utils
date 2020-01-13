package com.example.network_libs;

import com.example.network_libs.cookie.SimpleCookieJar;
import com.example.network_libs.https.HttpsUtils;
import com.example.network_libs.listener.DisposeDataHandle;
import com.example.network_libs.response.CommonJsonCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommonNetworkUtils {

    private static final int TIME_OUT = 30;
    private static OkHttpClient mHttpClient;

    static {
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        //为所有请求添加请求头，看个人需求
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request=chain.request().newBuilder().addHeader("User-Agent","bingo1118").build();
                return chain.proceed(request);
            }
        });
        builder.cookieJar(new SimpleCookieJar());
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.readTimeout(TIME_OUT,TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT,TimeUnit.SECONDS);
        builder.followRedirects(true);//重定向

//        builder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(),HttpsUtils.initTrustManager());//https相关
        mHttpClient=builder.build();
    }

    public static OkHttpClient getOkHttpClient(){
        return mHttpClient;
    };

    public static Call get(Request request, DisposeDataHandle handle){
        Call call=mHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return  call;
    }

    public static Call post(Request request, DisposeDataHandle handle) {
        Call call = mHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

}
