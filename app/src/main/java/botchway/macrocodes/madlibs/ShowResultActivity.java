package botchway.macrocodes.madlibs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.SimpleDialog;
import stanford.androidlib.data.SimpleDatabase;
import stanford.androidlib.data.SimpleRow;

public class ShowResultActivity extends SimpleActivity implements savedStoriesDatabase{
   private String name = "";
   private String story = "";
    private String title ;
    private String generated;
    private boolean wantsToSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

         title = getExtra("title");
         generated = getExtra("generated");
        findButton(R.id.saveStory).setVisibility(View.VISIBLE);
        log(title);
        log(generated);
        wantsToSave = false;
         /*add the back button to the action bar*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo2);

        findTextView(R.id.storyResult).setText(generated);
        findTextView(R.id.storyResult).setTextSize(15);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : startActivity(StoriesListActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    public void newStory(View view) {
        startActivity(StoriesListActivity.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(StoriesListActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }


    public void saveToStory(View view) {
        checkForDatabase(); //check if db exist
        makeDialog();   //make dialog to extract name
        }

    public void addInfoToDB(){
    SQLiteDatabase db = SimpleDatabase.with(this).open(DBName); //creates database
    String query = "INSERT INTO "+tableName+"( "+listNameCol+", "+storyCol+") "+
            "VALUES ( '"+name+"' , '"+ story+"')";
    if(wantsToSave) {
        db.execSQL(query);
        toast("story saved");
    }
    }


    public void checkForDatabase(){
       if(!SimpleDatabase.with(this).exists(DBName) ){
           SQLiteDatabase db = SimpleDatabase.with(this).create(DBName); //creates database
           db.execSQL(createTable); //creates table for the database
           log("created a new database for the app");
            }
       log("out of database so maybe didnt create tables");


    }

    public void makeDialog(){
        SimpleDialog.with(this).setDialogsCancelable(true);
        SimpleDialog.with(this).showInputDialog("Enter Name");

    }

    @Override
    public void onInputDialogClose(AlertDialog dialog, String input) {
        super.onInputDialogClose(dialog, input);
        name = input+"("+title+")";
        story = generated;
        wantsToSave = true;
        log("name is "+name + "and wants to save : "+ wantsToSave);
        addInfoToDB();

    }

    @Override
    public void onDialogCancel(DialogInterface dialog) {
        super.onDialogCancel(dialog);
        wantsToSave = false;
        toast("cant save story");
    }
}
