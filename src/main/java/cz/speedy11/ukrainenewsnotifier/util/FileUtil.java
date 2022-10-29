package cz.speedy11.ukrainenewsnotifier.util;

import com.moandjiezana.toml.Toml;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Utility class for file operations.
 *
 * @author Michal Spišak
 * @since 1.0.0
 */
public class FileUtil {

    private FileUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Creates a config file if it doesn't exist, then loads it.
     *
     * @param file file to load
     * @return loaded config file
     */
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
