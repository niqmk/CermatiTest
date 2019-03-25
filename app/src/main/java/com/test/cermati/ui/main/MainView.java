package com.test.cermati.ui.main;

import com.test.cermati.model.ItemModel;
import com.test.cermati.ui.base.BaseView;

import java.util.List;

public interface MainView extends BaseView {
    void showData(int total_count, List<ItemModel> itemModelList);

    void getUsersFailed(String message);
}