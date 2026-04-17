package com.webvul.shop.service;

import com.webvul.shop.model.User;
import com.webvul.shop.repo.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.UUID;

public final class AuthService {
  private final UserRepository users;

  public AuthService() {
    this.users = new UserRepository();
  }

  public Optional<User> authenticate(String email, String password) {
    return users.findByEmail(email)
      .filter(u -> BCrypt.checkpw(password, u.passwordHash()));
  }

  public User registerStudent(String email, String password) {
    return register(email, password, "student");
  }

  // [A04-MassAssignment] role nhận thẳng từ user input, không validate
  public User register(String email, String password, String role) {
    String hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
    User u = new User(UUID.randomUUID().toString(), email, hash, role, null);
    users.create(u);
    return u;
  }
}

