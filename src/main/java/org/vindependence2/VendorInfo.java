package org.vindependence2;

import org.springframework.lang.NonNull;

public class VendorInfo {
  @NonNull final String name;
  int committerCount;
  int pmcCount;

  public VendorInfo(@NonNull String name) {
    this.name = name;
    committerCount = 0;
    pmcCount = 0;
  }

  void incrementPMCCount() {
    pmcCount++;
  }

  public int getPMCCount() {
    return pmcCount;
  }

  @Override
  public String toString() {
    return "VendorInfo{" +
        "name='" + name + '\'' +
        ", committerCount=" + committerCount +
        ", pmcCount=" + pmcCount +
        '}';
  }
}
