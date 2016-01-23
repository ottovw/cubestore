package model

import org.cubestore.Id

case class ArtistId(id: Long) extends Id
case class AlbumId(id: Long) extends Id
case class SongId(id: Long) extends Id

case class Artist(id: ArtistId, name: String, website: String)
case class Album(id: AlbumId, artistId: ArtistId, title: String, year: Option[Int] = None)
case class Song(id: SongId, title: String, artistId: ArtistId, albumId: AlbumId)

