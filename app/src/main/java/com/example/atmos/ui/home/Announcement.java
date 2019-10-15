package com.example.atmos.ui.home;

class Announcement {

    Announcement(String announcementName, String announcementDate) {
        this.announcementName = announcementName;
        this.announcementDate = announcementDate;
    }

    private String announcementName;
    private String announcementDate;

    public String getAnnouncementName() {
        return announcementName;
    }

    public String getAnnouncementDate() {
        return announcementDate;
    }
}
