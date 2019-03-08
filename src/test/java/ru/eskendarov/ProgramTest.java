package ru.eskendarov;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;

/**
 * Unit test for Program.
 *
 * @author Enver Eskendarov
 */
public class ProgramTest {

  private final ArrayListReplica<Character> arrayList = new ArrayListReplica<>();
  private final ArrayListReplica<Integer> integerList = new ArrayListReplica<>();
  private final ArrayListReplica<Person> personList = new ArrayListReplica<>();
  private final Person oleg = new Person("Oleg", 25, 'm');
  private final Person manya = new Person("Manya", 21, 'f');
  private final Person olya = new Person("Olya", 32, 'f');
  private final Person roma = new Person("Roma", 23, 'm');
  private final ExecutorService service = Executors.newFixedThreadPool(3);

  @Test
  public void firstTest() {
    arrayList.add('@');
    arrayList.add('#');
    arrayList.add('c');
    arrayList.add('%');

    personList.add(oleg);
    personList.add(roma);
    personList.add(olya);

    System.out.println("\nprint()");
    System.out.println(arrayList);
    System.out.println(personList);

    System.out.println("\nget()");
    System.out.println(arrayList.get(2));
    System.out.println(personList.get(1));

    System.out.println("\nset()");
    arrayList.set(2, '2');
    arrayList.set(0, '0');
    System.out.println(arrayList);


    System.out.println("\ncontains()");
    System.out.println(arrayList.contains('4'));
    System.out.println(personList.contains(roma));
    System.out.println(personList.contains(manya));

    System.out.println("\nindexOf()");
    System.out.println(arrayList.indexOf('c'));
    System.out.println(personList.indexOf(oleg));

    System.out.println("\niterator()");
    for (final Person person : personList) {
      System.out.println(person);
    }
//    final Iterator<Person> iterator = personList.iterator();
//    while (iterator.hasNext()) {
//      System.out.print(iterator.next() + " ");
//    }
//    System.out.println();

    System.out.println("\nremove");
    personList.remove(oleg);
    System.out.println(personList);

    System.out.println("\nsize()");
    System.out.println(arrayList.size());
    System.out.println(personList.size());
  }

  @Test
  public void secondTest() {
    final long start = System.currentTimeMillis();
    for (int i = 0; i < 20; i++) {
      arrayList.add((char) (65 + new Random().nextInt(20)));
    }
    System.out.println(arrayList);
    System.out.println("after sorting");
    service.submit(() -> {
      arrayList.selectionSort(new CharComparator());
      System.out.println("selectionSorting time " +
              (System.currentTimeMillis() - start));
      System.out.println(arrayList);
    });
    service.submit(() -> {
      arrayList.insertionSort(new CharComparator());
      System.out.println("insertionSorting time " +
              (System.currentTimeMillis() - start));
      System.out.println(arrayList);
    });
    service.submit(() -> {
      System.out.println("\nresult for Q = " +
              arrayList.binarySearch('Q', new CharComparator()));
      System.out.println("binarySearching time " +
              (System.currentTimeMillis() - start));
    });
  }

  @Test
  public void thirdTest() {
    final long start = System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
      integerList.add(new Random().nextInt(1000));
    }
    System.out.println(integerList);
    System.out.println("---------------------------");
    service.submit(() -> {
      integerList.selectionSort(Integer::compareTo);
      System.out.println("selectionSorting time " +
              (System.currentTimeMillis() - start));
    });
    service.submit(() -> {
      integerList.insertionSort(Integer::compareTo);
      System.out.println("insertionSorting time " +
              (System.currentTimeMillis() - start));
    });
    service.submit(() -> {
      System.out.println("\nresult for 100 = " +
              integerList.binarySearch(100, Integer::compareTo));
      System.out.println("binarySearching time " +
              (System.currentTimeMillis() - start));
    });
    try {
      Thread.sleep(1000);
      System.out.println(integerList);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
