package com.fufufu.moviecatalogue.models;

public class Language {
    private String languageName;
    private String countryName;
    private String countryCode;

    public Language(){

    }

    public String getCountryName() {
        return countryName;
    }

    void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    void setLanguageName(String languaeName) {
        this.languageName = languaeName;
    }
}
