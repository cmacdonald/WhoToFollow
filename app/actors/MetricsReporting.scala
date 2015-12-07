package actors

import actors.MetricsReporting.{RecentQueries, GetRecentQueryList}
import actors.QueryHandler.FetchLatestQueryExperts
import akka.actor.{ActorRef, Actor}
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.name.Named
import com.google.inject.{Inject, Singleton}
import play.api.Logger

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object MetricsReporting {
  case class RecentQueries(recentQueriesList: Set[String])
  case class GetRecentQueryList()
}

class MetricsReporting @Inject()
  (@Named("redisReader") redisReader: ActorRef,
   @Named("webSocketSupervisor") webSocketSupervisor: ActorRef)
  extends Actor {

  implicit val timeout = Timeout(20 seconds)

  // Fetch the latest 'recent queries' list 1 second
  val tick = context.system.scheduler.schedule(Duration.Zero, 1.seconds, self, GetRecentQueryList())

  override def receive = {
    case msg @ GetRecentQueryList() =>
      (redisReader ? msg) onComplete {
        case Success(recentQueries) => webSocketSupervisor ! recentQueries
        case Failure(t) => Logger.debug("Error: " + t)
      }
  }

}
