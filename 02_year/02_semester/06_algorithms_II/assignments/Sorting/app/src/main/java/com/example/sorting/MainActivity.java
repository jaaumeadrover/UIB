package com.example.sorting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Ordenar(View view){
        //ATTRIBUTES DECLARATION
        LinkedList<Integer> llista=new LinkedList();
        TextView LlistaOriginal=(TextView) findViewById(R.id.textViewShowOriginal);
        TextView LlistaOrdenada=(TextView) findViewById(R.id.textViewShowOrdenat);
        int longitud;

        //WE OBTAIN INPUT PARAMETERS
        EditText editText= (EditText) findViewById(R.id.editTextTextPersonName);
        String string= editText.getText().toString();

        //CREATE LINKEDLIST
        longitud=Integer.parseInt(string);
        llista.generateRandomList(longitud);

        //SHOW ORIGINAL LINKEDLIST
        LlistaOriginal.setText(llista.toString());

        //MERGESORT, SORT YOUR LIST
        llista.mergeSort();

        //SHOW SORTED LIST
        LlistaOrdenada.setText(llista.toString());

    }
}