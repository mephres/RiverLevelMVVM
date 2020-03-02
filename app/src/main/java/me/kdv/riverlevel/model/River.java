package me.kdv.riverlevel.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class River {
    @PrimaryKey(autoGenerate = true)
    int id;
    private String name; //название
    private String station; //пункт
    private String overflow; //выход на пойму
    private String waterLevel; //уровень воды
    private String levelChange; //изменение уровня
    private String waterTemperature; //температура воды

    public River() {
    }

    public River(String name, String station, String overflow, String waterLevel, String levelChange, String waterTemperature) {
        this.name = name;
        this.station = station;
        this.overflow = overflow;
        this.waterLevel = waterLevel;
        this.levelChange = levelChange;
        this.waterTemperature = waterTemperature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getOverflow() {
        return overflow;
    }

    public void setOverflow(String overflow) {
        this.overflow = overflow;
    }

    public String getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getLevelChange() {
        return levelChange;
    }

    public void setLevelChange(String levelChange) {
        this.levelChange = levelChange;
    }

    public String getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(String waterTemperature) {
        this.waterTemperature = waterTemperature;
    }
}

