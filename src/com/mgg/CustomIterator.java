package com.mgg;
import java.util.Iterator;

public class CustomIterator<T> implements Iterator<T>{
	
	private CustomList<T> cl;
	private int index;

	public CustomIterator(CustomList<T> cl) {
		this.cl = cl;
		this.index = 0;
	}
	
	public boolean hasNext() {
		return  (index < cl.size());
	}

	@Override
	public T next() {
		return cl.get(index++);
	}

}
