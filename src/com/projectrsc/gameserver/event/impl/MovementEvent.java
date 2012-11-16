package com.projectrsc.gameserver.event.impl;

import com.projectrsc.gameserver.entity.LivingEntity;
import com.projectrsc.gameserver.event.Event;

public final class MovementEvent extends Event {

	private final LivingEntity entity;

	public MovementEvent(LivingEntity entity) {
		this.entity = entity;
	}

	public LivingEntity getEntity() {
		return entity;
	}

}
