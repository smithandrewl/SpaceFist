package com.spacefist.state.abst;

/**
 * Every state machine state implements State
 */
public interface State
{
    void Update();

    /**
     * Called when this state becomes the active state in
     * the state machine.
     */
    void EnteringState();

    /**
     * Called before another state becomes the active state.
     */
    void ExitingState();
}
