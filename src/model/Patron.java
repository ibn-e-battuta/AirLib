package model;

import java.util.ArrayList;
import java.util.List;

public class Patron {
    private final String id;
    private String name;
    private String email;
    private List<Reservation> borrowingHistory;
    private List<String> preferences;

    public Patron(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowingHistory = new ArrayList<>();
        this.preferences = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Reservation> getBorrowingHistory() {
        return borrowingHistory;
    }

    public void addReservation(Reservation reservation) {
        borrowingHistory.add(reservation);
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void addPreference(String preference) {
        preferences.add(preference);
    }

    @Override
    public String toString() {
        return "Patron [ Id=" + id + ", Name=" + name + ", Email=" + email + " ]";
    }
}
