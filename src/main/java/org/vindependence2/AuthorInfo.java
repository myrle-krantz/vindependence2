package org.vindependence2;

import java.util.Objects;
import java.util.Optional;
import org.springframework.lang.NonNull;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class AuthorInfo {
  @NonNull final Optional<String> gitHubId;
  @NonNull final Optional<String> apacheId;
  @NonNull final ProjectRole projectRole;
  @NonNull final Optional<String> employer;

  public AuthorInfo(
      @NonNull final Optional<String> gitHubId,
      @NonNull final Optional<String> apacheId,
      @NonNull final ProjectRole projectRole,
      @NonNull final Optional<String> employer) {
    this.gitHubId = gitHubId;
    this.apacheId = apacheId;
    this.projectRole = projectRole;
    this.employer = employer;
  }

  @NonNull
  public Optional<String> getGitHubId() {
    return gitHubId;
  }

  @NonNull
  public Optional<String> getApacheId() {
    return apacheId;
  }

  @NonNull
  public ProjectRole getProjectRole() {
    return projectRole;
  }

  @NonNull
  public Optional<String> getEmployer() {
    return employer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthorInfo that = (AuthorInfo) o;
    return gitHubId.equals(that.gitHubId) &&
        apacheId.equals(that.apacheId) &&
        projectRole == that.projectRole &&
        employer.equals(that.employer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gitHubId, apacheId, projectRole, employer);
  }

  @Override
  public String toString() {
    return "AuthorInfo{" +
        "gitHubId=" + gitHubId +
        ", apacheId=" + apacheId +
        ", projectRole=" + projectRole +
        ", employer=" + employer +
        '}';
  }
}
