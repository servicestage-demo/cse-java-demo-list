package com.huawei.entity;

public class Phone {

  private String id;

  private String type;

  private double length;

  private double width;

  private double thickness;

  private String color;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getThickness() {
    return thickness;
  }

  public void setThickness(double thickness) {
    this.thickness = thickness;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Phone() {
    super();
  }

  public Phone(String id, String type, double length, double width, double thickness, String color) {
    super();
    this.id = id;
    this.type = type;
    this.length = length;
    this.width = width;
    this.thickness = thickness;
    this.color = color;
  }

  @Override
  public String toString() {
    return "Phone [id=" + id + ", type=" + type + ", length=" + length + ", width=" + width + ", thickness="
        + thickness + ", color=" + color + "]";
  }

}
