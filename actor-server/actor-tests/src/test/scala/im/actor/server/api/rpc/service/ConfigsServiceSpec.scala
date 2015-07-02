package im.actor.server.api.rpc.service

import im.actor.api.rpc._
import im.actor.api.rpc.configs._
import im.actor.api.rpc.misc.ResponseSeq
import im.actor.server.BaseAppSuite
import im.actor.server.api.rpc.RpcApiService
import im.actor.server.api.rpc.service.auth.AuthConfig
import im.actor.server.api.rpc.service.configs.ConfigsServiceImpl
import im.actor.server.oauth.{ GmailProvider, OAuth2GmailConfig }
import im.actor.server.presences.{ GroupPresenceManager, PresenceManager }
import im.actor.server.push.{ SeqUpdatesManager, WeakUpdatesManager }
import im.actor.server.session.{ SessionConfig, Session }
import im.actor.server.social.SocialManager

class ConfigsServiceSpec extends BaseAppSuite {
  behavior of "Configs Service"

  it should "save parameter even if it already exists" in e1()

  it should "get parameters" in e2()

  implicit val seqUpdManagerRegion = buildSeqUpdManagerRegion()
  implicit val socialManagerRegion = SocialManager.startRegion()
  implicit val sessionRegion = Session.startRegionProxy()

  val oauth2GmailConfig = OAuth2GmailConfig.load(system.settings.config.getConfig("oauth.v2.gmail"))
  implicit val oauth2Service = new GmailProvider(oauth2GmailConfig)
  implicit val authSmsConfig = AuthConfig.fromConfig(system.settings.config.getConfig("auth"))
  implicit val authService = buildAuthService()

  val service = new ConfigsServiceImpl

  val (user, _, _) = createUser()
  val authId = createAuthId()
  val sessionId = createSessionId()

  implicit val clientData = ClientData(authId, sessionId, Some(user.id))

  def e1() = {
    whenReady(service.handleEditParameter("par1", "val1")) { resp ⇒
      resp should matchPattern {
        case Ok(ResponseSeq(_, _)) ⇒
      }
    }

    whenReady(service.handleEditParameter("par1", "val2")) { resp ⇒
      resp should matchPattern {
        case Ok(ResponseSeq(_, _)) ⇒
      }
    }
  }

  def e2() = {
    whenReady(service.handleGetParameters()) { resp ⇒
      resp should matchPattern {
        case Ok(ResponseGetParameters(Vector(Parameter("par1", "val2")))) ⇒
      }
    }
  }
}
