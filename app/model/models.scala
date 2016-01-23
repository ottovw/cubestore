package model

import org.cubestore.{Entity, Id}

case class ArtistId(id: Long) extends Id
case class AlbumId(id: Long) extends Id
case class SongId(id: Long) extends Id

case class Artist(id: ArtistId, name: String, website: String)
case class Album(id: AlbumId, title: String, year: Option[Int] = None) extends Entity[AlbumId]
case class Song(id: SongId, title: String, artistId: ArtistId, albumId: AlbumId)

