package com.knoldus.externalservice.api.service

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import com.knoldus.entities.{User, UserData, UserInformation}

trait ExternalUserService extends Service {

  def createUser(): ServiceCall[User, Done]

  def getUserDetails(id: Int): ServiceCall[NotUsed, User]

  def updateUser(id: Int): ServiceCall[UserData, String]

  def deleteUser(id: Int): ServiceCall[NotUsed, String]

  def testExternalService(): ServiceCall[NotUsed, UserInformation]

  override final def descriptor: Descriptor = {
    import Service._
    named("user-external-service")
      .withCalls(
        restCall(Method.POST, "/api/createUser", createUser _),
        restCall(Method.GET, "/api/getUser/:id", getUserDetails _),
        restCall(Method.PUT, "/api/updateUser/:id", updateUser _),
        restCall(Method.DELETE,"/api/deleteUser/:id", deleteUser _),
        pathCall("/api/user",testExternalService _)
      )
      .withAutoAcl(true)
  }
}
