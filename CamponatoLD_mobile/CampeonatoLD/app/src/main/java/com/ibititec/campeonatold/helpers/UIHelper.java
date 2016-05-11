package com.ibititec.campeonatold.helpers;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ibititec.campeonatold.MainActivity;

/**
 * Created by pedro on 11/02/16.
 */
public class UIHelper {
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        try {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
                Log.i(MainActivity.TAG, " Contador do setListViewAltura LisT" + i + " - TOTAL ITEM LISTA" + listAdapter.getCount());

            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            Log.i(MainActivity.TAG, " TOTAL ALTURA: " + totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)));

            listView.setLayoutParams(params);
            listView.requestLayout();
            Log.i(MainActivity.TAG, " Após ListLayouParams, total de itens listView: " + listView.getCount());

        }catch (Exception e){
            Log.i(MainActivity.TAG, "Erro - setListViewHeightBasedOnChildren: " + e.getMessage());

        }
    }
}
