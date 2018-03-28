package com.knoldus.externalservice.api.service

import akka.NotUsed
import com.knoldus.externalservice.api.entities.ExternalServiceUserInformation
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

trait ExternalService extends Service {

  def getUser(): ServiceCall[NotUsed, ExternalServiceUserInformation]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("external-service")
      .withCalls(
        pathCall("/posts/1", getUser _)
      ).withAutoAcl(true)
    // @formatter:on

  }
}
