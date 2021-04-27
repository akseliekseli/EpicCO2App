package com.example.epicco2app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private String selection;
    private String diet;
    private String lowCarbon;
    private TextInputLayout textInputBeef;
    private TextInputLayout textInputFish;
    private TextInputLayout textInputPork;
    private TextInputLayout textInputDairy;
    private TextInputLayout textInputCheese;
    private TextInputLayout textInputRice;
    private TextInputLayout textInputEgg;
    private TextInputLayout textInputWinterSalad;
    private TextInputLayout textInputRestaurant;
    private double doubleBeef;
    private double doubleFish;
    private double doublePork;
    private double doubleDairy;
    private double doubleCheese;
    private double doubleRice;
    private double doubleEgg;
    private double doubleWinterSalad;
    private double doubleRestaurant;
    private Button confirm_Button;
    private CheckBox checkBox;
    private TextView textView;

    APICaller apiCaller;
    IODatabase io;
    String userID;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container, false);

        // Variables used with API and Firebase
        mAuth = FirebaseAuth.getInstance();
        io = IODatabase.getInstance();
        apiCaller = APICaller.getInstance(getActivity().getApplicationContext());
        userID = mAuth.getCurrentUser().getUid();

        //Variables used in layout
        Spinner spinner = v.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.diets, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        checkBox = v.findViewById(R.id.checkbox);

        textInputBeef = v.findViewById(R.id.text_input_beef);
        textInputFish = v.findViewById(R.id.text_input_fish);
        textInputPork = v.findViewById(R.id.text_input_pork);
        textInputDairy = v.findViewById(R.id.text_input_dairy);
        textInputCheese = v.findViewById(R.id.text_input_cheese);
        textInputRice = v.findViewById(R.id.text_input_rice);
        textInputEgg = v.findViewById(R.id.text_input_egg);
        textInputWinterSalad = v.findViewById(R.id.text_input_winterSalad);
        textInputRestaurant = v.findViewById(R.id.text_input_restaurant);

        textView = v.findViewById(R.id.c02text);

        confirm_Button = v.findViewById(R.id.confirm_button);
        confirm_Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tests if all inputs are correct
                if (!validateBeefInput()| !validateFishInput() | !validatePorkInput() | !validateDairyInput() | !validateCheeseInput() | !validateRiceInput() | !validatEggInput() | !validateWinterSaladInput() | !validateRestaurantInput()){
                    return;
                }

                if (checkBox.isChecked()) {
                    lowCarbon = "true";
                } else {
                    lowCarbon = "false";
                }


                // Arraylist with all parameters
                ArrayList<String> params = new ArrayList<String>();
                params.add(diet);
                params.add(lowCarbon);
                //Percentage of consumption compared to Finnish average, rounded and converted to string
                params.add(String.valueOf(Math.round((doubleBeef / (400.0)) * 100)));
                params.add(String.valueOf(Math.round((doubleFish / (600.0)) * 100)));
                params.add(String.valueOf(Math.round((doublePork / (1000.0)) * 100)));
                params.add(String.valueOf(Math.round((doubleDairy / (3800.0)) * 100)));
                params.add(String.valueOf(Math.round((doubleCheese / (300.0)) * 100)));
                params.add(String.valueOf(Math.round((doubleRice / (90.0)) * 100)));
                params.add(String.valueOf(Math.round((doubleEgg / (3.0)) * 100)));
                params.add(String.valueOf(Math.round((doubleWinterSalad / (1400.0)) * 100)));
                params.add(String.valueOf(Math.round((doubleRestaurant * 4))));

                /*
                Calling the API and saving the response to the Firebase database
                Callback interface is used to transfer the data in async call.
                 */
                apiCaller.call(params, new APICaller.VolleyCallback() {
                    // onSuccess method is called after the request is complete
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        FoodLogObject foodLogObject = null;
                        foodLogObject = new FoodLogObject();
                        foodLogObject.setFromJSON(response);
                        io.addFoodToDB(userID, foodLogObject);
                        Log.v("Async", "API call successful");
                        Toast.makeText(FoodFragment.this.getContext(), "Kirjauksesi onnistui.", Toast.LENGTH_SHORT).show();
                        textView.setText("Tuotit näin paljon C02");

                    }
                });
            }


        });

        return v;
    }

    // Spinner onItemSelected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selection = parent.getItemAtPosition(position).toString();
        switch (selection){
            case "Kaikkiruokainen":
                diet = "omnivore";
                break;
            case "Kasvissyöjä":
                diet = "vegetarian";
                break;
            case "Vegaani":
                diet = "vegan";
                break;
        }

    }
    // Spinner if othing selected
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Tests to see if inputs are correct for the API and sets input as 0 if empty
    private boolean validateBeefInput() {
        if (textInputBeef.getEditText().getText().toString().equals("")){ doubleBeef = 0;
        } else { doubleBeef = Double.parseDouble(textInputBeef.getEditText().getText().toString()); }

        if (0 > doubleBeef || doubleBeef > 800) {
            textInputBeef.setError("Ei voi olla alle 0 tai yli 800");
            return false;
        } else {
            textInputBeef.setError(null);
            return true;
        }

    }
    private boolean validateFishInput() {
        if (textInputFish.getEditText().getText().toString().equals("")){ doubleFish = 0;
        } else { doubleFish = Double.parseDouble(textInputFish.getEditText().getText().toString()); }

        if (0 > doubleFish || doubleFish > 1200) {
            textInputFish.setError("Ei voi olla alle 0 tai yli 1200");
            return false;
        } else {
            textInputFish.setError(null);
            return true;
        }

    }
    private boolean validatePorkInput() {
        if (textInputPork.getEditText().getText().toString().equals("")) { doublePork = 0;
        } else { doublePork = Double.parseDouble(textInputPork.getEditText().getText().toString()); }

        if (0 > doublePork || doublePork > 2000) {
            textInputPork.setError("Ei voi olla alle 0 tai yli 2000");
            return false;
        } else {
            textInputPork.setError(null);
            return true;
        }

    }
    private boolean validateDairyInput() {
        if  (textInputDairy.getEditText().getText().toString().equals("")){ doubleDairy = 0;
        } else { doubleDairy = Double.parseDouble(textInputDairy.getEditText().getText().toString()); }

        if (0 > doubleDairy || doubleDairy > 7600) {
            textInputDairy.setError("Ei voi olla alle 0 tai yli 7600");
            return false;
        } else {
            textInputDairy.setError(null);
            return true;
        }

    }
    private boolean validateCheeseInput() {
        if (textInputCheese.getEditText().getText().toString().equals("")) { doubleCheese = 0;
        } else { doubleCheese = Double.parseDouble(textInputCheese.getEditText().getText().toString()); }

        if (0 > doubleCheese || doubleCheese > 600) {
            textInputCheese.setError("Ei voi olla alle 0 tai yli 600");
            return false;
        } else {
            textInputCheese.setError(null);
            return true;
        }

    }
    private boolean validateRiceInput() {
        if (textInputRice.getEditText().getText().toString().equals("")) { doubleRice = 0;
        } else { doubleRice = Double.parseDouble(textInputRice.getEditText().getText().toString());}

            if (0 > doubleRice || doubleRice > 180) {
            textInputRice.setError("Ei voi olla alle 0 tai yli 180");
            return false;
        } else {
            textInputRice.setError(null);
            return true;
        }

    }
    private boolean validatEggInput() {
        if (textInputEgg.getEditText().getText().toString().equals("")) { doubleEgg = 0;
        } else { doubleEgg = Double.parseDouble(textInputEgg.getEditText().getText().toString()); }

        if (0 > doubleEgg || doubleEgg > 33) {
            textInputEgg.setError("Ei voi olla alle 0 tai yli 33");
            return false;
        } else {
            textInputEgg.setError(null);
            return true;
        }

    }
    private boolean validateWinterSaladInput() {
        if (textInputWinterSalad.getEditText().getText().toString().equals("")) { doubleWinterSalad = 0;
        } else { doubleWinterSalad = Double.parseDouble(textInputWinterSalad.getEditText().getText().toString()); }

        if (0 > doubleWinterSalad || doubleWinterSalad > 2800) {
            textInputWinterSalad.setError("Ei voi olla alle 0 tai yli 2800");
            return false;
        } else {
            textInputWinterSalad.setError(null);
            return true;
        }

    }
    private boolean validateRestaurantInput() {
        if (textInputRestaurant.getEditText().getText().toString().equals("")) { doubleRestaurant = 0;
        } else { doubleRestaurant = Double.parseDouble(textInputRestaurant.getEditText().getText().toString()); }

        if (0 > doubleRestaurant || doubleRestaurant > 200) {
            textInputRestaurant.setError("Ei voi olla alle 0 tai yli 200");
            return false;
        } else {
            textInputRestaurant.setError(null);
            return true;
        }

    }

}
