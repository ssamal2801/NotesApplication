package com.swagatsamal.notesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ListView listView;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter noteAdapter;
    int xx;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        //notes = new ArrayList<String>();
        sharedPreferences = getApplicationContext().getSharedPreferences("com.swagatsamal.notesapplication", Context.MODE_PRIVATE);

        HashSet<String> getfromMemory = (HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if (getfromMemory == null)
        {
            notes.add("Sample note");
        }
        else notes = new ArrayList(getfromMemory);

        noteAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();

        //----view and edit note----
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),editActivity.class);
                intent.putExtra("noteIndex",i);
                startActivity(intent);
            }
        });

        //----delete note on long press----
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                xx = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                notes.remove(xx);
                                noteAdapter.notifyDataSetChanged();

                                HashSet<String> newSet = new HashSet<>(notes);
                                sharedPreferences.edit().putStringSet("notes",newSet).apply();
                            }
                        })
                        .setNegativeButton("No",null).show();

                return true;
            }
        });

    }

    //----add new note----


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

         if (item.getItemId()==R.id.addMenu)
         {
             Intent addIntet = new Intent(getApplicationContext(),editActivity.class);
             startActivity(addIntet);
             return true;
         }
         return false;
    }
}