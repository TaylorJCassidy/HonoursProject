package com.taylorcassidy.honoursproject.controllers;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FileController {
    private final Context context;
    private OutputStream outputStream;

    public FileController(Context context) {
        this.context = context;
    }

    public void open() {
        try {
            final String filename = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "-data.csv";
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String line) {
        try {
            outputStream.write((line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            Toast.makeText(context, "Saved to " + context.getFilesDir(), Toast.LENGTH_LONG).show();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
