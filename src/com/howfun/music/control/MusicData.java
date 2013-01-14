package com.howfun.music.control;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicData implements Parcelable{
	String mName = "";
	
     public static final Parcelable.Creator<MusicData> CREATOR =
        new Parcelable.Creator<MusicData>() {

        public MusicData createFromParcel(Parcel in) {
            return new MusicData(in);
        }

        public MusicData[] newArray(int size) {
            return new MusicData[size];
        }
    };
    
	public MusicData(Parcel in) {

		mName = in.readString();
	}

	public MusicData(String title) {
		mName = title;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mName);
	}

	public String getTitle() {
		return mName;
	}


}
