package io.github.krtkush.marsexplorer.WeatherJsonDataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeykushwaha on 08/06/16.
 */
public class Report {

    @SerializedName("terrestrial_date")
    @Expose
    private String terrestrialDate;
    @SerializedName("sol")
    @Expose
    private Long sol;
    @SerializedName("ls")
    @Expose
    private Double ls;
    @SerializedName("min_temp")
    @Expose
    private Double minTemp;
    @SerializedName("min_temp_fahrenheit")
    @Expose
    private Double minTempFahrenheit;
    @SerializedName("max_temp")
    @Expose
    private Double maxTemp;
    @SerializedName("max_temp_fahrenheit")
    @Expose
    private Double maxTempFahrenheit;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("pressure_string")
    @Expose
    private String pressureString;
    @SerializedName("abs_humidity")
    @Expose
    private Object absHumidity;
    @SerializedName("wind_speed")
    @Expose
    private Object windSpeed;
    @SerializedName("wind_direction")
    @Expose
    private String windDirection;
    @SerializedName("atmo_opacity")
    @Expose
    private String atmoOpacity;
    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("sunrise")
    @Expose
    private String sunrise;
    @SerializedName("sunset")
    @Expose
    private String sunset;

    /**
     *
     * @return
     *     The terrestrialDate
     */
    public String getTerrestrialDate() {
        return terrestrialDate;
    }

    /**
     *
     * @param terrestrialDate
     *     The terrestrial_date
     */
    public void setTerrestrialDate(String terrestrialDate) {
        this.terrestrialDate = terrestrialDate;
    }

    /**
     *
     * @return
     *     The sol
     */
    public Long getSol() {
        return sol;
    }

    /**
     *
     * @param sol
     *     The sol
     */
    public void setSol(Long sol) {
        this.sol = sol;
    }

    /**
     *
     * @return
     *     The ls
     */
    public Double getLs() {
        return ls;
    }

    /**
     *
     * @param ls
     *     The ls
     */
    public void setLs(Double ls) {
        this.ls = ls;
    }

    /**
     *
     * @return
     *     The minTemp
     */
    public Double getMinTemp() {
        return minTemp;
    }

    /**
     *
     * @param minTemp
     *     The min_temp
     */
    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    /**
     *
     * @return
     *     The minTempFahrenheit
     */
    public Double getMinTempFahrenheit() {
        return minTempFahrenheit;
    }

    /**
     *
     * @param minTempFahrenheit
     *     The min_temp_fahrenheit
     */
    public void setMinTempFahrenheit(Double minTempFahrenheit) {
        this.minTempFahrenheit = minTempFahrenheit;
    }

    /**
     *
     * @return
     *     The maxTemp
     */
    public Double getMaxTemp() {
        return maxTemp;
    }

    /**
     *
     * @param maxTemp
     *     The max_temp
     */
    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    /**
     *
     * @return
     *     The maxTempFahrenheit
     */
    public Double getMaxTempFahrenheit() {
        return maxTempFahrenheit;
    }

    /**
     *
     * @param maxTempFahrenheit
     *     The max_temp_fahrenheit
     */
    public void setMaxTempFahrenheit(Double maxTempFahrenheit) {
        this.maxTempFahrenheit = maxTempFahrenheit;
    }

    /**
     *
     * @return
     *     The pressure
     */
    public Double getPressure() {
        return pressure;
    }

    /**
     *
     * @param pressure
     *     The pressure
     */
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    /**
     *
     * @return
     *     The pressureString
     */
    public String getPressureString() {
        return pressureString;
    }

    /**
     *
     * @param pressureString
     *     The pressure_string
     */
    public void setPressureString(String pressureString) {
        this.pressureString = pressureString;
    }

    /**
     *
     * @return
     *     The absHumidity
     */
    public Object getAbsHumidity() {
        return absHumidity;
    }

    /**
     *
     * @param absHumidity
     *     The abs_humidity
     */
    public void setAbsHumidity(Object absHumidity) {
        this.absHumidity = absHumidity;
    }

    /**
     *
     * @return
     *     The windSpeed
     */
    public Object getWindSpeed() {
        return windSpeed;
    }

    /**
     *
     * @param windSpeed
     *     The wind_speed
     */
    public void setWindSpeed(Object windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     *
     * @return
     *     The windDirection
     */
    public String getWindDirection() {
        return windDirection;
    }

    /**
     *
     * @param windDirection
     *     The wind_direction
     */
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    /**
     *
     * @return
     *     The atmoOpacity
     */
    public String getAtmoOpacity() {
        return atmoOpacity;
    }

    /**
     *
     * @param atmoOpacity
     *     The atmo_opacity
     */
    public void setAtmoOpacity(String atmoOpacity) {
        this.atmoOpacity = atmoOpacity;
    }

    /**
     *
     * @return
     *     The season
     */
    public String getSeason() {
        return season;
    }

    /**
     *
     * @param season
     *     The season
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     *
     * @return
     *     The sunrise
     */
    public String getSunrise() {
        return sunrise;
    }

    /**
     *
     * @param sunrise
     *     The sunrise
     */
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    /**
     *
     * @return
     *     The sunset
     */
    public String getSunset() {
        return sunset;
    }

    /**
     *
     * @param sunset
     *     The sunset
     */
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

}