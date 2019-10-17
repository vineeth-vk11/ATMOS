package com.example.atmos.ui.slummberparty;

import java.util.Date;

public class PartyEvent {

    private Date partyDate;
    private String partyName;
    private String partyLocation;
    private String partyDuration;

    public PartyEvent(Date date, String name, String location, String duration) {
        partyDate = date;
        partyName = name;
        partyLocation = location;
        partyDuration = duration;
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
}
