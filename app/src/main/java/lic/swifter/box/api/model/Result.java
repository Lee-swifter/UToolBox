package lic.swifter.box.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lic on 16-8-1.
 */
public class Result<T> {

    @SerializedName(value = "resultcode", alternate = {"error_code"})
    public int resultcode;

    public String reason;

    public T result;
}
