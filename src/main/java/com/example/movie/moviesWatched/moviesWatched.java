package com.example.movie.moviesWatched;

import java.util.List;
import java.util.UUID;

import com.example.movie.user.User;
import jakarta.persistence.*;
import jdk.jfr.Description;

@Entity
@Table(name = "movies_watched")
@Description("General table for both movies and tv shows")
// TODO: Clear up collisions between TV shows id and Movie ID
public class moviesWatched {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name ="user_id")
    private int userID;

    @Column(nullable = false, name ="movie_id")
    private int movieID;

    @Column
    @Description("1-10 rating for the movie/tv show")
    private int rating;

    @Column
    private String description;

    @OneToMany
    private List<User> recommendedTo;
}
