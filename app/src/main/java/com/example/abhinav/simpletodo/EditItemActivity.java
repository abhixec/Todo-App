package com.example.abhinav.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static android.R.id.edit;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(getIntent().getStringExtra("EditText"));
    }

    public void editButton(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        Intent i = new Intent();
        Log.i("EditItem",editText.getText().toString());
        i.putExtra("resultText", editText.getText().toString());
        i.putExtra("resultId",getIntent().getIntExtra("EditId",0));
        Log.i("EditId",String.valueOf(getIntent().getIntExtra("EditId",0)));
        i.putExtra("pos",getIntent().getIntExtra("position",0));
        setResult(RESULT_OK,i);
        finish();
    }
}
