package com.chaoxing.sample.aac;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaoxing.sample.aac.accounts.Credential;
import com.chaoxing.sample.aac.accounts.PassportResult;
import com.chaoxing.sample.aac.retrofit.ApiResult;
import com.chaoxing.sample.aac.retrofit.Result;

public class MainActivity extends AppCompatActivity {

    private TextView mTvText;
    private Button mBtnGo;

    private MainViewModel viewModel;

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

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getResult().observe(this, new Observer<ApiResult<Result<PassportResult>>>() {
            @Override
            public void onChanged(@Nullable ApiResult<Result<PassportResult>> resultApiResult) {
                if (resultApiResult != null) {

                }
            }
        });
    }

    private void go() {
//        result = MainRepository.get().go("18511868208", "555666");

        viewModel.singin(new Credential("18511868208", "555666"));

//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);

//        Credential credential = new Credential();
//        credential.setAccount("huwei@chaoxing.com");
//        credential.setCode("555666");
//
//        AccountAuthenticator.get(this).confirmCredentials(credential);
    }

}
