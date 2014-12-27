package com.spacefist.util;

public interface Action<T, U> {
    void execute(T first, U second);
}
