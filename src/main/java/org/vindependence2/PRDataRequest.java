package org.vindependence2;

import java.util.Date;
import org.springframework.lang.NonNull;

public class PRDataRequest {
  @NonNull final String repositoryOwner;
  @NonNull final String repositoryName;
  @NonNull final Date sinceWhen;

  public PRDataRequest(@NonNull final String repositoryName, @NonNull final Date sinceWhen) {
    this.repositoryOwner = "apache";
    this.repositoryName = repositoryName;
    this.sinceWhen = sinceWhen;
  }

  @NonNull
  public String getRepositoryOwner() {
    return repositoryOwner;
  }

  public String getRepositoryName() {
    return repositoryName;
  }

  public Date getSinceWhen() {
    return sinceWhen;
  }
}
