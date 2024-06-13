package com.banking.cqrs.core.domain;

import java.util.ArrayList;
import java.util.List;

import com.banking.cqrs.core.events.BaseEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AggregateRoot {
	
	@Getter
	protected String id;
	
	@Getter
	@Setter
	private Integer version = -1;
	
	private final List<BaseEvent> changes = new ArrayList<>();
	
	public List<BaseEvent> getUncommitedChanges() {
		return changes;
	}
	
	public void markChangesAsCommited() {
		changes.clear();
	}
	
	protected void applyChange(BaseEvent event, Boolean isNew) {
		try {
			var method = getClass().getDeclaredMethod("apply", event.getClass());
			method.setAccessible(Boolean.TRUE);
			method.invoke(this, event);
		} catch (NoSuchMethodException e) {
			log.warn("El método apply no fue encontrado para {}", event.getClass().getName());
		} catch (Exception e) {
			log.error("Errores aplicando el evento al aggregate", e);
		} finally {
			if (isNew) {
				changes.add(event);
			}
		}
	}
	
	public void raiseEvent(BaseEvent event) {
		applyChange(event, Boolean.TRUE);
	}
	
	public void replayEvents(Iterable<BaseEvent> events) {
		events.forEach(event -> applyChange(event, Boolean.FALSE));
	}
}
