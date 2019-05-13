package com.example.runescapedungpuzzlesolver.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.runescapedungpuzzlesolver.R;
import com.example.runescapedungpuzzlesolver.Runners.TilesRunner;

import java.util.BitSet;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private TextView instructionText;
    private Button restartButton;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        gridView.setAdapter(new TilesAdapter());
        setListeners();

    }

    private void initViews() {
        gridView = findViewById(R.id.grid_view);
        instructionText = findViewById(R.id.textView);
        restartButton = findViewById(R.id.restart_button);
        startButton = findViewById(R.id.start_button);
        instructionText.setText(getString(R.string.instruction));
    }

    private void setListeners(){
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TilesAdapter adapter = (TilesAdapter) gridView.getAdapter();
                BitSet bitSet = new TilesRunner(adapter.getTilesState()).getSolution();
                if(bitSet!=null) {
                    instructionText.setText("Click tile to imbue");
                    adapter.setMoves(bitSet);
                    adapter.setSelected(gridView);
                } else {
                    instructionText.setText("No solution");
                }
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.setAdapter(new TilesAdapter());
                instructionText.setText(R.string.instruction);
            }
        });
    }
}
