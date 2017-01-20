package lic.swifter.box.data;

import lic.swifter.box.R;

/**
 * Created by cheng on 2016/8/16.
 */
public class ToolsCollection {
    public static ToolData[] tools = new ToolData[]{
            new ToolData(R.drawable.ip_icon, R.string.ip, FragmentsFlag.IpQueryFragment),
            new ToolData(R.drawable.id_inquiry_icon, R.string.id_inquiry, FragmentsFlag.IdQueryFragment),
            new ToolData(R.drawable.mobile_number_attr_icon, R.string.mobile_number_attr, FragmentsFlag.PhoneQueryFragment),
            new ToolData(R.drawable.today_in_history_icon, R.string.today_history, FragmentsFlag.TodayHistoryFragment),
            new ToolData(R.drawable.wx_carefully_chosen_icon, R.string.wx_carefully_chosen, FragmentsFlag.WxChosenFragment),
            new ToolData(R.drawable.qq_number_query_icon, R.string.qq_num_query, FragmentsFlag.QQLuckFragment),
            new ToolData(R.drawable.jokes_collection_icon, R.string.jokes_collection, FragmentsFlag.JokesFragment),
            new ToolData(R.drawable.films_box_office_icon, R.string.films_box_office, FragmentsFlag.MovieRankingFragment),
            new ToolData(R.drawable.top_news_icon, R.string.top_news, FragmentsFlag.TopNewsFragment),
            new ToolData(R.drawable.tv_list_icon, R.string.tv_list_table, FragmentsFlag.TvTableFragment),
            new ToolData(R.drawable.mobile_number_attr_icon,R.string.mobile_info, FragmentsFlag.MobileInfoFragment),
            new ToolData(R.drawable.font_convert_icon, R.string.font_convert, FragmentsFlag.FontConvertFragment),
            new ToolData(R.drawable.baidu_weight_icon, R.string.baidu_weight, FragmentsFlag.BaiduWeightFragment),
            new ToolData(R.drawable.website_security_icon, R.string.website_security, FragmentsFlag.WebsiteSecurityFragment)
    };
}
