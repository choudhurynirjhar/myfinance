package expenses.models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.sql._

class Expense(val item: String, val vendor: String, val price: Double, val location: String) { 
}

object Expense {
    def apply(rs: ResultSet) = 
        new Expense(rs.getString("item"), rs.getString("vendor"), rs.getDouble("price"), rs.getString("location"))
        
    def apply(json: JsValue) =
        new Expense((json \ "item").as[String],
            (json \ "vendor").as[String],
            (json \ "price").as[Double],
            (json \ "location").as[String]
        )
        
    def unapply(exp: Expense) = Some((exp.item, exp.vendor, exp.price, exp.location))

    implicit object ExpenseFormat extends Format[Expense] {

        // convert from Expense object to JSON (serializing to JSON)
        def writes(exp: Expense): JsValue = 
            JsObject(Seq(
                "item" -> JsString(exp.item),
                "vendor" -> JsString(exp.vendor),
                "price" -> JsNumber(exp.price),
                "location" -> JsString(exp.location)
            ))

        // convert from JSON string to a Expense object (de-serializing from JSON)
        // (i don't need this method; just here to satisfy the api)
        def reads(json: JsValue): JsResult[Expense] = JsSuccess(Expense(json))
    }
}
