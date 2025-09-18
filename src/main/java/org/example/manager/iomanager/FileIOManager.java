package org.example.manager.iomanager;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Ввод команд из файла (для execute_script). Вывод уходит в стандартные потоки.
 */
public class FileIOManager implements IOManager{
	private final BufferedReader reader;

	public FileIOManager(String filePath) throws FileNotFoundException {
		this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
	}

	@Override
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public void writeLine(String message) {
		System.out.println(message);
	}

	@Override
	public void writeError(String message) {
		System.err.println(message);
	}

	@Override
	public void prompt(String message) {
		// В режиме скрипта не выводим приглашения к вводу
	}
}


