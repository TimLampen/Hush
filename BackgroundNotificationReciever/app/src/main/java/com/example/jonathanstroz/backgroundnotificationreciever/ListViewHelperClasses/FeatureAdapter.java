package com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity;
import com.example.jonathanstroz.backgroundnotificationreciever.Database.DatabaseHelper;
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
            SeekBar sb = (SeekBar)convertView.findViewById(R.id.featureImportance);
            sb.setTag(item);
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                                              @Override
                                              public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                                              }

                                              @Override
                                              public void onStartTrackingTouch(SeekBar seekBar) {

                                              }

                                              @Override
                                              public void onStopTrackingTouch(SeekBar seekBar) {
                                                  FeatureListItem item = (FeatureListItem) seekBar.getTag();

                                                  if(item.getImportance() != seekBar.getProgress()) {
                                                      MainActivity.mDatabaseHelper.updateFeature(item.getFeatureID(), item.getAppID(), seekBar.getProgress(), item.getName());
                                                      item.setImportance(seekBar.getProgress());
                                                      //seekBar.setTag(item);
                                                  }
                                              }
                                          });

            holder = new FeatureViewHolder((TextView)convertView.findViewById(R.id.featureName), sb, item);
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
