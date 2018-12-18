package botchway.macrocodes.madlibs;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ListView;

import java.util.ArrayList;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.SimpleDialog;
import stanford.androidlib.SimpleList;
import stanford.androidlib.data.SimpleDatabase;
import stanford.androidlib.data.SimpleRow;

public class SavedStoriesActivity extends SimpleActivity implements savedStoriesDatabase {
    private   ArrayList<String> listnames ;
    private ArrayList<String> stories;
    private int selectedIndex = 0;
    private  ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_stories);

        listnames = new ArrayList<>();
        stories = new ArrayList<>();

        try {
            getAllColumsFromDB();
        }catch (SQLiteException e){
            log(e);
        }
         list = findListView(R.id.listSavedItems);
        SimpleList.with(this).setItems(list, listnames);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);

        //if arraylist of items is 0 then make a dialog  telling of no stories saved
       if(listnames.size() == 0){
           SimpleDialog.with(this).setDialogsTitle("");
           SimpleDialog.with(this).showAlertDialog("no saved stories");
       }
    }

    @Override
    public void onItemClick(ListView list, int index) {
        super.onItemClick(list, index);
        log("index clicked is " +index);

        String title = listnames.get(index);
        String generated = stories.get(index);
        startActivity(ShowResultActivity.class,"generated", generated,"title",title);
    }

    @Override
    public boolean onItemLongClick(ListView list, int index) {
//            listnames.remove(index);
//            stories.remove(index);
//            toast("removed item fromlist");
//         removeFromDB(index);
        selectedIndex = index;
        makeDialog();

        return super.onItemLongClick(list, index);
    }

    public  void makeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete story");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                removeFromDB(selectedIndex);
                listnames.remove(selectedIndex);
                stories.remove(selectedIndex);
                toast("removed item from list");
                startActivity(StoriesListActivity.class);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void removeFromDB(int index){
        String title = listnames.get(index);
        String story = stories.get(index);

        String query = "DELETE FROM "+tableName+" WHERE "+listNameCol+" = "+ "'"+title+"'";
        SQLiteDatabase db = SimpleDatabase.with(this).open(DBName);
        db.execSQL(query);
    }

    private void getAllColumsFromDB(){
        String query1 = "SELECT * FROM "+tableName;
        for (SimpleRow row : SimpleDatabase.with(this).query(DBName, query1)) {
            String name = row.get(listNameCol);
            String st = row.get(storyCol);
            log("name is "+name + "story is "+st);
            listnames.add(name);
            stories.add(st);

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(StoriesListActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }
}
