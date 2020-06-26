package com.example.trigiapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;



public class causa extends AppCompatActivity
{


  private boolean causa1b = false;
  private boolean causa2b= false;
  private boolean causa3b = false;
  private boolean push= false;
  boolean same ;
  //dichiaro una variabile di tipo Model
  Model date = new Model();
  int miavar;
  final ArrayList<Integer> identification = new ArrayList<>();
  int priority=0;
//Accedo all'istanza Cloud Firestore da questa Activity(causa)
 FirebaseFirestore db = FirebaseFirestore.getInstance();


       @Override
        protected void onCreate(Bundle savedInstanceState)
       {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_causa);
             final Button causa1 = findViewById(R.id.causa1);
             final Button causa2 = findViewById(R.id.causa2);
             final Button causa3 = findViewById(R.id.causa3);
             Button next = findViewById(R.id.next);
             Button back = findViewById(R.id.back);

             // stesso funzionamento nell'activity di zonaCorpo ci serviamo delle tre variabili cause per impostare in blu il button selezionato e in bianco
             // le restanti due mentre push serve sempre a verificare se una delle cause verrà selezionata
             causa1.setOnClickListener(new View.OnClickListener()
             {
                  @Override
                   public void onClick(View v)
                   {

                     if(!causa1b)
                     {
                        causa1.setBackgroundResource(R.drawable.backnext_button);
                        causa2.setBackgroundResource(R.drawable.border_button);
                        causa3.setBackgroundResource(R.drawable.border_button);
                        push=true;
                        causa1b = true;
                        causa2b = false;
                        causa3b = false;
                     }

                   }
             });


             // stesso funzionamento nell'activity di zonaCorpo ci serviamo delle tre variabili cause per impostare in blu il button selezionato e in bianco in questo caso solo la seconda causa è selezionata
             // le restanti due mentre push serve sempre a verificare se una delle cause verrà selezionata
             causa2.setOnClickListener(new View.OnClickListener()
             {
                  @Override
                  public void onClick(View v)
                  {

                    if(!causa2b)
                    {
                       causa2.setBackgroundResource(R.drawable.backnext_button);
                       causa1.setBackgroundResource(R.drawable.border_button);
                       causa3.setBackgroundResource(R.drawable.border_button);
                       push=true;
                       causa1b = false;
                       causa2b = true;
                       causa3b = false;
                    }
                  }
             });

            // stesso funzionamento nell'activity di zonaCorpo ci serviamo delle tre variabili cause per impostare in blu il button selezionato e in bianco in questo caso solo la terza causa è selezionata
            // le restanti due mentre push serve sempre a verificare se una delle cause verrà selezionata
            causa3.setOnClickListener(new View.OnClickListener()
             {
                   @Override
                    public void onClick(View v)
                   {

                      if(!causa3b)
                      {
                         causa3.setBackgroundResource(R.drawable.backnext_button);
                         causa1.setBackgroundResource(R.drawable.border_button);
                         causa2.setBackgroundResource(R.drawable.border_button);
                         push=true;
                         causa1b = false;
                         causa2b = false;
                         causa3b = true;
                      }
                   }
             });


            back.setOnClickListener(new View.OnClickListener()
            {
                  @Override
                  public void onClick(View v)
                  {
                    backActivity();
                  }
            });


            // attraverso una prima funzione creazioneCodiciCasuali viene creato un codice casuale da 100 a 300 nel nostro caso la quale verrà assegnato all'utente
             // però prima di assegnarlo bisogna verificare attraverso la funzione verificaEsistenza se questo codice già esiste nella lista di utenti nell'ospedale
            // se cosi fosse in tale metodo c'è un comando while che finche non trova un codice che non è presente nella lista non si ferma e giustamente si ferma se
             // trova un codice non contenuto all'interno della lista
               miavar = creazioneCodiciCasuali();
               verificaEsistenza();


