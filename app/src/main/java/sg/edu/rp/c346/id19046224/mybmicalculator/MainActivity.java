package sg.edu.rp.c346.id19046224.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.logging.StreamHandler;

public class MainActivity extends AppCompatActivity {

    TextView tvDate, tvBMI, tvResult, tvBMICalc, tvDateShow;
    EditText etWeight, etHeight;
    Button btnCalc, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //added dateshow and bmicalc because the text can repeat
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvResult = findViewById(R.id.textViewBMIResult);
        tvBMICalc = findViewById(R.id.textViewBMICalculated);
        tvDateShow = findViewById(R.id.textViewDateShow);
        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);

        //Does the stuff below when clicked
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //retrieves float values from the edittext
                float FWeight = Float.parseFloat(etWeight.getText().toString());
                float FHeight = Float.parseFloat(etHeight.getText().toString());

                //formula for bmi
                float BMI = FWeight / (FHeight * FHeight);

                //datetime stuff
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                //basic set text
                tvBMICalc.setText(String.format("%.3f", BMI));
                tvDateShow.setText(datetime);
                etHeight.setText("");
                etWeight.setText("");

                //enhancement for bmi
                if (BMI == 18.5) {
                    tvResult.setText("You are underweight");
                }
                else if (BMI >= 18.5 && BMI < 25) {
                    tvResult.setText("Your BMI is normal");
                }
                else if (BMI >= 25 && BMI < 30) {
                    tvResult.setText("You are overweight");
                }
                else if (BMI >= 30) {
                    tvResult.setText("You are obese");
                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etHeight.setText("");
                etWeight.setText("");
                tvBMI.setText("");
                tvDate.setText("");
                tvResult.setText("");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefsEdit = prefs.edit();
                prefsEdit.clear();
                prefsEdit.commit();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //obtain an instance of the sharedPreferences

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        //retrieve saved data
        float SBMI = prefs.getFloat("bmi", 0);
        String StrDateTime = prefs.getString("date","");

        //Update UI element with saved data
        tvBMICalc.setText(SBMI + "");
        tvDateShow.setText(StrDateTime);

    }

    @Override
    protected void onPause() {
        super.onPause();

        //Get user input from the TextView and store it in a variable
        String StrDateTime = tvDateShow.getText().toString();
        float SBMI = Float.parseFloat(tvBMICalc.getText().toString());

        //Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        //Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefsEdit = prefs.edit();

        //Add the key-value pair
        prefsEdit.putFloat("bmi", SBMI);
        prefsEdit.putString("date", StrDateTime);

        //Call commit() to save the changes into SharedPreferences
        prefsEdit.commit();

    }
}
