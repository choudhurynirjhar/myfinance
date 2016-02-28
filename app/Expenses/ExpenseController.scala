package expenses.controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.db._
import play.api.Play.current
import java.sql._

import expenses.providers._
import expenses.models._
import expenses.repositories._
import infrastructure._

object ExpenseController extends Controller {
    implicit val rds = (
        (__ \ 'item).read[String] and
        (__ \ 'vendor).read[String] and
        (__ \ 'price).read[Double] and
        (__ \ 'location).read[String]
      ) tupled
  
    def create = Action { request =>
        request.body.asJson.map { json =>
        json.validate[(String, String, Double, String)].map{ 
            case (item, vendor, price, location) => 
                ExpenseRepository.create(item, vendor, price, location).
                fold(l => BadRequest(l.reason), 
                    r => Ok(s"Item booked $item from vendor $vendor with price $price and location $location"))
            case _ => BadRequest("Unexpected input")
        }.recoverTotal{
            e => BadRequest("Detected error:"+ JsError.toJson(e))
        }
        }.getOrElse {
            BadRequest("Expecting Json data")
        }
    }
    
    def query = Action {
        ExpenseProvider.query.fold(l => BadRequest(l.reason), r => Ok(Json.toJson(r)))
    }
}