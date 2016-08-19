package lic.swifter.box.data;

import lic.swifter.box.R;

/**
 * Created by cheng on 2016/8/16.
 */

public class ToolsCollection {
    public static ToolData[] tools = new ToolData[]{
            new ToolData(R.drawable.ip_icon, R.string.ip, FragmentsFlag.IpQueryFragment),
            new ToolData(R.drawable.id_inquiry_icon, R.string.id_inquiry, FragmentsFlag.IdQueryFragment)
    };
}
