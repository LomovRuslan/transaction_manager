
package routers

import java.time.LocalDate

import models.Model.DebitTransaction
import play.api.routing.Router
import play.api.test._

class DebitTest extends BaseRouterSpecification {

  val fakeRouter = Router.from(FakeTransactionRouter())

  "Users Router" should {

    "Have a specific handler to CREATE a Debit" in new WithApplication() {
      val body = DebitTransaction(11, 500, LocalDate.now())
      val fakeRequest = FakeRequest(POST, "/debit").withBody(body)
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Have no handler" in new WithApplication() {
      val fakeRequest = FakeRequest(GET, "")
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_==(None)
    }

  }

}

