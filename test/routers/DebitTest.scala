
package routers

import java.time.LocalDate

import models.Model.DebitTransaction
import play.api.routing.Router
import play.api.test._

class DebitTest extends BaseRouterSpecification {

  val fakeRouter = Router.from(FakeTransactionRouter())

  "Users Router" should {

    //    "Not find the user" in new WithApplicationLoader(fakeAppLoader) {
    //      val fakeRequest = FakeRequest(GET, "/users/123")
    //      val Some(result) = route(fakeRequest)
    //
    //      status(result) must equalTo(NOT_FOUND)
    //    }

    //    "Create and Find the user" in new WithApplicationLoader(fakeAppLoader) {
    //      val body = Json.toJson(User(87, "Gabriel"))
    //      val fakePost = FakeRequest(POST, "/users").withJsonBody(body)
    //      val Some(postResult) = route(fakePost)
    //
    //      status(postResult) must equalTo(CREATED)
    //
    //      val fakeRequest = FakeRequest(GET, "/users/87")
    //      val Some(result) = route(fakeRequest)
    //
    //      status(result) must equalTo(OK)
    //      contentType(result) must beSome("application/json")
    //      contentAsJson(result) must equalTo(body)
    //    }
    //
    //    "Have a specific handler to GET an User by id" in new WithApplication() {
    //      val fakeRequest = FakeRequest(GET, "/users/87")
    //      val handler = fakeRouter.handlerFor(fakeRequest)
    //
    //      handler must be_!=(None)
    //    }

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

