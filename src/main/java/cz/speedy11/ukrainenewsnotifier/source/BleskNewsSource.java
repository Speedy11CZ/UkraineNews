package cz.speedy11.ukrainenewsnotifier.source;

import com.moandjiezana.toml.Toml;
import cz.speedy11.ukrainenewsnotifier.object.Report;
import net.pushover.client.MessagePriority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BleskNewsSource implements NewsSource {

    private static final String SERVICE_ID = "blesk";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d. M. yyyy H:mm");
    private static final Logger LOGGER = LogManager.getLogger(BleskNewsSource.class);

    private final String url;

    public BleskNewsSource(@NotNull Toml config) {
        this.url = config.getString("source." + SERVICE_ID + ".url");
    }

    @Override
    public Collection<Report> collectReports(int count) throws IOException {
        List<Report> reports = new ArrayList<>();
        Document document = Jsoup.connect(url).get();
        Element reportsElement = document.getElementById("onlineReport1_669");
        if(reportsElement != null) {
            Elements reportItems = reportsElement.getElementsByClass("report_item");
            for (int i = Math.min(reportItems.size(), count) ; i > 0 ; i--) {
                Element reportElement = reportItems.get(i - 1);
                Element detailsElement = reportElement.getElementsByClass("details").first();
                Element textElement = reportElement.getElementsByClass("text").first();

                String id = reportElement.id().substring(12);
                String text = textElement != null ? textElement.text() : null;
                long timestamp = System.currentTimeMillis();
                MessagePriority priority = MessagePriority.NORMAL;

                if(detailsElement != null) {
                    if(detailsElement.getElementsByClass("important").size() > 0) {
                        priority = MessagePriority.HIGH;
                    }

                    Element dateElement = detailsElement.getElementsByClass("date").first();
                    Element timeElement = detailsElement.getElementsByClass("time").first();
                    if(dateElement != null && timeElement != null) {
                        String date = dateElement.text();
                        String time = timeElement.text();
                        try {
                            timestamp = ZonedDateTime.of(LocalDateTime.parse(date + " " + time, DATE_TIME_FORMATTER), ZoneId.systemDefault()).toInstant().toEpochMilli();
                        } catch (DateTimeParseException exception) {
                            LOGGER.warn("Failed to parse date and time for report with id {}", reportElement.id());
                        }
                    }
                }

                reports.add(new Report(SERVICE_ID, id, text, timestamp, priority));
            }
        }
        return reports;
    }

    @Override
    public String id() {
        return SERVICE_ID;
    }
}
