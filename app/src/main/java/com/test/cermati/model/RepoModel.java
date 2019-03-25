package com.test.cermati.model;

import com.google.gson.annotations.SerializedName;
import com.test.cermati.injection.ActivityScope;

import java.util.List;

@ActivityScope
public class RepoModel {
	@SerializedName("total_count")
	private int total_count;

	@SerializedName("items")
	private List<ItemModel> items;

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public List<ItemModel> getItems() {
		return items;
	}

	public void setItems(List<ItemModel> items) {
		this.items = items;
	}
}