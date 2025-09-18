package org.example.manager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Менеджер истории команд.
 */
public class CommandHistoryManager {
	private final Deque<String> lastEight = new ArrayDeque<>();

	public void add(String name) {
		lastEight.addLast(name);
		while (lastEight.size() > 8) lastEight.removeFirst();
	}

	public List<String> getHistory() {
		return lastEight.stream().collect(Collectors.toList());
	}
}
