package org.android.events;



public class LocationEvent {

    private String country;
    private String region;
    private String city;

    public LocationEvent(String country, String region, String city) {
        this.country = country;
        this.region = region;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }
}
