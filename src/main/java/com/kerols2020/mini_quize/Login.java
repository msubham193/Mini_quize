package com.kerols2020.mini_quize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private ProgressDialog progressDialog;
    Button logInButton ;
    TextView loginhaveAccount ,forgetPass;
    FirebaseAuth firebaseAuth ;
    EditText Email ,pass;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(Login.this);
        forgetPass=(TextView)findViewById(R.id.forgetPassword);
        Email=(EditText)findViewById(R.id.EmailLoginID);
        pass=(EditText)findViewById(R.id.passwordLoginID);
        logInButton=(Button)findViewById(R.id.LoginButtonID);
        firebaseAuth=FirebaseAuth.getInstance();
        loginhaveAccount=(TextView)findViewById(R.id.LoginhaveAccountID);
        loginhaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a= new Intent(Login.this , SignUp.class);
                startActivity(a);
            }
        });


        logInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                password=pass.getText().toString();
                email=Email.getText().toString();
                if(email.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"يرجي أدخال البريد الألكتروني ",Toast.LENGTH_LONG).show();
                }
               else if(password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"يرجي أدخال رقم المرور ",Toast.LENGTH_LONG).show();
                }
                else
                {

                   progressDialog.setTitle("تسجيل دخول....");
                   progressDialog.setMessage("برجاء الانتظار جاري تسجيل الدخول");
                   progressDialog.show();
                   progressDialog.setCanceledOnTouchOutside(true);

                    firebaseAuth.signInWithEmailAndPassword(Email.getText().toString(),pass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Intent a= new Intent(Login.this , HomePage.class);
                                startActivity(a);
                                finish();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=Email.getText().toString();
                if(email.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"يرجي أدخال البريد الألكتروني ",Toast.LENGTH_LONG).show();
                }
                else
                {

                    progressDialog.setTitle("أرسال كلمة مرور جديده....");
                    progressDialog.setMessage("برجاء الانتظار جاري أرسال كلمة مرور جديده للبريد الألكتروني الخاص بك");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(true);
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"تم ارسال كلمة المرور بنجاح ",Toast.LENGTH_LONG).show();
                            }
                            else
                            {

                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }


}
