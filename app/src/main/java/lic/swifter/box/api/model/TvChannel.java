package lic.swifter.box.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Copyright (C) 2015, Lee-swifter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by cheng on 2016/10/24.
 */

public class TvChannel implements Parcelable {

    public String channelName;
    public int pId;
    public String rel;
    public String url;

    private TvChannel(Parcel in) {
        channelName = in.readString();
        pId = in.readInt();
        rel = in.readString();
        url = in.readString();
    }

    public static final Creator<TvChannel> CREATOR = new Creator<TvChannel>() {
        @Override
        public TvChannel createFromParcel(Parcel in) {
            return new TvChannel(in);
        }

        @Override
        public TvChannel[] newArray(int size) {
            return new TvChannel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channelName);
        dest.writeInt(pId);
        dest.writeString(rel);
        dest.writeString(url);
    }
}
