package com.test.cermati.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.cermati.R;
import com.test.cermati.model.ItemModel;

import java.util.List;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private final int VIEW_TYPE_ITEM = 0;
	private final int VIEW_TYPE_LOADING = 1;

	public interface OnClickListener {
		void onClick(final ItemModel itemModel);
	}

	public interface OnLoadMoreListener {
		void onLoadMore();
	}

	private FragmentActivity fragmentActivity;
	private OnClickListener listener;
	private List<Object> itemList;
	private OnLoadMoreListener onLoadMoreListener;
	private int visibleThreshold = 5;
	private int lastVisibleItem, totalItemCount;
	private boolean isLoading;

	public MainAdapter(
			final FragmentActivity fragmentActivity,
			final List<Object> itemList,
			final OnClickListener listener) {
		this.fragmentActivity = fragmentActivity;
		this.itemList = itemList;
		this.listener = listener;
	}

	public void setOnLoadMoreListener(final OnLoadMoreListener onLoadMoreListener) {
		this.onLoadMoreListener = onLoadMoreListener;
	}

	public void setLoaded() {
		isLoading = false;
	}

	@Override
	public int getItemViewType(int position) {
		return itemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder viewHolder = null;
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		if (viewType == VIEW_TYPE_ITEM) {
			View view = inflater.inflate(R.layout.list_users, parent, false);
			viewHolder = new MainViewHolder(view);
		} else if (viewType == VIEW_TYPE_LOADING) {
			View view = inflater.inflate(R.layout.list_loading, parent, false);
			viewHolder = new LoadingViewHolder(view);
		}
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof MainViewHolder) {
			MainViewHolder mainViewHolder = (MainViewHolder) holder;
			configure(mainViewHolder, position);
		}
	}

	private void configure(final MainViewHolder mainViewHolder, final int position) {
		final ItemModel itemModel = (ItemModel) itemList.get(position);

		mainViewHolder.lay_main.setOnClickListener(view -> {
			if (listener != null) {
				listener.onClick(itemModel);
			}
		});

		Glide.with(fragmentActivity)
				.load(itemModel.getOwner().getAvatar_url())
				.into(mainViewHolder.img_avatar);

		mainViewHolder.lbl_fullname.setText(itemModel.getName());
	}

	@Override
	public int getItemCount() {
		if (itemList == null) {
			return 0;
		} else {
			return itemList.size();
		}
	}

	public class MainViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.lay_main)
		LinearLayout lay_main;

		@BindView(R.id.img_avatar)
		ImageView img_avatar;

		@BindView(R.id.lbl_fullname)
		TextView lbl_fullname;

		public MainViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}

	public class LoadingViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.pgb_loading)
		ContentLoadingProgressBar pgb_loading;

		public LoadingViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}

	public void setScroll(final RecyclerView recyclerView) {
		if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
			final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
			recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView, dx, dy);
					totalItemCount = linearLayoutManager.getItemCount();
					lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
					if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
						if (onLoadMoreListener != null) {
							onLoadMoreListener.onLoadMore();
						}
						isLoading = true;
					}
				}
			});
		} else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
			final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
			recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView, dx, dy);
					totalItemCount = gridLayoutManager.getItemCount();
					lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
					if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
						if (onLoadMoreListener != null) {
							onLoadMoreListener.onLoadMore();
						}
						isLoading = true;
					}
				}
			});
		}
	}
}