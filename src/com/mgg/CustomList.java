package com.mgg;


import java.util.Comparator;
import java.util.Iterator;

public class CustomList<T> implements Iterable<T> {

	private T arr[];
	private int size;
	private int capacity = 1;
	private static final int increaseBy = 1;

	private Comparator<T> cmp = null;

	public CustomList() {
		this.arr = (T[]) new Object[capacity];
		this.size = 0;
	}

	public CustomList(Comparator<T> cmp) {
		this.arr = (T[]) new Object[capacity];
		this.size = 0;
		this.cmp = cmp;
	}

	public int size() {
		return this.size;
	}

	public void add(T x) {
		if (size == 0) {
			this.addAt(0, x);
			return;
		}
		if (cmp == null) {
			this.addAt(size, x);
			return;
		}

 		int index = 0;
		while ((index < size) && (cmp.compare(x, arr[index]) <= 0)) {
			index++;
		}
		this.addAt(index, x);

	}

	public void adds(T x[]) {
		for (T element : x) {
			this.add(element);
		}
	}

	private void addAt(int index, T x) {
		if ((index < 0) || (index > size)) {
			throw new IllegalArgumentException("Index out of Bounds");
		}

		if (this.size == capacity) {
			this.increaseCapacity();
		}

		for (int i = this.size; i > index; i--) {
			this.arr[i] = this.arr[i - 1];
		}
		this.arr[index] = x;
		this.size++;

	}

	private void increaseCapacity() {
		T newA[] = (T[]) new Object[capacity + increaseBy];
		for(int i = 0;i< capacity ;i++) {
			newA[i] = arr[i];
		}
		this.arr = newA;
		capacity += increaseBy;
	}

	private void decreaseCapacity() {
		T newA[] = (T[]) new Object[capacity - 1];
		for(int i = 0;i< capacity - 1 ;i++) {
			newA[i] = arr[i];
		}
		this.arr = newA;
		capacity -= 1;
	}
	public T get(int index) {
		return arr[index];
	}

	public void remove(int index) {
		if ((index < 0) || (index >= size)) {
			throw new IllegalArgumentException("Index out of Bounds");
		}
		for (int i = index; i < size - 1; i++) {
			this.arr[i] = this.arr[i + 1];
		}
		this.size--;
		this.decreaseCapacity();
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < size; i++) {
			str += arr[i].toString() + ", ";
		}
		return str;
	}

	@Override
	public Iterator<T> iterator() {
		return new CustomIterator<T>(this);
	}

}
