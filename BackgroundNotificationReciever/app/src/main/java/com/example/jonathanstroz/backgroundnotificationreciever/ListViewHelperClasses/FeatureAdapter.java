package com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.R;

import java.util.List;

/**
 * Used to fill in the features dynamically for each application
 */
public class FeatureAdapter extends ArrayAdapter<FeatureListItem> {

    private LayoutInflater inflater;
    private List<FeatureListItem> featureListItems;

    public FeatureAdapter(Context context, int resource, List<FeatureListItem> objects) {
        super(context, resource, objects);
        featureListItems = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.custom_list_element_features,null);
        FeatureViewHolder holder;
        FeatureListItem item = featureListItems.get(position);



        if(convertView.getTag() == null){// If the list has not been generated

            // generate the list
            ViewGroup.LayoutParams params = convertView.getLayoutParams();
            holder = new FeatureViewHolder((TextView)convertView.findViewById(R.id.featureName), (SeekBar)convertView.findViewById(R.id.featureImportance), item);
            if(params == null){
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,150);
            }

            convertView.setLayoutParams(params);
            convertView.setTag(holder);

        }
        else{ // load the list from the view
            holder = (FeatureViewHolder) convertView.getTag();
        }

        // load the data into the ViewHolder
        holder.setDataIntoViewHolder(item);

        return convertView;
    }

}
