package org.cubestore

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class InMemoryRepository[ID, E] extends Repository[ID, E] {

  private var entities = Map[ID, E]()

  override def findById(id: ID): Future[Option[E]] = Future {
    entities.get(id)
  }

  override def findAllById(ids: Seq[ID]): Future[Seq[E]] = Future {
    ids.flatMap(entities.get)
  }

  override def update(entity: E)(implicit description: EntityDescription[ID, E]): Future[Unit] = Future {
    entities = entities.updated(description.id(entity), entity)
  }

  override def updateAll(es: Seq[E])(implicit description: EntityDescription[ID, E]): Future[Unit] = Future {
    entities =  es.foldLeft(entities)((allEntities, e) =>
      allEntities.updated(description.id(e), e)
    )
  }

  override def insert(entity: E)(implicit description: EntityDescription[ID, E]): Future[Unit] = Future {
    entities = entities + (description.id(entity) -> entity)
  }

  override def insertAll(es: Seq[E])(implicit description: EntityDescription[ID, E]): Future[Unit] = Future {
    entities = entities ++ es.map(e => description.id(e) -> e).toMap
  }

  override def deleteById(id: ID): Future[Boolean] = Future {
    val hasId = entities.contains(id)
    entities = entities - id
    hasId
  }

  override def deleteAllByIds(ids: Seq[ID]): Future[Unit] = Future {
    entities = entities -- ids
  }

}
