package com.test.cermati.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.test.cermati.R;
import com.test.cermati.model.ItemModel;
import com.test.cermati.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;

public class MainActivity extends BaseActivity implements MainView {
	@Inject
	MainPresenter presenter;

	@BindView(R.id.txt_search)
	EditText txt_search;

	@BindView(R.id.lst_repos)
	RecyclerView lst_repos;

	@BindView(R.id.lbl_message)
	TextView lbl_message;

	@OnEditorAction(R.id.txt_search)
	boolean search(int actionId) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			search = txt_search.getText().toString();

			if (!search.trim().equals("")) {
				max = 0;
				page = 1;

				setRequest();
			}
			return true;
		}

		return false;
	}

	private MainAdapter mainAdapter;
	private List<Object> itemList = new ArrayList<>();
	private String search;
	private int max = 0;
	private int page = 1;
	private boolean request = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		component().inject(this);

		setContentView(R.layout.activity_main);

		presenter.attachView(this, getContext());

		ButterKnife.bind(this);

		setInitial();
	}

	@Override
	protected void onDestroy() {
		presenter.detachView();
		super.onDestroy();
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void showData(int total_count, List<ItemModel> itemModelList) {
		if (max == 0) {
			itemList.clear();
			max = total_count;
			setMessage("");
			itemList.addAll(itemModelList);
			mainAdapter.notifyDataSetChanged();
		} else {
			itemList.remove(itemList.size() - 1);
			mainAdapter.notifyItemRemoved(itemList.size());
			itemList.addAll(itemModelList);
			mainAdapter.notifyDataSetChanged();
			mainAdapter.setLoaded();
		}

		request = false;
	}

	@Override
	public void getUsersFailed(String message) {
		if (max == 0) {
			itemList.clear();
			setMessage(message);
			mainAdapter.notifyDataSetChanged();
		} else {
			removeLoadMore();
		}

		request = false;
	}

	private void setInitial() {
		lst_repos.setHasFixedSize(true);
		lst_repos.setLayoutManager(new LinearLayoutManager(this));
		mainAdapter = new MainAdapter(this, itemList, usersModel -> {
		});
		mainAdapter.setOnLoadMoreListener(() -> {
			itemList.add(null);
			mainAdapter.notifyItemInserted(itemList.size() - 1);
			setRequest();
		});
		mainAdapter.setScroll(lst_repos);
		lst_repos.setAdapter(mainAdapter);

		txt_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				final String text = txt_search.getText().toString();

				if (!text.equals("")) {
					search = text;

					max = 0;
					page = 1;

					setRequest();
				}
			}
		});
	}

	private void setRequest() {
		if (request) {
			return;
		}

		if (max == 0) {
		} else {
			if ((page * 10) > max) {
				itemList.remove(itemList.size() - 1);
				mainAdapter.notifyItemRemoved(itemList.size());
				mainAdapter.setLoaded();
				return;
			} else {
				page++;
			}
		}

		request = true;

		presenter.getRepos(search, page, 10);
	}

	private void removeLoadMore() {
		itemList.remove(itemList.size() - 1);
		mainAdapter.notifyItemRemoved(itemList.size());
		mainAdapter.notifyDataSetChanged();
		mainAdapter.setLoaded();
	}

	private void setMessage(final String message) {
		if (message.equals("")) {
			lst_repos.setVisibility(View.VISIBLE);
			lbl_message.setVisibility(View.GONE);
			lbl_message.setText(message);
		} else {
			lst_repos.setVisibility(View.GONE);
			lbl_message.setVisibility(View.VISIBLE);
			lbl_message.setText(message);
		}
	}
}