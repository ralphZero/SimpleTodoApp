package com.example.to_do;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditActivity extends AppCompatActivity {
    EditText etEdit;
    Spinner spinner;
    Button btnEdit;
    int position;
    String item;
    String priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        item = getIntent().getStringExtra("item");
        priority = getIntent().getStringExtra("priority");
        position = getIntent().getIntExtra("position",999);

        spinner = findViewById(R.id.spinner);
        switch (priority) {
            case "High":
                spinner.setSelection(0);
                break;
            case "Medium":
                spinner.setSelection(1);
                break;
            case "Low":
                spinner.setSelection(2);
                break;
            case "No":
                spinner.setSelection(3);
                break;
        }

        etEdit = findViewById(R.id.etEdit);
        etEdit.setText(item);


        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEdit.getText().length()!=0){
                    Intent intent = new Intent();
                    intent.putExtra("editedItem",etEdit.getText().toString());
                    intent.putExtra("priority", spinner.getSelectedItem().toString());
                    intent.putExtra("position",position);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
