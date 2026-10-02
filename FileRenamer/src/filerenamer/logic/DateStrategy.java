/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.logic;

/**
 *
 * @author oleksandr.dan
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DateStrategy implements RenameStrategy {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Map<String, Integer> usedNames = new HashMap<>();

    @Override
    public String generateName(File file, int index) {
        LocalDate date = extractDate(file);
        String baseName = date.format(FORMAT);

        int count = usedNames.merge(baseName, 1, Integer::sum);

        if (count == 1) {
            return baseName;
        } else {
            return baseName + " (" + count + ")";
        }
    }

    private LocalDate extractDate(File file) {
        try {
            BasicFileAttributes attrs =
                    Files.readAttributes(file.toPath(), BasicFileAttributes.class);

            Instant created = attrs.creationTime().toInstant();
            Instant modified = attrs.lastModifiedTime().toInstant();

            Instant older = created.isBefore(modified) ? created : modified;

            return older.atZone(ZoneId.systemDefault()).toLocalDate();

        } catch (IOException ex) {
            return LocalDate.now();
        }
    }
}