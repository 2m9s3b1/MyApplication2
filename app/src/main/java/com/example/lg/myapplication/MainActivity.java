package com.example.lg.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Button regist, login, find;
    EditText idtext, pwtext;
    String idstring;
    String pwstring;

    Toolbar tool_bar;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public void loginStart(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        Toast.makeText(getApplicationContext(),"존재하지 않는 id 입니다." ,Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(getApplicationContext(),"비밀번호가 올바르지 않습니다." ,Toast.LENGTH_SHORT).show();
                    } catch (FirebaseNetworkException e) {
                        Toast.makeText(getApplicationContext(),"Firebase NetworkException" ,Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Exception" ,Toast.LENGTH_SHORT).show();
                    }
                } else {
                    currentUser = mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(), "로그인 성공",Toast.LENGTH_SHORT).show(); // 로그인 성공 액티비티 연결하는 코드

                    Intent myIntent = new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(myIntent);
                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        regist = findViewById(R.id.regist);
        login = findViewById(R.id.login);
        find = findViewById(R.id.find);

        idtext = (EditText) findViewById(R.id.idtext);
        pwtext = (EditText) findViewById(R.id.pwtext);

        tool_bar = findViewById(R.id.toolbar);
        setSupportActionBar(tool_bar);
        getSupportActionBar().setTitle("CARPOOL");

        ActionBar actionBar = getSupportActionBar();


        login.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                idstring = idtext.getText().toString();
                pwstring = pwtext.getText().toString();

                loginStart(idstring, pwstring);

            }
        });

        regist.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), RegistActivity.class);
                startActivity(myIntent); // 회원가입 액티비티 연결하는 코드
            }

        });

        find.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(myIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
}