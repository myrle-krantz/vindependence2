package org.vindependence2;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.PullRequestService;

public class GitHubRequestor {

  static List<PRDescription> getPRs(
      final PRDataRequest prDataRequest,
      final PMCVendorStructure pmcVendorStructure,
      final String githubToken) throws IOException {
    final PullRequestService service = new PullRequestService();
    service.getClient().setOAuth2Token(githubToken);
    final RepositoryId repoId
        = new RepositoryId(prDataRequest.getRepositoryOwner(), prDataRequest.getRepositoryName());
    final List<PullRequest> pullRequests = service
        .getPullRequests(repoId, "all");

    return pullRequests
        .stream()
        .filter(x -> x.getCreatedAt().after(prDataRequest.getSinceWhen()))
        .map(x -> {
          final AuthorInfo authorInfo = pmcVendorStructure.lookupAuthor(x.getUser());
          final Optional<VendorStatus> vendorStatus
              = authorInfo.getEmployer().map(pmcVendorStructure::lookupVendorStatus);
          final int additions = x.getAdditions();
          final int deletions = x.getDeletions();
          final int linesOfCode = additions + deletions;
          return new PRDescription(x.getNumber(), x.getCreatedAt(), getState(x), linesOfCode,
              authorInfo, vendorStatus);
        })
        .collect(Collectors.toList());
  }

  static private String getState(final PullRequest pullRequest) {
    final String state = pullRequest.getState();
    final Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -1);
    Date aMonthAgo = cal.getTime();
    if (state.equals("open")) {
      if (pullRequest.getCreatedAt().before(aMonthAgo)) {
        return "starved";
      } else {
        return "open";
      }
    }
    else if (state.equals("closed")) {
      if (pullRequest.getMergedAt() != null) {
        return "accepted";
      }
      else {
        return "rejected";
      }
    }
    else {
      return "unknown";
    }
  }
}
