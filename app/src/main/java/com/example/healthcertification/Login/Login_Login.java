package com.example.healthcertification.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcertification.MainActivity;
import com.example.healthcertification.R;

public class Login_Login extends AppCompatActivity implements View.OnClickListener  {

    ImageView Login_Btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login_Btn = (ImageView)findViewById(R.id.login_btn);
        Login_Btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.login_btn:
                Intent intent = new Intent(Login_Login.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
