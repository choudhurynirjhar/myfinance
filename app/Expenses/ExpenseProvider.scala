package expenses.providers

import play.api.db._
import play.api.Play.current
import java.sql._

import expenses.models._
import infrastructure._

object ExpenseProvider {
    def query : Either[FailResult, Seq[Expense]] = {
        val conn = DB.getConnection()
        try {
            val stmt = conn.createStatement

            val rs = stmt.executeQuery("SELECT item, vendor, price, location FROM expenses")
            Right(createExpense(rs))
        } catch { 
            case e:SQLException => Left(FailResult(e.toString))
        } finally {
            conn.close()
        }
    }
    
    private def createExpense(rs: ResultSet) = {
        new Iterator[Expense] {
            def hasNext = rs.next()
            def next() = Expense(rs)
        }.toList
    }
}