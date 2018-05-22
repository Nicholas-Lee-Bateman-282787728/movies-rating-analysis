package io.anhkhue.miningservice.model;

public class Movie {

    private long id;
    private String title;

    public Movie() {
    }

    public Movie(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movie)
            return this.id == ((Movie) obj).id;
        return false;
    }
}
