package com.polarbirds.zeus.hudoverlay.chat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Harald on 23.01.2016.
 */
public class ChatMsg {

  private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
  private final String timeStamp;
  public final long time;
  public final String msg;

  public ChatMsg(String msg) {
    time = System.currentTimeMillis();
    this.msg = msg;
    timeStamp = timeFormatter.format(new Date(time));
  }

  @Override
  public String toString() {
    return timeStamp + " " + msg;
  }


}
