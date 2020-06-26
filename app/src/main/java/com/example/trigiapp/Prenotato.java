package com.example.trigiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Prenotato extends AppCompatActivity
{

  private boolean touch = false;
  private boolean touch1 = false;
  private boolean touch2 = false;
  private boolean push = false;



  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_prenotato);

    final Button button = findViewById(R.id.zerodue_V);
    final Button button1 =findViewById(R.id.duequattro_V);
    final Button button2 = findViewById(R.id.quattrosei_V);
          Button button_next = findViewById(R.id.next);
          Button button_back = findViewById(R.id.homeb);


          //se clicco il primo pulsante nel quiz riferito alla primo range di intensità di dolore tale pulsante si colorerà di blu per dare modo di fare vedere
          //che è stato selezionato quel elemento mentre gli altri sono settati in bianco, altrimenti se non viene selezionato rimane di colorazione bianca
          // la variabile touch e push verranno eguagliate e a true per fare alcuni controlli
          button.setOnClickListener(new View.OnClickListener()
          {
               @Override
               public void onClick(View v)
               {

                   if (!touch)
                   {
                     button.setBackgroundResource(R.drawable.backnext_button);
                     button1.setBackgroundResource(R.drawable.border_button);
                     button2.setBackgroundResource(R.drawable.border_button);
                     push = true;
                     touch = true;
                     touch1 =false;
                     touch2=false;
                   }

               }


          });

           //se clicco il primo pulsante nel quiz riferito al secondo range di intensità di dolore tale pulsante si colorerà di blu per dare modo di fare vedere
          //che è stato selezionato quel elemento mentre gli altri sono settati in bianco altrimenti se non viene selezionato rimane di colorazione bianca
          button1.setOnClickListener(new View.OnClickListener()
          {
               @Override
               public void onClick(View v)
               {


                 if (!touch1)
                 {
                   button1.setBackgroundResource(R.drawable.backnext_button);
                   button.setBackgroundResource(R.drawable.border_button);
                   button2.setBackgroundResource(R.drawable.border_button);
                   push = true;
                   touch = false;
                   touch2=false;
                   touch1 = true;
                 }

               }
          });


          //se clicco il primo pulsante nel quiz riferito al terzo range di intensità di dolore tale pulsante si colorerà di blu per dare modo di fare vedere
          //che è stato selezionato quel elemento mentre gli altri sono settati in bianco altrimenti se non viene selezionato rimane di colorazione bianca
        button2.setOnClickListener(new View.OnClickListener()
        {
               @Override
               public void onClick(View v)
               {

                   if (!touch2)
                    {
                      button2.setBackgroundResource(R.drawable.backnext_button);
                      button.setBackgroundResource(R.drawable.border_button);
                      button1.setBackgroundResource(R.drawable.border_button);
                      push = true;
                      touch2 = true;
                      touch = false;
                      touch1= false;
                    }

               }
        });


            //bottone che mi farà tornare indietro nella MainActivity
             button_back.setOnClickListener(new View.OnClickListener()
             {
                @Override
                public void onClick(View v)
                 {
                   backActivity();
                 }
             });



          //bottone che mi farà andare avanti di Activity nella zonaCorpo
            button_next.setOnClickListener(new View.OnClickListener()
            {
              @Override
              public void onClick(View v)
              {
                  openActivity();
              }
            });



  }

          private void backActivity()
          {
               Intent intent = new Intent(this, MainActivity.class);
               startActivity(intent);

          }

           // Questa funzione mi permette di andare all'activity successiva portandomi con se le variabili touch cioè attraverso tali variabili
           // riuscirò a sapere cosa è successo in precedenza nella pagina zonaCorpo cosi da continuare a fare determinati controlli dipendenti
           // da ciò che ho selezionato nell'activity Prenotato
           // la variabile push ci permette di capire se è stata selezionata almeno un range di intensità del dolore se cosi non fosse ci segnala con
           // un messaggio che dobbiamo selezionarne una intensità
          public void openActivity()
          {
             if(push)
              {
                Intent intent1 = new Intent(this, zonaCorpo.class);
                intent1.putExtra("intensità0", touch);
                intent1.putExtra("intensità2", touch1);
                intent1.putExtra("intensità4", touch2);
                startActivity(intent1);
              } else
                {
               Toast.makeText(getApplicationContext(),"devi selezionare un'intensità ",Toast.LENGTH_LONG).show();
                }
          }


}
