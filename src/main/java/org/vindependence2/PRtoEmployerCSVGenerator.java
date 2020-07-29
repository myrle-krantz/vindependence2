package org.vindependence2;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class PRtoEmployerCSVGenerator {

  public static void main(String [] args)
  {
    final ArgumentParser parser = ArgumentParsers.newFor("PRtoEmployerCSVGenerator").build()
        .defaultHelp(true)
        .description("generate CSV which matches PRs to employers in an Apache github repository.");
    parser.addArgument("-p", "--pmcCSVFile")
        .help("CSV File containing the members of the project PMC and their employers");
    parser.addArgument("-c", "--committersCSVFile")
        .help("CSV File containing the project committers and their employers");
    parser.addArgument("-f", "--contributorsCSVFile")
        .help("CSV File containing the project contributors and their employers");
    parser.addArgument("-o", "--outputDirectory")
        .help("The directory to accept output of results");
    //Feature request: this should be extended to allow for multiple repositories
    parser.addArgument("-r", "--githubRepository")
        .help("The name of the project repository in github");
    parser.addArgument("-t", "--githubToken")
        .help("A github token with permissions to access the respective data");
    parser.addArgument("-y", "--year")
        .help("The date from which to analyze pull requests");
    parser.addArgument("-m", "--month")
        .help("The date from which to analyze pull requests. January=0, etc.");
    parser.addArgument("-d", "--day")
        .help("The date from which to analyze pull requests");

    Namespace ns = null;

    try {
      ns = parser.parseArgs(args);
    } catch (ArgumentParserException e) {
      parser.handleError(e);
      System.exit(1);
    }
    try {
      final String pmcPathname = ns.getString("pmcCSVFile");
      final String committersPathname = ns.getString("committersCSVFile");
      final String contributorsPathname = ns.getString("contributorsCSVFile");
      final String apacheGithubRepository = ns.getString("githubRepository");
      final String githubToken = ns.getString("githubToken");
      final String sinceDateYear = ns.getString("year");
      final String sinceDateMonth = ns.getString("month");
      final String sinceDateDay = ns.getString("day");
      final Date sinceDate = new GregorianCalendar(
          Integer.parseInt(sinceDateYear),
          Integer.parseInt(sinceDateMonth),
          Integer.parseInt(sinceDateDay)).getTime();
      final String outputPathname = ns.getString("outputDirectory");

      final File csvFilePMC = new File(pmcPathname);
      final File csvFileCommitters = new File(committersPathname);
      final File csvFileContributors = new File(contributorsPathname);
      final PMCVendorStructure pmcVendorStructure
          = CommittersCSVReader.read(csvFileContributors, csvFileCommitters, csvFilePMC);

      final PRDataRequest prDataRequest = new PRDataRequest(apacheGithubRepository, sinceDate);
      final List<PRDescription> prDescriptions
          = GitHubRequestor.getPRs(prDataRequest, pmcVendorStructure, githubToken);

      final File csvFileTarget = PullRequestCSVWriter.writeCSVFile(prDescriptions, outputPathname);
      System.out.println("PR list written to " + csvFileTarget);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
