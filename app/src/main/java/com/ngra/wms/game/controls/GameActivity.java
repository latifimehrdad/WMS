package com.ngra.wms.game.controls;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.fragments.GameNew;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameActivity extends Activity {


    TextView textView;
    LinearLayout layout;
    GamePanel gamePanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        textView = (TextView) findViewById(R.id.txt);

        layout = (LinearLayout) findViewById(R.id.layout);

        gamePanel = new GamePanel(GameActivity.this, GameActivity.this);

        textView.setVisibility(View.GONE);
        GamePanel.MOVESPEED = 5;
        Player.SpeedScore = 1;
        Player.score = 0;


        textView.setOnClickListener(v -> {
            if(gamePanel != null) {
                layout.removeAllViews();
                gamePanel = null;
            }

            textView.setVisibility(View.GONE);
            GamePanel.MOVESPEED = 5;
            Player.SpeedScore = 1;
            Player.score = 0;
            gamePanel = new GamePanel(GameActivity.this, GameActivity.this);
            layout.addView(gamePanel);
        });

        //setContentView(new GamePanel(this));
    }


    public void ResetGame(Integer coin, Integer score) {
        sendCoin(coin, score);
        //textView.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(gamePanel != null)
            gamePanel.StopGame();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(gamePanel != null)
            gamePanel.StopGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        layout.removeAllViews();
        layout.addView(gamePanel);
        textView.setVisibility(View.GONE);
    }




    private void sendCoin(Integer coin, Integer score) {
        if (coin < 1) {
            onBackPressed();
            return;
        }

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        score = score / 300;

        coin = coin + (score * 5);

        RetrofitComponent component =
                ApplicationWMS.getApplicationWMS(this)
                .getRetrofitComponent();

        Integer finalCoin = coin;
        component
                .getRetrofitApiInterface()
                .submitPoint(coin, RetrofitApis.app_token,aToken,authorization)
                .enqueue(new Callback<ModelResponsePrimary>() {
                    @Override
                    public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                        Toast.makeText(GameActivity.this, "امتیاز " + finalCoin.toString() + " برای شما ثبت شد" , Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                        onBackPressed();
                    }
                });


    }



    //______________________________________________________________________________________________ getAuthorizationTokenFromSharedPreferences
    public String getAuthorizationTokenFromSharedPreferences() {
        String authorization = "Bearer ";
        SharedPreferences prefs = getSharedPreferences(getString(R.string.ML_SharePreferences), 0);
        if (prefs != null) {
            String access_token = prefs.getString(getString(R.string.ML_AccessToken), null);
            if (access_token != null)
                authorization = authorization + access_token;
        }
        return authorization;
    }
    //______________________________________________________________________________________________ getAuthorizationTokenFromSharedPreferences


    //______________________________________________________________________________________________ get_aToken
    public String get_aToken() {
        String aToken = "";
        SharedPreferences prefs = getSharedPreferences(getString(R.string.ML_SharePreferences), 0);
        if (prefs != null) {
            aToken = prefs.getString(getString(R.string.ML_aToken), null);
        }
        return aToken;
    }
    //______________________________________________________________________________________________ get_aToken


}
