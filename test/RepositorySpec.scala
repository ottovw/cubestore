import model.{ArtistId, AlbumId, Album}
import org.cubestore.{EntityDescription, InMemoryRepository}
import org.specs2.mutable.Specification

import scala.concurrent.Await
import scala.concurrent.duration._

class RepositorySpec extends Specification {

  "A repository" should {

    val repo = new InMemoryRepository[AlbumId, Album]()

    implicit object AlbumDescription extends EntityDescription[AlbumId, Album] {
      override def id(album: Album): AlbumId = album.id
    }

    "allow to insert a single entity" in {
      val albumId = AlbumId(0)
      val artistId = ArtistId(0)
      repo.insert(Album(albumId, artistId, "Original Pirate Material"))
      Await.result(repo.findById(albumId), 2 seconds) must beSome
      Await.result(repo.findById(AlbumId(42)), 2 seconds) must beNone
    }
  }
}
