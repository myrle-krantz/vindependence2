package org.vindependence2;

public enum ProjectRole {
  Contributor("Contributor"),
  Committer("Committer"),
  PMC_Member("PMC_Member");

  private final String name;

  ProjectRole(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
