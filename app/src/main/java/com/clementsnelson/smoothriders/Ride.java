package com.clementsnelson.smoothriders;

import androidx.annotation.NonNull;
import java.io.Serializable;


public class Ride implements Serializable {

    public String rideDescription, rideTime, pickupLocation, destinationLocation, rideTip, requesterEmail, driverEmail, rideId;
    private Boolean isAccepted;

    public Ride() {

    }


    public Ride(String rideDescription, String rideTime, String pickupLocation, String destinationLocation,
                String rideTip, String requesterEmail, String driverEmail, String rideId, Boolean isAccepted) {
        this.rideDescription = rideDescription;
        this.rideTime = rideTime;
        this.pickupLocation = pickupLocation;
        this.destinationLocation = destinationLocation;
        this.rideTip = rideTip;
        this.requesterEmail = requesterEmail;
        this.driverEmail = driverEmail;
        this.rideId = rideId;
        this.isAccepted = isAccepted;
    }

    public String getRideDescription(){ return rideDescription;}

    public String getRideTime(){return rideTime;}

    public String getPickupLocation(){return pickupLocation;}

    public String getDestinationLocation(){return destinationLocation;}

    public String getRideTip(){return rideTip;}

    public String getRequesterEmail(){return requesterEmail;}

    public String getDriverEmail(){return driverEmail;}

    public Boolean getIsAccepted(){return isAccepted;}

    public void setIsAccepted(Boolean isAccepted){this.isAccepted = isAccepted;}

    public void setDriverEmail(String driverEmail){this.driverEmail = driverEmail;}

    @NonNull
    @Override
    public String toString() {
        return this.requesterEmail + " is requesting a ride from " + this.pickupLocation + " to " +
                this.destinationLocation + " at " + this.rideTime + " for $" + this.rideTip + " dollars.";
        // return this.requesterEmail + " needs a ride at " + this.rideTime + " at " + this.destinationLocation + " for " + this.rideTip;
    }
}
