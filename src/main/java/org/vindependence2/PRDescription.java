package org.vindependence2;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import org.springframework.lang.NonNull;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PRDescription {
  private final long prid; //unique
  private final Date date;
  private final String status;
  private final int linesOfCode;
  private final AuthorInfo authorInfo;
  private final Optional<VendorStatus> vendorStatus;

  public PRDescription(
      final long prid,
      @NonNull final Date date,
      @NonNull final String status,
      @NonNull final int linesOfCode,
      @NonNull final AuthorInfo authorInfo,
      @NonNull final Optional<VendorStatus> vendorStatus) {
    this.prid = prid;
    this.date = date;
    this.status = status;
    this.linesOfCode = linesOfCode;
    this.authorInfo = authorInfo;
    this.vendorStatus = vendorStatus;
  }

  public long getPrid() {
    return prid;
  }

  public Date getDate() {
    return date;
  }

  public String getStatus() {
    return status;
  }

  public int getLinesOfCode() {
    return linesOfCode;
  }

  public AuthorInfo getAuthorInfo() {
    return authorInfo;
  }

  public Optional<VendorStatus> getVendorStatus() {
    return vendorStatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PRDescription that = (PRDescription) o;
    return prid == that.prid;
  }

  @Override
  public int hashCode() {
    return Objects.hash(prid);
  }
}
