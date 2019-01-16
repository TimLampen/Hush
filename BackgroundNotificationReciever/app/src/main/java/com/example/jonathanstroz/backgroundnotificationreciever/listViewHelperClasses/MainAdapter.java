package com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends ArrayAdapter<MainListItem> {

    private List<MainListItem> mainListItems;
    private LayoutInflater inflater;

    public MainAdapter(Context context, int resource, List<MainListItem> objects) {
        super(context, resource, objects);
        mainListItems = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mainListItems.size();
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
        Log.e("XXXX", position+"");
        // @TODO view
        convertView = inflater.inflate(R.layout.custom_list_element_main,null);

        View view = convertView;
        MainViewHolder holder;
        MainListItem item = mainListItems.get(position);

        if(view.getTag() == null){
            holder = new MainViewHolder((ImageView) view.findViewById(R.id.listAppIcon),(TextView) view.findViewById(R.id.listAppName), 0, item);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if(params == null){
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);
            }
            view.setLayoutParams(params);
            view.setTag(holder);
        }
        else{
            holder = (MainViewHolder) view.getTag();
        }


        Log.e("CHECK", mainListItems.get(position).getName()+"");
        holder.setDataIntoViewHolder(item);


        return view;
    }

    private ArrayList<MainListItem> clone(ArrayList<MainListItem> items){
        ArrayList<MainListItem> temp = new ArrayList<MainListItem>();



        for(MainListItem i: items){
            temp.add(i.clone());
        }

        return temp;
    }
}
