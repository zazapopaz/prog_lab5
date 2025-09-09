package org.example.manager.iomanager;

/**
 * Унифицированный ввод/вывод для интерактивного режима и скриптов.
 */
public interface IOManager {
	String readLine();
	void writeLine(String message);
	void writeError(String message);
	void prompt(String message);
}


