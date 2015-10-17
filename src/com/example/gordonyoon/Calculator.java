package com.example.gordonyoon;


import java.util.ArrayList;
import java.util.HashMap;

class Calculator {

    private HashMap<Character, Integer> savedVariables = new HashMap<>();

    public void evaluate(String expr) {
        ExprBuilder builder = new ExprBuilder();
        HashMap<Character, Integer> postIncrements = new HashMap<>();
        HashMap<Character, Integer> tempSavedVariables = new HashMap<>(savedVariables);

        int result = builder.build(expr, tempSavedVariables).evaluate(postIncrements);

        // if an exception is thrown, our values won't be mangled
        tempSavedVariables.put(builder.getVar(), result);

        for (char key : postIncrements.keySet()) {
            int addBy = postIncrements.get(key);
            tempSavedVariables.put(key, tempSavedVariables.get(key) + addBy);
        }

        savedVariables = tempSavedVariables;
    }

    public void printVars() {
        System.out.print("{");
        ArrayList<Character> keys = new ArrayList<>(savedVariables.keySet());
        for (int i = 0; i < keys.size(); i++) {
            System.out.print(keys.get(i) + "=" + savedVariables.get(keys.get(i)));
            if (i < keys.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.print("}");
    }
}