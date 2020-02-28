package com.graph.struct;

import java.lang.Integer;
import java.util.ArrayList;

public class BoardStateHeap<Integer extends Comparable<Integer>> extends BinaryHeap<Integer> {

    private int currentSize;
    private ArrayList<Integer> array;

    public BoardStateHeap() {
        this.currentSize = 0;
        this.array = new ArrayList<Integer>();
    }

    public boolean isAlreadyEncountered(Integer s) {
        return (find(s) != -1) ;
    }

}