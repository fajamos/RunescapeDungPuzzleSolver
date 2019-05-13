package com.example.runescapedungpuzzlesolver.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.example.runescapedungpuzzlesolver.Models.Tile;
import com.example.runescapedungpuzzlesolver.R;
import com.example.runescapedungpuzzlesolver.Models.TilesState;

import java.util.BitSet;

public class TilesAdapter extends BaseAdapter {
    private static final int SIZE = 5;
    private static final int[] STATE_LIGHT = {R.attr.state_light};
    private static final int[] STATE_DARK = {R.attr.state_dark};
    private static final int[] STATE_LIGHT_SELECTED = {R.attr.state_light,R.attr.state_tile_selected};
    private static final int[] STATE_DARK_SELECTED = {R.attr.state_dark,R.attr.state_tile_selected};
    private final TilesState tilesState = new TilesState();
    private BitSet moves = new BitSet(SIZE*SIZE);
    private int movesLeft = 0;

    public TilesAdapter() {
    }

    @Override
    public int getCount() {
        return SIZE*SIZE;
    }

    @Override
    public Object getItem(int position) {
        return tilesState.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    public TilesState getTilesState() {
        return tilesState;
    }

    public void setMoves(BitSet moves) {
        this.moves = moves;
    }

    public void setSelected(final ViewGroup parent){
        for (int i = 0; i < SIZE * SIZE; i++) {
            if(moves.get(i)) setSelected(i,parent);
        }

    }

    private void setSelected(int position, final ViewGroup parent){
        Tile tile = tilesState.get(position);
        ImageButton tileButton = parent.getChildAt(position).findViewById(R.id.tile_button);
        tile.setPointed(true);
        tileButton.getDrawable().setState(tileButton.onCreateDrawableState(2));
        tileButton.setImageState(tile.isLight() ? STATE_LIGHT_SELECTED : STATE_DARK_SELECTED, true);
        movesLeft++;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView!=null) return convertView;
        LayoutInflater infater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = infater.inflate(R.layout.tile_layout,null);
        final Tile tile = (Tile) getItem(position);
        final ImageButton tileButton = view.findViewById(R.id.tile_button);
        tileButton.getDrawable().setState(tileButton.onCreateDrawableState(2));
        tileButton.setImageState(tile.isLight() ? STATE_LIGHT : STATE_DARK,true);



        tileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tile.isPointed()) {
                    if(movesLeft==0) {
                        tile.changeColor();
                        tileButton.getDrawable().setState(tileButton.onCreateDrawableState(2));
                        tileButton.setImageState(tile.isLight() ? STATE_LIGHT : STATE_DARK, true);
                    }
                } else {
                    movesLeft--;
                    for (Integer i : tilesState.turnPositions(position)) {
                        Tile tile = tilesState.get(i);
                        ImageButton tileButton = parent.getChildAt(i).findViewById(R.id.tile_button);
                        tile.changeColor();
                        tileButton.getDrawable().setState(tileButton.onCreateDrawableState(2));
                        if(i==position || !tile.isPointed()){
                            tile.setPointed(false);
                            tileButton.setImageState(tile.isLight() ? STATE_LIGHT : STATE_DARK, true);
                        } else {
                            tileButton.setImageState(tile.isLight() ? STATE_LIGHT_SELECTED : STATE_DARK_SELECTED, true);
                        }

                    }

                }
            }
        });

        tileButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(movesLeft==0) {
                    for (Integer i : tilesState.turnPositions(position)) {
                        Tile tile = tilesState.get(i);
                        ImageButton tileButton = parent.getChildAt(i).findViewById(R.id.tile_button);
                        tile.changeColor();
                        tileButton.getDrawable().setState(tileButton.onCreateDrawableState(2));
                        tileButton.setImageState(tile.isLight() ? STATE_LIGHT : STATE_DARK, true);
                    }
                }
                return true;
            }
        });
        return view;
    }
}
