package org.example.base.iomanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Ввод/вывод через стандартные потоки консоли.
 */
public class StandartIOManager implements IOManager{
	private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

	@Override
	public String readLine() {
		try {
			return reader.readLine();
		} catch (Exception e) {
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
		System.out.print(message);
	}
}
