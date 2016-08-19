package lic.swifter.box.api;

import lic.swifter.box.api.model.IdResult;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.api.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lic on 16-8-1.
 */
public interface JuheApi {

    String IP_KEY = "a40d13784f1f965b4555cd59f12d76e3";
    String ID_KEY = "c11db665ff38bd3b33f65a65faa182c3";

    @GET("ip/ip2addr?key="+IP_KEY)
    Call<Result<IpLocation>> queryIp(@Query("ip") String ip);

    @GET("idcard/index?key="+ID_KEY)
    Call<Result<IdResult>> queryId(@Query("cardno") String id);
}
