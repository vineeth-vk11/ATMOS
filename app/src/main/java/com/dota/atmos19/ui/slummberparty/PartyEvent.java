package com.dota.atmos19.ui.slummberparty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PartyEvent implements Comparable<PartyEvent>{

    private Date partyDate;
    private String partyName;
    private String partyLocation;
    private String partyDuration;
    private String partylanguage;

    public PartyEvent(String date, String name, String location, String duration, String language) {
        try{
            partyDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm").parse(date);
        } catch (ParseException e){
            e.printStackTrace();
        }
        partyName = name;
        partyLocation = location;
        partyDuration = duration;
        partylanguage = language;
    }

    public Date getPartyDate() {
        return partyDate;
    }

    public String getPartyName() {
        return partyName;
    }

    public String getPartyLocation() {
        return partyLocation;
    }

    public String getPartyDuration() {
        return partyDuration;
    }

    public String getLanguage() {
        return partylanguage;
    }

    @Override
    public int compareTo(PartyEvent partyEvent) {
        return this.partyDate.compareTo(partyEvent.getPartyDate());
    }
}
