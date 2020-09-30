package com.scc.calculatorcovey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class About extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.about);
    toolbar.setTitleTextColor(Color.WHITE);
    setSupportActionBar(toolbar);
  }

  @Override
  public boolean onCreateOptionsMenu( Menu menu ){
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item){
    switch(item.getItemId()){
      case R.id.menuCalculator:
        Intent mainIntent = new Intent( this, MainActivity.class);
        startActivity(mainIntent);
        break;
      case R.id.menuConversions:
        Intent conversionsIntent = new Intent( this, Conversions.class);
        startActivity(conversionsIntent);
        break;
      case R.id.menuHistory:
        Intent historyIntent = new Intent( this, History.class);
        startActivity(historyIntent);
        break;
      case R.id.menuAbout:
        break;
    }
    return true;
  }
}
