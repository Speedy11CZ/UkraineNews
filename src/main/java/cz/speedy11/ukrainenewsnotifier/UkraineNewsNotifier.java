package cz.speedy11.ukrainenewsnotifier;

import com.moandjiezana.toml.Toml;
import cz.speedy11.ukrainenewsnotifier.manager.NewsManager;
import cz.speedy11.ukrainenewsnotifier.manager.PushoverManager;
import cz.speedy11.ukrainenewsnotifier.storage.database.SQLite;
import cz.speedy11.ukrainenewsnotifier.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class UkraineNewsNotifier {

    private static final Logger LOGGER = LogManager.getLogger(UkraineNewsNotifier.class);
    private final Toml config;
    private final SQLite sqLite;
    private final PushoverManager pushoverManager;
    private final NewsManager newsManager;

    protected UkraineNewsNotifier() {
        LOGGER.info("Starting Ukraine News Notifier");

        this.config = FileUtil.createConfigFile(new File("config.toml"));
        this.sqLite = new SQLite(new File("database.sql"));
        this.pushoverManager = new PushoverManager(this);
        this.newsManager = new NewsManager(this);
    }

    public Toml getConfig() {
        return config;
    }

    public SQLite getSqLite() {
        return sqLite;
    }

    public PushoverManager getPushoverManager() {
        return pushoverManager;
    }

    public NewsManager getNewsManager() {
        return newsManager;
    }

    public static void main(String[] args) {
        new UkraineNewsNotifier();
    }
}