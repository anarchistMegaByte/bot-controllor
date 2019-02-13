package com.dell.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends Activity {

    EditText e1, e2;
    Button button;
    String str1, str2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        e1 = findViewById(R.id.uid);
        e2 = findViewById(R.id.pwd);
        e1.setText("Revathi");
        e2.setText("Revathi");
        button = findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Testing", "Button Clicked");
                str1 = e1.getText().toString();
                str2 = e2.getText().toString();

                if (TextUtils.isEmpty(str1)) {
                    e1.setError("Please enter your User ID!");
                    e1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(str2)) {
                    e2.setError("Please enter your Password!");
                    e2.requestFocus();
                    return;
                }
                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                if ((str1.equals("Khushboo"))&&(str2.equals("Khushboo")))
                {
                    startActivity(intent);
                    clearText();
                }else if ((str1.equals("Revathi"))&&(str2.equals("Revathi"))){
                    startActivity(intent);
                    clearText();
                }else if ((str1.equals("Prathamesh"))&&(str2.equals("Prathamesh"))){
                    startActivity(intent);
                    clearText();
                }else{
                    Toast.makeText(getApplicationContext(), "Entered User ID and Password are not valid!!", Toast.LENGTH_LONG).show();
                    clearText();
                }
            }
        });
    }
    private void clearText(){
        e1.setText("");
        e2.setText("");
    }
}
