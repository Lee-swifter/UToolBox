package lic.swifter.box.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import lic.swifter.box.R;
import lic.swifter.box.api.model.IdResult;
import lic.swifter.box.util.ViewUtil;

/**
 * Created by cheng on 2016/8/19.
 */

public class IdResultHolder extends RecyclerView.ViewHolder {

    TextView idNumberText;
    TextView resultArea;
    TextView resultSex;
    TextView resultBirthday;

    public IdResultHolder(View itemView) {
        super(itemView);
        idNumberText = itemView.findViewById(R.id.item_id_number);
        resultArea = itemView.findViewById(R.id.item_id_result_area);
        resultSex = itemView.findViewById(R.id.item_id_result_sex);
        resultBirthday = itemView.findViewById(R.id.item_id_result_birthday);

        ViewUtil.waveView(itemView);
    }

    public void setData(IdResult data) {
        idNumberText.setText(data.idNumber);
        resultArea.setText(data.area);
        resultSex.setText(data.sex);
        resultBirthday.setText(data.birthday);
    }
}
