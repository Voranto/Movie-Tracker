package com.example.movie.user;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<Boolean> createUser(@Valid @RequestBody UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            return ResponseEntity.badRequest().body(false);
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(userDTO.passwordRaw());


        User user = new User(UUID.randomUUID(), userDTO.displayName(),userDTO.email(),hashedPassword);

        userRepository.save(user);

        return ResponseEntity.ok(true);
    }

}
