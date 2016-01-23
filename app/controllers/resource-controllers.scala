package controllers

import model.{Album, AlbumId}
import org.cubestore.{InMemoryRepository, Repository, Id, Entity}
import play.api.libs.json._
import play.api.mvc.{BodyParsers, Action, Controller}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AlbumController extends Controller with ResourceController[AlbumId, Album] {

  def repository = AlbumController.repo

  def jsonCodec = new JsonCodec[AlbumId, Album] {

    override def read(input: JsValue) = Json.fromJson(input)(Json.reads[Album])

    override def write(entity: Album) = Json.toJson(entity)(Json.writes[Album])

  }

}

object AlbumController {
  def repo = new InMemoryRepository[AlbumId, Album]()
}


object ArtistController extends Controller {

}

object SongController extends Controller {

}

trait ResourceController[ID <: Id, ENTITY <: Entity[ID]] extends Controller {

  def repository: Repository[ID, ENTITY]

  def jsonCodec: JsonCodec[ID, ENTITY]

  def list = Action.async {
    repository.findAllById(Seq()).map {
      entities =>
        Ok(Json.toJson(entities.map(jsonCodec.write)))
    }
  }

  def create = Action.async(BodyParsers.parse.json) {
    request =>
      jsonCodec.read(request.body) match {
        case JsSuccess(entity, _) => repository.insert(entity).map { _ => Ok("Oki!") }
        case err: JsError => Future.successful(BadRequest(Json.obj("message" -> "Fail Dikkka") ++ JsError.toFlatJson(err)))
      }
  }

  /*def get(id: String) = Action.async {
    val id: ID = new ID {} // TODO ID binding
    repository.findById(id).map {
      _ match {
        case Some(entity) => Ok(jsonCodec.write(entity))
        case None => NotFound("Not Found")
      }
    }
  }

  def update(id: String) = Action.async { request =>
    val id: ID = new ID {} // TOOD
  val entity: ENTITY = new ENTITY {}
    repository.update(entity).map { _ =>
      Ok("Updated Dikkka!")
    }
  }*/
}


trait JsonCodec[ID <: Id, ENTITY <: Entity[ID]] {

  def read(input: JsValue): JsResult[ENTITY]

  def write(entity: ENTITY): JsValue

}


trait ReadCodec[INPUT, ID <: Id, ENTITY <: Entity[Id]] {

  def read(input: INPUT): ENTITY

}

trait WriteCodec[OUTPUT, ID <: Id, ENTITY <: Entity[Id]] {

  def write(entity: ENTITY): OUTPUT

}