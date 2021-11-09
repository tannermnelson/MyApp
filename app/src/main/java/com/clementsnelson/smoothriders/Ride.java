package com.clementsnelson.smoothriders;

import androidx.annotation.NonNull;

public class Ride {

    public String rideDescription, rideTime, pickupLocation, destinationLocation, rideTip, requesterEmail;

    public Ride() {

    }


    public Ride(String rideDescription, String rideTime, String pickupLocation, String destinationLocation, String rideTip, String requesterEmail) {
        this.rideDescription = rideDescription;
        this.rideTime = rideTime;
        this.pickupLocation = pickupLocation;
        this.destinationLocation = destinationLocation;
        this.rideTip = rideTip;
        this.requesterEmail = requesterEmail;
    }

    public String getrideDescription(){ return rideDescription;}

    public String getRideTime(){return rideTime;}

    public String getPickupLocation(){return pickupLocation;}

    public String getDestinationLocation(){return destinationLocation;}

    public String getRideTip(){return rideTip;}

    public String getRequesterEmail(){return requesterEmail;}

    @NonNull
    @Override
    public String toString() {
        return this.requesterEmail + " is requesting a ride from " + this.pickupLocation + " to " +
                this.destinationLocation + " at " + this.rideTime + " for $" + this.rideTip + " dollars.";
        // return this.requesterEmail + " needs a ride at " + this.rideTime + " at " + this.destinationLocation + " for " + this.rideTip;
    }
}
