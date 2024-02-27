package com.taylorcassidy.honoursproject.helpers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;
import java.util.function.Consumer;

public class SpinnerHelper {

    public static <E> void populate(Spinner spinner, List<E> items, Consumer<E> onItemSelected, E selectedItem) {
        ArrayAdapter<E> aa = new ArrayAdapter<>(spinner.getContext(), android.R.layout.simple_spinner_item, items);
        aa.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner.setAdapter(aa);
        spinner.setSelection(items.indexOf(selectedItem));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                E item = items.get(position);
                onItemSelected.accept(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //noop
            }
        });
    }
}
