package com.company;

import java.util.Stack;

public class SpringArray {
    public static Spring equivalentSpring(String springExpr) throws IllegalArgumentException {
        Stack<Spring> springStack = new Stack<>();

        for (int i = 0; i < springExpr.length(); i++) {
            char c = springExpr.charAt(i);

            if (c == '{') {
                springStack.push(new Spring());
            } else if (c == '[') {
                springStack.push(null);
            } else if (c == '}') {
                Spring currentSpring = springStack.pop();
                Spring previousSpring = springStack.pop();

                if (currentSpring == null || previousSpring == null) {
                    throw new IllegalArgumentException("Invalid spring expression: " + springExpr);
                }

                Spring equivalentSpring = previousSpring.inSeries(currentSpring);
                springStack.push(equivalentSpring);
            } else if (c == ']') {
                Spring equivalentSpring = null;

                while (springStack.peek() != null) {
                    Spring currentSpring = springStack.pop();

                    if (equivalentSpring == null) {
                        equivalentSpring = currentSpring;
                    } else {
                        equivalentSpring = equivalentSpring.inParallel(currentSpring);
                    }
                }

                springStack.pop();
                springStack.push(equivalentSpring);
            }
        }

        if (springStack.size() != 1 || springStack.peek() == null) {
            throw new IllegalArgumentException("Invalid spring expression: " + springExpr);
        }

        return springStack.pop();
    }


//Couldn wrtie the other method of task 2 public static Spring equivalentSpring(String springExpr,Spring[] springs)




}
