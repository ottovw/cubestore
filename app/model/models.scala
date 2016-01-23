package model

import play.api.libs.json.Json
import org.cubestore.{EntityDescription, Id}

case class ArtistId(id: Long) extends Id
case class AlbumId(id: Long) extends Id

object ArtistId {
  implicit val format = Json.format[ArtistId]
}

object AlbumId {
  implicit val format = Json.format[AlbumId]
}


case object Album {
  implicit val albumDescription = new EntityDescription[AlbumId, Album] {
    override def id(album: Album): AlbumId = album.id
  }
}

case class SongId(id: Long) extends Id

case class Artist(id: ArtistId, name: String, website: String)
case class Album(id: AlbumId, artistId: ArtistId, title: String, year: Option[Int] = None)
case class Song(id: SongId, title: String, artistId: ArtistId, albumId: AlbumId)

