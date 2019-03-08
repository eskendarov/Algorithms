package ru.eskendarov;

        import java.util.Comparator;

public class CharComparator implements Comparator<Character> {

  @Override
  public int compare(final Character o1, final Character o2) {
    return o1 - o2;
  }
}