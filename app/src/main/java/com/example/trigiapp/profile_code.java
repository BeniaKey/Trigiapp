package com.example.trigiapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class profile_code extends Fragment
{
      private int text;
      private int thepriority;

        // È il momento in cui viene creato il layout del Fragment,si fa uso del LayoutInflater;
          @Override
          public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
          {

            //viene specificato il layout xml ed utilizzato il metodo inflate in modo tale che ritorni il layout sottoforma di una View
            // La quale sarà salvata nella variabile "view"
              View view = inflater.inflate(R.layout.fragment_code, container, false);
              TextView textView = view.findViewById(R.id.textView13);
              TextView textView1 = view.findViewById(R.id.priority);

                  //se dall'activity gestione riceviamo delle variabili allora
                  // settiamo nelle text view l'identificativo e la priorità
                  if(this.getArguments() != null)
                  {
                        text = this.getArguments().getInt("codiceprofilopersonale");
                        thepriority = this.getArguments().getInt("priority");
                        textView.setText(String.valueOf(text ));
                        textView1.setText(String.valueOf(thepriority));

                  }


            //ritorna la view che prima è stata assegnata
               return view;

          }
}
