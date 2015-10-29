package com.jonas.autocompletesearch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.jonas.autocompletesearch.utills.CharacterParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonas on 2015/10/22.
 */
public class HisAdapter extends ArrayAdapter<String> {
    private final int mFieldId2;
    private final int resource2;
    private List<String> historys = new ArrayList<String>();
    private List<String> matchHis = new ArrayList<String>();
    private final LayoutInflater mInflater;
    private String search = "";
    private CharacterParser mParser;
    private boolean partMatch;

    public HisAdapter(Context context, int resource, int textViewResourceId, List objects){
        super(context, resource, textViewResourceId, objects);
        //集合中 addAll和= 的区别
        historys = objects;
        matchHis.addAll(objects);
        mFieldId2 = textViewResourceId;
        mInflater = LayoutInflater.from(context);
        this.resource2 = resource;
        mParser = CharacterParser.getInstance();
    }

    @Override
    public int getCount(){
        return matchHis.size()>5 ? 5 : matchHis.size();
    }

    @Override
    public int getPosition(String item){
        return matchHis.indexOf(item);
    }

    @Override
    public String getItem(int position){
        return matchHis.get(position);
    }

    @Override
    public Filter getFilter(){
        return new hisFilter();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = null;
        TextView text;
        if(convertView == null) {
            view = mInflater.inflate(resource2, parent, false);
        }else {
            view = convertView;
        }

        try {
            text = (TextView)view.findViewById(mFieldId2);
            mParser.textHighLighting(text, getItem(position), search, "#6aa5e7",partMatch);
        }catch(Exception e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
        }
        return view;
    }

    public void setPartMatch(boolean partMatch){
        this.partMatch = partMatch;
    }

    public class hisFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix){

            FilterResults results = new FilterResults();
            if(historys.size()>0) {
                if(prefix == null || prefix.length() == 0) {
                    ArrayList<String> list;
                    list = new ArrayList<String>(historys);
                    results.values = list;
                    results.count = list.size();
                }else {
                    search = prefix.toString();
                    final ArrayList<String> newValues = new ArrayList<String>();
                    for(String history : historys) {
                        List<String> matchString = mParser.getMatchString(history, search, partMatch);
                        if(matchString.size()>0) {//判断是否能够匹配到
                            newValues.add(history);
                        }
                    }
                    results.values = newValues;
                    results.count = newValues.size();
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
            if(results.values != null && results.count>0) {
                matchHis.clear();
                matchHis = (List<String>)results.values;
                notifyDataSetChanged();
            }else {
                notifyDataSetInvalidated();
            }
        }
    }
}