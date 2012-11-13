package com.projectrsc.gameserver.core;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.projectrsc.gameserver.event.Event;
import com.projectrsc.gameserver.event.EventListener;

public final class GameEngine extends Thread {

	private final Object lock = new Object();

	private AtomicBoolean running = new AtomicBoolean(true);

	private final ConcurrentLinkedQueue<EventListener<? extends Event>> events = new ConcurrentLinkedQueue<>();

	public GameEngine() {
		super.setDaemon(true);
		super.start();
	}
	
	@Override
	public void run() {
		while (running.get()) {
			while (events.size() == 0) {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			EventListener<? extends Event> listener = events.peek();
			if (listener.satisfy()) {
				listener.execute();
				if (listener.remove()) {
					events.poll();	
				}
			}
		}
	}

	public void addEvent(EventListener<? extends Event> listener) {
		events.add(listener);

		synchronized (lock) {
			lock.notify();
		}
	}

}
