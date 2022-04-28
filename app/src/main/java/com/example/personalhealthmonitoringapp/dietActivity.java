package com.example.personalhealthmonitoringapp;

import android.content.Intent;
import android.graphics.Typeface;
//import android.support.v7.app.AlertDialog;
import androidx.appcompat.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.personalhealthmonitoringapp.R;

import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.personalhealthmonitoringapp.Email.email;


public class dietActivity extends AppCompatActivity {
    Button f,lgout;
    EditText eWeight, eHeight, eInches, eAge, healthcondition;
    TextView tweightlabel,bodytype;
    TextView tBMILable, tBMIResult, tBMRLable, tBMRResult;
    TextView tcalories,tcaloriesresult;
    Button bcalculate, bclear,bdietplan;
    float weight,value,age,BMI,inches,resultf,resultfb,h,hm,i,wpounds,r1,calorieintake;
    int healcon;
    String heightf;
    double result,bmrMen,bmrWomen,kg,weightinpounds;
    RadioButton rmale,rfemale;
    Spinner sp;

    String email;
    String w;
    String H;
    String a;
    String I;
    String HC;
    String gender="Male";
    String email_value;
    String weight_value;
    String height_value;
    String age_value;
    String gender_value;
    String healthcon_value;
    String Inch_value;

    DataBase dbh = new DataBase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        email = Email.email;
        List<Object> DietDetails=dbh.getDiet(email);
        //email_value = (String) DietDetails.get(0);
        weight_value = (String) DietDetails.get(0);
        height_value = (String) DietDetails.get(1);
        age_value = (String) DietDetails.get(2);
        gender_value = (String) DietDetails.get(3);
        Inch_value = (String) DietDetails.get(4);
        healthcon_value = (String) DietDetails.get(5);
        //Log.d("Check Value",weight_value);

        tweightlabel = (TextView) findViewById(R.id.textView);
        tBMILable = (TextView) findViewById(R.id.textView9);
        tBMIResult = (TextView) findViewById(R.id.textView10);
        tBMRLable = (TextView) findViewById(R.id.textView11);
        tBMRResult = (TextView) findViewById(R.id.textView12);
        tcalories = (TextView) findViewById(R.id.textView15);
        tcaloriesresult = (TextView) findViewById(R.id.textView14);
        bodytype = (TextView) findViewById(R.id.textView13);
        eWeight = (EditText) findViewById(R.id.editText);
        eHeight = (EditText) findViewById(R.id.editText2);
        eInches = (EditText) findViewById(R.id.editText3);
        eAge = (EditText) findViewById(R.id.editText4);
        healthcondition = (EditText) findViewById(R.id.editText16);
        bcalculate = (Button) findViewById(R.id.button);
        bclear = (Button) findViewById(R.id.button2);
        bdietplan = (Button)findViewById(R.id.button3);
        rmale = (RadioButton)findViewById(R.id.radioButton);
        rfemale = (RadioButton)findViewById(R.id.radioButton1);
        bdietplan.setVisibility(INVISIBLE);
        rfemale.setChecked(true);
        f = (Button) findViewById(R.id.faqb);
        lgout = (Button) findViewById(R.id.lgt);
        eWeight.setText(weight_value);
        eHeight.setText(height_value);
        eAge.setText(age_value);
        eInches.setText(Inch_value);
        healthcondition.setText(healthcon_value);

        //String test = "text";
        //Email =
        //Bundle bundle = getIntent().getExtras();
        //String email = bundle.getString("Email");
        //System.out.print("Test comment "+Email);
        //Log.d("TAG",test);
        //email_str = Email.getText().toString().trim();

