package com.example.runescapedungpuzzlesolver.Models;

import com.example.runescapedungpuzzlesolver.Models.Tile;

import java.util.ArrayList;
import java.util.BitSet;

public class TilesState {
    private static final int SIZE = 5;
    private final Tile[][] tiles = new Tile[SIZE][SIZE];

    public TilesState(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.tiles[j][i] = new Tile();
            }
        }
    }
    public TilesState(Tile[][] tiles){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.tiles[j][i] = new Tile(tiles[j][i]);
            }
        }
    }
    public TilesState(Tile[][] tiles,BitSet moves){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.tiles[j][i] = new Tile(tiles[j][i]);
            }
        }
        for (int i = 0; i < SIZE * SIZE; i++) {
            if(moves.get(i)) doTurn(i);
        }
    }


    public void doTurn(int position){
        int x = position%SIZE;
        int y = position/SIZE;
        tiles[y][x].changeColor();
        if(x>0) tiles[y][x-1].changeColor();
        if(x<SIZE-1) tiles[y][x+1].changeColor();
        if(y>0) tiles[y-1][x].changeColor();
        if(y<SIZE-1) tiles[y+1][x].changeColor();
    }

    public ArrayList<Integer> turnPositions(int position){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(position);
        int x = position%SIZE;
        int y = position/SIZE;
        if(x>0) result.add(position-1);
        if(x<SIZE-1) result.add(position+1);
        if(y>0) result.add(position-SIZE);
        if(y<SIZE-1) result.add(position+SIZE);
        return result;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile get(int position) {
        return tiles[position/SIZE][position%SIZE];
    }

    public boolean checkBoard(){
        boolean isLight = get(0).isLight();
        for (int i = 1; i < SIZE * SIZE; i++) {
            if(get(i).isLight()!=isLight) return false;
        }
        return true;
    }
}
