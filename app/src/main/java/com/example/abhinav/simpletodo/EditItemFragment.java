package com.example.abhinav.simpletodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.util.Arrays;

import static com.example.abhinav.simpletodo.AddItemFragment.PRIORITYLIST;

/**
 * Created by janabhi on 2/23/17.
 */

public class EditItemFragment extends DialogFragment  {

    public interface EditItemListener{
        void onFinishedDialog(Item item);
    }

    private EditText mEditText;
    private Spinner dropdown;
    private DatePicker datePicker;
    public EditItemFragment(){

    }

    public static EditItemFragment newInstance(String title, Item item, int position){
        EditItemFragment frag = new EditItemFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("item", item.text);
        args.putInt("id",item.id);
        args.putString("priority", item.priority);
        args.putString("date",item.date);
        args.putInt("position", position);
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
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.editText);
        mEditText.setText(getArguments().getString("item"));
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title");
        dropdown = (Spinner) view.findViewById(R.id.priority);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, PRIORITYLIST);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(Arrays.asList(PRIORITYLIST).indexOf(getArguments().getString("priority")));
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Button doneButton= (Button) view.findViewById(R.id.add_button);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item it = new Item();
                it.text = mEditText.getText().toString();
                it.id= getArguments().getInt("id");
                it.priority = dropdown.getSelectedItem().toString();
                it.date = String.format("%s/%s/%s",datePicker.getMonth()+1,datePicker.getDayOfMonth(),datePicker.getYear());
                ((MainActivity)getActivity()).updateItem(it, getArguments().getInt("position"));
                dismiss();
            }
        });
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
