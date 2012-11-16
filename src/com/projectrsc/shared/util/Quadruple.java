package com.projectrsc.shared.util;

/**
 * @author Hikilaka
 * @version 1
 * @since 0.1
 */
public final class Quadruple<F, S, T, Ft> {

	private final F first;

	private final S second;
	
	private final T third;
	
	private final Ft fourth;

	public Quadruple(F first, S second, T third, Ft fourth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
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
	
	public Ft getFourth() {
		return fourth;
	}

	@Override
	public int hashCode() {
		return (first.hashCode() >> fourth.hashCode()) | (second.hashCode() >> third.hashCode());
	}

	@Override
	public String toString() {
		return "Quadruple(" + first + ", " + second + ", " + third + ", " + fourth + ")";
	}

}