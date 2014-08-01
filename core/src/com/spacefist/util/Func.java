package com.spacefist.util;

public interface Func<T, U> {
    public U call(T arg);
}
