package controllers

import org.cubestore.{Repository, Id, Entity}
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{Action, Controller}

object AlbumController extends Controller with ResourceController {

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
        Ok(Json.toJson(Json.obj("codechere" -> entities.toString)))
    }
  }

  def get(id: String) = Action.async {
    val id: ID = new ID {} // TODO ID binding
    repository.findById(id).map {
      _ match {
        case Some(entity) => Ok(Json.toJson(Json.obj("codehere" -> entity.toString)))
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
  }
}


trait JsonCodec[ID <: Id, ENTITY <: Entity[Id]] {

  def read(input: JsObject): ENTITY

  def write(entity: ENTITY): JsObject

}


trait ReadCodec[INPUT, ID <: Id, ENTITY <: Entity[Id]] {

  def read(input: INPUT): ENTITY

}

trait WriteCodec[OUTPUT, ID <: Id, ENTITY <: Entity[Id]] {

  def write(entity: ENTITY): OUTPUT

}