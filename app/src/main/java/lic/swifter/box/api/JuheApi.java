package lic.swifter.box.api;

import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.api.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lic on 16-8-1.
 */
public interface JuheApi {

    String APP_KEY_IP = "a40d13784f1f965b4555cd59f12d76e3";

    @GET("ip/ip2addr")
    Call<Result<IpLocation>> queryIp(@Path("ip") String ip, @Path("key") String key);
}
