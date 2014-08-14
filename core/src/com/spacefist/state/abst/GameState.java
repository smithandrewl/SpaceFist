package com.spacefist.state.abst;

/**
 * The interface implemented by each game screen / state.
 */
public interface GameState extends State {
    void loadContent();
    void draw();
}

