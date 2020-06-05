package com.example.acciofitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {
    EditText userName;
    EditText userEmail;
    EditText userPwd;
    EditText userConPwd;
    EditText userContact;
    String name,email,pwd,contact,bdate,userBdate,conPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userName = findViewById(R.id.txtName);
        userEmail = findViewById(R.id.txtEmail);
        userPwd = findViewById(R.id.txtPwd);
        userContact = findViewById(R.id.txtContact);
        userConPwd = findViewById(R.id.txtConPwd);
        TextView login = findViewById(R.id.lnkLogin);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void doRegister(View v)
    {
        final DatePicker simpleDatePicker = findViewById(R.id.simpleDatePicker);
        String day = ""+simpleDatePicker.getDayOfMonth();
        String month = "" + (simpleDatePicker.getMonth() + 1);
        String year = "" + simpleDatePicker.getYear();
        userBdate = year+"-"+month+"-"+day;
        name=userName.getText().toString();
        email=userEmail.getText().toString();
        pwd=userPwd.getText().toString();
        contact=userContact.getText().toString();
        conPwd=userConPwd.getText().toString();
        bdate=userBdate;
        new AsyncTaskWork(this).execute("Register",name,email,pwd,contact,bdate,conPwd);
    }
}
