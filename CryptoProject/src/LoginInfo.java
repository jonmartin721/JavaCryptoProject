import java.io.Serializable;
import java.util.HashMap;

class LoginInfo implements Serializable {


    private HashMap<String, String> usersAndPasswords = new HashMap<>();

    // Looks through the map and returns true if the user specified is an authenticated user
    // That is: if they are a user, and the password entered is correct
    boolean isAuthenticated(String username, String password) {
        return usersAndPasswords.containsKey(username) && usersAndPasswords.get(username).equals(password);
    }

    // Lets users add a username and password to the map
    void addUserAndPassword(String username, String password) {

        usersAndPasswords.put(username, password);


    }

    // Removes a username and password.
    boolean removeUserAndPassword(String key) {

        usersAndPasswords.remove(key);
        return true;
    }




}
