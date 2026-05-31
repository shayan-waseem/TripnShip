package com.tripandship.patterns.singleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final String baseDbPath;

    private DatabaseManager() {
        baseDbPath = resolveDatabaseDirectory();
        File directory = new File(baseDbPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private static String resolveDatabaseDirectory() {
        String relative = "database";
        Path cwd = Paths.get(System.getProperty("user.dir")).normalize();
        for (Path dir : new Path[] { cwd, cwd.getParent() }) {
            if (dir == null) {
                continue;
            }
            Path db = dir.resolve(relative);
            if (Files.isDirectory(db) || Files.exists(db.resolve("users.json"))) {
                return db.toString() + File.separator;
            }
        }
        return relative + File.separator;
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public String readData(String fileName) throws IOException {
        String path = baseDbPath + fileName;
        File file = new File(path);
        if (!file.exists()) {
            Files.write(Paths.get(path), "[]".getBytes());
        }
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public void writeData(String fileName, String jsonContent) throws IOException {
        Files.write(Paths.get(baseDbPath + fileName), jsonContent.getBytes());
    }
}