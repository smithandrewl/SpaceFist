package com.spacefist.util;

public interface Action<T, U> {
    public void execute(T first, U second);
}
