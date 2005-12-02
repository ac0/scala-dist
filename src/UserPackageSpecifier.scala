package sbaz;

// a specification from a user for a package within a universe
abstract class UserPackageSpecifier {
  def chooseFrom(packages: PackageSet): Option[Package];
}


// a request for the newest package of a given name
case class UPSNewestNamed(val name: String) 
extends UserPackageSpecifier {
  def chooseFrom(packages: PackageSet): Option[Package] =
    packages.newestNamed(name);

  override def toString() = name;
}

// a request for a package with a specific version
case class UPSWithSpec(val spec: PackageSpec)
extends UserPackageSpecifier {
  def chooseFrom(packages: PackageSet): Option[Package] =
    packages.packageWithSpec(spec);

  override def toString() = spec.toString()
}


object UserPackageSpecifierUtil {
  // Parse a specifier from a user-supplied string.
  // If the string is simply a name, then a UPSNewestNamed is returned.
  // If the string includes a '/' character, then it is assumed
  // to be a PackageSpecification in slash notation, so a UPSWithSpec
  // is returned.
  def fromString(str: String): UserPackageSpecifier = {
    if(str.indexOf("/") >= 0) {
      val spec = PackageSpecUtil.fromSlashNotation(str);
      UPSWithSpec(spec);
    } else {
      UPSNewestNamed(str);
    }
  }
}
