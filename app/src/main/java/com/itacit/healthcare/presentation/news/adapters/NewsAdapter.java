package com.itacit.healthcare.presentation.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 26.10.15.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<NewsModel> news;
    private Picasso picasso;
    private OnNewsItemSelectedListener newsItemSelectedListener;

    public NewsAdapter(Context context, List<NewsModel> news) {
        this.context = context;
        this.news = news;
        picasso = new Picasso.Builder(context).build();
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_news, parent, false);
        return new ViewHolder(view, newsItemSelectedListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsModel newsModel = news.get(position);
        picasso.load(newsModel.getHeadlineUri()).into(holder.headlineIv);
        holder.headlineTv.setText(newsModel.getHeadline());
        holder.categoryTv.setText(newsModel.getCategoryName());
        holder.timeTv.setText(newsModel.getStartDate());
    }

    @Override
    public long getItemId(int position) {
        return news.get(position).getArticleId();
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setOnNewsItemSelectedListener(OnNewsItemSelectedListener listener) {
        this.newsItemSelectedListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.iv_headline_ILN)
        ImageView headlineIv;
        @Bind(R.id.tv_headline_ILN)
        TextView headlineTv;
        @Bind(R.id.tv_category_ILN)
        TextView categoryTv;
        @Bind(R.id.tv_time_ILN)
        TextView timeTv;

        private OnNewsItemSelectedListener newsItemSelectedListener;

        public ViewHolder(View itemView, OnNewsItemSelectedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            newsItemSelectedListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (newsItemSelectedListener != null) {
                newsItemSelectedListener.onNewsItemSelected(getItemId());
            }
        }
    }

    public interface OnNewsItemSelectedListener {
        void onNewsItemSelected(long newsId);
    }
}
