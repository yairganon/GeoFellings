package coreLogic.repos.mySql

import java.sql.ResultSet

import org.springframework.jdbc.core.RowMapper
import service.dto.{Location, UserLocation}
import util.UserId
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

  def rowMapperLocations: RowMapper[UserLocation] = new RowMapper[UserLocation] {
    override def mapRow(rs: ResultSet, rowNum: Int): UserLocation = {
      val dataBlob = rs.getBlob("data")
      val time = rs.getTimestamp("creation_date").getTime
      val userId = rs.getString("userId")
      val data = dataBlob.getBytes(1, dataBlob.length().toInt)
      UserLocation(UserId(userId), time, new String(data).fromJsonString[Location])
    }
  }

}
