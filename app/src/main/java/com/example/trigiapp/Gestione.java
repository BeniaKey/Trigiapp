package com.example.trigiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

public class Gestione extends AppCompatActivity
{
          //inizializzo una variabile di tipo Fragment
           Fragment selectedFragment = null;
           //Accedo all'istanza Cloud Firestore da questa Activity(MainActivity)
           FirebaseFirestore db = FirebaseFirestore.getInstance();
           int test;

                 @Override
                 protected void onCreate(Bundle savedInstanceState)
                 {
                     super.onCreate(savedInstanceState);
                     setContentView(R.layout.activity_gestione);

                     //Inizializzo La bottomnavigationview dove poi potremmo navigare tra i 2 elementi suddivisi in fragment
                       BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
                       bottomNavigationView.setOnNavigationItemSelectedListener(navListener);




                        // se non viene assegnato nulla alla variabile di tipo Fragment, Quindi se non viene selezionato alcun elemento
                        // nella BottomNavigationView viene mostrato in modo predefinito il fragment relativo alla lista di utenti
                         if ( selectedFragment == null)
                         {
                           bottomNavigationView.setSelectedItemId(R.id.list_code);
                         }


                 }


             // Chiamato quando è selezionata una voce nel menu di navigazione, quindi viene allegato
             // un listener per gli eventi click
              private BottomNavigationView.OnNavigationItemSelectedListener navListener =
                    new BottomNavigationView.OnNavigationItemSelectedListener()
                    {
                         @Override
                          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                          {
                                //recupero di dati dall'activit precedente riferiti all'identificativo e alla priorità
                                Bundle bundle = getIntent().getExtras();
                                int codiceprofilopersonale = bundle.getInt("codiceprofilo");
                                int allpriority= bundle.getInt("priority");
                                    test = codiceprofilopersonale;

                                        // vi è  tale ascoltatore che attende il click da parte dell'utente, nel caso ci fosse
                                        // attraverso il comando switch vediamo se l'id(elemento) selezionato corrisponde ad uno dei casi disponibili
                                        // se cosi fosse allora alla variabile selectFragment viene assegnato un determinato Fragment relativo all'elemento selezionato
                                         switch(menuItem.getItemId())
                                         {

                                               case R.id.code_first:
                                                //passaggio dei dati dall'activity in questione(Gestione) ad il fragment(profile_code), ciò avviene al click bella navigationbar in profile
                                                  selectedFragment = new profile_code();
                                                  //  Bundle è usato generalmente per passare dati tra activity con bundle è possibile passare qualsiasi tipo di dati
                                                  //  attraverso il metodo putInt
                                                  Bundle args = new Bundle();
                                                  args.putInt("codiceprofilopersonale", codiceprofilopersonale);
                                                  args.putInt("priority", allpriority);
                                                  selectedFragment.setArguments(args);
                                                  break;
                                               case R.id.list_code:
                                                 selectedFragment = new lista_code();
                                                 break;
                                         }

                                           // utilizzo il metodo getSupportFragmentManager per ottenere il FragmentManager
                                           // poi chiama il metodo beginTransaction per creare una FragmentTransaction e di conseguenza replace per sostituire il fragment nel contenitore di frammenti
                                            //infine effettuare tale transazione attraverso commit
                                          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_gestione, selectedFragment).commit();
                                           return true;
                          }
                    };


                         //questo metodo si riferisce al click nella Navigationbar del pulsante che permette di tornare indietro
                           @Override
                            public void onBackPressed()
                           {
                             // viene usata la classe builder la quale fornisce Api che ti permettono di creare un AlertDialog
                               final AlertDialog.Builder builder = new AlertDialog.Builder(Gestione.this);

                               //setto il messaggio che appare nell'Alert dialog una volta che viene premuto il tasto indietro
                                builder.setMessage("Sicuro di uscire? in questo modo perdererai la fila");
                                //ti permette di uscire dalla dialog con eventi di click
                                builder.setCancelable(true);

                                //aggiungo il bottone
                                // se viene schiacciato Si allora si torna nella MainActivity
                                // ed in più viene cancellato il record riferito alla paziente prenotato
                                // cio viene fatto collegandosi al database e ricercando attraverso whereEqualto
                                // L'identificativo assegnato al paziene ed attraverso il comando delete eliminato dal
                                // database e di conseguenza dalla lista
                                builder.setNegativeButton("Si", new DialogInterface.OnClickListener()
                                {
                                         @Override
                                          public void onClick(DialogInterface dialog, int which)
                                          {
                                                 Gestione.super.onBackPressed();
                                                 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                 startActivity(intent);

                                                    // elimino il mio codice dal database dato che esco attraverso l'exit button

                                                            db.collection("Codice")
                                                              .whereEqualTo("identification", test)
                                                              .get()
                                                              .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                                                               {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                                                                    {
                                                                      if (task.isSuccessful())
                                                                      {
                                                                          for (QueryDocumentSnapshot document : task.getResult())
                                                                          {

                                                                                 db.collection("Codice").
                                                                                 document(document.getId())
                                                                                 .delete()
                                                                                   .addOnSuccessListener(new OnSuccessListener<Void>()
                                                                                   {
                                                                                       @Override
                                                                                       public void onSuccess(Void aVoid)
                                                                                       {
                                                                                         Log.d("successo", "Documento Eliminato");
                                                                                       }
                                                                                   });

                                                                          }
                                                                       } else
                                                                             {
                                                                                 Log.d("test", "Error getting documents: ", task.getException());
                                                                             }
                                                                    }
                                                               });

                                          }
                                });

                                //se viene pigiato il tasto No la daialog viene chiusa di conseguenza senza
                                //nessun riscontro e cio avviene grazie al metodo dialog.cancel()
                                     builder.setPositiveButton("No", new DialogInterface.OnClickListener()
                               {
                                     @Override
                                      public void onClick(DialogInterface dialog, int which)
                                     {

                                       dialog.cancel();
                                     }
                               });

                              //l'alertDialog viene creata e di conseguenza mostrata
                              AlertDialog alertDialog = builder.create();
                              alertDialog.show();



                           }

}
