package com.example.to_do;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText etEdit;
    Button btnEdit;
    int position;
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        item = getIntent().getStringExtra("item");
        position = getIntent().getIntExtra("position",999);

        etEdit = findViewById(R.id.etEdit);
        etEdit.setText(item);

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEdit.getText().length()!=0){
                    Intent intent = new Intent();
                    intent.putExtra("editedItem",etEdit.getText().toString());
                    intent.putExtra("position",position);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}
