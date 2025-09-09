package org.example.manager;

import org.example.commands.*;
import org.example.file.FileManager;

/**
 * Настройщик менеджера команд.
 */
public class CommandManagerSetuper {
	public static void SetupCommandManager(org.example.manager.iomanager.IOManager io,
	                                       CollectionManager collectionManager,
	                                       CommandManager commandManager,
	                                       FileManager xmlFileManager) {
		commandManager.register(new HelpCommand(collectionManager, commandManager, xmlFileManager));
		commandManager.register(new InfoCommand(collectionManager, xmlFileManager));
		commandManager.register(new ShowCommand(collectionManager, xmlFileManager));
		commandManager.register(new AddCommand(collectionManager, xmlFileManager));
		commandManager.register(new UpdateIdCommand(collectionManager, xmlFileManager));
		commandManager.register(new RemoveByIdCommand(collectionManager, xmlFileManager));
		commandManager.register(new ClearCommand(collectionManager, xmlFileManager));
		commandManager.register(new SaveCommand(collectionManager, xmlFileManager));
		commandManager.register(new ExecuteScriptCommand(collectionManager, xmlFileManager, commandManager));
		commandManager.register(new ExitCommand(collectionManager, xmlFileManager));
		commandManager.register(new AddIfMinCommand(collectionManager, xmlFileManager));
		commandManager.register(new RemoveGreaterCommand(collectionManager, xmlFileManager));
		commandManager.register(new HistoryCommand(collectionManager, xmlFileManager, commandManager));
		commandManager.register(new AverageOfOscarsCount(collectionManager, xmlFileManager));
		commandManager.register(new MinByCreationTimeCommand(collectionManager, xmlFileManager));
		commandManager.register(new MaxByCoordinatesCommand(collectionManager, xmlFileManager));
	}
}
