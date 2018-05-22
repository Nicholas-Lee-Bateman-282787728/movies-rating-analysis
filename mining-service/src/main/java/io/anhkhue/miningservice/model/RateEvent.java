package io.anhkhue.miningservice.model;

public class RateEvent {

    private int rating;
    private long timestamp;

    public RateEvent() {
    }

    public RateEvent(int rating, long timestamp) {
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
