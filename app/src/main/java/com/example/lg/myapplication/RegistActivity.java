package com.example.lg.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.regex.Pattern;


public class RegistActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    HashMap<String, Object> newData = new HashMap<>();

    Button submit;
    EditText id, pass, name, nick, mail, phone, cor_pw;
    TextView PW_Check, Email_Check, Phone_Check;
    String idtext2, passtext, nametext, nicktext, mailtext, phonetext, corpwtext;
    RadioGroup radiogroup;
    int gender=0;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            newData.put("pass", passtext);
                            newData.put("name", nametext);
                            newData.put("nickname", nicktext);
                            newData.put("e-mail", mailtext);
                            newData.put("phone", phonetext);

                            if (gender == 1) newData.put("gender", "man");
                            else if (gender == 2) newData.put("gender", "woman");

                            DocumentReference newUserData = db.collection("userData").document(mailtext);
                            newUserData.set(newData);

                            pass.setText("");
                            name.setText("");
                            nick.setText("");
                            mail.setText("");
                            phone.setText("");

                            Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();                     // 여기까지 실행이 완료되면 액티비티 자동 종료

                        } else {

                            try {
                                throw task.getException();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Email_Check.setText("이미 존재하는 email 입니다.");
                            } catch(FirebaseAuthWeakPasswordException e) {
                                PW_Check.setText("비밀번호를 6자리 이상으로 설정하세요.");
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Email_Check.setText("email 형식에 맞지 않습니다.");
                            } catch(Exception e) {
                                Toast.makeText(getApplicationContext(),"다시 확인해주세요." ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        mAuth = FirebaseAuth.getInstance();

        pass = (EditText) findViewById(R.id.pass);
        name = (EditText) findViewById(R.id.name);
        nick = (EditText) findViewById(R.id.nick);
        mail = (EditText) findViewById(R.id.mail);
        phone = (EditText) findViewById(R.id.phone);
        cor_pw = (EditText) findViewById(R.id.cor_pw);

        PW_Check=findViewById(R.id.PW_Check);
        Email_Check=findViewById(R.id.Email_Check);
        Phone_Check=findViewById(R.id.Phone_Check);


        submit = findViewById(R.id.submit); // 제출버튼

        //radio button 눌렀을 때
        radiogroup = (RadioGroup) findViewById(R.id.raidogroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if(checkedId==R.id.man){
                    gender=1;
                }
                else if(checkedId==R.id.woman){
                    gender=2;
                }
            }
        });

        passtext = pass.getText().toString();
        corpwtext = cor_pw.getText().toString();
        mailtext = mail.getText().toString();
        phonetext = phone.getText().toString();

        //submit 버튼 눌렀을 때
        submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                int check = 0, check3 = 0;

                passtext = pass.getText().toString();
                nametext = name.getText().toString();
                nicktext = nick.getText().toString();
                mailtext = mail.getText().toString();
                phonetext = phone.getText().toString();
                corpwtext = cor_pw.getText().toString();

                //비밀번호 일치 확인
                if (!passtext.equals(corpwtext)) {
                    PW_Check.setText("비밀번호가 일치하지 않습니다.");
                    check = 1;
                } else {
                    PW_Check.setText("");
                    check = 0;
                }

                //이름 입력 확인
                if (nametext.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                }
                //닉네임 입력 확인
                if (nicktext.length() == 0) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력하세요.", Toast.LENGTH_LONG).show();
                }
                //이메일 입력 확인
                if (mailtext.toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Email을 입력하세요.", Toast.LENGTH_LONG).show();
                }

                //전화번호 입력 확인
                if (phonetext.length() == 0) {
                    Toast.makeText(getApplicationContext(), "전화번호를 입력하세요.", Toast.LENGTH_LONG).show(); }

                    // 전화번호 형식 확인
                if (!Pattern.matches("^01(0|1|[6-9])-(?:\\d{3,4})-(?:\\d{4})$", phonetext)) {
                    Phone_Check.setText("핸드폰 번호 형식이 아닙니다.");
                    check3 = 1;

                } else {
                    Phone_Check.setText("");
                    check3 = 0; }

                    //성별 입력 확인
                if (gender == 0) {
                    Toast.makeText(getApplicationContext(), "성별을 체크하세요.", Toast.LENGTH_LONG).show(); }

                if (check == 0 && check3 == 0) {
                    createUser(mail.getText().toString(), pass.getText().toString());

                }
            }
        });

    }
}
