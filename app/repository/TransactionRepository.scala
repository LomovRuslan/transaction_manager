package repository

import java.time.{LocalDate, ZonedDateTime}
import java.util.concurrent.locks.ReentrantLock

import models.Model._
import play.api.libs.concurrent.Execution.Implicits._

import scala.collection.mutable
import scala.concurrent.Future

trait TransactionRepository {
  def addCredit(credit: CreditCreateDto): Future[Unit]

  def getAllCredits: Future[Option[mutable.MutableList[CreditTransaction]]]

  def addDebit(debit: DebitCreateDto): Future[Unit]

  def getAllDebits: Future[Option[mutable.MutableList[DebitTransaction]]]

  def showBalance(): Future[Option[BigDecimal]]

}

class DefaultTransactionRepository extends TransactionRepository {
  val lock: ReentrantLock = new ReentrantLock()

  def addCredit(credit: CreditCreateDto): Future[Unit] = Future {
    lock.lock()
    try {
      val balanceAfter: BigDecimal = InMemoryData.balance - credit.sum
      if (balanceAfter < 0)
        throw new RuntimeException("Unacceptable amount of funds")
      InMemoryData.transactions += CreditTransaction(InMemoryData.transactionCounter.addAndGet(1), credit.sum, ZonedDateTime.now())
      InMemoryData.balance = balanceAfter
    } finally {
      lock.unlock()
    }
  }

  def getAllCredits: Future[Option[mutable.MutableList[CreditTransaction]]] = Future {
    Option(InMemoryData.transactions collect { case t: CreditTransaction => t })
  }

  def addDebit(debit: DebitCreateDto): Future[Unit] = Future {
    lock.lock()
    try {
      InMemoryData.transactions += DebitTransaction(InMemoryData.transactionCounter.addAndGet(1), debit.sum, ZonedDateTime.now())
      InMemoryData.balance += debit.sum
    } finally {
      lock.unlock()
    }
  }

  def getAllDebits: Future[Option[mutable.MutableList[DebitTransaction]]] = Future {
    Option(InMemoryData.transactions.flatMap(_ match {
      case t: DebitTransaction => Seq(t)
      case _ => Seq()
    }))
  }

  override def showBalance(): Future[Option[BigDecimal]] = Future {
    Option(InMemoryData.balance)
  }

}
