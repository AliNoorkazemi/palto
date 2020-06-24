package com.example.plato.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.plato.R;
import com.example.plato.game.SingletonGameContainer;
import com.example.plato.game.XOGamePageActivity;
import com.example.plato.game.startPage.StartGamePageActivity;

public class GameFrag extends Fragment {
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_game, container, false);

        Button xo_game_btn=view.findViewById(R.id.btn_gameFrag_xo);
        xo_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), StartGamePageActivity.class);
                intent.putExtra("GAME", SingletonGameContainer.getXoInstance());
                startActivity(intent);
            }
        });

        return view;
    }
}
