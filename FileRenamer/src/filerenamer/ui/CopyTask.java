/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.ui;

/**
 *
 * @author oleksandr.dan
 */

import filerenamer.logic.RenameStrategy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 * Копирует файлы с новыми именами в фоновом потоке
 * и обновляет прогресс-бар.
 */

public class CopyTask extends SwingWorker<int[], Integer> {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(CopyTask.class.getName());

    private final List<File> files;
    private final RenameStrategy strategy;
    private final File outputFolder;
    private final JProgressBar progressBar;
    private final BiConsumer<Integer, Integer> onFinished;

    public CopyTask(List<File> files,
                    RenameStrategy strategy,
                    File outputFolder,
                    JProgressBar progressBar,
                    BiConsumer<Integer, Integer> onFinished) {
        this.files = files;
        this.strategy = strategy;
        this.outputFolder = outputFolder;
        this.progressBar = progressBar;
        this.onFinished = onFinished;
    }

    /** Выполняется в фоновом потоке. Возвращает {скопировано, ошибок}. */
    @Override
    protected int[] doInBackground() {
        int copied = 0;
        int errors = 0;
        int index = 1;

        for (File f : files) {
            if (isCancelled()) {
                break;
            }

            try {
                File dest = uniqueDestination(f, index);
                Files.copy(f.toPath(), dest.toPath());
                copied++;
            } catch (IOException | RuntimeException ex) {
                errors++;
                logger.log(java.util.logging.Level.WARNING,
                        "Failed to copy: " + f.getName(), ex);
            }

            publish(index);
            index++;
        }

        return new int[]{copied, errors};
    }

    /** Выполняется в потоке окна (EDT): сюда приходят номера из publish(). */
    @Override
    protected void process(List<Integer> chunks) {
        progressBar.setValue(chunks.get(chunks.size() - 1));
    }

    /** Выполняется в потоке окна (EDT) после окончания doInBackground(). */
    @Override
    protected void done() {
        progressBar.setValue(progressBar.getMaximum());
        try {
            int[] result = get();
            onFinished.accept(result[0], result[1]);
        } catch (InterruptedException | ExecutionException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Copy task failed", ex);
            onFinished.accept(0, files.size());
        }
    }

    /** Имя от стратегии + расширение; если файл уже есть, добавляет (2), (3)... */
    private File uniqueDestination(File source, int index) {
        String name = source.getName();
        int dotIndex = name.lastIndexOf('.');
        String extension = (dotIndex > 0) ? name.substring(dotIndex) : "";

        String baseName = strategy.generateName(source, index);
        File dest = new File(outputFolder, baseName + extension);

        int n = 2;
        while (dest.exists()) {
            dest = new File(outputFolder, baseName + " (" + n + ")" + extension);
            n++;
        }
        return dest;
    }
}