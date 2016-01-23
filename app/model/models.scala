package model

case class ArtistId(id: Long) extends AnyVal
case class AlbumId(id: Long) extends AnyVal
case class SongId(id: Long) extends AnyVal

case class Artist(id: ArtistId, name: String, website: String)
case class Album(id: AlbumId, title: String, year: Option[Int])
case class Song(id: SongId, title: String, artistId: ArtistId, albumId: AlbumId)

