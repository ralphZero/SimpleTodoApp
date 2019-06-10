package com.example.to_do;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    static int REQUEST_CODE = 1234;
    String chosenPriority = "No";

    ListView listView;
    ArrayList<Item> arrayList;
    CustomAdapter adapter;

    ImageView priorityIndicator;
    Button btnAdd;
    EditText etAddItem;

    DatabaseController controller = new DatabaseController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        priorityIndicator = findViewById(R.id.priority);
        etAddItem = findViewById(R.id.etAddItem);
        btnAdd = findViewById(R.id.btnAdd);

        readItems();

        listView = findViewById(R.id.listview);
        adapter = new CustomAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(adapter);

        priorityIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPriority();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etAddItem.getText().toString().contentEquals("")){
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat dateOnly = new SimpleDateFormat("dd MMMM yyyy");
                    String today = dateOnly.format(cal.getTime());

                    adapter.add(new Item(etAddItem.getText().toString(), today, false, chosenPriority));

                    writeItems(etAddItem.getText().toString(), today, false, chosenPriority);

                    adapter.notifyDataSetChanged();
                    etAddItem.setText("");
                    chosenPriority = "No";
                    priorityIndicator.setImageResource(R.drawable.ic_bookmark_black_24dp);
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position : " + position);
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra("item", arrayList.get(position).getTask());
                intent.putExtra("priority", arrayList.get(position).getPriority());
                intent.putExtra("position",position);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                controller.DeleteItem(arrayList.get(position).getId());
                arrayList.remove(position);
                adapter.notifyDataSetChanged();
                Snackbar.make(view, "Item deleted", Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void createPriority() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Set priority");
        builder.setItems(R.array.priorities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        chosenPriority = "High";
                        priorityIndicator.setImageResource(R.drawable.ic_bookmark_red_24dp);
                        dialog.dismiss();
                        break;
                    case 1:
                        chosenPriority = "Medium";
                        priorityIndicator.setImageResource(R.drawable.ic_bookmark_yellow_24dp);
                        dialog.dismiss();
                        break;
                    case 2:
                        chosenPriority = "Low";
                        priorityIndicator.setImageResource(R.drawable.ic_bookmark_green_24dp);
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            int position = data.getIntExtra("position",999);
            String item = data.getStringExtra("editedItem");
            String priority = data.getStringExtra("priority");
            String date = arrayList.get(position).getDate();
            int comp;
            if (arrayList.get(position).isCompleted()) {
                comp = 1;
            } else {
                comp = 0;
            }
            int id = arrayList.get(position).getId();
            controller.UpdateItems(id, item, date, comp, priority);
            arrayList.remove(position);
            adapter.insert(new Item(id, item, date, arrayList.get(position).isCompleted(), priority), position);
            adapter.notifyDataSetChanged();
            Snackbar.make(getCurrentFocus(), "Item edited.", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void readItems() {
        Cursor cursor = controller.ReadItems();
        if (cursor.getCount() != 0) {
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex("id")));
                item.setTask(cursor.getString(cursor.getColumnIndex("task")));
                item.setDate(cursor.getString(cursor.getColumnIndex("date")));
                if (cursor.getInt(cursor.getColumnIndex("isCompleted")) == 0) {
                    item.setCompleted(false);
                } else {
                    item.setCompleted(true);
                }
                item.setPriority(cursor.getString(cursor.getColumnIndex("priority")));
                arrayList.add(item);
            }
        } else {
            arrayList = new ArrayList<>();
        }
    }

    public void writeItems(String task, String date, Boolean isCompleted, String priority) {
        int completed;
        if (isCompleted == true) {
            completed = 1;
        } else {
            completed = 0;
        }
        controller.WriteItems(task, date, completed, priority);
    }
}
