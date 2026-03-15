package com.example.movie.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name ="display_name")
    private String displayName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, name ="password_hash")
    private String passwordHash;

    @ManyToMany
    private List<User> friendsList;

    public User (UUID uuid, String displayName, String email, String passwordHash) {
        this.id = uuid;
        this.displayName = displayName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.friendsList = new ArrayList<>();
    }

    public User() {

    }
    public UUID getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public List<User> getFriendsList() {
        return friendsList;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void addFriend(User friend) {
        this.friendsList.add(friend);
    }
}