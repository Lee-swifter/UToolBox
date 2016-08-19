package lic.swifter.box.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.IdResult;
import lic.swifter.box.util.ViewUtil;

/**
 * Created by cheng on 2016/8/19.
 */

public class IdResultHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_id_number)
    TextView idNumberText;
    @Bind(R.id.item_id_result_area)
    TextView resultArea;
    @Bind(R.id.item_id_result_sex)
    TextView resultSex;
    @Bind(R.id.item_id_result_birthday)
    TextView resultBirthday;

    public IdResultHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        ViewUtil.waveView(itemView);
    }

    public void setData(IdResult data) {
        idNumberText.setText(data.idNumber);
        resultArea.setText(data.area);
        resultSex.setText(data.sex);
        resultBirthday.setText(data.birthday);
    }
}
