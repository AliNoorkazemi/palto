package com.example.plato.game.dotsandboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.plato.R;

public class DotAndBoxPageActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_and_box_page);

        GridView gridView = findViewById(R.id.grid_view_dots_and_boxes);

        GridViewDotsAndBoxesAdapter adapter = new GridViewDotsAndBoxesAdapter(DotAndBoxPageActivity.this);
        gridView.setAdapter(adapter);

    }
}
