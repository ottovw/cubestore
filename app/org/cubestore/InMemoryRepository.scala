package org.cubestore

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class InMemoryRepository[ID <: Id, E <: Entity[ID]] extends Repository[ID, E] {

  private var entities = Map[ID, E]()

  override def findById(id: ID): Future[Option[E]] =
    Future { entities.get(id) }

  override def findAllById(ids: Seq[ID]): Future[Seq[E]] =
    Future { ids.flatMap(entities.get) }

  override def update(entity: E): Future[Unit] =
    Future { entities = entities.updated(entity.id, entity) }

  override def updateAll(es: Seq[E]): Future[Unit] = Future {
    entities =  es.foldLeft(entities)((allEntities, e) =>
      allEntities.updated(e.id, e)
    )
  }

  override def insert(entity: E): Future[Unit] = {
    Future { entities = entities + (entity.id -> entity) }
  }

  override def insertAll(es: Seq[E]): Future[Unit] = {
    Future { entities = entities ++ es.map(e => e.id -> e).toMap }
  }

  override def deleteById(id: ID): Future[Boolean] = {
    Future {
      val hasId = entities.contains(id)
      entities = entities - id
      hasId
    }
  }

  override def deleteAllByIds(ids: Seq[ID]): Future[Unit] =
    Future { entities = entities -- ids }

}
