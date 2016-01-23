import controllers.AlbumController
import model.AlbumId
import org.cubestore.Entity
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
@RunWith(classOf[JUnitRunner])
class ResourceControllerSpec extends Specification {

  "ResourceController" should {

    "create an entity" in new WithApplication {
      val newAlbum = route(FakeRequest(POST, "/api/v1/albums").withJsonBody(Json.obj(
        "id" -> Json.obj("id" -> 1),
        "title" -> "The Doors",
        "year" -> 2016
      ))).get

      println(contentAsString(newAlbum))

      status(newAlbum) must equalTo(OK)
      val fromDb = Await.result(AlbumController.repo.findById(AlbumId(1)), Duration.Inf)
      fromDb must beSome.which(_.title == "The Doors")
    }

    "list all available entities" in new WithApplication {
      route(FakeRequest(GET, "/api/v1/albums")) must beSome.which(status(_) == NOT_FOUND)
    }

  }
}
