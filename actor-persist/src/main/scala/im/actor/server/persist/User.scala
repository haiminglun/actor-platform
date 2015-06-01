package im.actor.server.persist

import com.github.tototoshi.slick.PostgresJodaSupport._
import org.joda.time.DateTime
import slick.driver.PostgresDriver.api._

import im.actor.server.models

class UserTable(tag: Tag) extends Table[models.User](tag, "users") {
  import SexColumnType._
  import UserStateColumnType._

  def id = column[Int]("id", O.PrimaryKey)
  def accessSalt = column[String]("access_salt")
  def name = column[String]("name")
  def countryCode = column[String]("country_code")
  def sex = column[models.Sex]("sex")
  def state = column[models.UserState]("state")
  def deletedAt = column[Option[DateTime]]("deleted_at")
  def isBot = column[Boolean]("is_bot")

  def * = (id, accessSalt, name, countryCode, sex, state, deletedAt, isBot) <> (models.User.tupled, models.User.unapply)
}

object User {
  val users = TableQuery[UserTable]

  val activeHumanUsers =
    users.filter(u ⇒ u.deletedAt.isEmpty && !u.isBot)

  def create(user: models.User) =
    users += user

  def setCountryCode(userId: Int, countryCode: String) =
    users.filter(_.id === userId).map(_.countryCode).update(countryCode)

  def setDeletedAt(userId: Int) =
    users.filter(_.id === userId).
      map(_.deletedAt).
      update(Some(new DateTime))

  def setName(userId: Int, name: String) =
    users.filter(_.id === userId).map(_.name).update(name)

  def find(id: Int) =
    users.filter(_.id === id).result

  def findName(id: Int) =
    users.filter(_.id === id).map(_.name).result.headOption

  // TODO: #perf will it create prepared statement for each ids length?
  def findSalts(ids: Set[Int]) =
    users.filter(_.id inSet ids).map(u ⇒ (u.id, u.accessSalt)).result

  def findByIds(ids: Set[Int]) =
    users.filter(_.id inSet ids).result

  def findByIdsPaged(ids: Set[Int], number: Int, size: Int) = {
    val offset = (number - 1) * size
    users.
      filter(_.id inSet ids).
      sortBy(_.name).
      drop(offset).
      take(size).
      result
  }

  def activeUsersIds = activeHumanUsers.map(_.id).result

  def page(number: Int, size: Int) = {
    val offset = (number - 1) * size
    activeHumanUsers.
      sortBy(_.name).
      drop(offset).
      take(size)
  }
}
