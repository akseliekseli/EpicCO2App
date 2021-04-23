package com.example.epicco2app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class SettingsFragment extends Fragment {
    private TextView bmiText,infoText;
    private EditText heigthIn, weigthIn;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_settings, container, false);
        heigthIn = (EditText) layout.findViewById(R.id.heigthIn);
        weigthIn = (EditText) layout.findViewById(R.id.weigthIn);
        bmiText  = (TextView) layout.findViewById(R.id.bmiOut);
        infoText = (TextView) layout.findViewById(R.id.textView2);

        return layout;
    }

    public void upDate(View view){
        /* Bmi = (1.3*massa)/(pituus)^^ 2.5
        bmiText.setText("bmi");
        infoText.setText("Painoindeksi on ... ");*/

    }
    public void logOut(){
        FirebaseAuth.getInstance().signOut();
    }
}
