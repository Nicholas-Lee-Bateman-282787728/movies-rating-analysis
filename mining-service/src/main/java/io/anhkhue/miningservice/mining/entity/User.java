package io.anhkhue.miningservice.mining.entity;

public class User implements BasedMining {

    private long id;
    private String username;

    public User() {
    }

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User)
            return this.id == ((User) obj).id;

        return false;
    }
}