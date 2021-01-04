package com.example.simpletodo;

import android.os.FileUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.os.FileUtils.*;
import static org.apache.commons.io.FileUtils.readLines;
import static org.apache.commons.io.FileUtils.writeLines;

public class MainActivity extends AppCompatActivity {
    List<String> items = new ArrayList<>();

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd  = findViewById(R.id.btnAdd);          // collecting views as a reference
        etItem  = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);
        /*
        items = new ArrayList<>();
        items.add("Code");
        items.add("Work");
        items.add("Sleep");
        */
        loadItems();
        ItemsAdapter.OnLongClickListener onLongClickListener =  new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item
                items.remove(position);

                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed",Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };


        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));   //put things on ur UI in a vertical way

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                //Add items to model
                items.add(todoItem);


                //Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1);     //item inserted will be the last position

                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Item was added",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

    }
    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
    }
    private void loadItems(){
        try {
            items = new ArrayList<>(readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e){
            e.printStackTrace();
            Log.e("MainActivity","Error reading items, e");
            items = new ArrayList<>();
        }
    }
    private void saveItems(){
        try {
            writeLines(getDataFile(), items);
        }
        catch (IOException e){
            e.printStackTrace();
            Log.e("MainActivity","Error Writing items, e");
            items = new ArrayList<>();
        }



    }
}

