package com.example.yls.sina.classification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yls.sina.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<PicEntity> list = new ArrayList<>();
    private LayoutInflater inflater;

    public MyAdapter(Context context,List<PicEntity> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null) {
            ret = list.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PicEntity picEntity = (PicEntity) this.getItem(position) ;
        Viewholder viewHolder;

        if (convertView == null) {
            viewHolder = new Viewholder();
            convertView = inflater.inflate(R.layout.gridview_item, null);
            viewHolder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tx_id = (TextView) convertView.findViewById(R.id.iv_id);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) convertView.getTag();
        }
        viewHolder.iv_pic.setImageResource(picEntity.getImageId());
        viewHolder.tv_title.setText(picEntity.getTitle());
        viewHolder.tx_id.setText(picEntity.getTextId());

        return convertView;
    }
    public static class Viewholder {
        public TextView tv_title;
        public ImageView iv_pic;
        public TextView tx_id;
    }
}


