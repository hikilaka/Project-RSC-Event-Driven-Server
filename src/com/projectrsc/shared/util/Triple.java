package com.projectrsc.shared.util;

/**
 * @author Hikilaka
 * @version 1
 * @since 0.1
 */
public final class Triple<F, S, T> {

	private final F first;

	private final S second;
	
	private final T third;

	public Triple(F first, S second, T third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public F getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}
	
	public T getThird() {
		return third;
	}

	@Override
	public int hashCode() {
		return first.hashCode() | (second.hashCode() >> third.hashCode());
	}

	@Override
	public String toString() {
		return "Triple(" + first + ", " + second + ", " + third + ")";
	}

}