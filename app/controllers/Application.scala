package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.cache.Cache
import play.api.Play.current

import play.api.db._
import java.io._

object Application extends Controller {

  def db = Action {
    var out = ""
    val conn = DB.getConnection()
    try {
      val stmt = conn.createStatement

      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)")
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())")

      val rs = stmt.executeQuery("SELECT tick FROM ticks")

      while (rs.next) {
        out += "Read from DB: " + rs.getTimestamp("tick") + "\n"
      }
    } finally {
      conn.close()
    }
    Ok(out)
  }
  
  
  def people = Action {
    val json = Json.parse("""[
        {"id": 1, "firstName": "John", "lastName": "Papa", "age": 25, "location": "Florida"},
        {"id": 2, "firstName": "Ward", "lastName": "Bell", "age": 31, "location": "California"},
        {"id": 3, "firstName": "Colleen", "lastName": "Jones", "age": 21, "location": "New York"},
        {"id": 4, "firstName": "Madelyn", "lastName": "Green", "age": 18, "location": "North Dakota"},
        {"id": 5, "firstName": "Ella", "lastName": "Jobs", "age": 18, "location": "South Dakota"},
        {"id": 6, "firstName": "Landon", "lastName": "Gates", "age": 11, "location": "South Carolina"},
        {"id": 7, "firstName": "Haley", "lastName": "Guthrie", "age": 35, "location": "Wyoming"},
        {"id": 8, "firstName": "Aaron", "lastName": "Jinglehiemer", "age": 22, "location": "Utah"}
    ]""");
    Ok(json);
  }
}
