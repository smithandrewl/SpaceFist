package com.spacefist.state.abst;

/**
 * The interface implemented by all state machines.
 * </summary>
 */
public interface StateMachine<State> {
    /**
     * The state active in the machine.
     */
    public State getCurrentState();
    public void setCurrentState(State state);
}

