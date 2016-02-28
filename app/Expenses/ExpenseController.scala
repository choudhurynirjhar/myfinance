package expenses.controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

import expenses.providers._
import expenses.models._
import expenses.repositories._
import infrastructure._

object ExpenseController extends Controller {
  
    def create = Action { request =>
        request.body.asJson.map { json =>
            ExpenseRepository.create(Expense(json)).
                fold(l => BadRequest(l.reason), 
                    r => Ok(s"Item booked"))
        }.getOrElse {
            BadRequest("Expecting Json data")
        }
    }
    
    def query = Action {
        ExpenseProvider.query.fold(l => BadRequest(l.reason), r => Ok(Json.toJson(r)))
    }
}