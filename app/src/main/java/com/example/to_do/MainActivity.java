package com.example.to_do;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static int REQUEST_CODE = 1234;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    Button btnAdd;
    EditText etAddItem;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAddItem = findViewById(R.id.etAddItem);
        btnAdd = findViewById(R.id.btnAdd);

        readItems();
        listView = findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,list);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etAddItem.getText().toString().contentEquals("")){
                    arrayAdapter.add(etAddItem.getText().toString());
                    writeItems();
                    arrayAdapter.notifyDataSetChanged();
                    etAddItem.setText("");
                }
            }
        });

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra("item",list.get(position));
                intent.putExtra("position",position);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                list.remove(position);
                arrayAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            int position = data.getIntExtra("position",999);
            String item = data.getStringExtra("editedItem");
            list.remove(position);
            arrayAdapter.insert(item,position);
            arrayAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(),"todo.txt");
    }

    private void readItems(){
        try {
            list = new ArrayList<String>(FileUtils.readLines(getDataFile(),Charset.defaultCharset().toString()));
        } catch (IOException e) {
            list = new ArrayList<>();
        }
    }

    private void writeItems(){
        try {
            FileUtils.writeLines(getDataFile(),list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
