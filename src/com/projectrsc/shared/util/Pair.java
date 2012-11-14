package com.projectrsc.shared.util;

/**
 * @author Hikilaka
 * @version 1
 * @since 0.1
 */
public final class Pair<Value1, Value2> {

	private final Value1 first;

	private final Value2 second;

	public Pair(Value1 first, Value2 second) {
		this.first = first;
		this.second = second;
	}

	public Value1 getFirst() {
		return first;
	}

	public Value2 getSecond() {
		return second;
	}

	public Pair<Value2, Value1> swapValues() {
		return new Pair<Value2, Value1>(second, first);
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
