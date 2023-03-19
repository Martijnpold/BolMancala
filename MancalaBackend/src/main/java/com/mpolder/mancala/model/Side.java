package com.mpolder.mancala.model;

public enum Side {
    TOP,
    BOTTOM,
    ;

    public Side opponent() {
        return Side.values()[(ordinal() + 1) % values().length];
    }
}
