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

    APICaller apiCaller;
    IODatabase io;
    String userID;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container, false);

        //Spinner
        Spinner spinner = v.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.diets, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //CheckBox
        checkBox = v.findViewById(R.id.checkbox);

        //Text inputs
        textInputBeef = v.findViewById(R.id.text_input_beef);
        textInputFish = v.findViewById(R.id.text_input_fish);
        textInputPork = v.findViewById(R.id.text_input_pork);
        textInputDairy = v.findViewById(R.id.text_input_dairy);
        textInputCheese = v.findViewById(R.id.text_input_cheese);
        textInputRice = v.findViewById(R.id.text_input_rice);
        textInputEgg = v.findViewById(R.id.text_input_egg);
        textInputWinterSalad = v.findViewById(R.id.text_input_winterSalad);
        textInputRestaurant = v.findViewById(R.id.text_input_restaurant);

        // Variables used with API and Firebase
        mAuth = FirebaseAuth.getInstance();
        io = IODatabase.getInstance();
        apiCaller = APICaller.getInstance(getActivity().getApplicationContext());
        userID = mAuth.getCurrentUser().getUid();

        //Confirm button
        confirm_Button = v.findViewById(R.id.confirm_button);
        confirm_Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("TESTI");

                if (checkBox.isChecked()) {
                    lowCarbon = "true";
                } else {
                    lowCarbon = "false";
                }

                // Checks if textInput is empty. If is empty saves value as 0. If not saves value as double.
                if (textInputBeef.getEditText().getText().toString().equals("")){ doubleBeef = 0;
                } else { doubleBeef = Double.parseDouble(textInputBeef.getEditText().getText().toString()); }
                if (textInputFish.getEditText().getText().toString().equals("")){ doubleFish = 0;
                } else { doubleFish = Double.parseDouble(textInputFish.getEditText().getText().toString()); }
                if (textInputPork.getEditText().getText().toString().equals("")) { doublePork = 0;
                } else { doublePork = Double.parseDouble(textInputPork.getEditText().getText().toString()); }
                if  (textInputDairy.getEditText().getText().toString().equals("")){ doubleDairy = 0;
                } else { doubleDairy = Double.parseDouble(textInputDairy.getEditText().getText().toString()); }
                if (textInputCheese.getEditText().getText().toString().equals("")) { doubleCheese = 0;
                } else { doubleCheese = Double.parseDouble(textInputCheese.getEditText().getText().toString()); }
                if (textInputRice.getEditText().getText().toString().equals("")) { doubleRice = 0;
                } else { doubleRice = Double.parseDouble(textInputRice.getEditText().getText().toString()); }
                if (textInputEgg.getEditText().getText().toString().equals("")) { doubleEgg = 0;
                } else { doubleEgg = Double.parseDouble(textInputEgg.getEditText().getText().toString()); }
                if (textInputWinterSalad.getEditText().getText().toString().equals("")) { doubleWinterSalad = 0;
                } else { doubleWinterSalad = Double.parseDouble(textInputWinterSalad.getEditText().getText().toString()); }
                if (textInputRestaurant.getEditText().getText().toString().equals("")) { doubleRestaurant = 0;
                } else { doubleRestaurant = Double.parseDouble(textInputRestaurant.getEditText().getText().toString()); }

                // Arraylist with all parameters
                ArrayList<String> params = new ArrayList<String>();
                params.add("omnivore");
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
                params.add(String.valueOf(Math.round((doubleRestaurant / (71.0)) * 100)));

                for(int i = 0; i < params.size(); i++) {
                    System.out.print(params.get(i)+" ");
                }

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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
