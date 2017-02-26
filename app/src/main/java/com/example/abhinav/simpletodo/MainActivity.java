package com.example.abhinav.simpletodo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static android.R.attr.start;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.support.v7.widget.AppCompatDrawableManager.get;

public class MainActivity extends AppCompatActivity{
    private ArrayList<Item> items;
    private ItemAdapter itemsAdapter;
    private ListView lvItems;
    private TodoItemDatabase databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = TodoItemDatabase.getInstance(this);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items= databaseHelper.getAllItems();
        itemsAdapter= new ItemAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();

    }

//    public void onSubmit(View view) {
//        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
//        String itemText = etNewItem.getText().toString();
//        if(itemText.isEmpty()){
//            Toast.makeText(this,"Can't add empty item",Toast.LENGTH_SHORT).show();
//        }else{
//            Item item=new Item();
//            item.text= itemText;
//            itemsAdapter.add(item);
//            item.id= (int) databaseHelper.addItem(item);
//            etNewItem.setText("");
//            setupListViewListener();
//        }}

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View item, int pos, long id) {

                Item listItem= new Item();
                listItem = items.get(pos);
                items.remove(pos);
                databaseHelper.deleteItem(listItem);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    showEditDialog(items.get(i),i);
            }
        });

    }

    public void showEditDialog(Item item, int position) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemFragment editNameDialogFragment = EditItemFragment.newInstance("Edit Item",item, position);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }


    public void updateItem(Item item, int position){
        items.set(position, item);
        itemsAdapter.notifyDataSetChanged();
        databaseHelper.update(item);
    }

    public void addItem(View view) {
        showAddDialog();
    }

    public void showAddDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddItemFragment editNameDialogFragment = AddItemFragment.newInstance("Add Item");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }
    public void addNewItem(Item item) {
        itemsAdapter.add(item);
        databaseHelper.addItem(item);
        itemsAdapter.notifyDataSetChanged();
    }

}
