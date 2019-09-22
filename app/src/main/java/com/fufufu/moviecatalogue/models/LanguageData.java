package com.fufufu.moviecatalogue.models;

import java.util.ArrayList;

public class LanguageData {
    private ArrayList<Language> list;
    private final String[][] data = {{"English", "US", "en"},{"Bahasa Indonesia", "Indonesia", "in"}};

    public LanguageData(){
        list = new ArrayList<>();
    }

    public ArrayList<Language> getList() {
        return list;
    }

    public void setList() {
        for (String[] datum : data) {
            Language language = new Language();
            language.setLanguageName(datum[0]);
            language.setCountryName(datum[1]);
            language.setCountryCode(datum[2]);
            this.list.add(language);
        }
    }
}
