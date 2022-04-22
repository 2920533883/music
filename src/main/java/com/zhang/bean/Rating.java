package com.zhang.bean;

public class Rating {
    public int song_id;
    public double rating = 0.0;

    public Rating(int song_id, double rating) {
        this.song_id = song_id;
        this.rating = rating;
    }

    public void addRating(double num) {
        this.rating += num;
    }


    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            if (obj instanceof Rating) {
                Rating c = (Rating) obj;
                return c.song_id == this.song_id;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + song_id + ":" + rating + ")";
    }
}
