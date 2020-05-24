package com.kerols2020.mini_quize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {
    AlertDialog.Builder alert ;
    Intent intent;
    BottomNavigationView bottomNavigationView ;
    ImageView math;
    FirebaseAuth firebaseAuth ,auth;
    DatabaseReference ref;
    FirebaseUser firebaseUser ;
    private AdView mAdView,mAdView2;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);




        mAdView = findViewById(R.id.homeAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView2 = findViewById(R.id.home2AdView);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);


        MobileAds.initialize(this,
                " ca-app-pub-1669182856365767~9555627835");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1669182856365767/5561925391");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        firebaseAuth=FirebaseAuth.getInstance();
        math=(ImageView)findViewById(R.id.mathImage);

        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             intent =new Intent(HomePage.this ,Math.class);
             startActivity(intent);
            }
        });



        // here bottom navigation will be implemented

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener()
                {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.logoutMenu:
                       firebaseAuth.signOut();
                        intent=new Intent(HomePage.this,Login.class);
                        startActivity(intent);
                        break;
                    case R.id.walletMenu:
                        intent=new Intent(HomePage.this,MyWallet.class);
                        startActivity(intent);
                        break;
                    case R.id.instractionsMenu:
                        showAlertDialog();
                        break;

                }

                return true;
            }

        });
    }

    private void showAlertDialog()
    {
        alert = new AlertDialog.Builder(HomePage.this);
        alert.setTitle("التعليمات");
        alert.setMessage("هذا التطبيق عباره عن أسئلة متعدد الأختيارات , عند الأجابة الصحيحة تكتسب نقطتين و تحذف نقطة عند الخطأ");
        alert.setCancelable(false);
        alert.setPositiveButton("تم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();

    }


    @Override
    public void onBackPressed() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        alert = new AlertDialog.Builder(HomePage.this);
        alert.setMessage("هل تريد اغلاق التطبيق ؟!");
        alert.setCancelable(false);
        alert.setNegativeButton("خروج", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        alert.setPositiveButton("رجوع", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser==null)
        {
           intent =new Intent(HomePage.this,Login.class);
           startActivity(intent);
           finish();
        }
        else
            ref = FirebaseDatabase.getInstance().getReference().child("User").child(auth.getInstance().getUid());
    }
}

