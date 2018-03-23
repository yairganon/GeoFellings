package util

import service.dto.Location

object DistanceCalculator  {
    private val AVERAGE_RADIUS_OF_EARTH_KM = 6371
    def calculateDistanceInKilometer(userLocation: Location, warehouseLocation: Location): Int = {
        val latDistance = Math.toRadians(userLocation.latitude - warehouseLocation.latitude)
        val lngDistance = Math.toRadians(userLocation.longitude - warehouseLocation.longitude)
        val sinLat = Math.sin(latDistance / 2)
        val sinLng = Math.sin(lngDistance / 2)
        val a = sinLat * sinLat +
        (Math.cos(Math.toRadians(userLocation.latitude)) *
            Math.cos(Math.toRadians(warehouseLocation.latitude)) *
            sinLng * sinLng)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        (AVERAGE_RADIUS_OF_EARTH_KM * c).toInt
    }
}
