package com.project.mylenses;

import static android.content.ContentValues.TAG;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;
import static com.project.mylenses.R.id.TextLostUses;
import static com.project.mylenses.R.id.floatingActionButtonAdd;
import static com.project.mylenses.R.id.toSettings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DialogFragmentAddLens.NoticeDialogListener {

    private FloatingActionButton fabAdd;
    private TextView txtLostUses;
    private LensControl currentLensControl;
    private ProgressBar progressBar;
    private Button buttonPlusOne;
    private Button buttonMinusOne;

    private final static String FILENAME_LENS = "lensObj";

    final String LOG_TAG = "myLogs";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAdd = findViewById(floatingActionButtonAdd);
        txtLostUses = findViewById(TextLostUses);
        progressBar = findViewById(R.id.progressBar);
        buttonPlusOne = findViewById(R.id.buttonPlusOne);
        buttonMinusOne = findViewById(R.id.buttonMinusOne);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        try {
            currentLensControl = readFile(FILENAME_LENS);

        } catch (Exception e) {
            Log.w(TAG, "File not found");
        }
        setVisibleButtons();

        UpdateLostUses();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            writeFileLens(currentLensControl, FILENAME_LENS);
        } catch (Exception e) {
            Log.w(TAG, "onDestroy: can't write File");
        }

        Log.d(TAG, "onDestroy");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStop() {
        super.onStop();
        try {
            writeFileLens(currentLensControl, FILENAME_LENS);
        } catch (Exception e) {
            Log.w(TAG, "onStop: can't write File");
        }
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonPlusOne.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                currentLensControl.countUsesPlus();
                UpdateLostUses(); //вынести мб в другую часть

            }
        });

        buttonMinusOne.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                currentLensControl.countUsesMinus();
                UpdateLostUses(); //вынести мб в другую часть
            }
        });


        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == toSettings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    //Обновление кол-ва использований
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void UpdateLostUses() {

        if (currentLensControl == null) {
            txtLostUses.setText("0");
            return;
        }

        if (currentLensControl.getCountingMode().equals("toUp")) {

            if (currentLensControl == null)
                txtLostUses.setText("0");
            else
                txtLostUses.setText(currentLensControl.getCountUses().toString());
            // progressBar.setMax(currentLensControl.getCountUses());
            progressBar.setProgress(currentLensControl.getCountUses(), false);

        } else if (currentLensControl.getCountingMode().equals("toDown")) {

            if (currentLensControl == null)
                txtLostUses.setText("0");
            else
                txtLostUses.setText(currentLensControl.getCountUses().toString());

            progressBar.setMax(-currentLensControl.getCountUses());
            progressBar.setSecondaryProgress(0);

        }
    }


    public void showDialog() {
        DialogFragmentAddLens dialog = new DialogFragmentAddLens();
        dialog.show(getSupportFragmentManager(), "DialogFragmentAddLens");
    }

    //Запись в файл объекта линз
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void writeFileLens(LensControl lensControl, String FILENAME_LENS) {
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

    //Парсинг файла линз
    @RequiresApi(api = Build.VERSION_CODES.N)
    public LensControl readFile(String FILENAME_LENS) throws ParseException {

        String[] parseData = null;
        LensControl lensControlByFile;

        SimpleDateFormat nowSDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat endSDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME_LENS)));
            String str;
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                parseData = str.split(",\t");
                Log.d(LOG_TAG, str);
            }
            Log.d(LOG_TAG, "Объект получен");
        } catch (IOException e) {
            e.printStackTrace();
        }

        nowSDF.parse(parseData[2]);
        endSDF.parse(parseData[3]);
        lensControlByFile = new LensControl(parseData[0], Integer.parseInt(parseData[1]), nowSDF.getCalendar(), endSDF.getCalendar());

        return lensControlByFile;
    }

    //Преобразование объекта линз в строку
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String lensToFile(LensControl lensControl) {
        String lensString = "";
        lensString += lensControl.getCountingMode() + ",\t";
        lensString += lensControl.getCountUses().toString() + ",\t";
        lensString += lensControl.getNowDate().getTime().toString() + ",\t";
        lensString += lensControl.getEndDate().getTime().toString();
        return lensString;
    }

    //Кнопка диалогового окна "Ок"
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, LensControl lensControl) {
        System.out.printf(lensControl.toString());
        currentLensControl = lensControl;
        writeFileLens(currentLensControl, FILENAME_LENS);
        UpdateLostUses();
        setVisibleButtons();
    }

    public void setVisibleButtons() {

        if (currentLensControl == null) {
            buttonPlusOne.setVisibility(View.INVISIBLE);
            buttonMinusOne.setVisibility(View.INVISIBLE);
        }else{
            buttonPlusOne.setVisibility(View.VISIBLE);
            buttonMinusOne.setVisibility(View.VISIBLE);
        }
    }

}