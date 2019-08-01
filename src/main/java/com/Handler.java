package main.java.com;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Handler {
    public static void main(String[] args) {
        final String directoryPath = "C:\\multithreading\\src\\main\\resources";
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            getFileInformation(directory);
        } else {
            System.out.println("Не удалось найти директорию по указанному пути.");
        }
    }

    private static void getFileInformation(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Нет файлов для обработки");
            return;
        }

        ExecutorService service = Executors.newFixedThreadPool(4);
        for (final File f : files) {
            if (!f.isFile()) {
                continue;
            }

            service.execute(() -> {
                System.out.println("Файл: " + f.getName() + ". Расширение: " + getFileExtension(f) + ". Размер: " + f.length() + " байт.");
            });
        }
        service.shutdown();

        try {
            service.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "?";
    }
}


