package org.vindependence2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class PullRequestCSVWriter {
  private static final String PR_ID = "pr_id";
  private static final String DATE_COLUMN = "date";
  private static final String PR_STATUS = "pr_status";
  private static final String LINES_OF_CODE_COLUMN = "LOC (not finished)";
  private static final String AUTHOR_GITHUBID_COLUMN = "githubid";
  private static final String AUTHOR_APACHEID_COLUMN = "apacheid";
  private static final String AUTHOR_STATUS_COLUMN = "status";
  private static final String AUTHOR_EMPLOYER_COLUMN = "employer";
  private static final String AUTHOR_EMPLOYER_STATUS_COLUMN = "employer_status";

  static File writeCSVFile(
      final List<PRDescription> pullRequestDescriptions,
      final String pathname)
      throws IOException {
    final File file = File.createTempFile("prList", "", new File(pathname));
    final CSVFormat csvFormat = CSVFormat.RFC4180.withHeader(
        PR_ID,
        DATE_COLUMN,
        PR_STATUS,
        LINES_OF_CODE_COLUMN,
        AUTHOR_GITHUBID_COLUMN,
        AUTHOR_APACHEID_COLUMN,
        AUTHOR_STATUS_COLUMN,
        AUTHOR_EMPLOYER_COLUMN,
        AUTHOR_EMPLOYER_STATUS_COLUMN);
    try (final CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(file), csvFormat)) {
      pullRequestDescriptions.
          forEach(x -> {
            try {
              csvPrinter.printRecord(
                  x.getPrid(),
                  x.getDate(),
                  x.getStatus(),
                  x.getLinesOfCode(),
                  x.getAuthorInfo().getGitHubId().orElse("none"),
                  x.getAuthorInfo().getApacheId().orElse("none"),
                  x.getAuthorInfo().getProjectRole(),
                  x.getAuthorInfo().getEmployer().orElse("unknown"),
                  x.getVendorStatus().map(VendorStatus::toString).orElse("unknown"));
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
    }
    return file;
  }
}
