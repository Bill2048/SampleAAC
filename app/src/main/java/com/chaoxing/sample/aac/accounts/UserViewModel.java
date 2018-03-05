package com.chaoxing.sample.aac.accounts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by HUWEI on 2018/3/5.
 */

public class UserViewModel extends ViewModel {

    private AccountAuthenticator mAuthenticator;

    public UserViewModel(AccountAuthenticator authenticator) {
        this.mAuthenticator = authenticator;
    }

    public LiveData<User> getUser() {
        return null;
    }


    public static class Factory implements ViewModelProvider.Factory {

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T)new UserViewModel(null);
        }

    }
}
