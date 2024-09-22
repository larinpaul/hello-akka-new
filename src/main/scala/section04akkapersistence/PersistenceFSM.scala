package section04akkapersistence

import akka.persistence.fsm.PersistentFSM
import akka.persistence.fsm.PersistentFSM.FSMState

import scala.reflect.ClassTag

class PersistenceFSM {

  // * Replace Persistent Actor behavior via FSM

  // Account Actor
  // * States:
  // Empty // when zero
  // Active // when greater than 0


}

// package com.packt.akk

object Account {

  // Account States
  sealed trait State extends FSMState

  case class Empty extends State {
    override def identifier = "Empty"
  }

  case class Active extends State {
    override def identifier = "Active"
  }

  // Account Data
  sealed trait Data {
    val amount: Float
  }

  case object ZeroBalance extends Data {
    override val amount: Float = 0.0f
  }

  case class Balance(override val amount: Float) extends Data


  // Domain Events (Persist events)
  sealed trait DomainEvent

  case class AcceptedTransaction(amount: Float,
                                  `type`: TransactionType) extends DomainEvent

  case class RejectedTransaction(amount: Float,
                                  `type`: TransactionType, reason: String) extends DomainEvent

  // Transaction Types
  sealed trait TransactionType

  case object CR extends TransactionType
  case object DR extends TransactionType


  // Commands

  case class Operation(amount: Float, `type`: TransactionType)

}

class Account extends PersistentFSM[Account.State, Account.Data, Account.DomainEvent] {
  import Account._

  override def persistenceId: String = "account"

  override def applyEvent(evt: DomainEvent, currentData: Data): Data = {
    evt match {

    }
  }

  override def domainEventClassTag: ClassTag[DomainEvent] =

}




