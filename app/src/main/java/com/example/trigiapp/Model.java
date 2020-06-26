package com.example.trigiapp;

import java.util.Calendar;
import java.util.Date;

//Classe riferita al singolo paziente la quale verra inserita nel database per ogni persona

public class Model
{


           private int identification;
           private  int priorità;
           private String codicecolore;
           Date currentTime = Calendar.getInstance().getTime();

           //costruttore vuoto utile al database
          public Model()
          {

          }


           public Model(int identification, int priorità, String codicecolore, Date currentTime)
           {
            this.identification = identification;
            this.priorità = priorità;
            this.codicecolore = codicecolore;
            this.currentTime = currentTime;
           }

           //Metodi get e set

            public int getPriorità()
            {

              return priorità;

            }


            public void setPriorità(int priorità)
            {

               this.priorità = priorità;

            }

            public Date getCurrentTime()
            {

               return currentTime;

            }

            public void setCurrentTime(Date currentTime)
            {

               this.currentTime = currentTime;

            }

            public String getCodicecolore()
            {

               return codicecolore;
            }

            public void setCodicecolore(String codicecolore)
            {

                this.codicecolore = codicecolore;
            }

            public int getIdentification()
            {
               return identification;
            }

            public void setIdentification(int identification)
            {
             this.identification = identification;
            }
}