             next.setOnClickListener(new View.OnClickListener()
             {
                   @Override
                   public void onClick(View v)
                   {
                     nextActivity();
                   }
             });





       }



               public void backActivity()
               {
                  Intent back = new Intent(this,  zonaCorpo.class);
                  startActivity(back);

               }


              public void nextActivity()
              {

                //Raccogliamo tutti i dati proveniente dall'activity zonaCorpo per poi inserirli all'interno di tali variabili
                // le quali ci serviranno per assegnare un determinato codice in base alle referenze del paziente
                Bundle bundle = getIntent().getExtras();
                boolean primi2 = bundle.getBoolean("2primi");
                boolean secondoprim = bundle.getBoolean("secondoprimo");
                boolean terzoprim = bundle.getBoolean("terzoprimo");
                boolean terzoterzo = bundle.getBoolean("terzoterzo");
                boolean terzosecondo = bundle.getBoolean("terzosecondo");
                boolean secondosecondo = bundle.getBoolean("secondosecondo");
                boolean secondoterzo = bundle.getBoolean("secondoterzo");
                boolean primosecondo = bundle.getBoolean("primosecondo");
                boolean primoterzo= bundle.getBoolean("primoterzo");




                 // Se l'utente clicca il primo range dell'intensità di dolore(0-2) e zona corpo testa con causa indifferente
                 // o seleziona il secondo range dell'intensità di dolore(2-4) e zona corpo testa con causa indifferente
                 // gli sara assegnato un identificativo con codice verde e priorità 2 in una determinata data e ora
                 // quindi il tutto sarà inserito nel database
                  if (primi2 || secondoprim)
                  {
                     Map<String, Object> docData = new HashMap<>();
                     docData.put("codicecolore", "Verde");
                     docData.put("identification", miavar);
                     docData.put("priorità",priority = 2);
                     docData.put("date", date.getCurrentTime());
                     db.collection("Codice").document()
                    .set(docData)
                       .addOnSuccessListener(new OnSuccessListener<Void>()
                       {
                          @Override
                          public void onSuccess(Void aVoid)
                          {
                             Log.d("successo", "stato inserito");
                          }
                       });
                  }


                  // Se l'utente clicca il terzo range dell'intensità di dolore(4-6) e zona corpo testa con causa indifferente
                  // gli sara assegnato un identificativo con codice giallo e priorità 1 in una determinata data e ora
                  // quindi il tutto sarà inserito nel database
                  if (terzoprim)
                  {
                     Map<String, Object> docData = new HashMap<>();
                     docData.put("codicecolore", "Giallo");
                     docData.put("identification", miavar);
                     docData.put("priorità",priority =1);
                     docData.put("date", date.getCurrentTime());
                     db.collection("Codice").document()
                       .set(docData)
                       .addOnSuccessListener(new OnSuccessListener<Void>()
                       {
                          @Override
                          public void onSuccess(Void aVoid)
                          {
                            Log.d("successo", "stato inserito");
                          }
                       });

                  }



                // Se l'utente clicca il terzo range dell'intensità di dolore(4-6) e zona corpo zona bassa compresa di arti inferiori con causa indifferente
                // gli sara assegnato un identificativo con codice giallo e priorità 1 in una determinata data e ora
                // quindi il tutto sarà inserito nel database
                   if (terzoterzo)
                   {

                      Map<String, Object> docData = new HashMap<>();
                      docData.put("codicecolore", "Giallo");
                      docData.put("identification", miavar);
                      docData.put("priorità",priority = 1);
                      docData.put("date", date.getCurrentTime());
                      db.collection("Codice").document()
                       .set(docData)
                       .addOnSuccessListener(new OnSuccessListener<Void>()
                       {

                          @Override
                          public void onSuccess(Void aVoid)
                          {
                            Log.d("successo", "stato inserito");
                          }
                       });

                   }

                // Se l'utente clicca il terzo range dell'intensità di dolore(4-6) e zona corpo zona centrale compresa di arti superiori con causa indifferente
                // gli sara assegnato un identificativo con codice giallo e priorità 1 in una determinata data e ora
                // quindi il tutto sarà inserito nel database

                  if (terzosecondo)
                  {

                     Map<String, Object> docData = new HashMap<>();
                     docData.put("codicecolore", "Giallo");
                     docData.put("identification", miavar);
                     docData.put("priorità",priority = 1);
                     docData.put("date", date.getCurrentTime());
                     db.collection("Codice").document()
                     .set(docData)
                       .addOnSuccessListener(new OnSuccessListener<Void>()
                       {

                          @Override
                          public void onSuccess(Void aVoid)
                          {
                            Log.d("successo", "stato inserito");
                          }
                       });
                  }


                // Se l'utente clicca il secondo range dell'intensità di dolore(2-4) e zona corpo zona centrale compresa di arti superiori con causa indifferente
                // gli sara assegnato un identificativo con codice giallo e priorità 2 in una determinata data e ora
                // quindi il tutto sarà inserito nel database


                  if (secondosecondo)
                  {

                     Map<String, Object> docData = new HashMap<>();
                     docData.put("codicecolore", "Verde");
                     docData.put("identification", miavar);
                     docData.put("priorità",priority = 2);
                     docData.put("date", date.getCurrentTime());
                     db.collection("Codice").document()
                     .set(docData)
                      .addOnSuccessListener(new OnSuccessListener<Void>()
                      {

                          @Override
                          public  void onSuccess(Void aVoid)
                          {
                              Log.d("successo", "stato inserito");
                          }
                      });
                  }


                // Se l'utente clicca il secondo range dell'intensità di dolore(2-4) e zona corpo zona bassa compresa di arti inferiori con causa indifferente
                // gli sara assegnato un identificativo con codice giallo e priorità 2 in una determinata data e ora
                // quindi il tutto sarà inserito nel database

                 if (secondoterzo)
                 {

                    Map<String, Object> docData = new HashMap<>();
                    docData.put("codicecolore", "Verde");
                    docData.put("identification", miavar);
                    docData.put("priorità",priority = 2);
                    docData.put("date", date.getCurrentTime());
                    db.collection("Codice").document()
                     .set(docData)
                     .addOnSuccessListener(new OnSuccessListener<Void>()
                     {

                        @Override
                        public void onSuccess(Void aVoid)
                        {
                           Log.d("giallo", "stato inserito");
                        }
                     });
                 }


                // Se l'utente clicca il primo range dell'intensità di dolore(0-2) e zona corpo zona centrale compresa di arti superiori con causa indifferente
                // gli sara assegnato un identificativo con codice giallo e priorità 1 in una determinata data e ora
                // quindi il tutto sarà inserito nel database


                 if (primosecondo)
                 {

                   Map<String, Object> docData = new HashMap<>();
                   docData.put("codicecolore", "Bianco");
                   docData.put("identification", miavar);
                   docData.put("priorità",priority = 3);
                   docData.put("date", date.getCurrentTime());
                   db.collection("Codice").document()
                     .set(docData)
                     .addOnSuccessListener(new OnSuccessListener<Void>()
                     {

                        @Override
                        public void onSuccess(Void aVoid)
                        {
                          Log.d("successi", "stato inserito");
                        }
                     });
                 }




                // Se l'utente clicca il primo range dell'intensità di dolore(0-2) e zona corpo zona bassa compresa di arti inferiori con causa indifferente
                // gli sara assegnato un identificativo con codice giallo e priorità 1 in una determinata data e ora
                // quindi il tutto sarà inserito nel database

                 if (primoterzo)
                 {

                   Map<String, Object> docData = new HashMap<>();
                   docData.put("codicecolore", "Bianco");
                   docData.put("identification", miavar);
                   docData.put("priorità",priority = 1);
                   docData.put("date", date.getCurrentTime());
                   db.collection("Codice").document()
                    .set(docData)
                    .addOnSuccessListener(new OnSuccessListener<Void>()
                    {


                        @Override
                        public void onSuccess(Void aVoid)
                        {
                          Log.d("giallo", "stato inserito");
                        }
                    });
                }

              if(push)
              {
                Intent next = new Intent(this, Gestione.class);
                next.putExtra("codiceprofilo", miavar);
                next.putExtra("priority", priority);
                startActivity(next);
              }else
                {
                  Toast.makeText(getApplicationContext(), "devi selezionare una causa", Toast.LENGTH_SHORT).show();

                }

}

            // Questi due metodi sono stati descritti in precedenza nel caso rivedere riga 145

            private void verificaEsistenza()
            {

              db.collection("Codice")
             .get()
             .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
             {
                 @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task)
                 {

                    if(task.isSuccessful())
                      {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                         {
                            Model arraymodel = documentSnapshot.toObject(Model.class);
                            identification.add(arraymodel.getIdentification());

                         }
                            same = identification.contains(miavar);

                            if(same && identification.size() < 201)
                            {
                              while (same)
                              {
                               creazioneCodiciCasuali();
                               same = identification.contains(miavar);

                              }
                            }


                      }else
                         {
                          Log.d("codici", "Error getting documents: ", task.getException());

                         }


                 }
             });
              Log.d("successo2",  String.valueOf(same));

            }


            private int creazioneCodiciCasuali()
            {
               Random random = new Random();
               int a = 100; // numero minimo
               int b = 300; // numero massimo
               int c = ((b-a) + 1);
               miavar = random.nextInt(c) + a;
              return miavar;
            }





  }
