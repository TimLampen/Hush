package com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses;

import android.support.constraint.ConstraintLayout;
import android.widget.ListView;

public class MainColorHolder {


    private ListView mainListView;
    private ConstraintLayout layoutView;

    public MainColorHolder(ListView mainList, ConstraintLayout layoutView){
        this.mainListView = mainList;
        this.layoutView = layoutView;
    }

    public ConstraintLayout getLayoutView() {
        return layoutView;
    }

    public ListView getMainListView() {
        return mainListView;
    }
}
