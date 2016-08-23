package com.jonas.autocompletesearch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.jonas.autocompletesearch.adapter.FilterAdapter;
import com.jonas.autocompletesearch.utills.CharacterParser;

import java.util.Arrays;

public class ScrollingActivity extends AppCompatActivity implements TextWatcher {

    private EditText etContent;
    private TextView tvShow;
    private AutoCompleteTextView autvSearch;
    private CharacterParser mParser;
    private boolean partMatch;
    private FilterAdapter mAdapter;
    private Snackbar mSnackbar;

    private void assignViews() {
        etContent = (EditText) findViewById(R.id.et_content);
        tvShow = (TextView) findViewById(R.id.tv_show);
        autvSearch = (AutoCompleteTextView) findViewById(R.id.autv_search);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        mSnackbar = Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action22", null);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String showmsg = "拆分匹配";
                if(partMatch) {
                    showmsg = "连续匹配";
                }
                Snackbar.make(view, showmsg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                partMatch = !partMatch;
                mAdapter.setPartMatch(partMatch);
                mParser.setHighlight(tvShow,tvShow.getText().toString(),autvSearch.getText().toString(),"#6aa5e7",partMatch);
            }
        });

        assignViews();
        mParser = CharacterParser.getInstance();

        String[] strings = {"害怕","zg","rg","任何","如果","如真果"};
        mAdapter = new FilterAdapter(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, Arrays.asList(strings));
        autvSearch.setAdapter(mAdapter);
        autvSearch.addTextChangedListener(this);
        //completionThreshold xml属性 默认2 从第几个开始匹配
        autvSearch.setDropDownHeight(500);
        mParser.setHighlight(tvShow, tvShow.getText().toString(), "害怕", "#6aa5e7",partMatch);

        autvSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b){
                autvSearch.showDropDown();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
    }

    @Override
    public void afterTextChanged(Editable editable){
        mParser.setHighlight(tvShow,tvShow.getText().toString(),autvSearch.getText().toString(),"#6aa5e7",partMatch);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
