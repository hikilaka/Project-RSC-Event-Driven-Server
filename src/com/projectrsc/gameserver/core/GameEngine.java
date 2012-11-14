package com.projectrsc.gameserver.core;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.projectrsc.gameserver.event.TaskEvent;

public final class GameEngine extends Thread {

	private final Object lock = new Object();

	private final AtomicBoolean running = new AtomicBoolean(true);

	private final ConcurrentLinkedQueue<TaskEvent> taskEvents = new ConcurrentLinkedQueue<>();

	public GameEngine() {
		super.setDaemon(true);
		super.start();
	}

	@Override
	public void run() {
		while (running.get()) {
			while (taskEvents.size() == 0) {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			TaskEvent taskEvent = taskEvents.peek();
			if (taskEvent.satisfied()) {
				taskEvent.handle();
				if (taskEvent.remove()) {
					taskEvents.poll();
				}
			}
		}
	}

	/**
	 * Adds a <code>TaskEvent</code> to a queue
	 * 
	 * @param taskEvent
	 *            The <code>TaskEvent</code> to add
	 */
	public void addEvent(TaskEvent taskEvent) {
		taskEvents.add(taskEvent);

		synchronized (lock) {
			lock.notify();
		}
	}

}
