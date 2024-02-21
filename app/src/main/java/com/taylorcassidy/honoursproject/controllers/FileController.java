package com.taylorcassidy.honoursproject.controllers;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FileController { //TODO try and fix initial frame skips
    private final Context context;
    private BufferedOutputStream outputStream;
    private FileOutputStream file;

    public FileController(Context context) {
        this.context = context;
    }

    public void open(String headerline, String name) {
        try {
            final String filename = name + "-" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".csv";
            file = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream = new BufferedOutputStream(file);
            if (headerline != null) outputStream.write((headerline + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
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
            outputStream.close();
            file.close();
            Toast.makeText(context, "Saved to " + context.getFilesDir(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
