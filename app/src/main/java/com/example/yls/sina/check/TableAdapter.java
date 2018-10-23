package com.example.yls.sina.check;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yls.sina.R;

import java.util.List;

//构造BaseAdapter
public class TableAdapter extends BaseAdapter {

    private List<Check> list;
    private LayoutInflater inflater;

    public TableAdapter(Context context, List<Check> list) {
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

        Check check = (Check) this.getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder.check_month = (TextView) convertView.findViewById(R.id.text_month);
            viewHolder.check_has_tag = (TextView) convertView.findViewById(R.id.text_has_tag);
            viewHolder.check_no_tag = (TextView) convertView.findViewById(R.id.text_no_tag);
            viewHolder.check_all_count = (TextView) convertView.findViewById(R.id.text_count);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.check_month.setText(check.getMonth());
        viewHolder.check_month.setTextSize(13);
        viewHolder.check_has_tag.setText(check.getHas_tag()+"");
        viewHolder.check_has_tag.setTextSize(13);
        viewHolder.check_no_tag.setText(check.getNo_tag()+"");
        viewHolder.check_no_tag.setTextSize(13);
        viewHolder.check_all_count.setText(check.getAll_count() + "");
        viewHolder.check_all_count.setTextSize(13);

        return convertView;
    }

    public static class ViewHolder {
        public TextView check_month;
        public TextView check_has_tag;
        public TextView check_no_tag;
        public TextView check_all_count;
    }
}