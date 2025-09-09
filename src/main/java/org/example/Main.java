package org.example;

/**
 * Главный класс приложения.
 */
public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Использование: java -jar [имя_файла].jar <имя_коллекции>");
        }
        else{
            Run run = new Run(args[0]);
            run.run();
        }
    }
}
