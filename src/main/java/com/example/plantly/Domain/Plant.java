package com.example.plantly.Domain;

public class Plant {
    public String plantSpecies;
    public String plantGenus;
    public String plantInfo;
    public String water;
    public String tempature;
    public String humidity;
    public String flowering;
    public String pests;
    public String diseases;
    public String soil;
    public String potSize;
    public int poisonous;
    public int daysUntilWatering;
    public String fertilizer;
    public String light;
    public int plantId;

    public Plant(String plantSpecies, String plantGenus, String plantInfo, String water, String tempature, String humidity, String flowering, String pests, String diseases, String soil, String potSize, int poisonous, int daysUntilWatering, String fertilizer, String light, int plantId) {
        this.plantSpecies = plantSpecies;
        this.plantGenus = plantGenus;
        this.plantInfo = plantInfo;
        this.water = water;
        this.tempature = tempature;
        this.humidity = humidity;
        this.flowering = flowering;
        this.pests = pests;
        this.diseases = diseases;
        this.soil = soil;
        this.potSize = potSize;
        this.poisonous = poisonous;
        this.daysUntilWatering = daysUntilWatering;
        this.fertilizer = fertilizer;
        this.light = light;
        this.plantId = plantId;
    }

    public Plant(String plantSpecies, int plantId) {
        this.plantSpecies = plantSpecies;
        this.plantId = plantId;
    }

    public Plant(String plantSpecies, String plantGenus, int plantId) {
        this.plantSpecies = plantSpecies;
        this.plantGenus = plantGenus;
        this.plantId = plantId;
    }
}
