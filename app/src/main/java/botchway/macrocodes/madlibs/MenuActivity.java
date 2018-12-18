package botchway.macrocodes.madlibs;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;

import com.squareup.picasso.Picasso;
import stanford.androidlib.SimpleActivity;

public class MenuActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        loadLogoToView();
    }

    /*load logo into the image view*/
    private void loadLogoToView(){
        Picasso.with(this).load(R.drawable.macro)
                .into(findImageView(R.id.lo));
    }

    public void startGame(View view) {
        startActivity(StoriesListActivity.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
