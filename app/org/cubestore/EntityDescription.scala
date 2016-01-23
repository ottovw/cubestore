package org.cubestore

trait EntityDescription[ID, E] {
  def id(e: E): ID
}
