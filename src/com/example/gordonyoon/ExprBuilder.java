package com.example.gordonyoon;


import com.example.gordonyoon.evaluables.Evaluable;
import com.example.gordonyoon.evaluables.Expr;
import com.example.gordonyoon.evaluables.Op;
import com.example.gordonyoon.evaluables.SimpleExpr;
import com.example.gordonyoon.evaluables.SimpleVar;
import com.example.gordonyoon.exceptions.InvalidStatementException;
import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

class ExprBuilder {
    // the variable for the current expression
    private char var;

    public char getVar() {
        return var;
    }

    Evaluable build(String expr, @NotNull HashMap<Character, Integer> map) {
        if (!isValid(expr)) throw new InvalidStatementException();

        Scanner scanner = new Scanner(expr);  // tokens by whitespace

        // add the variable to the savedVariables - default value 0
        var = scanner.next().charAt(0);
        if (!map.containsKey(var)) map.put(var, 0);

        // handle potential += assignment
        if (scanner.next().equals("+=")) {
            Evaluable left = new SimpleVar(var, false, map);
            return new Expr(left, Op.ADD, buildExpr(scanner, map));
        }
        return buildExpr(scanner, map);
    }

    private Evaluable buildExpr(Scanner scanner, @NotNull HashMap<Character, Integer> map) {
        Evaluable left = getEvaluable(scanner.next(), map);
        return buildExpr(left, scanner, map);
    }

    private Evaluable buildExpr(Evaluable left, Scanner scanner, @NotNull HashMap<Character, Integer> map) {
        if (scanner.hasNext()) {
            switch (scanner.next()) {
                case "+":
                    return new Expr(left, Op.ADD, buildExpr(scanner, map));
                case "-":
                    return new Expr(left, Op.SUBTRACT, buildExpr(scanner, map));
                case "*":
                    Evaluable innerExpr = new Expr(left, Op.MULTIPLY, getEvaluable(scanner.next(), map));
                    if (scanner.hasNext()) {
                        return buildExpr(innerExpr, scanner, map);
                    }
                    return innerExpr;
            }
        }
        return left;
    }

    private Evaluable getEvaluable(String s, @NotNull HashMap<Character, Integer> map) {
        if (isSimple(s)) return new SimpleExpr(Integer.valueOf(s));
        if (isVariable(s)) return new SimpleVar(s.charAt(0), false, map);
        if (isPostIncrement(s)) return new SimpleVar(s.charAt(0), true, map);
        if (isPreIncrement(s))
            return new Expr(new SimpleExpr(1), Op.ADD, new SimpleVar(s.charAt(2), true, map));

        return null;
    }

    private boolean isValid(String expr) {
        Scanner scanner = new Scanner(expr);

        // the first token must be a variable
        if (!scanner.hasNext() || !Pattern.matches("[a-z]", scanner.next())) return false;
        // the second token must be an assignment
        if (!scanner.hasNext() || !isAssignment(scanner.next())) return false;

        boolean valid = false;
        while (scanner.hasNext()) {
            String s = scanner.next();
            if (valid) {  // the next token is an Op
                if (!isOp(s)) {
                    return false;
                }
                valid = false;
            } else {  // the next token is a value
                if (!isValue(s)) {
                    return false;
                }
                valid = true;
            }
        }
        return valid;
    }

    private boolean isAssignment(String s) {
        return Pattern.matches("=", s) || Pattern.matches("\\+=", s);
    }

    private boolean isOp(String s) {
        return (Pattern.matches("\\+", s))
                || Pattern.matches("-", s)
                || Pattern.matches("\\*", s);
    }

    private boolean isValue(String s) {
        return isSimple(s) || isVariable(s) || isPreIncrement(s) || isPostIncrement(s);
    }

    private boolean isSimple(String s) {
        return Pattern.matches("[0-9]+", s);
    }

    private boolean isVariable(String s) {
        return Pattern.matches("[a-z]", s);
    }

    private boolean isPreIncrement(String s) {
        return Pattern.matches("\\+\\+[a-z]", s);
    }

    private boolean isPostIncrement(String s) {
        return Pattern.matches("[a-z]\\+\\+", s);
    }
}