package com.itacit.healthcare.presentation.messages.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.data.network.interceptors.AuthInterceptor;
import com.itacit.healthcare.presentation.base.widgets.picasso.CircleTransformation;
import com.itacit.healthcare.presentation.messages.models.UserModel;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 12.11.15.
 */
public class UsersAdapter extends BaseAdapter implements Filterable {

	private Context context;
	private Picasso picasso;
	private List<UserModel> users;
	private List<String> selectedUsersIds = new ArrayList<>();
	private UserFilter mFilter;
	private OnUsersItemSelectedListener usersItemSelectedListener;

	public UsersAdapter(Context context, List<UserModel> users) {
		this.context = context;
		this.users = users;
		OkHttpClient picassoClient = new OkHttpClient();
		picassoClient.interceptors().add(new AuthInterceptor());
		picasso = new Picasso.Builder(context)
				.downloader(new OkHttpDownloader(picassoClient))
				.build();
	}

	@Override
	public int getCount() {
		return users.size();
	}

	@Override
	public UserModel getItem(int position) {
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<String> getSelectedUsersIds() {
		return selectedUsersIds;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(R.layout.list_item_search_users, parent, false);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) convertView.getTag();
		}

		UserModel userModel = users.get(position);
		picasso.load(userModel.getImageUri())
				.transform(new CircleTransformation())
				.fit()
				.into(viewHolder.ivIcon);
		viewHolder.tvName.setText(userModel.getFullName());
		String role = userModel.getRole() + ", " + userModel.getBusinessName();
		viewHolder.tvRole.setText(role);
		int resFilter;
		if (selectedUsersIds.contains(userModel.getId())) {
			resFilter = R.drawable.ic_check;
		} else {
			resFilter = R.drawable.abc_btn_radio_to_on_mtrl_000;
		}
		viewHolder.ivCheck.setImageResource(resFilter);
		viewHolder.view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isSelected = selectedUsersIds.contains(userModel.getId());

				if (isSelected) {
					selectedUsersIds.remove(userModel.getId());
					usersItemSelectedListener.onUsersDeselected(userModel.getId());
				} else {
					selectedUsersIds.add(userModel.getId());
					usersItemSelectedListener.onUsersSelected(userModel.getId());
				}
			}
		});

		return view;
	}

	@Override
	public Filter getFilter() {

		if (mFilter == null) {
			mFilter = new UserFilter();
		}
		return mFilter;
	}

	public void setOnUsersItemSelectedListener(OnUsersItemSelectedListener listener) {
		this.usersItemSelectedListener = listener;
	}

	public static class ViewHolder {
		@Bind(R.id.iv_icon_LISU)    ImageView ivIcon;
		@Bind(R.id.tv_name_LISU)    TextView tvName;
		@Bind(R.id.tv_role_LISU)    TextView tvRole;
		@Bind(R.id.iv_check_LISU)   ImageView ivCheck;

		public View view;

		ViewHolder(View itemView) {
			ButterKnife.bind(this, itemView);
			view = itemView;
		}
	}

	public interface OnUsersItemSelectedListener {
		void onUsersSelected(String userId);
		void onUsersDeselected(String userId);
	}

	private class UserFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();
			if (prefix == null) {
				return results;
			}
			prefix = prefix.toString().toLowerCase();
			List<UserModel> filteredUsers = new ArrayList<>();
			for (UserModel user : users) {
				if (user.getFullName().toLowerCase().contains(prefix)) {
					filteredUsers.add(user);
				}
			}
			results.count = filteredUsers.size();
			results.values = filteredUsers;
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {

			if (results.count > 0) {
				users = (List<UserModel>) results.values;
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}
}
