package cz.speedy11.ukrainenewsnotifier.storage;

import cz.speedy11.ukrainenewsnotifier.object.Report;
import cz.speedy11.ukrainenewsnotifier.storage.database.SQLite;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Database storage for news.
 *
 * @author Michal Spišak
 * @since 1.0.0
 */
public class NewsStorage {

    private final SQLite sqLite;

    public NewsStorage(SQLite sqLite) {
        this.sqLite = sqLite;
        this.sqLite.query("CREATE TABLE IF NOT EXISTS `reports` (`service_id` varchar(16) NOT NULL, `report_id` varchar(32) NOT NULL)");
    }

    /**
     * Saves a report to the database.
     *
     * @param report report to save
     */
    public void save(@NotNull Report report) {
        this.sqLite.query("INSERT INTO `reports` (`service_id`, `report_id`) VALUES (?, ?)", report.serviceId(), report.id());
    }

    /**
     * Saves all reports to database.
     *
     * @param reports reports to save
     */
    public void saveAll(@NotNull List<Report> reports) {
        for (Report report : reports) {
            save(report);
        }
    }

    /**
     * Checks if the report is already saved in the database.
     *
     * @param serviceId service ID
     * @param reportId report ID
     * @return true if the report is already saved in the database
     */
    public boolean exists(String serviceId, String reportId) {
        return this.sqLite.query("SELECT COUNT(*) FROM `reports` WHERE `service_id` = ? AND `report_id` = ?", serviceId, reportId).get(0).getInt("COUNT(*)") != 0;
    }
}
