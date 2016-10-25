package lic.swifter.box.api;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import lic.swifter.box.api.model.IdResult;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.api.model.JokesWrapper;
import lic.swifter.box.api.model.MovieRank;
import lic.swifter.box.api.model.PhoneResult;
import lic.swifter.box.api.model.QQLuck;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TodayHistoryResult;
import lic.swifter.box.api.model.TopNewsWrapper;
import lic.swifter.box.api.model.TvCategory;
import lic.swifter.box.api.model.TvChannel;
import lic.swifter.box.api.model.TvProgram;
import lic.swifter.box.api.model.WxChosenWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 聚合API接口及api-key定义
 * Created by lic on 16-8-1.
 */
public interface JuheApi {

    String IP_KEY = "a40d13784f1f965b4555cd59f12d76e3";
    String ID_KEY = "c11db665ff38bd3b33f65a65faa182c3";
    String PHONE_KEY = "cb0bad5a58017ce062ffa93108b72f1e";
    String TOH_KEY = "3b06779df8d6f97b66592aca7705beca";
    String WX_KEY = "bcc0719e0ad1eac6402bafa02cc9eea3";
    String QQ_LUCK_KEY = "d875bba41c2c4ea90d984751a8f0b942";
    String JOKE_KEY = "b036bf8e6703c1c6d2560fe4c2b121ea";
    String MOVIE_KEY = "e2f8ea575ba21732fb2ff7a58be66cc3";
    String TOP_NEWS_KEY = "5878230b3640a8c96776db219e90dfcb";
    String TV_TABLE_KEY = "206ccf92e1b0005fe6efead1dca99d0c";

    String MOVIE_CN = "CN";
    String MOVIE_US = "US";
    String MOVIE_HK = "HK";

    String NEWS_TOP = "top";
    String NEWS_SHEHUI = "shehui";
    String NEWS_GUONEI = "guonei";
    String NEWS_GUOJI = "guoji";
    String NEWS_YULE = "yule";
    String NEWS_TIYU = "tiyu";
    String NEWS_JUNSHI = "junshi";
    String NEWS_KEJI = "keji";
    String NEWS_CAIJING = "caijing";
    String NEWS_SHISHANG = "shishang";

    @StringDef({NEWS_TOP, NEWS_SHEHUI, NEWS_GUONEI, NEWS_GUOJI, NEWS_YULE, NEWS_TIYU, NEWS_JUNSHI, NEWS_KEJI, NEWS_CAIJING, NEWS_SHISHANG})
    @Retention(RetentionPolicy.SOURCE)
    @interface NewsType{}

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

    @GET("qqevaluate/qq?key="+QQ_LUCK_KEY)
    Call<Result<QQLuck>> queryQQLuck(@Query("qq") String qqNumber);

    @GET("joke/content/text.from?key="+JOKE_KEY+"&page=1&pagesize=20")
    Call<Result<JokesWrapper>> queryJokes();

    @GET("boxoffice/rank.php?key="+MOVIE_KEY)
    Call<Result<List<MovieRank>>> queryMovieRanking(@Query("area") String area);

    /**
     * 查询新闻接口
     * @param type top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     * @return 聚合数据
     */
    @GET("toutiao/index?key="+TOP_NEWS_KEY)
    Call<Result<TopNewsWrapper>> queryNews(@Query("type") @NewsType String type);


    @GET("tv/getCategory?key="+TV_TABLE_KEY)
    Call<Result<List<TvCategory>>> queryTvCategory();

    @GET("tv/getChannel?key="+TV_TABLE_KEY)
    Call<Result<List<TvChannel>>> queryTvChannel(@Query("pId") int id);

    @GET("tv/getProgram?key="+TV_TABLE_KEY)
    Call<Result<List<TvProgram>>> queryTvProgram(@Query("code") String code);
}
