package cz.speedy11.ukrainenewsnotifier.manager;

import cz.speedy11.ukrainenewsnotifier.UkraineNewsNotifier;
import cz.speedy11.ukrainenewsnotifier.object.Report;
import cz.speedy11.ukrainenewsnotifier.source.BleskNewsSource;
import cz.speedy11.ukrainenewsnotifier.source.NewsSource;
import cz.speedy11.ukrainenewsnotifier.storage.NewsStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewsManager extends TimerTask {

    private static final Logger LOGGER = LogManager.getLogger(NewsManager.class);

    private final List<NewsSource> sources = new ArrayList<>();
    private final PushoverManager pushoverManager;
    private final NewsStorage newsStorage;
    private final int reportCount;

    public NewsManager(@NotNull UkraineNewsNotifier unn) {
        this.sources.add(new BleskNewsSource(unn.getConfig()));
        this.pushoverManager = unn.getPushoverManager();
        this.newsStorage = new NewsStorage(unn.getSqLite());
        this.reportCount = Math.toIntExact(unn.getConfig().getLong("collector.report-count", 0L));

        Timer timer = new Timer("NewsCollector");
        timer.scheduleAtFixedRate(this, 0L, unn.getConfig().getLong("collector.interval", 60000L));
    }

    @Override
    public void run() {
        List<Report> reports = new ArrayList<>();
        for (NewsSource source : sources) {
            try {
                for (Report report : source.collectReports(reportCount)) {
                    if(!newsStorage.exists(report.serviceId(), report.id())) {
                        pushoverManager.notify(report);
                        reports.add(report);
                    }
                }
            } catch (IOException exception) {
                LOGGER.error("Failed to collect reports from source " + source.id(), exception);
            }
        }

        if(reports.size() > 0) {
            LOGGER.info("Collected " + reports.size() + " reports");
            newsStorage.saveAll(reports);
        }

    }
}
