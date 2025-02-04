/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author 2417011
 */
import org.junit.jupiter.api.*;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import tpvo.DatabaseManager;

class DatabaseManagerTest {

    private DatabaseManager dbManager;

    @BeforeEach
    void setUp() {
        dbManager = DatabaseManager.getInstance();
    }

    @Test
    void testSingletonInstance() {
        DatabaseManager anotherInstance = DatabaseManager.getInstance();
        assertSame(dbManager, anotherInstance, "DatabaseManager doit être un singleton.");
    }

    @Test
    void testGetConnection() {
        Connection connection = dbManager.getConnection();
        assertNotNull(connection, "La connexion ne doit pas être nulle.");
        assertDoesNotThrow(() -> connection.isValid(2), "La connexion doit être valide.");
    }

    @Test
    void testReconnectAfterClose() {
        Connection connection = dbManager.getConnection();
        assertNotNull(connection, "La connexion initiale ne doit pas être nulle.");
        dbManager.closeConnection();
        Connection newConnection = dbManager.getConnection();
        assertNotNull(newConnection, "La connexion doit pouvoir être rétablie après fermeture.");
        assertDoesNotThrow(() -> newConnection.isValid(2), "La nouvelle connexion doit être valide.");
    }

    @Test
    void testCloseConnection() {
        Connection connection = dbManager.getConnection();
        assertNotNull(connection, "La connexion initiale ne doit pas être nulle.");
        dbManager.closeConnection();
        assertDoesNotThrow(() -> connection.isClosed(), "La connexion doit être fermée sans exception.");
    }
}
