package com.itacit.healthcare.chipsView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.itacit.healthcare.R;

public class Main2Activity extends AppCompatActivity {
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final ChipsEditText chipsEditText = (ChipsEditText) findViewById(R.id.chipsET);
        findViewById(R.id.btn).setOnClickListener(v -> chipsEditText.addChip("CHIP " + count++));
        findViewById(R.id.btnDl).setOnClickListener(v -> chipsEditText.removeChips());

        chipsEditText.getChipRemovedSubject().subscribe(c -> Log.i("MY", c.getDisplay().toString()));
    }
}
