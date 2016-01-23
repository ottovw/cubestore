package org.cubestore

import scala.concurrent.Future

trait Repository[ID <: Id, E <: Entity[ID]] {

  def findById(id: ID): Future[Option[E]]

  def findAllById(ids: Seq[ID]): Future[Seq[E]]

  def insert(entity: E): Future[Unit]

  def insertAll(entities: Seq[E]): Future[Unit]

  def update(entity: E): Future[Unit]

  def updateAll(entities: Seq[E]): Future[Unit]

  def deleteById(id: ID): Future[Boolean]

  def deleteAllByIds(ids: Seq[ID]): Future[Unit]

}
