package com.knoldus.externalservice.api.entities

import play.api.libs.json.{Format, Json}

case class User(id: Int,name: String,salary: Int)

object User {

  implicit val format: Format[User] = Json.format
}