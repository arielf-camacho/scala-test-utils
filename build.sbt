name := "TestUtils"
version := "0.1"
scalaVersion := "2.12.8"
useGpg := true
pgpSecretRing := pgpPublicRing.value

libraryDependencies += "org.scalatestplus.play" % "scalatestplus-play_2.11" % "1.5.1"

credentials += Credentials(
  "GnuPG Key ID",
  "gpg",
  "7172CADFE48537EC7E25163ECCA06217C65EBFCC", // key identifier
  "ignored" // this field is ignored; passwords are supplied by pinentry
)

ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true