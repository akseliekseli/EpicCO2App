package com.example.epicco2app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;

public class BMIFragment extends Fragment {
    private TextView bmiText,infoText;
    private TextInputLayout heigthIn, weigthIn;
    private Button button;
    String userID;
    IODatabase io;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_settings, container, false);
        heigthIn = (TextInputLayout) layout.findViewById(R.id.heigthIn);
        weigthIn = (TextInputLayout) layout.findViewById(R.id.weigthIn);
        bmiText  = (TextView) layout.findViewById(R.id.bmiOut);
        infoText = (TextView) layout.findViewById(R.id.textView2);
        button = (Button) layout.findViewById(R.id.button2);

        userID = ((HomeActivity)getActivity()).userID;
        io = ((HomeActivity)getActivity()).io;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upDate();
            }
        });
        return layout;
    }

    public void upDate(){
        float weight = Float.parseFloat(weigthIn.getEditText().getText().toString());
        float height = Float.parseFloat(heigthIn.getEditText().getText().toString());
        height = height/100;
        float bmi = (weight)/(height*height);
        DecimalFormat df = new DecimalFormat("#.#");
        String b = df.format(bmi);
        bmiText.setText("Painoindeksisi on " + b);
        if (bmi < 18.0 ){
            infoText.setText("Painoindeksin mukaan olet merkittävästi alipainoinen.");
        }else if(18.0<=bmi && bmi <= 19.9 ){
            infoText.setText("Painoindeksin mukaan olet lievästi alipainoinen.");
        }else if(19.0<=bmi && bmi <= 24.9 ){
            infoText.setText("Painoindeksin mukaan olet normaali painoinen.");
        }else if (25.0<=bmi && bmi <= 29.9){
            infoText.setText("Painoindeksin mukaan olet lievästi ylipainoinen.");
        }else if(30.0<=bmi && bmi <= 34.9){
            infoText.setText("Painoindeksin mukaan olet ylipainoinen.");
        }else{
            infoText.setText("Painoindeksin mukaan olet merkittävästi ylipainoinen.");
        }

        // Add BMI, weight and height to database.
        WeightLogObject logObject = new WeightLogObject();
        logObject.setWeight(weight);
        logObject.setHeight(height);
        logObject.setBmi(bmi);
        io.addWeightToDB(userID, logObject);
        Toast.makeText(BMIFragment.this.getContext(), "Tiedot tilastoitu.", Toast.LENGTH_SHORT).show();

    }

}
