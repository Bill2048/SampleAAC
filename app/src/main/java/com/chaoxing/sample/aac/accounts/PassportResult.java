package com.chaoxing.sample.aac.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HUWEI on 2018/3/6.
 */

public class PassportResult implements Parcelable {

    private String mes;
    private int type;
    private String url;
    private boolean status;

    private String webUrl;
    private String ignoreUrl;
    private String bindUrl;
    private boolean needbind;

    protected PassportResult(Parcel in) {
        mes = in.readString();
        type = in.readInt();
        url = in.readString();
        status = in.readByte() != 0;
        webUrl = in.readString();
        ignoreUrl = in.readString();
        bindUrl = in.readString();
        needbind = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mes);
        dest.writeInt(type);
        dest.writeString(url);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(webUrl);
        dest.writeString(ignoreUrl);
        dest.writeString(bindUrl);
        dest.writeByte((byte) (needbind ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PassportResult> CREATOR = new Creator<PassportResult>() {
        @Override
        public PassportResult createFromParcel(Parcel in) {
            return new PassportResult(in);
        }

        @Override
        public PassportResult[] newArray(int size) {
            return new PassportResult[size];
        }
    };

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(String ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    public String getBindUrl() {
        return bindUrl;
    }

    public void setBindUrl(String bindUrl) {
        this.bindUrl = bindUrl;
    }

    public boolean isNeedbind() {
        return needbind;
    }

    public void setNeedbind(boolean needbind) {
        this.needbind = needbind;
    }
}
