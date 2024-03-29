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
import com.itacit.healthcare.global.utils.AndroidUtils;
import com.itacit.healthcare.presentation.base.widgets.picasso.CircleTransformation;
import com.itacit.healthcare.presentation.messages.models.UserModel;
import com.itacit.healthcare.presentation.messages.presenters.NewMessagePresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 12.11.15.
 */
public class UsersAdapter extends BaseAdapter implements Filterable {
	private final NewMessagePresenter presenter;
	private Context context;
	private Picasso picasso;
	private List<UserModel> users;
	private UserFilter mFilter;

	public UsersAdapter(Context context, List<UserModel> users, NewMessagePresenter presenter) {
		this.context = context;
		this.users = users;
		this.presenter = presenter;
		picasso = AndroidUtils.createPicassoWithAuth(context);
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
				.into(viewHolder.ivIcon);
		viewHolder.tvName.setText(userModel.getFullName());
		String role = userModel.getRole() + ", " + userModel.getBusinessName();
		viewHolder.tvRole.setText(role);
		viewHolder.ivCheck.setImageResource(presenter.isUserSelected(userModel.getId()) ?
				R.drawable.ic_check_act : R.drawable.ic_unselect);
		viewHolder.view.setOnClickListener(v -> {
			presenter.onUserClicked(userModel, viewHolder.ivIcon.getDrawingCache());
			viewHolder.ivCheck.setImageResource(presenter.isUserSelected(userModel.getId()) ?
					R.drawable.ic_check_act : R.drawable.ic_unselect);
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
