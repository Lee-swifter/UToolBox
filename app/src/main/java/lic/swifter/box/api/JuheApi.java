package lic.swifter.box.api;

import java.util.List;

import lic.swifter.box.api.model.IdResult;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.api.model.PhoneResult;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TodayHistoryResult;
import lic.swifter.box.api.model.WxChosenWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lic on 16-8-1.
 */
public interface JuheApi {

    String IP_KEY = "a40d13784f1f965b4555cd59f12d76e3";
    String ID_KEY = "c11db665ff38bd3b33f65a65faa182c3";
    String PHONE_KEY = "cb0bad5a58017ce062ffa93108b72f1e";
    String TOH_KEY = "3b06779df8d6f97b66592aca7705beca";
    String WX_KEY = "bcc0719e0ad1eac6402bafa02cc9eea3";

    @GET("ip/ip2addr?key="+IP_KEY)
    Call<Result<IpLocation>> queryIp(@Query("ip") String ip);

    @GET("idcard/index?key="+ID_KEY)
    Call<Result<IdResult>> queryId(@Query("cardno") String id);

    @GET("mobile/get?key="+PHONE_KEY)
    Call<Result<PhoneResult>> queryPhone(@Query("phone") String phone);

    @GET("japi/toh?key="+TOH_KEY+"&v=1.0")
    Call<Result<List<TodayHistoryResult>>> queryToh(@Query("month") int month, @Query("day") int day);

    @GET("weixin/query?key="+WX_KEY+"&ps=50")
    Call<Result<WxChosenWrapper>> queryWxChosen();
}
