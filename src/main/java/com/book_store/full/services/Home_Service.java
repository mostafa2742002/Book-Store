package com.book_store.full.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book_store.full.data.AuthRequest;
import com.book_store.full.data.Book;
import com.book_store.full.data.User;
import com.book_store.full.data.UserResponse;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.security.UserInfoUserDetailsService;

@Service
public class Home_Service {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private User_Service user_Service;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Autowired
    Book_Repo book_repo;

    @Autowired
    User_Repo user_repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Book> home() {
        return book_repo.findAll();
    }

    public List<Book> resentllyadded() {
        List<Book> books = book_repo.findAll();
        if (books.size() > 10) {
            return books.subList(0, 4);
        }
        return books;
    }

    public List<Book> topselling() {
        List<Book> books = book_repo.findAll(Sort.by(Sort.Direction.DESC, "buyed"));
        if (books.size() > 10) {
            return books.subList(0, 4);
        }
        return books;
    }

    public ResponseEntity<String> addUser(User user) {
        try {

            String verificationToken = jwtService.generateToken(user.getEmail());

            user.setEmailVerified(false);
            user.setVerificationToken(verificationToken);

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = user_repo.save(user);

            String subject = "Verify Your Email";
            String body = "Click the link to verify your email: https://bookstore-cs41.onrender.com/home/verifyemail?token="
                    + verificationToken;

            emailService.sendEmail(savedUser.getEmail(), subject, body);

            return ResponseEntity.ok("User registered successfully. Check your email for verification.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
        }
    }

    public ResponseEntity<String> verifyEmail(String token) {
        String email = jwtService.extractEmail(token);
        Optional<User> user = user_repo.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user1 = user.get();
        if (user1.getVerificationToken().equals(token)) {
            user1.setEmailVerified(true);
            user1.setVerificationToken(null);
            user_repo.save(user1);
            return ResponseEntity.ok("Email verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Token");
        }
    }

    public User authenticateAndGetToken(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String t = jwtService.generateToken(authRequest.getEmail());
            return user_Service.get_user(t, authRequest.getEmail(), authRequest.getPassword());
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }

    public ResponseEntity<?> validateToken(String token) {
        String email = jwtService.extractEmail(token);

        if (email != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtService.validateToken(token, userDetails)) {
                Optional<User> user = user_repo.findByEmail(email);

                if (user != null) {
                    // Create a custom response JSON object
                    User user1 = user.get();
                    UserResponse userResponse = new UserResponse(userDetails, user1);
                    return ResponseEntity.ok(userResponse);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Token");
        }
    }

    public List<Book> search(String search) {
        return book_repo
                .findByTitleIgnoreCaseContainingOrAuthorIgnoreCaseContainingOrCategoryIgnoreCaseContainingOrTranslatorIgnoreCaseContainingOrPublisherIgnoreCaseContaining(
                        search, search, search, search, search);
    }
}
