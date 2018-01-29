package routers

import models.Model._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Action
import play.api.mvc.BodyParsers.parse
import play.api.mvc.Results._
import play.api.routing.Router
import play.api.routing.sird._
import repository.{DefaultTransactionRepository, TransactionRepository}

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global


object TransactionRouter extends DefaultTransactionRepository with TransactionRouter {
  def apply(): Router.Routes = routes
}

trait TransactionRouter {

  self: TransactionRepository =>

  def routes: Router.Routes = {

    case POST(p"/debit") => Action.async(parse.json[DebitCreateDto]) {
      implicit request =>
        val debit = request.body
        addDebit(debit) map (_ => Created)
    }
    case GET(p"/debits") => Action.async {
      getAllDebits map {
        case Some(allDebits) => Ok(convertDebitsToJson(allDebits))
        case None => NotFound
      }
    }

    case POST(p"/credit") => Action.async(parse.json[CreditCreateDto]) {
      implicit request =>
        val credit = request.body
        addCredit(credit) map (_ => Created)
    }
    case GET(p"/credits") => Action.async {
      getAllCredits map {
        case Some(allDebits) => Ok(convertCreditsToJson(allDebits))
        case None => NotFound
      }
    }

    case GET(p"/balance") => Action.async {
      showBalance() map {
        case Some(balance) => Ok(Json.toJson(balance))
        case None => NotFound
      }
    }
  }

  def convertDebitsToJson(debits: mutable.MutableList[DebitTransaction]): JsValue = Json.toJson(debits)

  def convertCreditsToJson(credits: mutable.MutableList[CreditTransaction]): JsValue = Json.toJson(credits)
}

