package com.knoldus.externalservice.impl

import akka.{Done, NotUsed}
import com.knoldus.externalservice.api.entities.{ExternalServiceUserInformation, User}
import com.knoldus.externalservice.api.entities.com.knoldus.externalservice.api.entities.UserData
import com.knoldus.externalservice.api.service.{ExternalService, ExternalUserService}
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

class ExternalUserServiceImpl(externalService: ExternalService)(implicit ec: ExecutionContext) extends ExternalUserService {

  val userList = new ListBuffer[User]

  /**
    * adds a user to the list
    * @return Done
    */
  override def createUser(): ServiceCall[User, Done] = ServiceCall {
    request =>
      val user = new User(request.id, request.name, request.salary)
      val userExists = userList.find(user => user.id == request.id)
      userExists match {
        case Some(user: User) => Future.successful(Done)
        case None =>
          userList += user
          Future.successful(Done)
      }
  }

  /**
    * gets details of a user
    * @param id: Integer id of a user
    * @return User: details of user
    */
  override def getUserDetails(id: Int): ServiceCall[NotUsed, User] = ServiceCall {
    _ =>
      val user = userList.find(user => user.id == id)
      user match {
        case Some(user: User) => Future.successful(user)
        case None => Future.successful(null)
      }
  }

  /**
    * updates the details of a user
    * @param id : Integer id of a user
    * @return String of updated user list
    */
  override def updateUser(id: Int): ServiceCall[UserData, String] = ServiceCall {
    request =>
      val userExists = userList.find(user => user.id == id)
      userExists match {
        case Some(user: User) =>
          val updatedUser = user.copy(name = request.name, salary = request.salary)
          userList -= user
          userList += updatedUser
          Future.successful(s"Updated user list is $userList")
        case None => Future.successful("User not found..!!")
      }
  }

  /**
    * deletes a particular user from list
    * @param id : Integer id of a user
    * @return String of updated user list after deletion
    */
  override def deleteUser(id: Int): ServiceCall[NotUsed, String] = ServiceCall {
    _ =>
      val user = userList.find(user => user.id == id)
      user match {
        case Some(user: User) =>
          userList -= user
          Future.successful(s"After deletion user list is $userList")
        case None =>
          Future.successful(s"User not found..!!!")
      }
  }

  /**
    * tests the external unmanaged service and gets data
    * @return data from the unmanaged service
    */
  override def testExternalService(): ServiceCall[NotUsed, ExternalServiceUserInformation] = ServiceCall {
    _ =>
      val result = externalService.getUser().invoke()
      result.map(response => response)
  }
}
