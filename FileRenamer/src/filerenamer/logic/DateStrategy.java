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

/**
 * Generates file names based on the date associated with the file.
 *
 * The strategy first tries to obtain the date from embedded metadata
 * and falls back to the file system timestamps when no valid embedded
 * date is available.
 *
 * Files with the same date receive a numeric suffix to keep the
 * generated names unique.
 */

public class DateStrategy implements RenameStrategy {
    // Format used for generated date-based names.
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Used to append a unique numeric suffix to files sharing the same date.
    private final Map<String, Integer> usedNames = new HashMap<>();

/**
 * Generates a date-based name for the given file.
 *
 * The date is formatted as {@code yyyy-MM-dd}. When multiple files
 * resolve to the same date, the first file keeps the base date name,
 * while subsequent files receive a numeric suffix such as
 * {@code "2025-03-10 (2)"}.
 *
 * @param file file for which the name is generated
 * @param index file position in the current renaming operation
 * @return generated name based on the file date
 */
    
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
    
/**
 * Extracts the best available date for the file.
 *
 * Embedded metadata is preferred. When no valid embedded date is
 * available, the file system timestamp is used as a fallback.
 */

    private LocalDate extractDate(File file) {
        LocalDate embedded = extractEmbeddedDate(file);
        if (embedded != null) {
            return embedded;
        }
        
        // Fall back to file system timestamps when embedded metadata is unavailable.
        return extractFileSystemDate(file);
    }

    // Reject obviously invalid or corrupted metadata dates before using them.
    private static final int MIN_PLAUSIBLE_YEAR = 1990;

/**
 * Attempts to extract a valid date from embedded media metadata.
 *
 * The metadata sources are checked in the following order:
 * EXIF original date, MP4 creation time, then QuickTime creation time.
 *
 * Dates are validated before being accepted. If no supported or valid
 * embedded date can be obtained, {@code null} is returned.
 */
    
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
            // Ignore metadata reading errors and fall back to the file system date.
        }

        return null;
    }
    
/**
 * Checks whether the extracted date falls within an acceptable range.
 *
 * Dates earlier than {@link #MIN_PLAUSIBLE_YEAR} or later than the
 * current year plus one are treated as invalid metadata.
 */
    
    private boolean isPlausible(LocalDate date) {
        int year = date.getYear();
        int maxYear = LocalDate.now().getYear() + 1;
        return year >= MIN_PLAUSIBLE_YEAR && year <= maxYear;
    }
    
/**
 * Extracts a date from the file system timestamps.
 *
 * The earlier of the creation time and last modified time is used
 * as the file date.
 *
 * If the file system attributes cannot be read, the current date
 * is used as a fallback.
 */
    
    private LocalDate extractFileSystemDate(File file) {
        try {
            BasicFileAttributes attrs =
                    Files.readAttributes(file.toPath(), BasicFileAttributes.class);

            Instant created = attrs.creationTime().toInstant();
            Instant modified = attrs.lastModifiedTime().toInstant();

            // Use the earlier timestamp as the best available approximation of the file date.
            Instant older = created.isBefore(modified) ? created : modified;

            return toLocalDate(older);

        } catch (IOException ex) {
            // Use today's date when file system timestamps are unavailable.
            return LocalDate.now();
        }
    }

    private LocalDate toLocalDate(Date date) {
        return toLocalDate(date.toInstant());
    }

    private LocalDate toLocalDate(Instant instant) {
        // Convert using the system time zone so that the resulting date matches
        // the user's local environment.
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}