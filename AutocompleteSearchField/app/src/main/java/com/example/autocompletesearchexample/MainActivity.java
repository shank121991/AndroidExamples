package com.example.autocompletesearchexample;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the search menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_city, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        // Get SearchView autocomplete object and add a Adapter to it
        final SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.background_light);
        ArrayAdapter<String> cityNamesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.city_names));
        searchAutoComplete.setAdapter(cityNamesAdapter);

        // Listen to searchView item on click and start the search
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String query=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText(query);
                searchForCity(query);
                MenuItemCompat.collapseActionView(searchItem);
            }
        });

        // Configure the search info and add any event listeners
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    MenuItemCompat.collapseActionView(searchItem);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchForCity(query);
                MenuItemCompat.collapseActionView(searchItem);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    private void searchForCity(String searchString){
        mTextView = (TextView)findViewById(R.id.text_view);
        mTextView.setText(searchString + " is selected.");
    }
}
