package com.practice.bankaccount.application.adapters.http

import akka.http.scaladsl.server.Route

trait SetRoutes extends Routes {

  def getRoutes: Route = route

}
