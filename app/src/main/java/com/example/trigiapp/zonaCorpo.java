package com.example.trigiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class zonaCorpo extends AppCompatActivity
{

      private boolean superiorev = false;
      private boolean centrov = false;
      private boolean inferiorev = false;
      private boolean push = false;
      private boolean primo = false;
      private boolean secondoprimo = false;
      private boolean terzoprimo = false;
      private boolean terzoterzo = false;
      private boolean terzosecondo = false;
      private boolean secondosecondo = false;
      private boolean secondoterzo = false;
      private boolean primosecondo = false;
      private boolean primoterzo = false;






        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_corpo);
        final Button superiore = findViewById(R.id.testaB);
        final Button centro = findViewById(R.id.centroB);
        final Button inferiore = findViewById(R.id.inferioreB);
        Button next = findViewById(R.id.next);
        Button back = findViewById(R.id.back);



          // stesso funzionamento nell'activity di prenotato ci serviamo delle tre variabili  per impostare in blu il button selezionato e in bianco
          // le restanti due mentre push serve sempre a verificare se una delle zone verrà selezionata
          // in questo caso viene selezionata la testa
            superiore.setOnClickListener(new View.OnClickListener()
            {

             @Override
              public void onClick(View v)
             {

                if(!superiorev)
                {
                  superiore.setBackgroundResource(R.drawable.backnext_button);
                  centro.setBackgroundResource(R.drawable.border_button);
                  inferiore.setBackgroundResource(R.drawable.border_button);
                  push=true;
                  superiorev= true;
                  centrov= false;
                  inferiorev= false;
                }

             }

           });

          // stesso funzionamento nell'activity di prenotato ci serviamo delle tre variabili  per impostare in blu il button selezionato e in bianco
          // le restanti due mentre push serve sempre a verificare se una delle zone verrà selezionata
          // in questo caso viene selezionata la zona centrale compresa di arti superiori

            centro.setOnClickListener(new View.OnClickListener()
            {
                @Override
                 public void onClick(View v)
                {
                   if(!centrov)
                    {
                      centro.setBackgroundResource(R.drawable.backnext_button);
                      superiore.setBackgroundResource(R.drawable.border_button);
                      inferiore.setBackgroundResource(R.drawable.border_button);
                      push=true;
                      superiorev= false;
                      centrov= true;
                      inferiorev= false;
                    }
                }
            });


          // stesso funzionamento nell'activity di prenotato ci serviamo delle tre variabili  per impostare in blu il button selezionato e in bianco
          // le restanti due mentre push serve sempre a verificare se una delle zone verrà selezionata
          // in questo caso viene selezionata la zona centrale compresa di arti superiori

             inferiore.setOnClickListener(new View.OnClickListener()
             {
                 @Override
                  public void onClick(View v)
                 {

                   if(!inferiorev)
                     {
                       inferiore.setBackgroundResource(R.drawable.backnext_button);
                       centro.setBackgroundResource(R.drawable.border_button);
                       superiore.setBackgroundResource(R.drawable.border_button);
                       push=true;
                       superiorev= false;
                       centrov= false;
                       inferiorev= true;
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

                Intent back = new Intent(this, Prenotato.class);
                startActivity(back);

              }

              public void nextActivity()
              {
                // catturiamo i dati derivanti dall'activity prenotato che ci fornirà
                // variabili booleane per fare determinati controlli
               Bundle bundle = getIntent().getExtras();
               boolean intensitad = bundle.getBoolean("intensità0");
               boolean intensitad1 = bundle.getBoolean("intensità2");
               boolean intensitad2 = bundle.getBoolean("intensità4");


                 if(push)
                 {

                   // se la variabile intensitad e superiorev sono true ciò significa che
                   // l'utente ha selezionato il primo range(0-2) di dolore ed la zona testa
                   // se ciò viene confermato salviamo il tutto nella variabile primo che poi utilizzeremo nell'activity causa
                    if(intensitad && superiorev)
                    {
                      primo = true;
                    }

                   // se la variabile intensitad1 e superiorev sono true ciò significa che
                   // l'utente ha selezionato il secondo range(2-4) di dolore  ed la zona testa
                   // se ciò viene confermato salviamo il tutto nella variabile secondo primo che poi utilizzeremo nell'activity causa
                    if(intensitad1 && superiorev)
                    {
                      secondoprimo =true;
                    }


                   // se la variabile intensitad2 e superiorev sono true ciò significa che
                   // l'utente ha selezionato il terzo range(4-6) di dolore  ed la zona testa
                   // se ciò viene confermato salviamo il tutto nella variabile terzoprimo che poi utilizzeremo nell'activity causa
                    if(intensitad2 && superiorev)
                    {
                       terzoprimo= true;
                    }


                   // se la variabile intensitad2 e centrov sono true ciò significa che
                   // l'utente ha selezionato il terzo range(4-6) di dolore  ed la zona centrale compresa di arti superiori
                   // se ciò viene confermato salviamo il tutto nella variabile terzosecondo che poi utilizzeremo nell'activity causa
                    if(intensitad2 && centrov)
                    {
                       terzosecondo= true;
                    }

                   // se la variabile intensitad1 e centrov sono true ciò significa che
                   // l'utente ha selezionato il secondo range(2-4) di dolore  ed la zona centrale compresa di arti superiori
                   // se ciò viene confermato salviamo il tutto nella variabile secondosecondo che poi utilizzeremo nell'activity causa
                    if(intensitad1 && centrov)
                    {
                      secondosecondo= true;
                    }

                   // se la variabile intensitad1 e inferiorev sono true ciò significa che
                   // l'utente ha selezionato il secondo range(2-4) di dolore  ed la zona bassa compresa di arti inferiori
                   // se ciò viene confermato salviamo il tutto nella variabile secondoterzo che poi utilizzeremo nell'activity causa
                    if(intensitad1 && inferiorev)
                    {
                      secondoterzo = true;
                    }

                   // se la variabile intensitad e centrov sono true ciò significa che
                   // l'utente ha selezionato il primo range(0-2) di dolore  ed la zona centrale compresa di arti superiori
                   // se ciò viene confermato salviamo il tutto nella variabile primosecondo che poi utilizzeremo nell'activity causa
                    if(intensitad && centrov)
                    {
                       primosecondo = true;
                    }

                   // se la variabile intensitad e inferiorev sono true ciò significa che
                   // l'utente ha selezionato il primo range(0-2) di dolore  ed la zona bassa compresa di arti inferiori
                   // se ciò viene confermato salviamo il tutto nella variabile primoterzo che poi utilizzeremo nell'activity causa
                    if(intensitad && inferiorev)
                    {
                     primoterzo = true;
                    }

                   // se la variabile intensitad2 e inferiorev sono true ciò significa che
                   // l'utente ha selezionato il terzo range(2-4) di dolore  ed la zona bassa compresa di arti inferiori
                   // se ciò viene confermato salviamo il tutto nella variabile terzoterzo che poi utilizzeremo nell'activity causa
                    if(intensitad2 && inferiorev)
                    {
                    terzoterzo = true;
                    }

                     // Attraverso l'intent passiamo all'activity successiva causa
                     // ed grazie al metodo putExtra trasportiamo i valori derivanti dalle variabili controllo nell'activity causa
                     // i quali ci serviranno poi per assegnare un determinato codice ai pazienti che si prenotato
                      Intent next = new Intent(this, causa.class);
                      next.putExtra("2primi", primo);
                      next.putExtra("secondoprimo", secondoprimo);
                      next.putExtra("terzoprimo", terzoprimo);
                      next.putExtra("terzoterzo", terzoterzo);
                      next.putExtra("terzosecondo", terzosecondo);
                      next.putExtra("secondosecondo", secondosecondo);
                      next.putExtra("secondoterzo", secondoterzo);
                      next.putExtra("primosecondo", primosecondo);
                      next.putExtra("primoterzo", primoterzo);
                      startActivity(next);

                 }else
                   {
                      Toast.makeText(getApplicationContext(), "devi selezionare una zona del corpo", Toast.LENGTH_SHORT).show();
                   }
              }


      }
