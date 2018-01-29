package models

import java.time.{LocalDate, ZonedDateTime}

import play.api.libs.json._

import scala.collection.mutable

object Model {

  trait Transaction {
    val id: Long
    val sum: BigDecimal
    val date: ZonedDateTime
  }

  case class DebitTransaction(id: Long, sum: BigDecimal, date: ZonedDateTime) extends Transaction

  object DebitTransaction {

    implicit object DebitFormat extends Format[DebitTransaction] {
      def writes(debit: DebitTransaction): JsValue = {
        val debitList = mutable.MutableList(
          "id" -> JsNumber(debit.id),
          "addSum" -> JsNumber(debit.sum),
          "date" -> JsString(debit.date.toString)
        )
        JsObject(debitList)
      }

      def reads(json: JsValue): JsResult[DebitTransaction] = {
        JsSuccess(DebitTransaction(
          (json \ "id").as[Long],
          (json \ "addSum").as[BigDecimal],
          (json \ "date").as[ZonedDateTime]
        ))
      }
    }

  }

  case class DebitCreateDto(sum: BigDecimal)

  object DebitCreateDto {

    implicit object DebitCreateDtoReads extends Reads[DebitCreateDto] {
      def reads(json: JsValue): JsResult[DebitCreateDto] = {
        JsSuccess(DebitCreateDto(
          (json \ "addSum").as[BigDecimal]))
      }
    }

  }

  case class CreditTransaction(id: Long, sum: BigDecimal, date: ZonedDateTime) extends Transaction

  object CreditTransaction {

    implicit object CreditFormat extends Format[CreditTransaction] {
      def writes(credit: CreditTransaction): JsValue = {
        val debitList = mutable.MutableList(
          "id" -> JsNumber(credit.id),
          "minusSum" -> JsNumber(credit.sum),
          "date" -> JsString(credit.date.toString)
        )
        JsObject(debitList)
      }

      def reads(json: JsValue): JsResult[CreditTransaction] = {
        JsSuccess(CreditTransaction(
          (json \ "id").as[Long],
          (json \ "minusSum").as[BigDecimal],
          (json \ "date").as[ZonedDateTime]))
      }
    }

  }

  case class CreditCreateDto(sum: BigDecimal)

  object CreditCreateDto {

    implicit object CreditCreateDtoReads extends Reads[CreditCreateDto] {
      def reads(json: JsValue): JsResult[CreditCreateDto] = {
        JsSuccess(CreditCreateDto(
          (json \ "minusSum").as[BigDecimal]))
      }
    }

  }

  object Implicits {
    implicit val debitFormat = Json.format[DebitTransaction]
    implicit val creditFormat = Json.format[CreditTransaction]
  }

}
