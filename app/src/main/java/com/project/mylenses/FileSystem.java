package com.project.mylenses;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.Telephony.Mms.Part.FILENAME;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;


import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Objects;

public class FileSystem extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    final String FILENAME_LENS = "LensObj";

    public void writeFileLens(LensControl lensControl) {
        String toFile = lensToFile(lensControl);
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME_LENS, MODE_PRIVATE)));
            // пишем данные
            bw.write(toFile);
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LensControl readFile() {
        String[] data = null;
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME_LENS)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                data = str.split("\n");

                Log.d(LOG_TAG, str);
            }
           // LensControl lensControlByFile = new LensControl(data[0], Integer.parseInt(data[1]), data[2],data[3]); // парс календарной строки
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return
    }

    public String lensToFile(LensControl lensControl) {
        String lensString = "";
        lensString += lensControl.getCountingMode().toString() + "\n";
        lensString += lensControl.getCountUses().toString() + "\n";
        lensString += lensControl.getNowDate().toString() + "\n";
        lensString += lensControl.getEndDate().toString() + "\n";
        return lensString;
    }

    public


}
