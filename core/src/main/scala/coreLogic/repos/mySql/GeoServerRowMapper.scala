package coreLogic.repos.mySql

import java.sql.ResultSet

import org.springframework.jdbc.core.RowMapper
import util.Utils._
import scala.reflect.ClassTag

trait GeoServerRowMapper {
  def rowMapper[T: ClassTag](implicit manifest: Manifest[T]) : RowMapper[T] = new RowMapper[T] {
    override def mapRow(rs: ResultSet, rowNum: Int): T = {
      val dataBlob = rs.getBlob("data")
      val data = dataBlob.getBytes(1, dataBlob.length().toInt)
      new String(data).fromJsonString[T]
    }
  }

}
