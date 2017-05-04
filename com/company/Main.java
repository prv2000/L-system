package com.company;

import ru.ege.engine.EGEngine;
import ru.ege.examples.stuff.DebugObjects;

public class Main {
    public static void main(String[] args) {
        EGEngine.i().startDrawingThread();
        EGEngine.i().addDrawableObject(new Lsystem());
        DebugObjects.addDebugObjects(EGEngine.i());
    }
}
