package ru.eskendarov;

/**
 * @author Enver Eskendarov
 */
public class Person {

  private final String name;
  private final int age;
  private final char sex;

  public Person(final String name, final int age, final char sex) {
    this.name = name;
    this.sex = sex;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  @Override
  public String toString() {
    return String.format("{%s %s(%S)}", name, age, sex);
  }
}