package com.example.abhinav.simpletodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by janabhi on 2/24/17.
 */

public class AddItemFragment extends DialogFragment {
    private EditText mEditText;
    private Spinner dropdownButton;
    private DatePicker mDueDate;
    public static final String[] PRIORITYLIST = new String[] {"High", "Medium","Low"};
    public AddItemFragment(){

    }

    public static AddItemFragment newInstance(String title){
        AddItemFragment frag = new AddItemFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (EditText) view.findViewById(R.id.editText);
        mDueDate = (DatePicker) view.findViewById(R.id.datePicker);
        final Item newItem = new Item();

        dropdownButton= (Spinner) view.findViewById(R.id.priority);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, PRIORITYLIST);
        dropdownButton.setAdapter(adapter);
        mEditText.requestFocus();

        mDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Button addButton = (Button) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Can't add empty item",Toast.LENGTH_SHORT).show();
                }else{
                    newItem.text= mEditText.getText().toString();
                    newItem.priority= dropdownButton.getSelectedItem().toString();
                    newItem.date = String.format("%s/%s/%s",mDueDate.getMonth()+1,mDueDate.getDayOfMonth(),mDueDate.getYear());
                    Log.i("Day:",String.valueOf(mDueDate.getDayOfMonth()));
                    Log.i("Month:",String.valueOf(mDueDate.getMonth()));
                    Log.i("Year",String.valueOf(mDueDate.getYear()));
                    Log.i("Priority:", dropdownButton.getSelectedItem().toString());
                    ((MainActivity)getActivity()).addNewItem(newItem);
                    dismiss();
                }
            }
        });
    }
}

