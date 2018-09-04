package com.example.lg.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FindActivity extends AppCompatActivity {
    Button find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        find = findViewById(R.id.find);

        //ID,PW 찾기 버튼 누르면
        find.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //아이디찾기
                //비밀번호 찾기
                 //이메일 인증
                //비밀번호 재설정 이메일 보내기
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String emailAddress = "user@example.com";

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("TAG", "Email sent.");
                                }
                            }
                        });

            }
        });



    }
}
