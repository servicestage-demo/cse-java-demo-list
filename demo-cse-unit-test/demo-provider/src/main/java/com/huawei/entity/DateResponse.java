package com.huawei.entity;

import java.util.Date;

public class DateResponse {

  private String msg;

  private Date timestamp;

  public DateResponse() {
    super();
  }

  public DateResponse(String msg, Date timestamp) {
    super();
    this.msg = msg;
    this.timestamp = timestamp;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
