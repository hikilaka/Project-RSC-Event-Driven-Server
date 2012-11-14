package com.projectrsc.gameserver.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.projectrsc.gameserver.entity.Entity;

public final class StatefulEntityCollection<T extends Entity> {

	private final Set<T> entitiesToRemove = new HashSet<>();

	private final Set<T> knownEntities = new HashSet<>();

	private final Set<T> newEntities = new HashSet<>();

	public void add(Collection<T> entities) {
		newEntities.addAll(entities);
	}

	public void add(T entity) {
		newEntities.add(entity);
	}

	public boolean changed() {
		return !entitiesToRemove.isEmpty() || !newEntities.isEmpty();
	}

	public boolean contains(T entity) {
		return newEntities.contains(entity) || knownEntities.contains(entity);
	}

	public Collection<T> getAllEntities() {
		Set<T> temp = new HashSet<T>();
		temp.addAll(newEntities);
		temp.addAll(knownEntities);
		return temp;
	}

	public Collection<T> getKnownEntities() {
		return knownEntities;
	}

	public Collection<T> getNewEntities() {
		return newEntities;
	}

	public Collection<T> getRemovingEntities() {
		return entitiesToRemove;
	}

	public boolean isRemoving(T entity) {
		return entitiesToRemove.contains(entity);
	}

	public void remove(T entity) {
		entitiesToRemove.add(entity);
	}

	public void update() {
		knownEntities.removeAll(entitiesToRemove);
		knownEntities.addAll(newEntities);
		newEntities.clear();
		entitiesToRemove.clear();
	}

}