import play.api.ApplicationLoader.Context
import play.api.routing.Router
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import routers.TransactionRouter

class Boot extends ApplicationLoader {

  def load(context: Context): Application = new BuiltInComponentsFromContext(context) {
    def router: Router = Router.from {
      TransactionRouter()
    }
  }.application

}
