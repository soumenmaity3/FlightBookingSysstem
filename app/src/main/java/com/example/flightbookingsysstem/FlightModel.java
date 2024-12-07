package com.example.flightbookingsysstem;

public class FlightModel {
int flightLogo;
String start;
String Destination;
String startTime,endTime,flightName,flightNumber;

    public FlightModel(int flightLogo,String flightName,String flightNumber, String start, String destination, String startTime, String endTime) {
        this.flightLogo = flightLogo;
        this.start = start;
        Destination = destination;
        this.startTime = startTime;
        this.endTime = endTime;
        this.flightName=flightName;
        this.flightNumber=flightNumber;
    }

    public int getFlightLogo() {
        return flightLogo;
    }

    public void setFlightLogo(int flightLogo) {
        this.flightLogo = flightLogo;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {this.flightName = flightName;}

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
