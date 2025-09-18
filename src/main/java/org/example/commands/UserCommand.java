package org.example.commands;

import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;
import org.example.file.FileManager;

/**
 * Базовый класс пользовательской команды.
 */
public abstract class UserCommand {
	private final String name;
	private final String description;
	protected final CollectionManager collectionManager;
	protected final CommandHistoryManager historyManager;
	protected final FileManager fileManager;

	protected UserCommand(String name,
	                     String description,
	                     CollectionManager collectionManager,
	                     CommandHistoryManager historyManager,
	                     FileManager fileManager) {
		this.name = name;
		this.description = description;
		this.collectionManager = collectionManager;
		this.historyManager = historyManager;
		this.fileManager = fileManager;
	}

	public String getName() { return name; }
	public String getDescription() { return description; }

	/**
	 * Выполнить команду.
	 * @param args строка аргументов
	 * @param io IO-менеджер для ввода/вывода
	 * @return true если приложение должно продолжать работу
	 */
	public abstract boolean execute(String args, IOManager io) throws Exception;
}
