/*
 * 
 * 
 * 
 */

package com.loeffler.utilitylibrary.Tools;

/**
 *  <p><strong>Clicker</strong></p>
 *  <em>@author</em>  John Loeffler
 *  
 *  <strong>Contact</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    github.com/JohnLoeffler
 *    <em>@Website</em>   JohnLoeffler.com
 */
public class Clicker {
  private int Tick, Mod;
  
  /**
   * Sets number of states before clicker starts again
   * @param states the number of states to click through; if zero or less, it only has one state
   */
  public Clicker(int states){Tick =0; if(states <=0) Mod =1; else Mod =states;}
  /**
   * Clicks to next state
   */
  public void Click(){Tick = ((Tick++)%Mod);}
  /**
   * Returns the current State
   * @return and int representing the state of the clicker
   */
  public int GetTick(){return Tick;}
  /**
   * Returns clicker to zero
   */
  public void Reset(){Tick = 0;}
}
