package com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<ListItem> {

    private List<ListItem> listItems;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, int resource, List<ListItem> objects) {
        super(context, resource, objects);
        listItems = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    /*
    public Object getItem(int position) {
        return null;
    }*/

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // @TODO view
        convertView = inflater.inflate(R.layout.custom_list_element_main,null);

        View view = convertView;
        ViewHolder holder;

        if(view.getTag() == null){
            view = inflater.inflate(R.layout.custom_list_element_main,null);
            holder = new ViewHolder((ImageView) view.findViewById(R.id.listAppIcon),(TextView) view.findViewById(R.id.listAppName));
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if(params == null){
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);
            }
            view.setLayoutParams(params);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        ListItem item = listItems.get(position);
        Log.e("CHECK",listItems.get(position).getName()+"");
        holder.setDataIntoViewHolder(item);


        return view;
    }

    private ArrayList<ListItem> clone(ArrayList<ListItem> items){
        ArrayList<ListItem> temp = new ArrayList<ListItem>();



        for(ListItem i: items){
            temp.add(i.clone());
        }

        return temp;
    }
}
