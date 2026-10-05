/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.logic;

/**
 *
 * @author oleksandr.dan
 */

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.mp4.Mp4Directory;
import com.drew.metadata.mov.QuickTimeDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        LocalDate embedded = extractEmbeddedDate(file);
        if (embedded != null) {
            return embedded;
        }
        return extractFileSystemDate(file);
    }

    private static final int MIN_PLAUSIBLE_YEAR = 1990;

    private LocalDate extractEmbeddedDate(File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (exif != null) {
                Date date = exif.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                if (date != null) {
                    LocalDate localDate = toLocalDate(date);
                    if (isPlausible(localDate)) {
                        return localDate;
                    }
                }
            }

            Mp4Directory mp4 = metadata.getFirstDirectoryOfType(Mp4Directory.class);
            if (mp4 != null) {
                Date date = mp4.getDate(Mp4Directory.TAG_CREATION_TIME);
                if (date != null) {
                    LocalDate localDate = toLocalDate(date);
                    if (isPlausible(localDate)) {
                        return localDate;
                    }
                }
            }

            QuickTimeDirectory mov = metadata.getFirstDirectoryOfType(QuickTimeDirectory.class);
            if (mov != null) {
                Date date = mov.getDate(QuickTimeDirectory.TAG_CREATION_TIME);
                if (date != null) {
                    LocalDate localDate = toLocalDate(date);
                    if (isPlausible(localDate)) {
                        return localDate;
                    }
                }
            }

        } catch (ImageProcessingException | IOException ex) {
            // Файл не поддерживается библиотекой, повреждён, или без метаданных
        }

        return null;
    }

    private boolean isPlausible(LocalDate date) {
        int year = date.getYear();
        int maxYear = LocalDate.now().getYear() + 1;
        return year >= MIN_PLAUSIBLE_YEAR && year <= maxYear;
    }

    private LocalDate extractFileSystemDate(File file) {
        try {
            BasicFileAttributes attrs =
                    Files.readAttributes(file.toPath(), BasicFileAttributes.class);

            Instant created = attrs.creationTime().toInstant();
            Instant modified = attrs.lastModifiedTime().toInstant();

            Instant older = created.isBefore(modified) ? created : modified;

            return toLocalDate(older);

        } catch (IOException ex) {
            return LocalDate.now();
        }
    }

    private LocalDate toLocalDate(Date date) {
        return toLocalDate(date.toInstant());
    }

    private LocalDate toLocalDate(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}