package zad2;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        Path startingDir = Paths.get(dirName);
        Path resultFile = Paths.get(resultFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(resultFile, Charset.forName("UTF-8"))) {
            Files.walk(startingDir)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .flatMap(Futil::readAllLines)
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Stream<String> readAllLines(Path path) {
        try {
            return Files.lines(path, Charset.forName("Cp1250"));
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }
}
