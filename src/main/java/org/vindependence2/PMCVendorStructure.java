package org.vindependence2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.eclipse.egit.github.core.User;
import org.springframework.lang.NonNull;

public class PMCVendorStructure {
  private final Map<String, VendorInfo> knownVendors = new HashMap<>();
  private final Map<String, AuthorInfo> knownContributors = new HashMap<>();
  private int pmcMemberCount = 0;

  public PMCVendorStructure() {
  }

  AuthorInfo lookupAuthor(final User user) {
    return knownContributors.computeIfAbsent(
        user.getLogin(),
        x -> new AuthorInfo(
            Optional.of(x),
            Optional.empty(),
            ProjectRole.Contributor,
            Optional.empty()));
  }

  VendorStatus lookupVendorStatus(@NonNull final String employer) {
    final VendorInfo vendorInfo = knownVendors.getOrDefault(employer, new VendorInfo(employer));
    if (vendorInfo.getPMCCount()*2 >= pmcMemberCount) {
      return VendorStatus.MajorityRepresented;
    }
    else if (vendorInfo.getPMCCount() >= 3) {
      return VendorStatus.WellRepresented;
    }
    else if (vendorInfo.getPMCCount() >= 1) {
      return VendorStatus.Represented;
    }
    else {
      return VendorStatus.Unrepresented;
    }
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  public void addContributor(
      @NonNull final String gitHubId,
      @NonNull final Optional<String> apacheId,
      @NonNull final ProjectRole projectRole,
      @NonNull final Optional<String> employer) {
    if (!knownContributors.containsKey(gitHubId)) {
      final AuthorInfo authorInfo
          = new AuthorInfo(Optional.of(gitHubId), apacheId, projectRole, employer);
      if (!gitHubId.isEmpty()) {
        knownContributors.put(gitHubId, authorInfo);
      }
    }

    if (projectRole == ProjectRole.PMC_Member)
      pmcMemberCount++;
    employer.ifPresent(x -> {
      if (!knownVendors.containsKey(x)) {
        final VendorInfo vendorInfo = new VendorInfo(x);
        if (projectRole == ProjectRole.PMC_Member)
          vendorInfo.incrementPMCCount();
        knownVendors.put(x, vendorInfo);
      }
      else {
        if (projectRole == ProjectRole.PMC_Member)
          knownVendors.get(x).incrementPMCCount();
      }
    });
  }
}
