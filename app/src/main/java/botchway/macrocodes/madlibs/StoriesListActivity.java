package botchway.macrocodes.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.SimpleList;

public class StoriesListActivity extends SimpleActivity {

    private ArrayList<String> names;
    private int itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTraceLifecycle(true);
        setContentView(R.layout.activity_stories_list);

        /*add the back button to the action bar*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo2);

        /*init the container for the list*/
        names = new ArrayList<>();
        itemSelected = 0;

        /*extract offline stories from file*/
        extractStories(names);

        ListView list = find(R.id.list);
       SimpleList.with(this).setItems(list,names);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(ListView list, int index) {
        super.onItemClick(list, index);
            log("index clicked is " +index);
            itemSelected = index;
        String title = names.get(index);
       startActivity(InputAnswersActivity.class,"index", itemSelected,"title",title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();   // reads XML
        inflater.inflate(R.menu.menu_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case android.R.id.home : startActivity(MenuActivity.class); break;
                case R.id.settings : startActivity(SettingsActivity.class); break;
            }
        return super.onOptionsItemSelected(item);
    }

    /*extracts titles and filenames from file*/
    public void extractStories(ArrayList<String> names){
        int index = 0;
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.games));
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            String[] parts = line.split("   ");
            if(parts.length >=2){
                String title = parts[0].substring(1, (parts[0].length())-1);
                String rep = parts[1].substring(1, (parts[1].length())-1);

                names.add(title);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           startActivity(MenuActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }


    public void newStory(View view) {
        startActivity(SavedStoriesActivity.class);
    }
}
