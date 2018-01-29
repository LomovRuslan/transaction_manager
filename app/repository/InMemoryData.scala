package repository

import java.util.concurrent.atomic.AtomicLong

import models.Model.Transaction

import scala.collection.mutable

object InMemoryData {
  val transactionCounter: AtomicLong = new AtomicLong()

  var balance: BigDecimal = 2000

  val transactions: mutable.MutableList[Transaction] = mutable.MutableList[Transaction]()

}
