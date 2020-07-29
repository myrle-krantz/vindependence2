package org.vindependence2;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class CommittersCSVReader {


  private static final String GITHUBID_COLUMN = "github";
  private static final String APACHEID_COLUMN = "id";
  private static final String EMPLOYER_COLUMN = "Likely employer";

  public static PMCVendorStructure read(
      final File csvFileContributors,
      final File csvFileCommitters,
      final File csvFilePMC)
      throws IOException {
    final PMCVendorStructure ret = new PMCVendorStructure();
    try (final FileReader csvFileReaderContributors = new FileReader(csvFileContributors)) {
      final CSVParser committerRecords
          = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(csvFileReaderContributors);
      committerRecords.forEach(x -> {
        final String gitHubId = x.get(GITHUBID_COLUMN);
        String employer = x.get(EMPLOYER_COLUMN);
        if (employer.equals("NA"))
          employer = null;
        ret.addContributor(gitHubId, Optional.empty(), ProjectRole.Contributor,
            Optional.ofNullable(employer));
      });
    }

    try (final FileReader csvFileReaderCommitters = new FileReader(csvFileCommitters)) {
      final CSVParser committerRecords
          = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(csvFileReaderCommitters);
      committerRecords.forEach(x -> {
        final String gitHubId = x.get(GITHUBID_COLUMN);
        ret.addContributor(gitHubId, Optional.empty(), ProjectRole.Committer, Optional.empty());
      });
    }

    try (final FileReader csvFileReaderPMC = new FileReader(csvFilePMC)) {
      final CSVParser pmcRecords
          = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(csvFileReaderPMC);
      pmcRecords.forEach(x -> {
        final String gitHubId = x.get(GITHUBID_COLUMN);
        String apacheId = x.get(APACHEID_COLUMN);
        if (apacheId.isEmpty())
          apacheId = null;
        String employer = x.get(EMPLOYER_COLUMN);
        if (employer.equals("NA"))
          employer = null;
        ret.addContributor(
            gitHubId,
            Optional.ofNullable(apacheId),
            ProjectRole.PMC_Member,
            Optional.ofNullable(employer));
      });
    }
    return ret;
  }
}
