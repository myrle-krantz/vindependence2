package org.vindependence2;

public enum VendorStatus {
  Unrepresented ("Unrepresented"),  //No PMC members or more.
  Represented ("Represented"), //1 PMC member.
  WellRepresented ("WellRepresented"), //3 PMC members or more.
  MajorityRepresented ("MajorityRepresented"), //50% or more.
  ;

  private final String name;

  VendorStatus(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
