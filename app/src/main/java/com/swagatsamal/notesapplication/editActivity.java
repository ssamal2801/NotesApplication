package com.swagatsamal.notesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class editActivity extends AppCompatActivity {

    int noteIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        EditText noteEdit = findViewById(R.id.editText);
        Intent getNoteIntent = getIntent();
        noteIndex = getNoteIntent.getIntExtra("noteIndex",-1);

        if (noteIndex!=-1)
        {
            noteEdit.setText(MainActivity.notes.get(noteIndex));
        } else
        {
            MainActivity.notes.add("");
            noteIndex = MainActivity.notes.size() - 1;
        }


            noteEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    MainActivity.notes.set(noteIndex,String.valueOf(charSequence));
                    MainActivity.noteAdapter.notifyDataSetChanged();

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.swagatsamal.notesapplication", Context.MODE_PRIVATE);
                    HashSet<String> set = new HashSet<>(MainActivity.notes);
                    sharedPreferences.edit().putStringSet("notes",set).apply();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });




    }
}