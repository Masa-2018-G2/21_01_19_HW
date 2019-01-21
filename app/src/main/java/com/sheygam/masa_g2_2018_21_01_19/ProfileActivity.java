package com.sheygam.masa_g2_2018_21_01_19;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nameTxt, lastNameTxt, emailTxt, phoneTxt;
    private Button logoutBtn, editBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameTxt = findViewById(R.id.name_txt);
        lastNameTxt = findViewById(R.id.last_name_txt);
        emailTxt = findViewById(R.id.email_txt);
        phoneTxt = findViewById(R.id.phone_txt);
        logoutBtn = findViewById(R.id.logout_btn);
        editBtn = findViewById(R.id.edit_btn);
        logoutBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);
        loadData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout_btn){
            logout();
        }else if(v.getId() == R.id.edit_btn){
            Intent intent = new Intent(this,EditActivity.class);
            startActivityForResult(intent,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == 1){
            nameTxt.setText(data.getStringExtra("NAME"));
            lastNameTxt.setText(data.getStringExtra("LAST_NAME"));
            emailTxt.setText(data.getStringExtra("EMAIL"));
            phoneTxt.setText(data.getStringExtra("PHONE"));
        }else if(resultCode == 400 && requestCode == 1){
            setResult(RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadData(){
        String id = getSharedPreferences("AUTH",MODE_PRIVATE)
                .getString("CURR",null);
        if(id == null){
            logout();
        }else{
            String data = getSharedPreferences("DATA",MODE_PRIVATE)
                    .getString(id,null);
            if(data != null){
                String[] arr = data.split(",");
                nameTxt.setText(arr[0]);
                lastNameTxt.setText(arr[1]);
                emailTxt.setText(arr[2]);
                phoneTxt.setText(arr[3]);
            }
        }
    }

    private void logout() {
        getSharedPreferences("AUTH", MODE_PRIVATE)
                .edit()
                .remove("CURR")
                .commit();
        setResult(RESULT_OK);
        finish();
    }
}
