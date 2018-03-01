package com.chaoxing.sample.aac;

import android.os.Bundle;
import android.os.LocaleList;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaoxing.sample.aac.accounts.AccountAuthenticator;
import com.chaoxing.sample.aac.accounts.Credential;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mTvText;
    private Button mBtnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvText = findViewById(R.id.tv_text);

//        String language = Locale.getDefault().getDisplayLanguage().toString();
        String language = Locale.getDefault().toString();
        String language2 = LocaleList.getAdjustedDefault().get(0).toString();

        mTvText.setText(language + " : " + language2);


        mBtnGo = findViewById(R.id.btn_go);
        mBtnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });

    }

    private void go() {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);

        Credential credential = new Credential();
        credential.setAccount("huwei@chaoxing.com");
        credential.setCode("098909");

        AccountAuthenticator.get(this).confirmCredentials(credential);
//        Toast.makeText(this, "result : " + result, Toast.LENGTH_SHORT).show();
    }

}