        //rfemale.setChecked(true);
        //sp = (Spinner) findViewById(R.id.spinner);
        f.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FAQs.class));
            }


        });
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        bclear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                eWeight.setText("");
                eWeight.setError(null);
                eHeight.setText("");
                eHeight.setError(null);
                eInches.setText(" ");
                eInches.setError(null);
                eAge.setText(" ");
                eAge.setError(null);
                healthcondition.setText("");
                healthcondition.setError(null);
                tBMIResult.setVisibility(INVISIBLE);
                tBMILable.setVisibility(INVISIBLE);
                bodytype.setVisibility(INVISIBLE);
                tBMRLable.setVisibility(INVISIBLE);
                tBMIResult.setVisibility(INVISIBLE);
                tBMRResult.setVisibility(INVISIBLE);
                rmale.setChecked(false);
                rmale.setEnabled(true);
                rfemale.setChecked(false);
                rfemale.setEnabled(true);
                tcalories.setVisibility(INVISIBLE);
                tcaloriesresult.setVisibility(INVISIBLE);
                bdietplan.setVisibility(INVISIBLE);
                rfemale.setChecked(true);

            }
        });


        bcalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (eWeight.length() == 0||eHeight.length()==0 ||eAge.length()==0|| healthcondition.length()==0) {
                    if(eWeight.length() == 0)
                    {
                        eWeight.setError("Enter Weight");
                    }
                    else if(eHeight.length()==0)
                    {
                        eHeight.setError("Enter the height");
                    }
                    else if(eAge.length()==0)
                    {
                        eAge.setError("Enter the age");
                    }
                    else if (healthcondition.length()==0)
                    {
                        healthcondition.setError("Enter the medical condition");
                    }

                }


                else{
                    if(eInches.length()==0)
                    {

                        eInches.setText((String.valueOf(0)));
                        weight = Float.parseFloat(eWeight.getText().toString());
                        h = Float.parseFloat(eHeight.getText().toString());
                        i = Float.parseFloat(eInches.getText().toString());
                        age = Float.parseFloat(eAge.getText().toString());
                        healcon = Integer.parseInt(healthcondition.getText().toString());
                        String srate = healthcondition.getText().toString();
                        optionsChecked();

                        calculateBMI();
                        calculateBMR();
                        bdietplan.setVisibility(VISIBLE);
                    }
                    else
                    {
                        weight = Float.parseFloat(eWeight.getText().toString());
                        h = Float.parseFloat(eHeight.getText().toString());
                        i = Float.parseFloat(eInches.getText().toString());
                        age = Float.parseFloat(eAge.getText().toString());
                        healcon = Integer.parseInt(healthcondition.getText().toString());
                        String srate = healthcondition.getText().toString();
                        optionsChecked();

                        calculateBMI();
                        calculateBMR();
                        bdietplan.setVisibility(VISIBLE);
                    }

                }
                w =eWeight.getText().toString();
                H =eHeight.getText().toString();
                a = eAge.getText().toString();
                I = eInches.getText().toString();
                HC= healthcondition.getText().toString();
                //..
                //dbh.insertdiet(String Email, weight, h, age,"Male",healcon);
                dbh.insertdiet(email, w, H,a, gender,I, HC);
            }
        });

        bdietplan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("bmi is","value"+resultfb);
                optionsChecked();
                if(healcon ==1) {
                    startActivity(new Intent(getApplicationContext(), DiabetesDP.class));
                }
                else if(healcon ==2)
                {
                    startActivity(new Intent(getApplicationContext(), ObesityDP.class));
                }
                else if(healcon ==3)
                {
                    startActivity(new Intent(getApplicationContext(), CancerDP.class));
                }
                else if(healcon ==4)
                {
                    startActivity(new Intent(getApplicationContext(), CholestrolDP.class));
                }
                else if(healcon ==5)
                {
                    startActivity(new Intent(getApplicationContext(), OsteoporosisDP.class));
                }



          /*      if(resultfb<= 18.5) {
                    Intent t = new Intent(MainActivity1.this, DietPlan.class);
                    String s = Float.toString(calorieintake);
                    Log.d("bmi is","value"+calorieintake);
                    t.putExtra("calories", s);
                    startActivityForResult(t, 0);
                }
                else if (resultfb >= 18.5 && resultfb <=24.9)
                {
                    Intent t1 = new Intent(MainActivity1.this, NormalWeight.class);
                    String s = Float.toString(calorieintake);
                    Log.d("bmi is","value"+calorieintake);
                    t1.putExtra("calories", s);
                    startActivityForResult(t1, 0);
                }
                else if (resultfb >= 25.0 && resultfb <= 29.9){

                    Intent t1 = new Intent(MainActivity1.this, OverWeight.class);
                    String s = Float.toString(calorieintake);
                    Log.d("bmi is","value"+calorieintake);
                    t1.putExtra("calories", s);
                    startActivityForResult(t1, 0);

                }
                else if ((resultfb >= 30.0))
                {
                    Intent t1 = new Intent(MainActivity1.this, Obese.class);
                    String s = Float.toString(calorieintake);
                    t1.putExtra("calories", s);
                    Log.d("bmi is","value"+calorieintake);
                    startActivityForResult(t1, 0);
                } */
            }
        });
    }

    public void calculateBMI(){
        wpounds = (float)0.45;
        wpounds = weight* wpounds;
        //Log.d("weightinpounds","value" + wpounds);
        optionsChecked();
        hm = h*12;
        hm = hm+i;
        float hinches = (float)0.025;
        float heightininches = hm*hinches;
        float sinches = heightininches *heightininches;
        //Log.d("sinches","value" + sinches);
        BMI = wpounds /sinches;
        //Log.d("BMI","BMI" + BMI);
        result = (float)(Math.round(BMI * 100.0) / 100.0);
        resultfb = (float)result;
        tBMIResult.setVisibility(VISIBLE);
        tBMIResult.setTextSize(18);
        tBMIResult.setTypeface(tBMIResult.getTypeface(), Typeface.BOLD);
        tBMIResult.setText(Float.toString(resultfb));
        tBMILable.setVisibility(VISIBLE);
        tBMILable.setTextSize(18);
        tBMILable.setTypeface(tBMILable.getTypeface(), Typeface.BOLD);
        displayBodyType();

    }

    public void displayBodyType(){
        optionsChecked();
        if(resultfb<= 18.5){
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("UnderWeight");
        }
        else if (resultfb >= 18.5 && resultfb <=24.9){
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("NormalWeight");

        }
        else if (resultfb >= 25.0 && resultfb <= 29.9){
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("OverWeight");

        }
        else if (resultfb >= 30.0){
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("Obese");
        }
        else
        {

        }
    }

    public void calculateBMR(){
        optionsChecked();
        if(rmale.isChecked()) {
            bmrMen = 66 + (6.23 * weight) + (12.7 * hm) - (6.8 * age);
            Log.d("male", "value" + bmrMen);
            result = (Math.round(bmrMen * 100.0) / 100.0);
            resultf = (float) result;
            Log.d("male", "value" + bmrMen);
            tBMRLable.setVisibility(VISIBLE);
            tBMRLable.setTextSize(18);
            tBMRLable.setTypeface(tBMRLable.getTypeface(), Typeface.BOLD);
            tBMRResult.setVisibility(VISIBLE);
            tBMRResult.setTextSize(18);
            tBMRResult.setTypeface(tBMRResult.getTypeface(), Typeface.BOLD);

            bmrMen = Math.round(bmrMen);
            tBMRResult.setText(Double.toString(bmrMen));
            tcalories.setVisibility(VISIBLE);
            tcalories.setTextSize(18);
            tcalories.setTypeface(tcalories.getTypeface(), Typeface.BOLD);
            tcaloriesresult.setVisibility(INVISIBLE);
            tcaloriesresult.setTextSize(18);
            tcaloriesresult.setTypeface(tcaloriesresult.getTypeface(), Typeface.BOLD);
            healcon = Integer.parseInt(healthcondition.getText().toString());
            if (healcon == 1)
            {
                calorieintake = resultf * (float) 1.2;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else if(healcon ==2)
            {
                calorieintake = resultf * (float) 1.375;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            }
            else if(healcon ==3)
            {
                calorieintake = resultf * (float) 1.55;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            }
            else if (healcon ==4)
            {
                calorieintake = resultf * (float) 1.725;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else if (healcon ==5)
            {
                calorieintake = resultf * (float) 1.9;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(dietActivity.this);
                alert.setTitle("Activity Rate Error");
                alert.setMessage("Enter valid Activity Rate from 1 to 5 ...");
                alert.setPositiveButton("OK",null);
                alert.show();
            }
        }
        else if (rfemale.isChecked())
        {
            bmrWomen = 655 + (4.35 * weight) + (4.7 *hm) - (4.7 *age);
            Log.d("female","value" +bmrWomen);
            Log.d("female","valueW" +weight);
            Log.d("female","valuei" +inches);
            Log.d("female","valuea" +age);
            result = (Math.round(bmrWomen * 100.0) / 100.0);
            resultf = (float)result;
            Log.d("female","value" +bmrWomen);
            tBMRLable.setVisibility(VISIBLE);
            tBMRLable.setTextSize(18);
            tBMRLable.setTypeface(tBMRLable.getTypeface(), Typeface.BOLD);

            tBMRResult.setVisibility(VISIBLE);
            tBMRResult.setTextSize(18);
            tBMRResult.setTypeface(tBMRResult.getTypeface(), Typeface.BOLD);

            tcalories.setVisibility(VISIBLE);
            tcalories.setTextSize(18);
            tcalories.setTypeface(tcalories.getTypeface(), Typeface.BOLD);

            tcaloriesresult.setVisibility(INVISIBLE);
            tcaloriesresult.setTextSize(18);
            tcaloriesresult.setTypeface(tcalories.getTypeface(), Typeface.BOLD);

            bmrWomen = Math.round(bmrWomen);
            tBMRResult.setText(Double.toString(bmrWomen));
            healcon = Integer.parseInt(healthcondition.getText().toString());
            if (healcon == 1)
            {
                calorieintake = resultf * (float) 1.2;
                calorieintake = Math.round(calorieintake);
                Log.d("female","value" +calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else if(healcon ==2)
            {
                calorieintake = resultf * (float) 1.375;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            }
            else if(healcon ==3)
            {
                calorieintake = resultf * (float) 1.55;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            }
            else if (healcon ==4)
            {
                calorieintake = resultf * (float) 1.725;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else if (healcon ==5)
            {
                calorieintake = resultf * (float) 1.9;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(dietActivity.this);
                alert.setTitle("Activity Rate Error");
                alert.setMessage("Enter valid Activity Rate from 1 to 5 ...");
                alert.setPositiveButton("OK",null);
                alert.show();
            }

        }

    }

    public void optionsChecked(){
        Log.d("inside","male"+rmale.isChecked());
        Log.d("inside","female"+rfemale.isChecked());
        if(rmale.isChecked()){
            Log.d("inside","male");
            rfemale.setChecked(false);
        }
        else
        {
            Log.d("inside","male");
            rmale.setChecked(false);

        }

    }

}