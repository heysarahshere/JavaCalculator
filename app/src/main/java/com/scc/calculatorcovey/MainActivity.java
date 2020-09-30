package com.scc.calculatorcovey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  Button buttonCalculate;
  EditText edtResult;
  TextView textResultView;
  TextView operationsTextView;
  String displayResult = "";
  String operationsString = "";
  String operator = "";
  private double firstNumber = Double.MIN_VALUE;
  private double secondNumber = Double.MIN_VALUE;
  private double percentage = Double.MIN_VALUE;
  private double sqRoot = Double.MIN_VALUE;
  Boolean haveDecimal = false;
  Boolean operationPending = false;
  Boolean isNegative = false;
  Boolean isPercent = false;
  Boolean isSpecial = false;
  DecimalFormat df = new DecimalFormat("###########.########");
  final double PI = 3.141592653589793;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.calculator);
    toolbar.setTitleTextColor(Color.WHITE);
    setSupportActionBar(toolbar);

    buttonCalculate = findViewById(R.id.buttonCalculate);
    edtResult = findViewById(R.id.edtResult);
    textResultView = findViewById(R.id.textResultView);
    operationsTextView = findViewById(R.id.operationsTextView);
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
        Intent conversionIntent = new Intent( this, Conversions.class);
        conversionIntent.putExtra("result", displayResult);
        startActivity(conversionIntent);
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

  @Override
  public void onSaveInstanceState(Bundle outBundle){
    outBundle.putString("result", displayResult);
    outBundle.putString("opString", operationsString);
    outBundle.putString("operator", operator);

    outBundle.putBoolean("opPending", operationPending);
    outBundle.putBoolean("decimal", haveDecimal);
    outBundle.putBoolean("negative", isNegative);
    outBundle.putBoolean("percant", isPercent);
    outBundle.putBoolean("special", isSpecial);

    outBundle.putDouble("first", firstNumber);
    outBundle.putDouble("second", secondNumber);

    Log.i("CALC", "in onStart method.");
    super.onSaveInstanceState(outBundle);
  }
  @Override
  public void onRestoreInstanceState(@NonNull Bundle savedBundle){
    super.onRestoreInstanceState(savedBundle);
    displayResult = savedBundle.getString("result");
    operationsString = savedBundle.getString("opString");
    operator = savedBundle.getString("operator");

    operationPending = savedBundle.getBoolean("opPending");
    haveDecimal = savedBundle.getBoolean("decimal");
    isNegative = savedBundle.getBoolean("negative");
    isPercent = savedBundle.getBoolean("percent");
    isSpecial = savedBundle.getBoolean("special");

    firstNumber = savedBundle.getDouble("first");
    secondNumber = savedBundle.getDouble("second");

    edtResult.setText(displayResult);
    operationsTextView.setText(operationsString);
    Log.i("CALC", "in onStart method.");
  }

  public void buttonNumberOnClick(View v) {
    String buttonName = ((Button) v).getText().toString();
    if (buttonName.equals(".")) {
      // verify decimal with modulus
      if (Double.parseDouble(displayResult) % 1 == 0) {
        displayResult += buttonName;
        edtResult.setText(displayResult);
      }
    } else {
      isSpecial = false;  // need to hit trig func to make special??
      displayResult += buttonName;
      edtResult.setText(displayResult);
    }
  }

  public void buttonOpOnClick(View v) {
    String buttonName = ((Button) v).getText().toString();
    if (!displayResult.equals("")) {
      if (operationPending) {
        // get display number
        // use last operator
        if (isNegative) {
          secondNumber = -Double.parseDouble(displayResult);
          isNegative = false;
        } else {
          secondNumber = Double.parseDouble(displayResult);
        }
        switch (operator) {
          // this assumes there IS a first number
          // perform operation between firstNumber & display number
          // save result as firstNumber
          case "+":
            firstNumber += secondNumber;
            displayResult = String.valueOf(df.format(firstNumber));
            edtResult.setText(displayResult);

            displayResult = "";
            operator = buttonName;
            break;
          case "-":
            firstNumber -= secondNumber;
            displayResult = String.valueOf(df.format(firstNumber));
            edtResult.setText(displayResult);

            displayResult = "";
            operator = buttonName;
            break;
          case "/":
            firstNumber /= secondNumber;
            displayResult = String.valueOf(df.format(firstNumber));
            edtResult.setText(displayResult);

            displayResult = "";
            operator = buttonName;
            break;
          case "*":
            firstNumber *= secondNumber;
            displayResult = df.format(firstNumber);
            edtResult.setText(displayResult);

            displayResult = "";
            operator = buttonName;
            break;
          default:
            Log.i("Op", "Error");
            return;
        }

        if (!isSpecial) {
          operationsString += df.format(secondNumber);
          isSpecial = false;
        }
        operationsString += operator;
        operationsTextView.setText(operationsString);

      } else {
        operationPending = true;
        switch (buttonName) {
          case "+":
            operator = "+";
            break;
          case "-":
            operator = "-";
            break;
          case "/":
            operator = "/";
            break;
          case "*":
            operator = "*";
            break;
          default:
            Log.i("Op", "Error");
            return;
        }
        // prepare for next number
        // save first number
        if (isNegative) {
          firstNumber = -Double.parseDouble(displayResult);
          isNegative = false;
        } else {
          firstNumber = Double.parseDouble(displayResult);
        }
        // display number and operator
        if (!isSpecial) {
          operationsString = df.format(firstNumber);
          isSpecial = false;
        }
        operationsString += operator;
        operationsTextView.setText(operationsString);
        // reset display to get next number
        displayResult = "";
        // reset for decimals
//                haveDecimal = false;
        //show blank
        edtResult.setText(displayResult);

      }
    }
    // maybe append to a text view above
  }

  public void buttonEqualsOnClick(View v) {
    if (!displayResult.equals("")) {

      if (operationPending) {
        // get display number
        if (isNegative) {
          secondNumber = -Double.parseDouble(displayResult);
        } else {
          secondNumber = Double.parseDouble(displayResult);
        }
        // use last operator
        switch (operator) {
          // this assumes there IS a first number
          // perform operation between firstNumber & display number
          // save result as firstNumber
          case "+":
            firstNumber += secondNumber;
            break;
          case "-":
            firstNumber -= secondNumber;
            break;
          case "/":
            firstNumber /= secondNumber;
            break;
          case "*":
            firstNumber *= secondNumber;
            break;
          default:
            Log.i("Op", "Error");
            return;
        }

        DecimalFormat df = new DecimalFormat("###########.########");
        if (!isSpecial) {
          operationsString += df.format(secondNumber); // =+ to = (second to first #)
        }
        operationsString += "=";
        operationsTextView.setText(operationsString);
        displayResult = String.valueOf(df.format(firstNumber));
        edtResult.setText(displayResult);
        // keep number there for more operations
        haveDecimal = false;
        isSpecial = false;
        operationPending = false;
        double secondNumber = Double.MIN_VALUE;
      } else {
        Log.i("Op", "Error with equal operation.");
      }

    }
    operationPending = false;
    isNegative = false;
    isPercent = false;
  }

  public void buttonClearOnClick(View v) {
    String buttonName = ((Button) v).getText().toString();
    Log.i("Calc", "You hit the clear button.");
    displayResult = "";
    operationsTextView.setText("");
    operationsString = "";
    edtResult.setText(displayResult);
//        haveDecimal = false; // don't need bool with mod
    operationPending = false;
    isNegative = false;
    isPercent = false;
    double firstNumber = Double.MIN_VALUE;
    double secondNumber = Double.MIN_VALUE;
    double percentage = Double.MIN_VALUE; // not using?
  }

  public void buttonRandom(View v) {
    displayResult = Double.toString(Math.random());
    edtResult.setText(displayResult);
  }

  public void buttonPercent(View v) {
    // reg calc just keeps dividing ???
    if (operationPending && !displayResult.equals("")) {
      switch (operator) {
        // this assumes there IS a first number
        // perform operation between firstNumber & display number
        // save result as firstNumber
        case "+":
          secondNumber = Double.parseDouble(displayResult) / 100;
          secondNumber = firstNumber * secondNumber;
          displayResult = String.valueOf(df.format(secondNumber));
          edtResult.setText(displayResult);
          operationPending = true;
          break;
        case "-":
          secondNumber = Double.parseDouble(displayResult) / 100;
          secondNumber = firstNumber * secondNumber;
          displayResult = String.valueOf(df.format(secondNumber));
          edtResult.setText(displayResult);
          operationPending = true;
          break;
        case "/":
          secondNumber = Double.parseDouble(displayResult) / 100;
          displayResult = String.valueOf(df.format(secondNumber));
          edtResult.setText(displayResult);
          operationPending = true;
          break;
        case "*":
          secondNumber = Double.parseDouble(displayResult) / 100;
          displayResult = String.valueOf(df.format(secondNumber));
          edtResult.setText(displayResult);
          operationPending = true;
          break;
        default:
          Log.i("Op", "Error");
      }


    }
  }

  public void buttonTrigFunc(View v) {
    String buttonName = ((Button) v).getText().toString();
    if (operationPending && !displayResult.equals("")) {
      isSpecial = true;
      switch (buttonName) {
        case "SIN":
          secondNumber = Math.sin(Double.parseDouble(displayResult));
          operationsString += "sin(" + displayResult + ")";
          break;
        case "SEN":
          secondNumber = Math.sin(Double.parseDouble(displayResult));
          operationsString += "sin(" + displayResult + ")";
          break;
        case "COS":
          secondNumber = Math.cos(Double.parseDouble(displayResult));
          operationsString += "cos(" + displayResult + ")";
          break;
        case "TAN":
          secondNumber = Math.tan(Double.parseDouble(displayResult));
          operationsString += "tan(" + displayResult + ")";
          break;
        case "X\u00B2": //squared
          secondNumber = Double.parseDouble(displayResult) * Double.parseDouble(displayResult);
          operationsString += "sqr(" + displayResult + ")";
          break;
        case "\u221A":  //square root
          secondNumber = Math.sqrt(Double.parseDouble(displayResult));
          operationsString += "\u221A(" + displayResult + ")";
          break;
        default:
          Log.i("Op", "Error");
          return;
      }
      operationsTextView.setText(operationsString);
      displayResult = df.format(secondNumber);
      edtResult.setText(displayResult);

    } else if (!displayResult.equals("")) {
      isSpecial = true;
      switch (buttonName) {
        case "SIN":
          firstNumber = Math.sin(Double.parseDouble(displayResult));
          operationsString += "sin(" + displayResult + ")";
          break;
        case "COS":
          firstNumber = Math.cos(Double.parseDouble(displayResult));
          operationsString += "cos(" + displayResult + ")";
          break;
        case "TAN":
          firstNumber = Math.tan(Double.parseDouble(displayResult));
          operationsString += "tan(" + displayResult + ")";
          break;
        case "X\u00B2": //squared
          firstNumber = Double.parseDouble(displayResult) * Double.parseDouble(displayResult);
          operationsString += "sqr(" + displayResult + ")";
          break;
        case "\u221A":  //square root
          firstNumber = Math.sqrt(Double.parseDouble(displayResult));
          operationsString += "\u221A(" + displayResult + ")";
          break;
        default:
          Log.i("Op", "Error");
          return;
      }
      operationsTextView.setText(operationsString);
      displayResult = df.format(firstNumber);
      edtResult.setText(displayResult);
    }

  }

  public void buttonPi(View v) {
    displayResult = Double.toString(PI);
    edtResult.setText(displayResult);
    haveDecimal = true;
  }

  public void buttonPlusOrMinus(View v) {
    if (isNegative) {
      String posNegResult = displayResult;
      edtResult.setText(posNegResult);
      isNegative = false;
    } else {
      String posNegResult = "-" + displayResult;
      edtResult.setText(posNegResult);
      isNegative = true;
    }
  }

  public void buttonBackSpace(View v) {
    if (!displayResult.equals("")) {
      displayResult = displayResult.substring(0, displayResult.length() - 1);
      edtResult.setText(displayResult);
    }
  }

}
