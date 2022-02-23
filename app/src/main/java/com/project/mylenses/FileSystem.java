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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Locale;


public class FileSystem extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    private final static String FILENAME_LENS = "LensObj.txt";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void writeFileLens(LensControl lensControl) {
        String toFile = lensToFile(lensControl);
        FileOutputStream fos = null;
        try {

            fos = openFileOutput(FILENAME_LENS, MODE_PRIVATE);
            fos.write(toFile.getBytes());
            //    fos.close();
            // отрываем поток для записи
            //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME_LENS, MODE_PRIVATE)));
            // пишем данные
            // bw.write(toFile);
            // закрываем поток
            // bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            }catch(IOException ex){

                Log.d(LOG_TAG, "WARNING NFC");
            }
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
            Log.d(LOG_TAG, "Объект получен");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nowSDF.parse(parseData[2]);
        endSDF.parse(parseData[3]);

        lensControlByFile = new LensControl(parseData[0], Integer.parseInt(parseData[1]), nowSDF.getCalendar(), endSDF.getCalendar());
        return lensControlByFile;


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String lensToFile(LensControl lensControl) {
        String lensString = "";
        lensString += lensControl.getCountingMode() + "\n";
        lensString += lensControl.getCountUses().toString() + "\n";
        lensString += lensControl.getNowDate().getTime().toString() + "\n";
        lensString += lensControl.getEndDate().getTime().toString();
        return lensString;
    }

}
