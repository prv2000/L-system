package com.company;

import ru.ege.engine.DrawableObject;
import ru.ege.engine.Vector2D;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Lsystem implements DrawableObject {

    Vector2D position = new Vector2D(500, 500);
    Color color = Color.BLACK;
    double stepSize = 10;
    boolean trail = true;
    double angle = 0;

    public static String tree(int n, String s) {
        for (int i = 0; i < n; i++) {
            s = replaceChars(s, new char[]{'x', 'f'}, new String[]{"f-c[[x]+x]+f[+fx]-x", "ff"});
        }
        return s;
    }

    @Override
    public void drawAndUpdate(Graphics2D g, double v) {
        Random r = new Random(255);
        String s = "x";
        LinkedList<Double> angles = new LinkedList<>();
        LinkedList<Vector2D> positions = new LinkedList<>();
//начальная позиция
        angle = -Math.PI / 2;
        setPosition(new Vector2D(500, 1000));
//правила
        s = tree(5, s);
//рисование
        System.out.println("Drawing string:" + s);
        for (int i = 0; i < s.length(); i++) {
            double t = System.currentTimeMillis() / 1000.0;
            switch (s.charAt(i)) {
                case 'f':
                case 'x':
                case 'd':
                    go(g);
                    break;
                case 'c':
                    setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
                    break;
                case 'l':
                case '+':
                    rotate(Math.sin(3 * t) / 10 + 25 * Math.PI / 180);
                    break;
                case 'r':
                case '-':
                    rotate(Math.sin(5 * t) / 10 - 25 * Math.PI / 180);
                    break;
                case '[':
                    positions.addLast(position);
                    angles.addLast(angle);
                    break;
                case ']':
                    position = positions.getLast();
                    positions.removeLast();
                    angle = angles.getLast();
                    angles.removeLast();
                    break;
            }

        }

    }

    public void go(Graphics2D g) {
        Vector2D end = position.add(new Vector2D(stepSize, 0).rotate(angle));
        if (trail) {
            g.setColor(color);
            g.drawLine(position.getXInt(), position.getYInt(), end.getXInt(), end.getYInt());
        }
        position = end;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }

    public void setLeaveTrail(boolean trail) {
        this.trail = trail;
    }

    public void rotate(double angle) {
        this.angle += angle;
    }

    public static String replaceChars(String s, char[] toReplace, String[] replaces) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            boolean replaced = false;
            for (int j = 0; j < toReplace.length; j++) {
                if (s.charAt(i) == toReplace[j]) {
                    result += replaces[j];
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                result += s.charAt(i);
            }
        }
        return result;
    }
}
