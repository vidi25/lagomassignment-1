package com.knoldus.externalservice.api.entities

import play.api.libs.json.{Format, Json}

case class ExternalServiceUserInformation(userId,Int,id: Int,title: String,body: String)

object ExternalServiceUserInformation {

  implicit val format: Format[ExternalServiceUserInformation] = Json.format
}