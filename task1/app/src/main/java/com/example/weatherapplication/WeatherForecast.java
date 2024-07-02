// WeatherForecast.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.weatherapplication;
import java.util.List;
import java.time.OffsetDateTime;

public class WeatherForecast {
    private City city;
    private long cnt;
    private String cod;
    private long message;
    private List<ListElement> list;

    public City getCity() { return city; }
    public void setCity(City value) { this.city = value; }

    public long getCnt() { return cnt; }
    public void setCnt(long value) { this.cnt = value; }

    public String getCod() { return cod; }
    public void setCod(String value) { this.cod = value; }

    public long getMessage() { return message; }
    public void setMessage(long value) { this.message = value; }

    public List<ListElement> getList() { return list; }
    public void setList(List<ListElement> value) { this.list = value; }
}

// City.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class City {
    private String country;
    private Coord coord;
    private long sunrise;
    private long timezone;
    private long sunset;
    private String name;
    private long id;
    private long population;

    public String getCountry() { return country; }
    public void setCountry(String value) { this.country = value; }

    public Coord getCoord() { return coord; }
    public void setCoord(Coord value) { this.coord = value; }

    public long getSunrise() { return sunrise; }
    public void setSunrise(long value) { this.sunrise = value; }

    public long getTimezone() { return timezone; }
    public void setTimezone(long value) { this.timezone = value; }

    public long getSunset() { return sunset; }
    public void setSunset(long value) { this.sunset = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public long getid() { return id; }
    public void setid(long value) { this.id = value; }

    public long getPopulation() { return population; }
    public void setPopulation(long value) { this.population = value; }
}

// Coord.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Coord {
    private double lon;
    private double lat;

    public double getLon() { return lon; }
    public void setLon(double value) { this.lon = value; }

    public double getLat() { return lat; }
    public void setLat(double value) { this.lat = value; }
}

// ListElement.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation


class ListElement {
    private long dt;
    private double pop;
    private Rain rain;
    private long visibility;
    private String dt_txt;
    private List<Weather> weather;
    private Main main;
    private Clouds clouds;
    private Sys sys;
    private Wind wind;

    public long getDt() { return dt; }
    public void setDt(long value) { this.dt = value; }

    public double getPop() { return pop; }
    public void setPop(double value) { this.pop = value; }

    public Rain getRain() { return rain; }
    public void setRain(Rain value) { this.rain = value; }

    public long getVisibility() { return visibility; }
    public void setVisibility(long value) { this.visibility = value; }

    public String getDtTxt() { return dt_txt; }
    public void setDtTxt(String value) { this.dt_txt = value; }

    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> value) { this.weather = value; }

    public Main getMain() { return main; }
    public void setMain(Main value) { this.main = value; }

    public Clouds getClouds() { return clouds; }
    public void setClouds(Clouds value) { this.clouds = value; }

    public Sys getSys() { return sys; }
    public void setSys(Sys value) { this.sys = value; }

    public Wind getWind() { return wind; }
    public void setWind(Wind value) { this.wind = value; }
}

// Clouds.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Clouds {
    private long all;

    public long getAll() { return all; }
    public void setAll(long value) { this.all = value; }
}

// Main.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Main {
    private double temp;
    private double temp_min;
    private long grndLevel;
    private double tempKf;
    private long humidity;
    private long pressure;
    private long sea_level;
    private double feelsLike;
    private double temp_max;

    public double getTemp() { return temp; }
    public void setTemp(double value) { this.temp = value; }

    public double getTempMin() { return temp_min; }
    public void settemp_min(double value) { this.temp_min = value; }

    public long getGrndLevel() { return grndLevel; }
    public void setGrndLevel(long value) { this.grndLevel = value; }

    public double getTempKf() { return tempKf; }
    public void setTempKf(double value) { this.tempKf = value; }

    public long getHumidity() { return humidity; }
    public void setHumidity(long value) { this.humidity = value; }

    public long getPressure() { return pressure; }
    public void setPressure(long value) { this.pressure = value; }

    public long getSeaLevel() { return sea_level; }
    public void setsea_level(long value) { this.sea_level = value; }

    public double getFeelsLike() { return feelsLike; }
    public void setFeelsLike(double value) { this.feelsLike = value; }

    public double getTempMax() { return temp_max; }
    public void settemp_max(double value) { this.temp_max = value; }
}

// Rain.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Rain {
    private double the3H;

    public double getThe3H() { return the3H; }
    public void setThe3H(double value) { this.the3H = value; }
}

// Sys.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Sys {
    private String pod;
    long sunrise,sunset;

    public String getPod() { return pod; }
    public void setPod(String value) { this.pod = value; }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}

// Weather.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Weather {
    private String icon;
    private String description;
    private String main;
    private long id;

    public String getIcon() { return icon; }
    public void setIcon(String value) { this.icon = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getMain() { return main; }
    public void setMain(String value) { this.main = value; }

    public long getid() { return id; }
    public void setid(long value) { this.id = value; }
}

// Wind.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Wind {
    private long deg;
    private double speed;
    private double gust;

    public long getDeg() { return deg; }
    public void setDeg(long value) { this.deg = value; }

    public double getSpeed() { return speed; }
    public void setSpeed(double value) { this.speed = value; }

    public double getGust() { return gust; }
    public void setGust(double value) { this.gust = value; }
}
