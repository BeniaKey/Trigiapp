package com.example.trigiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity

    {
  //inizializzo una variabile di tipo Fragment
     Fragment selectedFragment = null;
  //Accedo all'istanza Cloud Firestore da questa Activity(MainActivity)
     FirebaseFirestore db = FirebaseFirestore.getInstance();
     final ArrayList<Integer> identification = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
       {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         ImageView imageView = findViewById(R.id.google_maps);

         //Inizializzo La bottomnavigationview dove poi potremmo navigare tra i 3 elementi suddivisi in frammenti
         BottomNavigationView bottonNav = findViewById(R.id.bottomNavigationView);
         bottonNav.setOnNavigationItemSelectedListener(navListener);


      // se non viene assegnato nulla alla variabile di tipo Fragment, Quindi se non viene selezionato alcun elemento
      // nella BottomNavigationView viene mostrato in modo predefinito il primo fragment
           if ( selectedFragment == null)
             {
               bottonNav.setSelectedItemId(R.id.firstFragment);
             }



      // Riguarda l'immagine con l'icona di Google Maps, se verrà cliccato si accederà
      // all'activity con nome MapsActivity la quale mostrerà la posizione precisa dell'ospedale

           imageView.setOnClickListener(new View.OnClickListener()
            {
             @Override
              public void onClick(View v)
                   {
                   Intent intent =  new Intent(getApplicationContext(), MapsActivity.class);
                   startActivity(intent);
                   }
            });


      // Se clicchiamo il pulsante Prenota verremo portati nell'activity
      // Prenota dove verrà avviato un quiz per conoscere la diagnosi

           Button prenotato = findViewById(R.id.prenota);
             prenotato.setOnClickListener(new View.OnClickListener()
             {
               @Override
               public void onClick(View v)
                {
                openPrenotato();
                }
             });




       }


    // Chiamato quando è selezionata una voce nel menu di navigazione, quindi viene allegato
    // un listener per gli eventi click
     private BottomNavigationView.OnNavigationItemSelectedListener navListener =
         new BottomNavigationView.OnNavigationItemSelectedListener()
         {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
              {
          // vi è  tale ascoltatore che attende il click da parte dell'utente, nel caso ci fosse
          // attraverso il comando switch vediamo se l'id(elemento) selezionato corrisponde ad uno dei casi disponibili
          // se cosi fosse allora alla variabile selectFragment viene assegnato un determinato Fragment relativo all'elemento selezionato

                 switch(menuItem.getItemId())
                {
                          case R.id.firstFragment:
                            selectedFragment = new firstFragment();
                              break;
                          case R.id.secondFragment:
                            selectedFragment = new secondFragment();
                              break;
                          case R.id.thirdFragment:
                            selectedFragment = new thirdFragment();
                              break;
                 }



        // utilizzo il metodo getSupportFragmentManager per ottenere il FragmentManager
        // poi chiama il metodo beginTransaction per creare una FragmentTransaction e di conseguenza replace per sostituire il fragment nel contenitore di frammenti
        //infine effettuare tale transazione attraverso commit
             getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
              return true;
              }
           };



         public void openPrenotato()
         {


    // attraverso questo codice riesco a connetermi nel database Codice ed a prendere il contenuto di tutti i documenti
    // se cio va a buon fine allora inseriamo in una variabile di tipo Model(arraymodel)dove abbiamo i nostri dati di ogni utente
    // tutti i dati letti dai vari documenti e convertiti nel tipo Model
    // per poi aggiungere questi dati in un Arraylist e verificare se vi sono posti ancora nel prontosoccorso attraverso la lunghezza dell'Arraylist
    // altrimenti viene visualizzato il messaggio di errore


            db.collection("Codice")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
            {
              @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {

                if (task.isSuccessful())
                  {
                  for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                    {
                    Model arraymodel = documentSnapshot.toObject(Model.class);

                    identification.add(arraymodel.getIdentification());

                    }

                 if(identification.size() < 201)
                    {
                    Intent intent = new  Intent(getApplicationContext(), Prenotato.class);
                    startActivity(intent);
                    } else {

                          Toast.makeText(getApplicationContext(), "Siamo arrivati al numero limite di pazienti", Toast.LENGTH_LONG).show();

                           }


                  } else {
                            Log.d("codici", "Error getting documents: ", task.getException());
                         }

                }
            });

          }





     }
