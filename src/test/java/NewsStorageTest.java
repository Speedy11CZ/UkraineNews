import cz.speedy11.ukrainenewsnotifier.object.Report;
import cz.speedy11.ukrainenewsnotifier.storage.NewsStorage;
import cz.speedy11.ukrainenewsnotifier.storage.database.SQLite;
import net.pushover.client.MessagePriority;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

public class NewsStorageTest {

    @TempDir
    private static File tempDir;

    private static File databaseFile;
    private static SQLite sqLite;
    private static NewsStorage newsStorage;

    @BeforeAll
    public static void setUp() {
        databaseFile = new File(tempDir, "test.sql");
        sqLite = new SQLite(databaseFile);
        newsStorage = new NewsStorage(sqLite);
        newsStorage.save(new Report("test1", "test1", "test1", 0, MessagePriority.NORMAL));
    }

    @Test
    public void exists() {
        Assertions.assertTrue(newsStorage.exists("test1", "test1"));
        Assertions.assertFalse(newsStorage.exists("test2", "test2"));
    }

    @Test
    public void save() {
        Assertions.assertFalse(newsStorage.exists("test2", "test2"));
        newsStorage.save(new Report("test2", "test2", "test2", 0, MessagePriority.NORMAL));
        Assertions.assertTrue(newsStorage.exists("test2", "test2"));
    }

    @Test
    public void saveAll() {
        Assertions.assertFalse(newsStorage.exists("test3", "test3"));
        Assertions.assertFalse(newsStorage.exists("test4", "test4"));
        newsStorage.save(new Report("test3", "test3", "test3", 0, MessagePriority.NORMAL));
        newsStorage.save(new Report("test4", "test4", "test4", 0, MessagePriority.NORMAL));
        Assertions.assertTrue(newsStorage.exists("test3", "test3"));
        Assertions.assertTrue(newsStorage.exists("test4", "test4"));
    }

    @AfterAll
    public static void tearDown() {
        newsStorage = null;
        sqLite = null;
        databaseFile.delete();
    }

}
