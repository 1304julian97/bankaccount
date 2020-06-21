package domainservices.repository

trait Repository {
  val url: String
  val port: Int
  val user: String
  val password: String

}
