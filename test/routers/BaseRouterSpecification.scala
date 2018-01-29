package routers

import play.api.ApplicationLoader.Context
import play.api.routing.Router
import play.api.test.PlaySpecification
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import repository.DefaultTransactionRepository

abstract class BaseRouterSpecification extends PlaySpecification {

  object FakeTransactionRouter extends DefaultTransactionRepository with TransactionRouter {
    def apply(): Router.Routes = routes
  }

  val fakeAppLoader = new ApplicationLoader() {
    def load(context: Context): Application = new BuiltInComponentsFromContext(context) {
      def router = Router.from {
        List(FakeTransactionRouter()) reduceLeft (_ orElse _)
      }
    }.application
  }

}
