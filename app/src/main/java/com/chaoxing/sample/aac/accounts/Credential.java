package com.chaoxing.sample.aac.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HUWEI on 2018/2/1.
 */

public class Credential implements Parcelable {

    private String account;
    private String code;

    public Credential() {
    }

    protected Credential(Parcel in) {
        account = in.readString();
        code = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(account);
        dest.writeString(code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Credential> CREATOR = new Creator<Credential>() {
        @Override
        public Credential createFromParcel(Parcel in) {
            return new Credential(in);
        }

        @Override
        public Credential[] newArray(int size) {
            return new Credential[size];
        }
    };

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
