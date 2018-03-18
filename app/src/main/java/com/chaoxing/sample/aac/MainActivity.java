package com.chaoxing.sample.aac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaoxing.sample.aac.accounts.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTvText;
    private Button mBtnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvText = findViewById(R.id.tv_text);

        mBtnGo = findViewById(R.id.btn_go);
        mBtnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });

    }

    private void go() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

//        Credential credential = new Credential();
//        credential.setAccount("huwei@chaoxing.com");
//        credential.setCode("555666");
//
//        AccountAuthenticator.get(this).confirmCredentials(credential);
    }

}
