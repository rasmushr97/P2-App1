package com.example.rasmus.menucomplete.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rasmus.menucomplete.R;
import com.example.rasmus.menucomplete.models.ShoppingListItemModel;

import java.util.List;

public class ShoppingListAdapter extends BaseAdapter {
    Activity activity;
    List<ShoppingListItemModel> items;
LayoutInflater inflater;


    public ShoppingListAdapter(Activity activity) {
        this.activity = activity;
    }

    public ShoppingListAdapter(Activity activity, List<ShoppingListItemModel> items) {
        this.activity = activity;
        this.items = items;

        inflater = activity.getLayoutInflater();

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null){

            view = inflater.inflate(R.layout.list_view_item_shopping_list, viewGroup, false);

            holder = new ViewHolder();

            holder.tvItemName = (TextView)view.findViewById(R.id.tv_item_name);
            holder.ivCheckbox = (ImageView)view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        ShoppingListItemModel model = items.get(i);

        holder.tvItemName.setText(model.getItemName());

        if(model.isSelected())
            holder.ivCheckbox.setBackgroundResource(R.drawable.checked);

        else
            holder.ivCheckbox.setBackgroundResource(R.drawable.unchecked);

        return view;
    }

    public void updateRecords(List<ShoppingListItemModel> items) {
        this.items = items;

        notifyDataSetChanged();
    }



    class ViewHolder{

        TextView tvItemName;
        ImageView ivCheckbox;
    }
}
