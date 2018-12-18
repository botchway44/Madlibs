package botchway.macrocodes.madlibs;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.SimpleDialog;

public class SettingsActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        /*Action bar support settings*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_settings);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo2);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(StoriesListActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : startActivity(StoriesListActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    public void developerClick(View view) {
        SimpleDialog.with(this).setDialogsTitle("Macro Codes Lab ");
        SimpleDialog.with(this).setDialogsCancelable(true);
        SimpleDialog.with(this).showAlertDialog("Email us : \n macrocodeslab@gmail.com \n\n " +
                "Join us on Facebook : \n facebook.com/macrocodeslab/");
    }
}
