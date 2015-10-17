package com.example.gordonyoon.evaluables;


import java.util.HashMap;

public class Expr implements Evaluable {
    private final Evaluable left;
    private final Op op;
    private final Evaluable right;

    public Expr(Evaluable left, Op op, Evaluable right) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public int evaluate(HashMap<Character, Integer> postIncrements) {
        int leftValue = left.evaluate(postIncrements);
        int rightValue = right.evaluate(postIncrements);

        switch (op) {
            case ADD:
                return leftValue + rightValue;
            case SUBTRACT:
                return leftValue - rightValue;
            case MULTIPLY:
                return leftValue * rightValue;
        }
        throw new UnsupportedOperationException();
    }
}
