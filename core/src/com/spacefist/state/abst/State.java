package com.spacefist.state.abst;

/**
 * Every state machine state implements State
 */
public interface State {
    void update();

    /**
     * Called when this state becomes the active state in
     * the state machine.
     */
    void enteringState();

    /**
     * Called before another state becomes the active state.
     */
    void exitingState();
}
