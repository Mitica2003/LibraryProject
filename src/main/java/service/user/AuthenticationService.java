package service.user;

import model.User;
import model.validator.Notification;

public interface AuthenticationService {
    Notification<Boolean> register (String username, String password);

    Notification<Boolean> registerEmployee (String username, String password);

    Notification<User> login (String username, String password);

    boolean deleteUser (User user);

    boolean logout (User user);
}
