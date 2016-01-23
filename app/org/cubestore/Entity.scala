package org.cubestore

trait Id

trait Entity[ID <: Id] {
  def id: ID
}
