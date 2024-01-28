package com.book_store.full.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.book_store.full.data.AuthRequest;
import com.book_store.full.data.Book;
import com.book_store.full.data.User;
import com.book_store.full.data.UserResponse;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.security.UserInfoUserDetailsService;
import com.book_store.full.validation.Home_Service_validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@SpringJUnitConfig
public class Home_ServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private EmailService emailService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private User_Service user_Service;

    @Mock
    private UserInfoUserDetailsService userDetailsService;

    @Mock
    Book_Repo book_repo;

    @Mock
    User_Repo user_repo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private Home_Service home_Service;

    @Mock
    private Home_Service_validation home_validation;

    @Test
    void testHome() {

        List<Book> allBooks = Arrays.asList(new Book(), new Book(), new Book(), new Book());
        when(book_repo.findAll()).thenReturn(allBooks);

        List<Book> books = home_Service.home();

        verify(book_repo, times(1)).findAll();
        assert books.size() == 4;
    }

    @Test
    void testResentllyadded() {

        List<Book> allBooks = Arrays.asList(new Book(), new Book(), new Book(), new Book());
        when(book_repo.findAll()).thenReturn(allBooks);

        List<Book> books = home_Service.resentllyadded();

        verify(book_repo, times(1)).findAll();
        assert books.size() == 4;
    }

    @Test
    void testTopselling() {

        List<Book> allBooks = Arrays.asList(new Book(), new Book(), new Book(), new Book());

        when(book_repo.findAll(Sort.by(Sort.Direction.DESC, "buyed"))).thenReturn(allBooks);

        List<Book> books = home_Service.topselling();

        verify(book_repo, times(1)).findAll(Sort.by(Sort.Direction.DESC, "buyed"));

        assert books.size() == 4;

    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setEmail("mossdftafa19500mahmoud@gmail.com");
        user.setPassword("Mostafa*10*");
        user.setPhone("01555331990");
        user.setRoles("ROLE_ADMIN");
        user.setName("mahmoud ismail");

        when(jwtService.generateToken(anyString())).thenReturn("verificationToken");
        when(user_repo.save(any(User.class))).thenReturn(user);
        when(home_validation.validate_user(any(User.class))).thenReturn(null);
        ResponseEntity<String> result = home_Service.addUser(user);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User registered successfully. Check your email for verification.", result.getBody());
    }

    @Test
    void testVerifyEmail() {

        String token = "verificationToken";
        User user = new User();
        user.setEmailVerified(false);
        user.setVerificationToken("verificationToken");
        Optional<User> optionalUser = Optional.of(user);
        when(jwtService.extractEmail(token)).thenReturn("test@example.com");
        when(user_repo.findByEmail("test@example.com")).thenReturn(optionalUser);

        ResponseEntity<String> result = home_Service.verifyEmail(token);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Email verified successfully", result.getBody());
        assertTrue(user.isEmailVerified());
        assertNull(user.getVerificationToken());
    }

    @Test
    void testAuthenticateAndGetToken() {

        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(anyString())).thenReturn("token");

        ResponseEntity<User> token = home_Service.authenticateAndGetToken(authRequest);

        assertEquals(HttpStatus.OK, token.getStatusCode());
        assertNotNull(token.getBody());
    }

    @Test
    void testValidateToken() {

        String token = "token";
        when(jwtService.extractEmail(token)).thenReturn("test@example.com");
        when(jwtService.validateToken(eq(token), any(UserDetails.class))).thenReturn(true);
        Optional<User> optionalUser = Optional.of(new User());
        when(user_repo.findByEmail("test@example.com")).thenReturn(optionalUser);
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(mock(UserDetails.class));

        ResponseEntity<?> result = home_Service.validateToken(token);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody() instanceof UserResponse);
    }

    @Test
    void testSearch() {

        Book book1 = new Book();
        book1.setTitle("keyword");

        Book book2 = new Book();
        book2.setAuthor("keyword");

        List<Book> expectedBooks = Arrays.asList(book1, book2);
        when(book_repo
                .findByTitleIgnoreCaseContainingOrAuthorIgnoreCaseContainingOrCategoryIgnoreCaseContainingOrTranslatorIgnoreCaseContainingOrPublisherIgnoreCaseContaining(
                        anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(expectedBooks);

        ResponseEntity<List<Book>> result = home_Service.search("keyword");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedBooks, result.getBody());
    }

}
