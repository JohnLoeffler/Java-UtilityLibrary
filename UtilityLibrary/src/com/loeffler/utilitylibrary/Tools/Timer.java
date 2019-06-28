package com.loeffler.utilitylibrary.Tools;
import java.util.ArrayList;
import java.util.List;

/**
 *  <p><strong>Timer</strong></p>
 *  <em>@author</em>  John Loeffler
 *  
 *  <strong>Contact</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    Github.com/JohnLoeffler
 *    <em>@Bitbucket</em> Bitbucket.org/JohnLoeffler
 *
 *  Adapted from a C++ timer I built because there are 20 different ways to
 *    get milliseconds elapsed since epoch in C++, and they all suck. Java
 *    makes it much easier, but sometimes its handy to just have a stopwatch
 */
public class Timer{
    /* DATA MEMBERS */
  private long        Start, Checkpoint, PauseTime;
  private List<Long>  Laps;
  private boolean     bActive, bPaused;
  
    /* MEMBER METHODS */
  /** Default Constructor */
  public Timer(){
    Laps = new ArrayList<>();
    this.Start      = 0;
    this.Checkpoint = 0;
    this.bActive    = false;
    this.bPaused    = false;
  }
  /**
   * Get the current system time in milliseconds
   * @return A long of the milliseconds
   */
  public static long  Now()           {return System.currentTimeMillis();}
  /**
   * Starts the Timer
   * @return A long of the start time
   */
  public long         Start()           {
    if(!this.bPaused){
      this.Start = Now(); this.bActive = true; return this.Start;
    }else{
      this.Start += (Now()-this.PauseTime); this.bPaused = false; return this.Start;
    }
  }
  /**
   * Tells you how much time has passed since the timer was started
   * @return A long of the elapsed milliseconds since the timer was started
   */
  public long         Elapsed()         {return Now()-this.Start;}
  /**
   * Marks a separate checkpoint time
   * @return A long of the new checkpoint time
   */
  public long         Checkpoint()      {this.Checkpoint = Now(); return this.Checkpoint;}
  /**
   * Gets the time since the last checkpoint
   * @return A long of the time elapsed since the last checkpoint
   */
  public long         SinceCheckpoint() {return (this.Checkpoint == 0 ? 0 : Now()-this.Checkpoint);}
  /**
   * Stops the timer
   * @return A long of the milliseconds recorded by the Timer
   */
  public long         Stop()            {this.Checkpoint = Now(); this.bPaused = false; this.bActive = false; return this.Checkpoint - this.Start;}
  /**
   * TODO Fill this in!
   */
  public void         Pause()           {this.PauseTime = Now(); this.bPaused = true; }
  /**
   * Adds the elapsed time since the last checkpoint, or if there isn't one, the
   *  time since the timer was started. If the Timer was never started, adds 0
   *  
   */
  public void         MarkLap()         {
    Laps.add(this.Checkpoint== 0 ? (this.Start == 0 ? -1 : Elapsed()) : SinceCheckpoint());
    this.Checkpoint();
  }
  /**
   * Gets the start time of the timer
   * @return A long of the time the timer began
   */
  public long         GetStart()        {return this.Start;}
  /**
   * Gets the value of the last checkpoint
   * @return A long of the time of the last checkpoint
   */
  public long         GetCheckpoint()   {return this.Checkpoint;}
  /**
   * Creates a list of the lap times recorded by the timer. Removes any 
   *  non-positive values before returning the list.
   * @return A List with all of the lap values recorded so far by the timer
   */
  public List<Long>   GetLaps()         {
    List<Long> temp = new ArrayList<>();
    for(Long l : Laps){
      if(l <= 0)
        continue;
      else
        temp.add(l);
    }
    return temp;
  }
  /**
   * Tells you if the Timer is active
   * @return True if timer has started and not currently paused, false otherwise
   */
  public boolean      isActive(){return this.bActive;}
  /**
   * Tells if a running timer is currently paused
   * @return True if and only if pause() has been called on an active timer
   */
  public boolean      isPaused(){return this.bPaused;}
}
