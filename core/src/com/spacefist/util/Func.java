package com.spacefist.util;

public interface Func<T, U> {
    U call(T arg);
}
