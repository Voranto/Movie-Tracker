package com.example.movie.user;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/email/exists")
    public ResponseEntity<Boolean> doesEmailExist(@RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return ResponseEntity.ok(user.isPresent());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(userDTO.passwordRaw());

        User user = new User(UUID.randomUUID(), userDTO.displayName(),userDTO.email(),hashedPassword);

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteUser(@Valid @RequestBody LoginDTO loginDTO) {
        String hashedPassword = new BCryptPasswordEncoder().encode(loginDTO.passwordRaw());

        // Check for existance
        Optional<User> user =  userRepository.findByEmail(loginDTO.email());
        if (user.isPresent()) {
            if (user.get().getPasswordHash() == hashedPassword) {
                userRepository.deleteById(user.get().getId());
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDTO loginDTO) {
        String hashedPassword = new BCryptPasswordEncoder().encode(loginDTO.passwordRaw());

        // Check for existance
        Optional<User> user =  userRepository.findByEmail(loginDTO.email());
        if (user.isPresent()) {
            if (user.get().getPasswordHash() == hashedPassword) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/add-friend")
    public ResponseEntity<Void> addFriend(@RequestParam UUID userID, @RequestParam UUID friendID) {
        Optional<User> user = userRepository.findById(userID);
        Optional<User> friend = userRepository.findById(friendID);
        if (user.isPresent() && friend.isPresent()) {
            user.get().addFriend(friend.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
