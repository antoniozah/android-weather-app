package com.azachos.weatherapp.utils

import com.azachos.weatherapp.datapersistence.entities.ConditionEntity
import com.azachos.weatherapp.datapersistence.entities.CurrentEntity
import com.azachos.weatherapp.datapersistence.entities.DayEntity
import com.azachos.weatherapp.datapersistence.entities.ForecastDataEntity
import com.azachos.weatherapp.datapersistence.entities.ForecastDayEmbed
import com.azachos.weatherapp.datapersistence.entities.ForecastDayEntity
import com.azachos.weatherapp.datapersistence.entities.ForecastEntity
import com.azachos.weatherapp.datapersistence.entities.HourEmbed
import com.azachos.weatherapp.datapersistence.entities.HourEntity
import com.azachos.weatherapp.datapersistence.entities.LocationEntity
import com.azachos.weatherapp.model.forecast.ForecastResponse
import com.azachos.weatherapp.model.forecast.domain.MyForecast

fun ForecastResponse.toEntity(): ForecastDataEntity =
    ForecastDataEntity(
        id = 0,
        current = CurrentEntity(
            cloud = this.current.cloud,
            feelsLikeC = this.current.feelslike_c,
            humidity = this.current.humidity,
            isDay = this.current.is_day,
            lastUpdated = this.current.last_updated,
            condition = ConditionEntity(
                code = this.current.condition.code,
                icon = this.current.condition.icon,
                text = this.current.condition.text
            )
        ),
        forecast = ForecastEntity(
            forecastDayEmbed = ForecastDayEmbed(
                forecastDay = this.forecast.forecastDay.map {
                    ForecastDayEntity(
                        date = it.date,
                        dateEpoch = it.dateEpoch,
                        day = DayEntity(
                            avgHumidity = it.day.avghumidity,
                            avgTempC = it.day.avgtemp_c,
                            condition = ConditionEntity(
                                code = it.day.condition.code,
                                text = it.day.condition.text,
                                icon = it.day.condition.icon
                            ),
                            maxTempC = it.day.maxtemp_c,
                            minTempC = it.day.mintemp_c
                        ),
                        hourList = HourEmbed(
                            hour = it.hour.map {hourItem ->
                                HourEntity(
                                    chanceOfRain = hourItem.chance_of_rain,
                                    cloud = hourItem.cloud,
                                    condition = ConditionEntity(
                                        code = hourItem.condition.code,
                                        icon = hourItem.condition.icon,
                                        text = hourItem.condition.text
                                    ),
                                    feelsLikeC = hourItem.feelslike_c,
                                    humidity = hourItem.humidity,
                                    isDay =  hourItem.is_day,
                                    tempC = hourItem.temp_c,
                                    time = hourItem.time,
                                    timeEpoch = hourItem.time_epoch
                                )
                            }
                        )
                    )
                }
            )
        ),
        location = LocationEntity(
            country = this.location.country,
            region = this.location.region,
            name = this.location.name,
            localtime = this.location.localtime,
            localtimeEpoch = this.location.localtime_epoch,
            tzId = this.location.tz_id
        )
    )

fun ForecastResponse.toDomain(): MyForecast =
    MyForecast(
        date = if (this.forecast.forecastDay.isNotEmpty()) this.forecast.forecastDay.first().date else "",
        dateEpoch = if (this.forecast.forecastDay.isNotEmpty()) this.forecast.forecastDay.first().dateEpoch else 0,
        locationCountry = this.location.country,
        localtime = this.location.localtime,
        locationRegion = this.location.region,
        locationName = this.location.name,
        conditionIcon = this.current.condition.icon,
        conditionText = this.current.condition.text
    )