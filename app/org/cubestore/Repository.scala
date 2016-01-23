package org.cubestore

import scala.concurrent.Future

trait Repository[ID, E] {

  def findById(id: ID): Future[Option[E]]

  def findAllById(ids: Seq[ID]): Future[Seq[E]]

  def insert(entity: E)(implicit description: EntityDescription[ID, E]): Future[Unit]

  def insertAll(entities: Seq[E])(implicit description: EntityDescription[ID, E]): Future[Unit]

  def update(entity: E)(implicit description: EntityDescription[ID, E]): Future[Unit]

  def updateAll(entities: Seq[E])(implicit description: EntityDescription[ID, E]): Future[Unit]

  def deleteById(id: ID): Future[Boolean]

  def deleteAllByIds(ids: Seq[ID]): Future[Unit]

}
