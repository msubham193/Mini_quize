package com.kerols2020.mini_quize;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


public class GetStarted extends AppCompatActivity {
    AlertDialog.Builder alert ;
    Button start ;
    ImageView logo ;
    Animation fromBottom ;
    Animation fromTop ;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.get_started);
   start =(Button)findViewById(R.id.getStartedID);
   logo=(ImageView)findViewById(R.id.logo1ID);
   fromBottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
   start.setAnimation(fromBottom);
   fromTop=AnimationUtils.loadAnimation(this,R.anim.fromtop);
   logo.setAnimation(fromTop);

   start.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
      checkInternet();
     }
   });

  }

  private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager
            = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }


  private void checkInternet()
  {
      if (isNetworkAvailable())
      {
          Intent intent =new Intent(GetStarted.this,HomePage.class);
          startActivity(intent);
          finish();
      }
      else
      {
          alert = new AlertDialog.Builder(GetStarted.this);
          alert.setTitle("تنبيه هام");
          alert.setMessage("تعذر الأتصال بالأنترنت");
          alert.setCancelable(false);
          alert.setNegativeButton("خروج", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  finish();
                  System.exit(0);

              }
          });
          alert.setPositiveButton("اعاده المحاولة", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
               checkInternet();
              }
          });
          AlertDialog alertDialog=alert.create();
          alertDialog.show();

      }
  }
}

