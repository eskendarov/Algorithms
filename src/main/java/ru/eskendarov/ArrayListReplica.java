package ru.eskendarov;

import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Enver Eskendarov
 */
public class ArrayListReplica<T> implements Iterable<T> {

  private int size;
  private Object[] array = new Object[1];

  public int size() {
    return size;
  }

  public T get(final int index) {
    checkIndexInBounds(index);
    return (T) array[index];
  }

  private void checkIndexInBounds(final int index) {
    if (index < 0 || index > size - 1) {
      throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new CustomIterator();
  }

  public void set(final int index, final T object) {
    checkIndexInBounds(index);
    array[index] = object;
  }

  public void add(final T object) {
    if (size == array.length) {
      resize(2 * array.length);
    }
    array[size] = object;
    size++;
  }

  private void resize(final int capacity) {
    final Object[] temp = new Object[capacity];
    if (size >= 0) {
      System.arraycopy(array, 0, temp, 0, size);
      array = temp;
    }
  }

  public boolean contains(T object) {
    return indexOf(object) != -1;
  }

  public int indexOf(T object) {
    for (int i = 0; i < size; i++) {
      if (array[i].equals(object)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < size; i++) {
      stringBuilder.append(array[i]);
      if (i != size - 1) {
        stringBuilder.append(",");
      }
    }
    return String.format("{%s}", stringBuilder);
  }

  public void insertionSort(Comparator<T> cmp) {
    for (int i = 0; i < size; i++) {
      for (int j = i; j > 0; j--) {
        if (less((T) array[j], (T) array[j - 1], cmp)) {
          exch(j, j - 1);
        } else {
          break;
        }
      }

    }
  }

  private boolean less(final T object1, final T object2,
                       final Comparator<T> comparator) {
    return comparator.compare(object1, object2) < 0;
  }

  private void exch(final int i, final int j) {
    final Object temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  public boolean remove(T object) {
    int i = 0;
    while (i < size && !array[i].equals(object)) {
      i++;
    }
    if (i == size) {
      return false;
    }
    if (size - 1 - i >= 0) {
      System.arraycopy(array, i + 1, array, i, size - 1 - i);
    }
    array[size - 1] = null;
    size--;
    if (size == array.length / 4 && size > 0) {
      resize(array.length / 2);
    }
    return true;
  }

  public void selectionSort(final Comparator<T> comparator) {
    for (int i = 0; i < size - 1; i++) {
      int min = i;
      for (int j = i + 1; j < size; j++) {
        if (less((T) array[j], (T) array[min], comparator)) {
          min = j;
        }
      }
      exch(i, min);
    }
  }

  public boolean binarySearch(T object, Comparator<T> comparator) {
    int low = 0;
    int high = size - 1;
    while (low <= high) {
      int mid = low + (high - low) / 2;
      if (comparator.compare(object, (T) array[mid]) < 0) {
        high = mid - 1;
      }
      if (comparator.compare(object, (T) array[mid]) > 0) {
        low = mid + 1;
      } else {
        return true;
      }
    }
    return false;
  }

  private class CustomIterator implements Iterator<T> {

    int cursor = 0;

    @Override
    public boolean hasNext() {
      return cursor != size;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new IndexOutOfBoundsException();
      }
      final T object = (T) array[cursor];
      cursor++;
      return object;
    }
  }
}