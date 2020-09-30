package com.example;




class Plate<T>{

	public Plate(T item) {
		this.item = item;
	}

	private T item;

	public void setItem(T item) {
		this.item = item;
	}

	public T getItem() {
		return item;
	}
}
