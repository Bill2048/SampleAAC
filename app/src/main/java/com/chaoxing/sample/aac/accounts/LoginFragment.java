package com.chaoxing.sample.aac.accounts;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaoxing.sample.aac.R;
import com.chaoxing.sample.aac.databinding.FragmentLoginBinding;

/**
 * Created by HUWEI on 2018/3/2.
 */

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.btnLogin.setOnClickListener(onClickListener);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.equals(binding.btnLogin)) {
                Credential credential = new Credential();
                credential.setAccount("18511868208");
                credential.setCode("555666");
                loginViewModel.passport(credential);
            }
        }
    };

}
