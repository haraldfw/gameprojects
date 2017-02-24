package com.polarbirds.zeus.input;

/**
 * An interface for defining a motive. I. e.  xbox-controller, keyboard or an intelligence Created
 * by Harald on 14.5.15.
 */
public interface IMotiveProcessor {

  float moveX();

  float moveY();

  float lookX();

  float lookY();

  boolean attack1();

  boolean attack2();

  boolean jump();

  boolean interact();

  void update();
}
