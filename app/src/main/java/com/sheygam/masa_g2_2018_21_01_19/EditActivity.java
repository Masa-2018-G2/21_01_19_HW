package com.sheygam.masa_g2_2018_21_01_19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputName, inputLastName, inputEmail, inputPhone;
    private Button saveBtn;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        inputName = findViewById(R.id.input_name);
        inputLastName = findViewById(R.id.input_last_name);
        inputEmail = findViewById(R.id.input_email);
        inputPhone = findViewById(R.id.input_phone);
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);
        loadData();
    }

    private void loadData() {
        id = getSharedPreferences("AUTH",MODE_PRIVATE)
                .getString("CURR",null);
        if(id == null){
            getSharedPreferences("AUTH",MODE_PRIVATE)
                    .edit()
                    .remove("CURR")
                    .commit();
            setResult(400);
            finish();
        }else{
            String data = getSharedPreferences("DATA",MODE_PRIVATE)
                    .getString(id,null);
            if(data!=null){
                String[] arr = data.split(",");
                inputName.setText(arr[0]);
                inputLastName.setText(arr[1]);
                inputEmail.setText(arr[2]);
                inputPhone.setText(arr[3]);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.save_btn){
            String name = inputName.getText().toString().trim();
            String lastName = inputLastName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String phone = inputPhone.getText().toString().trim();
            if(validator(name,lastName,email,phone)){
                String data = name + "," + lastName + "," + email + "," + phone;
                getSharedPreferences("DATA",MODE_PRIVATE)
                        .edit()
                        .putString(id, data)
                        .commit();
                Intent intent = new Intent();
                intent.putExtra("NAME",name);
                intent.putExtra("LAST_NAME",lastName);
                intent.putExtra("EMAIL",email);
                intent.putExtra("PHONE",phone);
                setResult(RESULT_OK,intent);
                finish();
            }else{
                Toast.makeText(this, "All fields need by fill!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean validator(String name, String lastName, String email, String phone){
        return !name.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !phone.isEmpty();
    }
}
