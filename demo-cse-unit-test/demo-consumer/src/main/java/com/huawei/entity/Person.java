package com.huawei.entity;

public class Person {

  private String pid;

  private String name;

  private String password;

  private String gender;

  private int age;

  public Person() {
    super();
  }

  public Person(String pid, String name, String password, String gender, int age) {
    super();
    this.pid = pid;
    this.name = name;
    this.password = password;
    this.gender = gender;
    this.age = age;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "Person [pid=" + pid + ", name=" + name + ", password=" + password + ", gender=" + gender + ", age=" + age
        + "]";
  }

}
