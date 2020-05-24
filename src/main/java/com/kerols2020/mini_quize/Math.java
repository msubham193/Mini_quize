package com.kerols2020.mini_quize;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Math extends AppCompatActivity {

    Timer timerToMoveToNextActivity ;
    private AdView mAdView,mAdVie2;
    Intent intent ;
    TextView question ,trueAnswer,falseAnswer;
    ImageView trueImage,falseImage ;
    int P;
    String Answer="";
    DatabaseReference ref,Qref ;
    FirebaseAuth auth ;
   // SharedPreferences sharedPreferences ;
    String Question="";
    private InterstitialAd mInterstitialAd;
    int r ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        LoadAd();

        ref = FirebaseDatabase.getInstance().getReference().child("User").child(auth.getInstance().getUid());
        Qref= FirebaseDatabase.getInstance().getReference().child("Question");
        Qref.keepSynced(true);
        question=(TextView)findViewById(R.id.mathQuestion);
        trueAnswer=(TextView)findViewById(R.id.mathTrueMark);
        falseAnswer=(TextView)findViewById(R.id.mathFalseMark);
        trueImage=(ImageView)findViewById(R.id.mathTrueImage);
        falseImage=(ImageView)findViewById(R.id.mathfalseImage);
        timerToMoveToNextActivity=new Timer();


        mAdView = findViewById(R.id.mathAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdVie2 = findViewById(R.id.mathAdView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdVie2.loadAd(adRequest2);
        updateQuestion();
        //sharedPreferences =getSharedPreferences("WALLET", Context.MODE_PRIVATE);
        //P=sharedPreferences.getInt("points",-10000);

        trueAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trueAnswer.setTextColor(Color.BLUE);
                falseAnswer.setVisibility(View.INVISIBLE);
                if(Answer.equals("نعم"))
                {
                    trueImage.setVisibility(View.VISIBLE);

                            //P+=2;

                    //sharedPreferences =getSharedPreferences("WALLET", Context.MODE_PRIVATE);
                    //SharedPreferences.Editor editor=sharedPreferences.edit();
                    //editor.putInt("points",P);
                    //editor.apply();
                    //if(P!=-10000) {
                    addTwoPoints();
                    //ref.child("points").setValue(P).addOnCompleteListener(new OnCompleteListener<Void>() {
                      //      @Override
                        //    public void onComplete(@NonNull Task<Void> task) {

                          //  }
                        //});

                    //}


                }
                else
                {
                    falseImage.setVisibility(View.VISIBLE);
                    //P-=1;

                    //sharedPreferences =getSharedPreferences("WALLET", Context.MODE_PRIVATE);
                    //SharedPreferences.Editor editor=sharedPreferences.edit();
                    //editor.putInt("points",P);
                    //editor.apply();
                    //if(P!=-10000) {
                      //  ref.child("points").setValue(P).addOnCompleteListener(new OnCompleteListener<Void>() {
                        //    @Override
                          //  public void onComplete(@NonNull Task<Void> task) {

                            //}
                        //});
                    substractOnePoint();

                }




                timerToMoveToNextActivity.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        intent=new Intent(Math.this,Math.class);
                        startActivity(intent);
                        finish();
                    }
                },3000);
            }

        });
        falseAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                falseAnswer.setTextColor(Color.BLUE);
                trueAnswer.setVisibility(View.INVISIBLE);
                if(Answer.equals("لا"))
                {
                    trueImage.setVisibility(View.VISIBLE);

                /*    P+=2;
                    sharedPreferences =getSharedPreferences("WALLET", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt("points",P);
                    editor.apply();
                    if(P!=-10000) {
                        ref.setValue(P).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
*/
                addTwoPoints();
                    }



                else
                {
                    falseImage.setVisibility(View.VISIBLE);
  /*
                    P-=1;

                    sharedPreferences =getSharedPreferences("WALLET", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt("points",P);
                    editor.apply();
                    if(P!=-10000) {
                        ref.child("points").setValue(P).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                    }
*/
                    substractOnePoint();
                }
                timerToMoveToNextActivity.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        intent=new Intent(Math.this,Math.class);
                        startActivity(intent);
                        finish();
                    }
                },3000);
            }
        });

    }

        private void updateQuestion() {

        Qref.child("n").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                    Question=dataSnapshot.getValue().toString();
                Random rand = new Random();
                int m=1;
                r = rand.nextInt(Integer.parseInt(Question)-m+1)+m;


                Qref.child(String.valueOf(r)).child("Q").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        question.setText(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Qref.child(String.valueOf(r)).child("A").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Answer=dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }



    private void LoadAd() {
        MobileAds.initialize(this,
                "ca-app-pub-1669182856365767~9555627835");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1669182856365767/4356654932");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }



            });
        }
        finish();
    }

   void addTwoPoints()
   {
       ref.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.hasChild("points"))
               {
                   String n= dataSnapshot.child("points").getValue().toString();
                   int nn=Integer.valueOf(n);
                   ref.child("points").setValue(String.valueOf(nn+2));
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }

   void substractOnePoint()
   {
       ref.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.hasChild("points"))
               {
                  String n= dataSnapshot.child("points").getValue().toString();
                  int nn=Integer.valueOf(n);
                  ref.child("points").setValue(String.valueOf(nn-1));
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }
}
