package com.eleontech.galleryapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Integer[] pics =  {R.drawable.ic_avto_ur,
                                R.drawable.ic_brush,
                                R.drawable.ic_cam,
                                R.drawable.ic_games,
                                R.drawable.ic_knjiga,
                                R.drawable.ic_oseben,
                                R.drawable.ic_recycling_bin,
                                R.drawable.ic_robot};

    private ImageView imageView;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gallery gallery = (Gallery) findViewById(R.id.gallery1);

         //Create an adapter
        gallery.setAdapter(new ImageAdapter(this)); //setting adapter for gallery and calling BaseAdapter class thru ImageAdater class
        imageView = (ImageView) findViewById(R.id.imageView1);//Init imageView
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() { //Make the gallery to respond when clicked
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Toast.makeText(getApplicationContext(), "pic: " + arg2, Toast.LENGTH_LONG).show();
                imageView.setImageResource(pics[arg2]);
                String speakPic = String.valueOf(pics[arg2]); //Convert pics position from int to string

                //How do I make the app say  the name of photo that is clicked???????????????????????

                //Call tts speak method and pass in the converted pic position
                textToSpeech.speak(" Hello, you just clicked picture " + speakPic, TextToSpeech.QUEUE_FLUSH, null);


            }
        });


            //
        //Init textToSpeech class, pass in a listener and set its properties/structures
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {//You can also say if(status != testToSpeech.ERROR)
                    //tts structure
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setPitch(13/10);
                    textToSpeech.setSpeechRate(10/10);

                }else{

                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();

                }
            }

        });

    }


    public class ImageAdapter extends BaseAdapter {
        private Context mcontext;
        int imageBackground;

        public  ImageAdapter(Context context){
            this.mcontext = context;

        }

        @Override
        public int getCount() {
            return pics.length;
        }

        @Override
        public Object getItem(int arg0) {
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ImageView imageView = new ImageView(mcontext);
            imageView.setImageResource(pics[arg0]);

            return imageView;
        }
    }


    public void onPause(){
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onPause();
    }

}
