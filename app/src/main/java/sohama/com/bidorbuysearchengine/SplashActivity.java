package sohama.com.bidorbuysearchengine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ProgressBar;

import sohama.com.bidorbuysearchengine.Helpers.DataController;
import sohama.com.bidorbuysearchengine.singleton.SharedClass;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        SharedClass sharedClass = SharedClass.getInstance(this.getApplicationContext());
        sharedClass.setDataController(new DataController());

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.splash_progress_bar);

        new Thread(){
            @Override
            public void run() {
                    int oldProgress = 5;
                    progressBar.setProgress(oldProgress);
                    while(progressBar.getProgress()<100){
                        int value = progressBar.getProgress()+5;
                        progressBar.setProgress(value);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));

            }
        }.start();

    }

}
