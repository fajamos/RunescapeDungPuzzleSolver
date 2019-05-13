package com.example.runescapedungpuzzlesolver.Models;


public class Tile {
    private boolean isLight = true;
    private boolean isPointed = false;

    public Tile() {}
    public Tile(Tile tile){
        isLight = tile.isLight;
        isPointed = tile.isPointed;
    }

    public boolean isLight() {
        return isLight;
    }

    public void setLight(boolean light) {
        isLight = light;
    }

    public boolean isPointed() {
        return isPointed;
    }

    public void setPointed(boolean pointed) {
        isPointed = pointed;
    }
    public void changeColor(){
        isLight = !isLight;
    }
}
