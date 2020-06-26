package com.example.trigiapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


import java.util.List;


//creazione del ViewHolder che sarà poi utilizzato nell'adapter
      public  class ModelHolder extends RecyclerView.ViewHolder
{

          TextView codice;
          TextView codicecolore;
          TextView priorità;


            public ModelHolder(@NonNull View itemView)
            {

              super(itemView);
              codice = itemView.findViewById(R.id.codici);
              codicecolore = itemView.findViewById(R.id.codicecolore);
              priorità = itemView.findViewById(R.id.date);
            }
  }


