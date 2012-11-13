package com.projectrsc.shared.event;

public abstract class CompletionHandler<T> {

	public abstract void completed(T t);
	
}