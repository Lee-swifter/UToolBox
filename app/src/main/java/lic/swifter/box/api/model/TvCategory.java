package lic.swifter.box.api.model;
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

import android.os.Parcel;
import android.os.Parcelable;

public class TvCategory implements Parcelable{
    public int id;
    public String name;

    protected TvCategory(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<TvCategory> CREATOR = new Creator<TvCategory>() {
        @Override
        public TvCategory createFromParcel(Parcel in) {
            return new TvCategory(in);
        }

        @Override
        public TvCategory[] newArray(int size) {
            return new TvCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
