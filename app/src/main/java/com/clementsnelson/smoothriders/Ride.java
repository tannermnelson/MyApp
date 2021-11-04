package com.clementsnelson.smoothriders;

public class Ride {

    public String rideDescription, rideTime, pickupLocation, destinationLocation, rideTip;

    public Ride() {

    }


    public Ride(String rideDescription, String rideTime, String pickupLocation, String destinationLocation, String rideTip) {
        this.rideDescription = rideDescription;
        this.rideTime = rideTime;
        this.pickupLocation = pickupLocation;
        this.destinationLocation = destinationLocation;
        this.rideTip = rideTip;
    }
}
