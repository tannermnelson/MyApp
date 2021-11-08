package com.clementsnelson.smoothriders;

import androidx.annotation.NonNull;

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

    public String getrideDescription(){ return rideDescription;}

    public String getRideTime(){return rideTime;}

    public String getPickupLocation(){return pickupLocation;}

    public String getDestinationLocation(){return destinationLocation;}

    public String getRideTip(){return rideTip;}

    @NonNull
    @Override
    public String toString() {
        return this.rideTime + " " + this.destinationLocation + " " + this.rideTip;
    }
}
