package com.feicui.android.yitaobao.Presentation.Other;


import com.feicui.android.yitaobao.Base.MvpView;
import com.feicui.android.yitaobao.Model.DetailEntry;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/18.
 *
 */
public interface DetailView extends MvpView {

    void setData(DetailEntry data);

    void setImageData(ArrayList<String> list);

    DetailView NULL = new DetailView() {
        @Override
        public void setData(DetailEntry data) {

        }

        @Override
        public void setImageData(ArrayList<String> list) {

        }

    };
}
