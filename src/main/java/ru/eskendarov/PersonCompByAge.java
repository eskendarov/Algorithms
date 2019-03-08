package ru.eskendarov;

import java.util.Comparator;

public class PersonCompByAge implements Comparator<Person> {

  @Override
  public int compare(final Person o1, final Person o2) {
    return o1.getAge() - o2.getAge();
  }
}