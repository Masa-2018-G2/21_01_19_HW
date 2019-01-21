package com.sheygam.masa_g2_2018_21_01_19;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputEmail, inputPassword;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String curr = getSharedPreferences("AUTH",MODE_PRIVATE)
                .getString("CURR",null);
        if(curr!=null){
            showNextActivity();
        }
        setContentView(R.layout.activity_main);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }

    private void showNextActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_btn){
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            boolean res = getSharedPreferences("AUTH",MODE_PRIVATE)
                    .edit()
                    .putString("CURR",email + "&" + password)
                    .commit();
            showNextActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != RESULT_OK && requestCode == 1){
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
