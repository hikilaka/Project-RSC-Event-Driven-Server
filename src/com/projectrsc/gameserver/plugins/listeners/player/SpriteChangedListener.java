package com.projectrsc.gameserver.plugins.listeners.player;

import com.projectrsc.gameserver.entity.LivingEntity;
import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.event.EventListener;
import com.projectrsc.gameserver.event.impl.SpriteChangedEvent;

public final class SpriteChangedListener extends EventListener<SpriteChangedEvent> {

	public SpriteChangedListener() {
		super(SpriteChangedEvent.class);
	}

	@Override
	public boolean satisfied(SpriteChangedEvent event) {
		return true;
	}

	@Override
	public void handle(SpriteChangedEvent event) {
		LivingEntity entity = event.getEntity();
		
		if (entity instanceof Player) {
			
		} else {
			
		}
	}

}
