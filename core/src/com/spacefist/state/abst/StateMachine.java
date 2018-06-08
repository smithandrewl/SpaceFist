package com.spacefist.state.abst;

/**
 * The interface implemented by all state machines.
 */
public interface StateMachine<T> {
    /**
     * The state active in the machine.
     */
    T getCurrentState();
    void setCurrentState(T state);
}

