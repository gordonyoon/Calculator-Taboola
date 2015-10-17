package com.example.gordonyoon.evaluables;


import com.example.gordonyoon.exceptions.UndeclaredVariableException;

import java.util.HashMap;

public class SimpleVar implements Evaluable {
    private final char var;
    private final HashMap<Character, Integer> map;

    private boolean increment = false;

    public SimpleVar(char var, boolean increment, HashMap<Character, Integer> map) {
        this.var = var;
        this.map = map;
        this.increment = increment;
    }

    @Override
    public int evaluate(HashMap<Character, Integer> postIncrements) {
        if (increment) {
            if (!postIncrements.containsKey(var)) postIncrements.put(var, 0);
            postIncrements.put(var, postIncrements.get(var) + 1);
        }
        if (!map.containsKey(var)) throw new UndeclaredVariableException();

        return map.get(var);
    }
}