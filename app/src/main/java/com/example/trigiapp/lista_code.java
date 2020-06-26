package com.example.trigiapp;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class lista_code extends Fragment
{

                // Dichiariamo il RecyclerView adapter per il cloud firestore
                FirestoreRecyclerAdapter adapter;

                //  Accedo all'istanza Cloud Firestore da questo Fragment(lista_code)
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                RecyclerView recyclerView;


                  // È il momento in cui viene creato il layout del Fragment,si fa uso del LayoutInflater;
                  @Override
                  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
                  {
                    //viene specificato il layout xml(fragment_lista_code) ed utilizzato il metodo inflate in modo tale che ritorni il layout sottoforma di una View
                    // La quale sarà salvata nella variabile "view"
                      View ContactsView = inflater.inflate(R.layout.fragment_lista_code, container, false);

                     //dichiaro e assegno la variabile riferita al componente FAB
                      FloatingActionButton email = ContactsView.findViewById(R.id.fabbb);

                      //se viene cliccato esso apre un altra Activity in un altra app effettuando una semplice azione
                      // in questo caso apre l'email in gmail con oggetto definito e email dell'ospedale
                      // con la possibilità di scrivere e quindi successivamente inviarla
                      email.setOnClickListener(new View.OnClickListener()
                      {
                           @Override
                            public void onClick(View v)
                           {
                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                emailIntent.setType("message/rfc822");
                                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info.urp@ausl.pe.it"});
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Problemi Pronto Soccorso");
                                startActivity(emailIntent);
                           }
                      });



                                recyclerView= ContactsView.findViewById(R.id.codicilista);
                                Query query = db.collection("Codice");

                                //permette l'inserimento della query nell'adapter e converte i dati presi nel database
                                // nel tipo Model
                                FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                               .setQuery(query , Model.class)
                                  .build();

                                //lega le query alla recyclerview e risponde a tutti gli eventi in real time come l'inserimento, rimozione, spostamento o il cambiamento
                                //creiamo l'oggetto firestoreRecyclerAdapter
                              adapter = new FirestoreRecyclerAdapter<Model, ModelHolder>(options)
                              {

                                       @NonNull
                                       @Override
                                       public ModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                                       {
                                             //creiamo un istanza del ViewHolder nel nostro caso è personalizzata
                                             // i layout per ogni elemento è rappresentato dal R.layout.rowitem
                                            View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem,parent,false);
                                            return new ModelHolder(view);
                                       }


                                       // qui leghiamo gli oggetti della classe Model con ModelHolder
                                       @Override
                                       protected void onBindViewHolder(@NonNull final ModelHolder holder, int position, @NonNull final Model model)
                                       {
                                                      //organizzati per il colore del codice effettiuamo il bind

                                                        if(model.getCodicecolore().equals("Verde"))
                                                        {
                                                            holder.codicecolore.setBackgroundResource(R.drawable.codice_verde);
                                                            holder.codicecolore.setText("V");
                                                            holder.priorità.setText("Priorità:" +"  "+String.valueOf(model.getPriorità()));
                                                            holder.codice.setText(String.valueOf(model.getIdentification()));

                                                        }



                                                        if(model.getCodicecolore().equals("Giallo"))
                                                        {
                                                            holder.codicecolore.setBackgroundResource(R.drawable.codice_giallo);
                                                            holder.codicecolore.setText("G");
                                                            holder.priorità.setText("Priorità:" +"  "+String.valueOf(model.getPriorità()));
                                                            holder.codice.setText(String.valueOf(model.getIdentification()));
                                                        }

                                                        if(model.getCodicecolore().equals("Bianco"))
                                                        {
                                                            holder.codicecolore.setBackgroundResource(R.drawable.codice_bianco);
                                                            holder.codicecolore.setText("B");
                                                            holder.priorità.setText("Priorità:"+"  "+String.valueOf(model.getPriorità()));
                                                            holder.codice.setText(String.valueOf(model.getIdentification()));

                                                        }




                                       }
                               };
                                         //
                                         recyclerView.setHasFixedSize(true);
                                         //utilizziamo un layout manager standard
                                         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                        //settiamo l'adapter
                                         recyclerView.setAdapter(adapter);








                                         return ContactsView;



                  }


                  //sono il lyfecycle dell'adapter

                             @Override
                             public void onStart()
                             {
                               //si mette in ascolto di vari cambiamenti
                                super.onStart();
                                adapter.startListening();


                             }

                             @Override
                              public void onStop()
                             {
                               //quando l'app viene stoppata con essa l'adapter smette di ascoltare
                                super.onStop();
                                adapter.stopListening();
                             }



}
