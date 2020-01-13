package com.example.utilstest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.network_libs.CommonNetworkUtils;
import com.example.network_libs.listener.DisposeDataHandle;
import com.example.network_libs.listener.DisposeDataListener;
import com.example.network_libs.request.CommonRequest;
import com.example.utilstest.entity.LoginEntity;
import com.example.utilstest.global.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getdata();
    }

    private void getdata() {
        CommonNetworkUtils.get(CommonRequest.createGetRequest(Constants.login_url,null),
                new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        LoginEntity loginEntity = (LoginEntity)responseObj;
                        Toast.makeText(MainActivity.this,loginEntity.getError(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }
                }, LoginEntity.class));
    }
}
