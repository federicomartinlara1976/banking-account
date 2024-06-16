package com.banking.account.cmd.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.banking.cqrs.core.commands.BaseCommand;
import com.banking.cqrs.core.commands.CommandHandlerMethod;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountCommandDispatcher implements CommandDispatcher {
	
	@SuppressWarnings("rawtypes")
	private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

	@Override
	public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
		var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>()); 
		log.info("Registrando {}", handler.toString());
		handlers.add(handler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void send(BaseCommand command) {
		var handlers = routes.get(command.getClass());
		if (CollectionUtils.isEmpty(handlers)) {
			log.error("El command handler no fue registrado");
			throw new RuntimeException("El command handler no fue registrado");
		}
		
		if (handlers.size() > 1) {
			log.error("No puede enviar un command que tiene más de un handler");
			throw new RuntimeException("No puede enviar un command que tiene más de un handler");
		}
		
		handlers.get(0).handle(command);
	}

}
