package com.project.mylenses;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.Telephony.Mms.Part.FILENAME;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;


import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;
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
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
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
    //Sat Nov 25 10:42:12 MSK 2017
    @RequiresApi(api = Build.VERSION_CODES.N)
    public LensControl readFile() throws ParseException {

        String[] parseData = null;
        LensControl lensControlByFile;

        SimpleDateFormat nowSDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat endSDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME_LENS)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                parseData = str.split("\n");
                Log.d(LOG_TAG, str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nowSDF.parse(parseData[2]);
        endSDF.parse(parseData[3]);

        lensControlByFile = new LensControl(parseData[0], Integer.parseInt(parseData[1]),  nowSDF.getCalendar(), endSDF.getCalendar() );
        return lensControlByFile;
    }

    public String lensToFile(LensControl lensControl) {
        String lensString = "";
        lensString += lensControl.getCountingMode().toString() + "\n";
        lensString += lensControl.getCountUses().toString() + "\n";
        lensString += lensControl.getNowDate().toString() + "\n";
        lensString += lensControl.getEndDate().toString() + "\n";
        return lensString;
    }

   // public LensControl lensFromFile(String)


}
