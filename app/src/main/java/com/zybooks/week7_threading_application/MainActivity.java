package com.zybooks.week7_threading_application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressbar);
        mTv = (TextView) findViewById(R.id.smryTv);

        mTv.setOnClickListener(this::enableAlert);
        DownloadTask dt = new DownloadTask();
      //  dt.execute("https://google.com", //create object of async task, execute on these values
      //          "Https://wikipidia.com",
         //       "http://www.farmingdale.edu");

    }

    private void enableAlert(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Tino Gerrato");
        alert.setPositiveButton("Yes", (v,a) ->{
            Toast.makeText(MainActivity.this, "You clcked yes on the alert",
            Toast.LENGTH_LONG).show();
                });
        alert.setNegativeButton("No", (v,a) ->{
            Toast.makeText(MainActivity.this, "You clcked no on the alert",
                    Toast.LENGTH_LONG).show();
        });


        alert.create().show();
       // AlertDialog alt= alert.create();
       // alt.show();
    }

    private boolean downloadURL(String URL){
        try{
            Thread.sleep(100);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return true;
    }

    class DownloadTask extends AsyncTask<String, Integer, Integer>{ //template, have to pass object
        //int is primitive, can't pass
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setProgress(0);
        }

        @Override //in the background, sends an update and catch it on progressupdate, publish
        protected Integer doInBackground(String... url) { //... variable length array
            int  downloadSuccess = 0;
            for(int i=0; i<url.length; i++){
                if(downloadURL(url[i])){
                    downloadSuccess++;
                }
                publishProgress((i+1)*100/url.length);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }



        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mTv.setText("Done " + integer);
            if (integer>2) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        }
    }
}