package com.scc.calculatorcovey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class History extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_history);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.history);
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
        break;
      case R.id.menuAbout:
        Intent aboutIntent = new Intent( this, About.class);
        startActivity(aboutIntent);
        break;
    }
    return true;
  }
}
