package com.example.healthcertification.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthcertification.R;

public class Login_FirstPage extends AppCompatActivity implements View.OnClickListener {
    ImageView Start_Btn;
    TextView SignUp_text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        Start_Btn = (ImageView)findViewById(R.id.start_btn);
        Start_Btn.setOnClickListener(this);
        SignUp_text = (TextView)findViewById(R.id.firstpage_signup);
        SignUp_text.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.start_btn:
                Intent intent = new Intent(Login_FirstPage.this, Login_Login.class);
                startActivity(intent);
                break;
            case R.id.firstpage_signup:
                Intent intent1 = new Intent(Login_FirstPage.this, Login_SignUp.class);
                startActivity(intent1);
                break;
        }
    }
}
