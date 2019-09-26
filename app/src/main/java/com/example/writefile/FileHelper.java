package com.example.writefile;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {
    // write from database
    private static final String TAG= FileHelper.class.getName();
    static void writeToFile(FileModel fileModel, Context context){
        try {
            OutputStreamWriter outputStreamWriter= new OutputStreamWriter(context.openFileOutput(fileModel.getFilename(), Context.MODE_PRIVATE));
            // menulis data ke dalam storage internal
            outputStreamWriter.write(fileModel.getFile());
            outputStreamWriter.close();
        }
//        catch (InterruptedException e){
//
//        }
        catch (IOException e){

        }
    }


    // read from database
    static FileModel readFromFile(Context context, String filename){
        // make object from filemodel class
        FileModel fileModel= new FileModel();
        try {
            // input stream with name file
            InputStream inputStream= context.openFileInput(filename);
            if(inputStream!=null){
                // membaca data melalui stream
                InputStreamReader inputStreamReader= new InputStreamReader(inputStream);
                // mengecek setiap baris dari berkas , jika baris memiliki data maka akan memasukkan data kedalam string builder
                BufferedReader bufferedReader= new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder= new StringBuilder();
                while ((receiveString= bufferedReader.readLine()) != null){
                    // melakukan perulangan pembacaan data yang nanytinya akan dimasukkan ke dalam string builder
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                // mengeset data berdasarkan proses read
                fileModel.setFilename(filename);
                // mengeset file berdasarkan proses read
                fileModel.setData(stringBuilder.toString());
            }
        }
        catch (FileNotFoundException e){
            Log.d(TAG, e.getMessage());
        }
        catch (IOException e){
            Log.d(TAG, e.getMessage());
        }
        // mengembalikan nilai dari file model yang telah diisi proses read
        return fileModel;
    }
}
