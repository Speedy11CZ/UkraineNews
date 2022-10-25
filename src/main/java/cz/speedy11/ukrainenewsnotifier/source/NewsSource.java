package cz.speedy11.ukrainenewsnotifier.source;

import cz.speedy11.ukrainenewsnotifier.object.Report;

import java.io.IOException;
import java.util.Collection;

public interface NewsSource {

    Collection<Report> collectReports(int count) throws IOException;

    String id();
}
