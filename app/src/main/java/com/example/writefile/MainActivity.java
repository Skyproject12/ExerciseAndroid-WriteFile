package com.example.writefile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonNew;
    Button buttonOpen;
    Button buttonSave;
    EditText inputContent;
    EditText inputTitle;
    File path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNew= findViewById(R.id.button_new);
        buttonOpen= findViewById(R.id.button_open);
        buttonSave= findViewById(R.id.button_save);
        inputContent= findViewById(R.id.input_file);
        inputTitle= findViewById(R.id.input_title);
        buttonNew.setOnClickListener(this);
        buttonOpen.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        // mengambil path dari internal storage
        path= getFilesDir();
    }

    // make new file
    public void newFile(){
        // make inputTitle null
        inputTitle.setText("");
        // make inputContent null
        inputContent.setText("");
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }


    // settext title and content call data from filemodel
    private void loadData(String title){
        // call file helper class
        FileModel fileModel= FileHelper.readFromFile(this, title);
        inputTitle.setText(fileModel.getFilename());
        inputContent.setText(fileModel.getFile());
        Toast.makeText(this, "Loading"+fileModel.getFilename(), Toast.LENGTH_SHORT).show();
    }

    // open file save
    public void openFile(){
        showList();
    }

    private void showList(){
        ArrayList<String> arrayList= new ArrayList<>();
        Collections.addAll(arrayList, path.list());
        final  CharSequence[] items= arrayList.toArray(new CharSequence[arrayList.size()]);
        // make alert to choose showlist
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        // make alert to display file
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadData(items[which].toString());
            }
        });

        // show and create alert
        AlertDialog alert= builder.create();
        alert.show();
    }

    public void saveFile(){
        if(inputTitle.getText().toString().isEmpty()){
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
        else if(inputContent.getText().toString().isEmpty()){
            Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
        else {
            // get text from inputTitle
            String title= inputTitle.getText().toString();
            // get text from input content
            String text= inputContent.getText().toString();
            // call object filemodel
            FileModel fileModel= new FileModel();
            // set name file
            fileModel.setFilename(title);
            // set file
            fileModel.setData(text);
            // save into filehelper
            FileHelper.writeToFile(fileModel, this);
            Toast.makeText(this, "Saving"+fileModel.getFilename(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case R.id.button_new:
                newFile();
                break;
            case R.id.button_open:
                openFile();
                break;
            case R.id.button_save:
                saveFile();
                break;
        }
    }
}
