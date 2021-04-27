package com.mgg;

/**
 * ArrayBased List that can be set to have elements sorted by comparator 
 */

import java.util.Comparator;
import java.util.Iterator;

public class CustomList<T> implements Iterable<T> {

	private T arr[];
	private int size;
	// Capacity is a capacity of how big the arr is
	// almost always is equal to size but not always
	private int capacity = 1;
	// The amount capacity is raised by when increasing size
	// 1 for minimum memory use, more for better performance
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

	/**
	 * Adds elements to end of no comparator given
	 * 
	 * If comparator given will place in order
	 * 
	 * @param x What to add
	 */
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

	// Adds a array of elements to list
	public void adds(T x[]) {
		for (T element : x) {
			this.add(element);
		}
	}

	/**
	 * Add at adds element x to index Is private to maintain order of list
	 * 
	 * @param index to add at
	 * @param x     what to add
	 */
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

	/**
	 * Increases Capacity of underlining array
	 */
	private void increaseCapacity() {
		T newA[] = (T[]) new Object[capacity + increaseBy];
		for (int i = 0; i < capacity; i++) {
			newA[i] = arr[i];
		}
		this.arr = newA;
		capacity += increaseBy;
	}

	/**
	 * Decreases Capacity of underlining array
	 */
	private void decreaseCapacity() {
		T newA[] = (T[]) new Object[capacity - 1];
		for (int i = 0; i < capacity - 1; i++) {
			newA[i] = arr[i];
		}
		this.arr = newA;
		capacity -= 1;
	}

	/**
	 * Returns Index of needle or -1 if not in list Uses Comparator if given
	 * 
	 * @param needle
	 * @return index
	 */
	public int indexOf(T needle) {
		if (cmp == null) {
			for (int i = 0; i < this.size; i++) {
				if (needle == arr[i]) {
					return i;
				}
			}
			return -1;
		}

		for (int i = 0; i < this.size; i++) {
			if (cmp.compare(needle, arr[i]) == 0) {
				return i;
			}
		}
		return -1;
	}

	public T get(int index) {
		return arr[index];
	}

	/**
	 * Removes Element at index
	 * 
	 * @param index
	 */
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
