package cz.speedy11.ukrainenewsnotifier.util;

import com.moandjiezana.toml.Toml;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileUtil {

    private FileUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Toml createConfigFile(@NotNull File file) {
        if (!file.exists()) {
            try (InputStream inputStream = FileUtil.class.getResourceAsStream("/" + file.getName())) {
                if (inputStream != null) {
                    Files.copy(inputStream, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
        return new Toml().read(file);
    }
}
