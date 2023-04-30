package it.municipalitiesregistry.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtility {


    public static boolean checkFileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    public static void deleteFileIfExists(String fileLocation) throws IOException {
        var filePath = Paths.get(fileLocation);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }

    public static void renameFile(Path source, String newName) throws IOException {
        Files.move(source, source.resolveSibling(newName), StandardCopyOption.REPLACE_EXISTING);
    }
}
