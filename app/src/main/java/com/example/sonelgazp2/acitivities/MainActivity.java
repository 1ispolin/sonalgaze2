package com.example.sonelgazp2.acitivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sonelgazp2.R;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       image=findViewById(R.id.image);
       image.animate().alpha(1).setDuration(0);
       Realm.init(this);
       handler=new Handler();
       handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUserAuthenticated()) {
                    Intent intent = new Intent(MainActivity.this, Principal_activity.class);
                    startActivity(intent);
                    finish(); // Finish the sign-in activity
                } else {
                    Intent intent=new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1000);

    }

    private boolean isUserAuthenticated() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = preferences.getString("username", null);
        String token = preferences.getString("token", null);

        // Check if the required authentication data exists
        return username != null && token != null;
    }


}