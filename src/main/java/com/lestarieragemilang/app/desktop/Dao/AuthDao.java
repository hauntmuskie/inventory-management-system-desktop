package com.lestarieragemilang.app.desktop.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;

/**
 * Auth Repositories
 */
public class AuthDao extends DatabaseConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(AuthDao.class);

  /**
   * Login Repository
   *
   * @param username
   * @param password
   * @return
   */
  private static final String LOGIN_QUERY = "SELECT a.password_hash FROM users u INNER JOIN auth a ON u.id = a.id WHERE u.username = ?";

  public boolean loginRepo(String username, String password) {
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(LOGIN_QUERY)) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          String hashedPassword = resultSet.getString("password_hash");
          return BCrypt.checkpw(password, hashedPassword);
        }
      }
    } catch (SQLException e) {
      logger.error("Error during login", e);
    }
    return false;
  }

  /**
   * Register Repository
   *
   * @param username
   * @param email
   * @param name
   * @param password
   * @return
   */
  private static final String REGISTER_QUERY = "INSERT INTO users (username, email, name) VALUES (?,?,?)";
  private static final String REGISTER_AUTH_QUERY = "INSERT INTO auth (id, password_hash, salt) VALUES (?,?,?)";

  public boolean registerRepo(String username, String email, String name, String password) throws SQLException {
    try (Connection connection = getConnection();
        PreparedStatement userStatement = connection.prepareStatement(REGISTER_QUERY,
            PreparedStatement.RETURN_GENERATED_KEYS);
        PreparedStatement authStatement = connection.prepareStatement(REGISTER_AUTH_QUERY)) {
      connection.setAutoCommit(false);

      userStatement.setString(1, username);
      userStatement.setString(2, email);
      userStatement.setString(3, name);
      userStatement.executeUpdate();

      try (ResultSet resultSet = userStatement.getGeneratedKeys()) {
        if (resultSet.next()) {
          int userId = resultSet.getInt(1);

          String salt = BCrypt.gensalt();
          String hashedPassword = BCrypt.hashpw(password, salt);

          authStatement.setInt(1, userId);
          authStatement.setString(2, hashedPassword);
          authStatement.setString(3, salt);
          authStatement.executeUpdate();

          connection.commit();
          return true;
        }
      }
    } catch (SQLException e) {
      logger.error("Error during registration", e);
    }
    return false;
  }

  public List<String> fetchProfiles() {
    List<String> profiles = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT email, username FROM users");
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        String email = resultSet.getString("email");
        String username = resultSet.getString("username");
        // Use "||" as a delimiter
        profiles.add(email + "||" + username);
      }
    } catch (SQLException e) {
      logger.error("Error during profile fetching", e);
    }
    return profiles;
  }

  /**
   * Delete Repository
   *
   * @param username
   * @return
   */
  private static final String DELETE_QUERY = "DELETE FROM users WHERE username = ?";

  public boolean deleteRepo(String username) throws SQLException {
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
      statement.setString(1, username);
      int rowsAffected = statement.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      logger.error("Error during deletion", e);
    }
    return false;
  }

}
