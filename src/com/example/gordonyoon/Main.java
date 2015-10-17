package com.example.gordonyoon;


import com.example.gordonyoon.exceptions.InvalidStatementException;
import com.example.gordonyoon.exceptions.UndeclaredVariableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {

    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator();

        System.out.print("This text based calculator evaluates multiple expressions.\n" +
                "The following operations are supported: addition, subtraction, multiplication, pre-increment, and post-increment.\n\n" +
                "Separate all values and operators with a space and press enter with a blank statement to print the variables and their values.\n\n");

        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter statement: ");
            String s = br.readLine();

            if (s.isEmpty()) break;

            try {
                calculator.evaluate(s);
            } catch (InvalidStatementException e) {
                System.out.println("Invalid statement. Please try again.");
            } catch (UndeclaredVariableException e) {
                System.out.println("You tried to use an undeclared variable. Please try again.");
            } catch (UnsupportedOperationException e) {
                System.out.println("You used an unsupported operation. Please try again.");
            }
        }
        calculator.printVars();
    }
}
