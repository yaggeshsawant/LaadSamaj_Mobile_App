package com.madsssoft.laadsamaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LauncherActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    // Hooks
    View first, second, third, fourth, fifth, sixth;
    TextView slogan;
    ImageView a;
    // Animations
    Context context;
    Animation topAnimation, bottomAnimation, middleAnimation;
    SessionManager sessionManager = null;
    RetrofitApiInterface retrofitApiInterface;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_launcher);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_clr));
        //Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();

        retrofitApiInterface = RetrofitApiClient.getApiClient(getApplicationContext()).create(RetrofitApiInterface.class);
        sessionManager = new SessionManager(LauncherActivity.this);

        // Hooks
  /*      first = findViewById(R.id.first_line);
        second = findViewById(R.id.second_line);
        third = findViewById(R.id.third_line);
        fourth = findViewById(R.id.fourth_line);
        fifth = findViewById(R.id.fifth_line);
        sixth = findViewById(R.id.sixth_line);*/
        a = findViewById(R.id.a);
        mediaPlayer = MediaPlayer.create(this, R.raw.splash_sound);
        slogan = findViewById(R.id.tagLine);

        // Animation Calls
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        // Setting Animations to the elements of Splash Screen
/*        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);*/
        a.setAnimation(middleAnimation);
        slogan.setAnimation(bottomAnimation);

        // Splash Screen Code to call new Activity after some time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mediaPlayer.start();
                checkLogin();

            }
        }, SPLASH_TIME_OUT);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    private void checkLogin() {
        if (sessionManager.getRegId() != 0) {
            //Toast.makeText(context, ""+sessionManager.getRegId(), Toast.LENGTH_SHORT).show();
            retrofitApiInterface.getMemberInfoById(sessionManager.getRegId()).enqueue(new Callback<PojoMemberInfo>() {
                @Override
                public void onResponse(Call<PojoMemberInfo> call, Response<PojoMemberInfo> response) {
                    if (response.isSuccessful()) {
                        //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        PojoMemberInfo list = response.body();
                        if (list.getId() != 0) {
                            changeIntent(DashBoardActivity.class, null);
                        } else {
                            changeIntent(ActivityLogin.class, null);
                        }
                    } else {
                        changeIntent(ActivityLogin.class, null);
                    }

                }

                @Override
                public void onFailure(Call<PojoMemberInfo> call, Throwable t) {
                    Toast.makeText(context, "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                    changeIntent(ActivityLogin.class, null);
                }
            });

        } else {
            changeIntent(ActivityLogin.class, null);
        }
    }

    public void changeIntent(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(LauncherActivity.this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        startActivity(intent);
        finish();
    }


}
