package com.example.gordonyoon.evaluables;


import java.util.HashMap;

public class SimpleExpr implements Evaluable {
    private final int value;

    public SimpleExpr(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(HashMap<Character, Integer> postIncrements) {
        return value;
    }
}