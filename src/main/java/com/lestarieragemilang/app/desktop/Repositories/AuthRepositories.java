package com.lestarieragemilang.app.desktop.Repositories;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;

/**
 * Auth Repositories
 */
public class AuthRepositories extends DatabaseConfiguration {
  /**
   * Login Repository
   * 
   * @param username
   * @param password
   * @return
   */
  protected boolean loginRepo(String username, String password) {
    Connection connection = getConnection();
    String query = ("SELECT * FROM auth WHERE username = ?");

    try {
      PreparedStatement statement = connection
          .prepareStatement(query);

      statement.setString(1, username);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        String hashedPassword = resultSet.getString("password");

        // Use BCrypt to check if the provided password matches the hashed password in
        // the database
        if (BCrypt.checkpw(password, hashedPassword)) {
          return true;
        }
      }

      return false;

    } catch (SQLException exception) {
      exception.printStackTrace();
      return false;
    }
  }

  // Manual testing
  public static void main(String[] args) {
    AuthRepositories repository = new AuthRepositories();
    boolean result = repository.loginRepo("", "");

    System.out.println(result);
  }
}
