package com.chaoxing.sample.aac;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HUWEI on 2018/2/26.
 */

public class Reception<T extends Parcelable> implements Parcelable {

    T data;

    public Reception(){}

    protected Reception(Parcel in) {
        String className = in.readString();
        try {
            data = in.readParcelable(Class.forName(className).getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data.getClass().getName());
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reception> CREATOR = new Creator<Reception>() {
        @Override
        public Reception createFromParcel(Parcel in) {
            return new Reception(in);
        }

        @Override
        public Reception[] newArray(int size) {
            return new Reception[size];
        }
    };

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
