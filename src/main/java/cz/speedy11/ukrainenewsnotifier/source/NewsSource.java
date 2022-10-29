package cz.speedy11.ukrainenewsnotifier.source;

import cz.speedy11.ukrainenewsnotifier.object.Report;

import java.io.IOException;
import java.util.Collection;

/**
 * <p>Interface for different news sources.</p>
 * <p>Used for parsing and collecting data from them.</p>
 *
 * @author Michal Spišak
 * @since 1.0.0
 */
public interface NewsSource {

    /**
     * Gets all reports from the source.
     *
     * @param count count of reports to get
     * @return collection of reports
     * @throws IOException if an I/O error occurs
     */
    Collection<Report> collectReports(int count) throws IOException;

    /**
     * Get news source ID.
     *
     * @return news source ID
     */
    String id();
}
