package com.fufufu.moviecataloguemvvm.models;

public class Language {
    private String languageName;
    private String countryName;
    private String countryCode;

    public Language(){

    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languaeName) {
        this.languageName = languaeName;
    }
}
