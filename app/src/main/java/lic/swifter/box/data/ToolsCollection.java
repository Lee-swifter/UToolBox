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
            new ToolData(R.drawable.wx_carefully_chosen_icon, R.string.wx_carefully_chosen, FragmentsFlag.WxChosenFragment)
    };
}
