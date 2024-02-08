package com.book_store.full.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book_store.full.data.AuthRequest;
import com.book_store.full.data.AuthResponse;
import com.book_store.full.data.Book;
import com.book_store.full.data.BookElasticsearch;
import com.book_store.full.data.User;
import com.book_store.full.data.UserResponse;
import com.book_store.full.repository.BookElasticsearchRepository;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.security.UserInfoUserDetailsService;
import com.book_store.full.validation.HomeServiceValidation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class HomeService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService user_Service;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Autowired
    Book_Repo book_repo;

    @Autowired
    User_Repo user_repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HomeServiceValidation home_validation;

    @Autowired
    BookElasticsearchRepository book_elastic_repo;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.api.key}")
    private String apiKey;

    public List<Book> home() {
        try {
            return book_repo.findAll();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Book> resentllyAdded() {
        try {
            List<Book> books = book_repo.findAll();

            if (books.size() > 4) {
                return books.subList(0, 4);
            }

            return books;
        } catch (Exception e) {
            System.err.println(e);
            return Collections.emptyList();
        }
    }

    public List<Book> topSelling() {
        try {
            List<Book> books = book_repo.findAll(Sort.by(Sort.Direction.DESC, "buyed"));

            if (books.size() > 4) {
                return books.subList(0, 4);
            }

            return books;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public ResponseEntity<String> addUser(User user) {
        try {
            String valid_user = home_validation.validateUser(user);

            if (valid_user != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valid_user);
            }

            String verificationToken = jwtService.generateToken(user.getEmail());

            user.setEmailVerified(false);
            user.setVerificationToken(verificationToken);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User savedUser = user_repo.save(user);
            String subject = "Verify Your Email";

            // if we use render site then use this
            String body = "Click the link to verify your email: https://bookstore-cs41.onrender.com/home/verifyemail?token="
                    + verificationToken;

            // if we use localhost then use this
            // String body = "Click the link to verify your
            // email:http://localhost:8080/home/verifyemail?token="+ verificationToken;
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

    public ResponseEntity<AuthResponse> authenticateAndGetToken(AuthRequest authRequest) {

        Optional<User> user = user_repo.findByEmail(authRequest.getEmail());
        if (user.isEmpty() || !user.get().isEmailVerified()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String access_token = jwtService.generateToken(authRequest.getEmail());
            String refresh_token = jwtService.generateRefreshToken(authRequest.getEmail());

            AuthResponse t = new AuthResponse(access_token, refresh_token);

            return ResponseEntity.ok(t);
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }

    public ResponseEntity<?> validateToken(String token) {
        try {
            String email = jwtService.extractEmail(token);
            if (email == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Token");
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (!jwtService.validateToken(token, userDetails)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("expired Token or Invalid");
            }

            Optional<User> user = user_repo.findByEmail(email);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            // Create a custom response JSON object
            User user1 = user.get();
            UserResponse userResponse = new UserResponse(userDetails, user1);

            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("expired Token or Invalid");
        }

    }

    public String refreshToken(String token) {
        String email = jwtService.extractEmail(token);
        if (email == null) {
            return null;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (!jwtService.validateToken(token, userDetails)) {
            return null;
        }

        return jwtService.generateToken(email);
    }

    public ResponseEntity<List<Book>> search(String search) {
        if (search == null || search.trim().isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // THE OLD WAY
        return ResponseEntity.ok(book_repo
                .findByTitleIgnoreCaseContainingOrAuthorIgnoreCaseContainingOrCategoryIgnoreCaseContainingOrTranslatorIgnoreCaseContainingOrPublisherIgnoreCaseContaining(
                        search, search, search, search, search));

        // THE NEW WAY
        // return
        // ResponseEntity.ok(book_elastic_repo.findByTitleOrAuthorOrCategoryOrTranslatorOrPublisher(search,
        // search,
        // search, search, search));
    }

    public String sendRequest(String prompt) {
        // https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyDopen6fiZzchbVRmOBMbLnrzZQtOLB16Y
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // this is the request body : "contents":[{"parts":[{"text":"hello"}]}]
        String body = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                String.class);

        return responseEntity.getBody();
    }
}
