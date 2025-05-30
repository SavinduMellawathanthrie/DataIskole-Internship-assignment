package network;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SuperHero {
    private int id;
    private String name;
    private LocalDate addedDate;
    private Set<Integer> friends;

    public SuperHero(int id, String name, LocalDate addedDate) {
        this.id = id;
        this.name = name;
        this.addedDate = addedDate;
        this.friends = new HashSet<>();
    }

    public void addFriend(int friendId) {
        friends.add(friendId);
    }

    public Set<Integer> getFriends() {
        return friends;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public void setFriends(Set<Integer> friends) {
        this.friends = friends;
    }
}
