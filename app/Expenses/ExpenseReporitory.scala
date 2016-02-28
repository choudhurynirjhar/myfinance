package expenses.repositories

import play.api.db._
import play.api.Play.current
import java.sql._

import expenses.models._

object ExpenseRepository {
    def create(item: String, vendor: String, price: Double, location: String) = {
        val conn = DB.getConnection()
        try {
            val stmt = conn.createStatement

            stmt.executeUpdate("""CREATE TABLE IF NOT EXISTS 
                                    expenses (
                                        item varchar(255), 
                                        vendor varchar(255), 
                                        price decimal, 
                                        location text)""")
            stmt.executeUpdate(s"INSERT INTO expenses VALUES ('$item', '$vendor', $price, '$location')")
        }
        finally {
            conn.close()
        }
    }
}