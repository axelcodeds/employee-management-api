package dev.axeldiego.ems.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JWT Utility Tests")
class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // Set the secret key using reflection
        ReflectionTestUtils.setField(jwtUtil, "secret", "mySecretKeyForJwtTokenGenerationThatIsLongEnoughForHS256Algorithm");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86400000L);
    }

    @Test
    @DisplayName("Should generate valid JWT token without role")
    void testGenerateToken_WithoutRole() {
        String token = jwtUtil.generateToken("testuser");

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    @DisplayName("Should generate valid JWT token with role")
    void testGenerateToken_WithRole() {
        String token = jwtUtil.generateToken("testuser", "ADMIN");

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    @DisplayName("Should extract username from token")
    void testExtractUsername() {
        String token = jwtUtil.generateToken("testuser");

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals("testuser", extractedUsername);
    }

    @Test
    @DisplayName("Should extract role from token")
    void testExtractRole() {
        String token = jwtUtil.generateToken("testuser", "ADMIN");

        String extractedRole = jwtUtil.extractRole(token);

        assertEquals("ADMIN", extractedRole);
    }

    @Test
    @DisplayName("Should return USER as default role when role not present in token")
    void testExtractRole_DefaultRole() {
        String token = jwtUtil.generateToken("testuser");

        String extractedRole = jwtUtil.extractRole(token);

        assertEquals("USER", extractedRole);
    }

    @Test
    @DisplayName("Should validate valid token")
    void testIsTokenValid_ValidToken() {
        String token = jwtUtil.generateToken("testuser", "ADMIN");

        boolean isValid = jwtUtil.isTokenValid(token);

        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should reject invalid token")
    void testIsTokenValid_InvalidToken() {
        String invalidToken = "invalid.token.here";

        boolean isValid = jwtUtil.isTokenValid(invalidToken);

        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should reject empty token")
    void testIsTokenValid_EmptyToken() {
        boolean isValid = jwtUtil.isTokenValid("");

        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should generate different tokens for different usernames")
    void testGenerateToken_DifferentUsernames() {
        String token1 = jwtUtil.generateToken("user1");
        String token2 = jwtUtil.generateToken("user2");

        assertNotEquals(token1, token2);
        assertEquals("user1", jwtUtil.extractUsername(token1));
        assertEquals("user2", jwtUtil.extractUsername(token2));
    }

    @Test
    @DisplayName("Should generate different tokens for different roles")
    void testGenerateToken_DifferentRoles() {
        String adminToken = jwtUtil.generateToken("testuser", "ADMIN");
        String userToken = jwtUtil.generateToken("testuser", "USER");

        assertEquals("ADMIN", jwtUtil.extractRole(adminToken));
        assertEquals("USER", jwtUtil.extractRole(userToken));
    }

    @Test
    @DisplayName("Should maintain token structure consistency")
    void testTokenStructureConsistency() {
        String token = jwtUtil.generateToken("testuser", "ADMIN");
        String[] parts = token.split("\\.");

        assertEquals(3, parts.length);
        assertTrue(parts[0].length() > 0);
        assertTrue(parts[1].length() > 0);
        assertTrue(parts[2].length() > 0);
    }

    @Test
    @DisplayName("Should handle special characters in username")
    void testGenerateToken_SpecialCharactersInUsername() {
        String specialUsername = "test-user.123@example.com";
        String token = jwtUtil.generateToken(specialUsername);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(specialUsername, extractedUsername);
    }
}



