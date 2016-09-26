package core;

/**
 * Created by Jens on 26-Sep-16.
 */
public class RoleHelper {
    public static final int ROLE_ADMIN = 0;
    public static final int ROLE_DEFAULT = 1;

    public static boolean isAdmin(String role) {
        return role.equals("admin");
    }

}
