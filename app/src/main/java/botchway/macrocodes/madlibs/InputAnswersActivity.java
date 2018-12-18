package botchway.macrocodes.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Scanner;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.util.RandomGenerator;

public class InputAnswersActivity extends SimpleActivity {

    private ArrayList placeHolders;
    private ArrayList answers;
    private String generated;
    private String title;
    private Story mad;
    private int numberOfPlaceHolders;
    private int counter;

    String[] responses = {"Yeah your doing it", "that's alright", "almost there","Keep going"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTraceLifecycle(true);
        setContentView(R.layout.activity_input_answers);

        generated ="";

        int index = getExtra("index");
        title = getExtra("title");
        log("title is "+title);

        log("index is "+ index);
        readFileForInfo(index);

        placeHolders = mad.getPlaceHolderList();
        answers = new ArrayList<>();
        numberOfPlaceHolders = placeHolders.size();
        counter = 0;
        initAll();
    }

    private void readFileForInfo(int index){

      if(index == 0){
          Scanner scan = new Scanner(getResources().openRawResource(R.raw.romeoandjuliet));
          mad = new Story(scan);
      }

        if(index == 1){
            Scanner scan = new Scanner(getResources().openRawResource(R.raw.tarzan));
            mad = new Story(scan);
        }

        if(index == 2){
            Scanner scan = new Scanner(getResources().openRawResource(R.raw.canihaveyourdaughtershand));
            mad = new Story(scan);
        }

        if(index == 3){
            Scanner scan = new Scanner(getResources().openRawResource(R.raw.wonderwoman));
            mad = new Story(scan);
        }

        if(index == 4){
            Scanner scan = new Scanner(getResources().openRawResource(R.raw.happybirthday));
            mad = new Story(scan);
        }

        if(index == 5){
            Scanner scan = new Scanner(getResources().openRawResource(R.raw.makemeproud));
            mad = new Story(scan);
        }

        if(index == 6){
            Scanner scan = new Scanner(getResources().openRawResource(R.raw.test));
            mad = new Story(scan);
        }

    }

    public void OKClick(View view) {
        String input = findEditText(R.id.wordInput).getText().toString();
        if(input.length() < 1){
            toast("enter the correct info");
        }else{
            int index = RandomGenerator.getInstance().nextInt(0,3);
            toast(responses[index]);
            answers.add(input);
            numberOfPlaceHolders--;
            counter++;

        }
        log("counter size is "+counter);
        log("counter size is "+(placeHolders.size()));

        if(counter == placeHolders.size()){
            toast("done");
            mad.setListToMap(answers);
           generated=mad.toString();
            log(mad.toString());
            startActivity(ShowResultActivity.class,"title",title,"generated",generated);
        }else {
            findTextView(R.id.wordsLeft).setText("word(s) left : "+numberOfPlaceHolders);
            findTextView(R.id.placeholder).setText("enter a " + placeHolders.get(counter));
        }

        findEditText(R.id.wordInput).setText("");

    }

    private void initAll(){
        findTextView(R.id.wordsLeft).setText("word(s) left : "+numberOfPlaceHolders);
        findTextView(R.id.placeholder).setText("enter a " + placeHolders.get(counter));
    }

}
