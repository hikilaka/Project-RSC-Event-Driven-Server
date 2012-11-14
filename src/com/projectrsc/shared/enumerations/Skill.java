package com.projectrsc.shared.enumerations;

public enum Skill {

	Attack(0), Defense(1), Strength(2), Hits(3), Range(4), Prayer(5), Magic(6), Cooking(
			7), Woodcutting(8), Fletching(9), Fishing(10), Firemaking(11), Crafting(
			12), Smithing(13), Mining(14), Herblaw(15), Agility(16), Thieving(
			17);

	private final int value;

	Skill(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
