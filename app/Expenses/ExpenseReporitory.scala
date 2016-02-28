package expenses.repositories

import play.api.db._
import play.api.Play.current
import java.sql._

import expenses.models._
import infrastructure._

object ExpenseRepository {
    def create(exp: Expense) : Either[FailResult, Unit] = {
        val conn = DB.getConnection()
        try {
            val stmt = conn.createStatement

            stmt.executeUpdate("""CREATE TABLE IF NOT EXISTS 
                                    expenses (
                                        item varchar(255), 
                                        vendor varchar(255), 
                                        price decimal, 
                                        location text)""")
            stmt.executeUpdate(s"INSERT INTO expenses VALUES ('${exp.item}', '${exp.vendor}', ${exp.price}, '${exp.location}')")
            Right(Unit)
        } catch { 
            case e:SQLException => Left(FailResult(e.toString))
        } finally {
            conn.close()
        }
    }
}