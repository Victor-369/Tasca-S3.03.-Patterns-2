package com.patterns.builder.interfaces;

public interface AfterStarter {
    public AfterMainCourse withMainCourse(String mainCourse);
    public AfterStarter isVegan();
    public AfterStarter isGlutenFree();
}
