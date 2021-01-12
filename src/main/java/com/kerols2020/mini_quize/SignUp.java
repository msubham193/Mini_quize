package com.kerols2020.mini_quize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    private ProgressDialog progressDialog;
    Button signUpButton ;
    TextView signUphaveAccount ;
    EditText Email,userName,passWord,confirmPassWord;
    String name ,email,pass,Conpass;
    FirebaseAuth firebaseAuth ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //SharedPreferences sharedPreferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // this is on create.
        databaseReference=firebaseDatabase.getInstance().getReference().child("User");
        firebaseAuth =FirebaseAuth.getInstance();
        Email=(EditText)findViewById(R.id.EmailSignupID);
        userName=(EditText)findViewById(R.id.userNamesignUpID);
        passWord=(EditText)findViewById(R.id.passwordSignupID);
        confirmPassWord=(EditText)findViewById(R.id.confirmedpasswordSignupID);
        signUphaveAccount =(TextView)findViewById(R.id.SignUphaveAccountID);
        signUpButton =(Button)findViewById(R.id.SignupButtonID);
        progressDialog=new ProgressDialog(SignUp.this);

       signUphaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a= new Intent(SignUp.this , Login.class);
                startActivity(a);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=userName.getText().toString();
                email=Email.getText().toString();
                pass=passWord.getText().toString();
                Conpass=confirmPassWord.getText().toString();

                if(!name.isEmpty()&&!email.isEmpty()&&!pass.isEmpty()&&!Conpass.isEmpty()&&pass.equals(Conpass))
                {


                    progressDialog.setTitle("أنشاء حساب..");
                    progressDialog.setMessage("برجاء الانتظار جاري أنشاء الحساب");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(true);

                    firebaseAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                HashMap user=new HashMap<>();
                                user.put("E-mail",email);
                                user.put("Name",name);
                                user.put("PassWord",pass);
                                user.put("points","0");
                                databaseReference.child(firebaseAuth.getInstance().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        //sharedPreferences =getSharedPreferences("WALLET", Context.MODE_PRIVATE);
                                        //SharedPreferences.Editor editor=sharedPreferences.edit();
                                        //editor.putInt("points",0);
                                        //editor.apply();

                                        progressDialog.dismiss();
                                        Intent intent=new Intent(SignUp.this,HomePage.class);
                                        startActivity(intent);
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
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"hello"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else if (email.isEmpty())
                {
                    Toast.makeText(SignUp.this,"يرجي ادخال البريد الألكتروني ", Toast.LENGTH_LONG).show();
                }
               else if (name.isEmpty())
                {
                    Toast.makeText(SignUp.this,"يرجي ادخال أسم المستخدم ", Toast.LENGTH_LONG).show();
                }
                else if (pass.isEmpty())
                {
                    Toast.makeText(SignUp.this,"يرجي ادخال كلمة المرور ", Toast.LENGTH_LONG).show();
                }
                else if (Conpass.isEmpty())
                {
                    Toast.makeText(SignUp.this,"يرجي ادخال كلمة المرور مرة اخري ", Toast.LENGTH_LONG).show();
                }
                else if (!pass.equals(Conpass))
                {
                    Toast.makeText(SignUp.this,"الرقم المرور غير متطابق ", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
