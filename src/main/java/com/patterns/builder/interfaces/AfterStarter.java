package com.patterns.builder.interfaces;

public interface AfterStarter {
    public AfterMainCourse withMainCourse();
    public AfterStarter isVegan();
    public AfterStarter isGlutenFree();
}
