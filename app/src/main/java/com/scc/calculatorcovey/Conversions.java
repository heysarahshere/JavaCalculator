package com.scc.calculatorcovey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import java.io.*;

public class Conversions extends AppCompatActivity {

    String result = "";
    String fromString = "";
    String conversionPhrase = "";
    String[] conversionWords;
    EditText firstConvertNumber;
    EditText secondConvertNumber;
    TextView from, to;
    ListView listview;

    Double[] conversionRates = {
        // length
        0.62, // Kilometers to Miles
        1.609, // Miles to Kilometers
        3280.8, // Kilometers to Feet
        0.0003048, // Feet to Kilometers
        3.281, // Meters to Feet
        0.3, // Feet to Meters
        0.39, // Centimeters to Inches
        2.54, // Inches to Centimeters
        0.039, // Millimeters to Inches
        25.4, // Inches to Millimeters
        39.37, // Meters to Inches
        0.0254, // Inches to Meters
        1.09361, // Meters to Yards
        0.9144, // Yards to Meters
        1093.61, // Kilometers to Yards
        0.00091, // Yards to Kilometers
        // temp
        0.5555555556, // Fahrenheit to Celsius
        1.8, // Celsius to Fahrenheit
        // volume
        1.057, // Liters to Quarts
        0.95, // Quarts to Liters
        0.264, // Liters to Gallons
        3.785, // Gallons to Liters
        0.0042, // Milliliters to Cups
        236.6, // Cups to Milliliters
        0.0338, // Milliliters to Ounces
        29.574, // Ounces to Milliliters
        2.0, // Pints to Cups
        0.5, // Cups to Pints
        // mass
        0.0011, // Kilograms to Tons
        907.18, // Tons to Kilograms
        2.2046, // Kilograms to Pounds
        0.454, // Pounds to Kilograms
        0.035, // Grams to Ounces
        28.35,// Ounces to Grams
        0.002205, // Grams to Pounds
        453.592, // Pounds to Grams
        0.000035, // Milligrams to Ounces
        28349.5, // Ounces to Milligrams
        // dog years
        7.0, // Dog Years to Human Years
        0.143 // Human Years to Dog Years
    };
    String[] items = {
        "Kilometers to Miles", // 0
        "Miles to Kilometers", // 1
        "Kilometers to Feet", // 2
        "Feet to Kilometers", // 3
        "Meters to Feet", // 4
        "Feet to Meters", // 5
        "Centimeters to Inches", // 6
        "Inches to Centimeters", // 7
        "Millimeters to Inches", // 8
        "Inches to Millimeters", // 8
        "Meters to Inches", // 10
        "Inches to Meters", // 11
        "Meters to Yards", // 12
        "Yards to Meters", // 13
        "Kilometers to Yards", // 14
        "Yards to Kilometers", // 15
        // temp
        "Fahrenheit to Celsius", // 16
        "Celsius to Fahrenheit", // 17
        // volume
        "Liters to Quarts", // 18
        "Quarts to Liters", // 19
        "Liters to Gallons", // 20
        "Gallons to Liters", // 21
        "Milliliters to Cups", // 22
        "Cups to Milliliters", // 23
        "Milliliters to Ounces", // 24
        "Ounces to Milliliters", // 25
        "Pints to Cups", // 26
        "Cups to Pints", // 27
        // Mass
        "Kilograms to Tons", // 28
        "Tons to Kilograms", // 29
        "Kilograms to Pounds", // 30
        "Pounds to Kilograms", // 31
        "Grams to Ounces", // 32
        "Ounces to Grams", // 33
        "Grams to Pounds", // 34
        "Pounds to Grams", // 35
        "Milligrams to Ounces", // 36
        "Ounces to Milligrams", // 37
        // space stuff
        "Dog Years to Human Years", // 38
        "Human Years to Dog Years", // 39
    };

    String[] spanish_items = {
        "Kilómetros a Millas", // 0
        "Millas a Kilómetros", // 1
        "Kilómetros a Pies", // 2
        "Pies a Kilómetros", // 3
        "Metros a Pies", // 4
        "Pies a metros", // 5
        "Centímetros a Pulgadas", // 6
        "Pulgadas a Centímetros", // 7
        "Milímetros a pulgadas", // 8
        "Pulgadas a milímetros", // 8
        "Metros a Pulgadas", // 10
        "Pulgadas a metros", // 11
        "Metros a Yardas", // 12
        "Yardas a metros", // 13
        "Kilómetros a yardas", // 14
        "Yardas a Kilómetros", // 15
        // temp
        "Fahrenheit a Celsius", // 16
        "Celsius a Fahrenheit", // 17
        // volumen
        "Litros a Cuartos", // 18
        "Cuartos a Litros", // 19
        "Litros a Galones", // 20
        "Galones a Litros", // 21
        "Mililitros a Copas", // 22
        "Tazas a Mililitros", // 23
        "Mililitros a Onzas", // 24
        "Onzas a Mililitros", // 25
        "Pintas a Copas", // 26
        "Tazas a Pintas", // 27
        // Masa
        "Kilogramos a Toneladas", // 28
        "Toneladas a Kilogramos", // 29
        "Kilogramos a Libras", // 30
        "Libras a Kilogramos", // 31
        "Gramos a Onzas", // 32
        "Onzas a Gramos", // 33
        "Gramos a Libras", // 34
        "Libras a Gramos", // 35
        "Miligramos a Onzas", // 36
        "Onzas a Miligramos", // 37
        // cosas espaciales
        "Años de perro a años humanos", // 38
        "Años humanos a años de perros", // 39
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.conversions);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        firstConvertNumber = findViewById(R.id.firstConvertNumber);
        secondConvertNumber = findViewById(R.id.secondConvertNumber);

        Intent intent = getIntent();
        result = intent.getStringExtra("result");
        firstConvertNumber.setText(result);
        from = findViewById(R.id.convertedFromTextView);
        to = findViewById(R.id.convertedToTextView);

        listview = findViewById(R.id.conversionsListView);

        ArrayAdapter adapter = new ArrayAdapter<String>( this, R.layout.conversion_item, items);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                performConversion( position );
            }
        });
    }

    public void performConversion(int position){
        Double fromNumber = 0.0;
        Double toNumber = 0.0;
        fromString = firstConvertNumber.getText().toString();
        if (!fromString.equals("")){
            fromNumber = Double.parseDouble(fromString);
            if(position == 16){
                // f to c
                toNumber = (fromNumber - 32) * conversionRates[position];
            } else if (position == 17){
                // c to f
                toNumber = (fromNumber * conversionRates[position]) + 32;
            } else {
                // others
                toNumber = fromNumber * conversionRates[position];
            }
            secondConvertNumber.setText(Double.toString(toNumber));
            conversionPhrase = items[position];
            conversionWords = conversionPhrase.split(" to ");
            from.setText(conversionWords[0]);
            to.setText(conversionWords[1]);
        } else {
            return;
        }
    }

    public void  calcButtonOnClick(View v){
        Intent mainIntent = new Intent( this, MainActivity.class);
        startActivity(mainIntent);
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
                break;
            case R.id.menuHistory:
                Intent historyIntent = new Intent( this, History.class);
                startActivity(historyIntent);
                break;
            case R.id.menuAbout:
                Intent aboutIntent = new Intent( this, About.class);
                startActivity(aboutIntent);
                break;
        }
        return true;
    }
}
