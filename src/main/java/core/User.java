package core;

import java.security.Principal;

/**
 * Created by Jens on 26-Sep-16.
 */
public class User implements Principal {
    private String name;
    private String role;

    public User(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
