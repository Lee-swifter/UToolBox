package lic.swifter.box.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lic on 16-8-1.
 */
public class ApiHelper {

    public static JuheApi getJuhe() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.juhe.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(JuheApi.class);
    }
}
