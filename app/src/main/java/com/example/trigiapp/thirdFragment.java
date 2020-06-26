package com.example.trigiapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

//il terzo Fragments il quale si riferisce al codice giallo
public class thirdFragment extends Fragment
{
      TextView textView ;
     //Accedo all'istanza Cloud Firestore da questo fragments (thirdFragments)
      FirebaseFirestore db = FirebaseFirestore.getInstance();
      int count = 0;

          // È il momento in cui viene creato il layout del Fragment,si fa uso del LayoutInflater;
           @Override
           public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
           {
                  //viene specificato il layout xml(R.layout.fragment_third) ed utilizzato il metodo inflate in modo tale che ritorni il layout sottoforma di una View
                  // La quale sarà salvata nella variabile "view"
                   View view =  inflater.inflate(R.layout.fragment_third, container, false);
                   textView = view.findViewById(R.id.personeg);


                        // in questo caso selezioniamo tutti gli utenti nel database con codice giallo e in una specifica
                        // textview inseriamo il numero di persone in fila per quel determinato codice
                        db.collection("Codice")
                       .whereEqualTo("codicecolore", "Giallo")
                         .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                        {
                             @Override
                             public void onComplete(@NonNull Task<QuerySnapshot> task)
                             {
                                   if (task.isSuccessful())
                                    {
                                        for (QueryDocumentSnapshot document : task.getResult())
                                        {
                                          count++;
                                        }
                                          textView.setText("Numero di persone in fila:" + " " + String.valueOf(count));

                                    }

                             }
                        });

             //ritorna la view che prima è stata assegnata
             return  view;




           }
}
