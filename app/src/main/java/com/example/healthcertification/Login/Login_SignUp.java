package com.example.healthcertification.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthcertification.R;

public class Login_SignUp extends AppCompatActivity implements View.OnClickListener {

    ImageView SignUp_Btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SignUp_Btn = (ImageView) findViewById(R.id.signup_btn);
        SignUp_Btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_btn:
                Intent intent = new Intent(Login_SignUp.this, Login_Login.class);
                startActivity(intent);
                break;
        }
    }
}
