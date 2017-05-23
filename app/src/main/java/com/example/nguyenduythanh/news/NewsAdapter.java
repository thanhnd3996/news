package com.example.nguyenduythanh.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nguyen Duy Thanh on 22/05/2017.
 * Chuyển dữ liệu parser từ file RSS hiển thị lên listview
 */

public class NewsAdapter extends ArrayAdapter<News> {

    NewsAdapter(Context context, int resource, List<News> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.news_list_view, null);
        }
        News p = getItem(position);
        if (p != null) {
            TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            textViewTitle.setText(p.getTitle());

            TextView textViewPubDate = (TextView) view.findViewById(R.id.textViewPubDate);
            textViewPubDate.setText(p.getPubDate());

            ImageView imgView = (ImageView) view.findViewById(R.id.imageView);
            Picasso.with(getContext()).load(p.getImage()).into(imgView);
        }
        return view;
    }
}