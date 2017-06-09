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
 * @author yun.
 * @date 2015/10/22.
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 */
public class FilterAdapter extends ArrayAdapter<String> {
    private final int mItemResid;
    private final int mItemLayoutRes;
    private List<String> mDataSource = new ArrayList<String>();
    private List<String> mMatchData = new ArrayList<String>();
    private final LayoutInflater mInflater;
    private String search = "";
    private CharacterParser mParser;
    private boolean partMatch;

    public FilterAdapter(Context context, int resource, int textViewResourceId, List dataSource){
        super(context, resource, textViewResourceId, dataSource);
        //集合中 addAll和= 的区别
        mDataSource = dataSource;
        mMatchData.addAll(dataSource);
        mItemResid = textViewResourceId;
        mInflater = LayoutInflater.from(context);
        this.mItemLayoutRes = resource;
        mParser = CharacterParser.getInstance();
    }

    @Override
    public int getCount(){
        return mMatchData.size();
    }

    @Override
    public int getPosition(String item){
        return mMatchData.indexOf(item);
    }

    @Override
    public String getItem(int position){
        return mMatchData.get(position);
    }

    @Override
    public Filter getFilter(){
        return new JFilter();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = null;
        TextView text;
        if(convertView == null) {
            view = mInflater.inflate(mItemLayoutRes, parent, false);
        }else {
            view = convertView;
        }

        try {
            text = (TextView)view.findViewById(mItemResid);
            mParser.setHighlight(text, getItem(position), search, "#6aa5e7",partMatch);
        }catch(Exception e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
        }
        return view;
    }

    public void setPartMatch(boolean partMatch){
        this.partMatch = partMatch;
    }

    public class JFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix){

            FilterResults results = new FilterResults();
            if(mDataSource.size()>0) {
                if(prefix == null || prefix.length() == 0) {
                    ArrayList<String> list;
                    list = new ArrayList<String>(mDataSource);
                    results.values = list;
                    results.count = list.size();
                }else {
                    search = prefix.toString();
                    final ArrayList<String> newValues = new ArrayList<String>();
                    for(String history : mDataSource) {
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
                mMatchData.clear();
                mMatchData = (List<String>)results.values;
                notifyDataSetChanged();
            }else {
                notifyDataSetInvalidated();
            }
        }
    }
}