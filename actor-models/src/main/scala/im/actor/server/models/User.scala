package im.actor.server.models

import scala.collection.immutable
import scala.language.postfixOps
import scodec.bits.BitVector

sealed trait UserState {
  def toInt: Int
}

object UserState {
  @SerialVersionUID(1L)
  case object Registered extends UserState {
    def toInt = 1
  }

  @SerialVersionUID(1L)
  case object Email extends UserState {
    def toInt = 2
  }

  @SerialVersionUID(1L)
  case object Deleted extends UserState {
    def toInt = 3
  }

  def fromInt(i: Int): UserState = i match {
    case 1 => Registered
    case 2 => Email
    case 3 => Deleted
  }
}

@SerialVersionUID(1L)
case class User(
  uid: Int,
  authId: Long,
  publicKeyHash: Long,
  publicKeyData: BitVector,
  phoneNumber: Long,
  accessSalt: String,
  name: String,
  countryCode: String,
  sex: Sex,
  phoneIds: immutable.Set[Int],
  emailIds: immutable.Set[Int],
  state: UserState,
  publicKeyHashes: immutable.Set[Long]
)
