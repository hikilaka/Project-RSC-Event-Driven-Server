package com.projectrsc.shared.util;

/**
 * @author Hikilaka
 * @version 1
 * @since 0.1
 */
public final class Pair<F, S> {

	private final F first;

	private final S second;

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public F getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}

	public Pair<S, F> swapValues() {
		return new Pair<S, F>(second, first);
	}

	@Override
	public int hashCode() {
		return first.hashCode() | second.hashCode();
	}

	@Override
	public String toString() {
		return "Pair(" + first + ", " + second + ")";
	}

}
