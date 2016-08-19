package lic.swifter.box.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lic on 16-8-1.
 */
public class ApiHelper {

    public static final int API_JUHEAPI = 1;

    public static JuheApi getJuhe() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.juhe.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(JuheApi.class);
    }

    public static JuheApi getJuhe(int type) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addConverterFactory(GsonConverterFactory.create());
        switch (type) {
            case API_JUHEAPI:
                builder.baseUrl("http://api.juheapi.com/");
                break;
        }
        return builder.build().create(JuheApi.class);
    }
}
