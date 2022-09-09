package com.example.phamducduy.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phamducduy.R;
import com.example.phamducduy.database.AppDatabase;
import com.example.phamducduy.entity.UserEntity;
import com.example.phamducduy.util.EditTextValidation;

public class MainActivity extends AppCompatActivity {

    EditText edUsername, edEmail, edDescription;
    Spinner spGender;
    Button btnSend;
    CheckBox cbAccept;
    String gender = "Male";
    String[] listGender = {"Male", "Female", "Other"};
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getAppDatabase(this);

        initView();

        setSpSex();

        setBtnSend();
    }

    private void initView() {
        edUsername = findViewById(R.id.edUsername);
        edDescription = findViewById(R.id.edDescription);
        edEmail = findViewById(R.id.edEmail);

        spGender = findViewById(R.id.spGender);

        cbAccept = findViewById(R.id.cbAccept);
        btnSend = findViewById(R.id.btnSend);
    }

    private void setBtnSend() {
        UserEntity user = new UserEntity();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String description = edDescription.getText().toString().trim();
                if (username.isEmpty() || description.isEmpty() || email.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Username and description and email is required", Toast.LENGTH_SHORT).show();
                }else {
                    if (EditTextValidation.isValidUsername(username)) {
                        if (cbAccept.isChecked()) {
                            user.setUsername(username);
                            user.setEmail(email);
                            user.setDescription(description);
                            user.setSex(gender);
                            db.userDao().insertUser(user);
                            Toast.makeText(MainActivity.this,
                                    "Send feedback success and you have "+db.userDao().getAllUser().size() + "record", Toast.LENGTH_SHORT).show();
//                            toListUser();
                        } else
                            Toast.makeText(MainActivity.this,
                                    "Check the checkbox", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(MainActivity.this,
                                "Username's characters > 4 and < 20", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void toListUser() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void setSpSex() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listGender);
        spGender.setAdapter(adapter);
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = listGender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}