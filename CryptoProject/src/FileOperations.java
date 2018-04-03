
/*
This static utility class handles saving and loading data from file. It will be done on the server side and sent to the clients.
Data will be serialized out to a file. Each user will have one wallet file. This also handles saving and loading login information.

Exceptions are handled here, so as to be easy to consume and process.
 */

import java.io.*;

class FileOperations {

    // Saves the wallet passed into it as a serialized object
    static boolean saveWallet(Wallet wallet) {

        String fileName = wallet.getUsername() + "_wallet.ser";

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName, false)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream)) {
                oos.writeObject(wallet);
            } catch (Exception ex) {
                printException();
                ex.printStackTrace();
                return false;
            }

        } catch (IOException e) {
            printIoException();
            e.printStackTrace();
        }

        return true;

    }

    // Loads a wallet from file. To reach this method in normal flow, it SHOULD exist.
    static Wallet loadWallet(String username) {

        String fileName = username + "_wallet.ser";

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {

            // Read and return the specific wallet with the username
            return (Wallet) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            printIoException();
            e.printStackTrace();
        }

        return null;

    }

    // This method saves login information to a "loginInfo.ser"
    static boolean saveLoginInfo(LoginInfo loginInfo) {

        try (FileOutputStream fileOutputStream = new FileOutputStream("loginInfo.ser", false)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream)) {
                oos.writeObject(loginInfo);
            } catch (Exception ex) {
                printException();
                ex.printStackTrace();
                return false;
            }

        } catch (IOException e) {
            printIoException();
            e.printStackTrace();
        }

        return true;
    }

    // This method loads login information (usernames and passwords) from a serialized LoginInfo object
    static LoginInfo loadLoginInfo() {

        LoginInfo loginInfo = null;

        try {

            FileInputStream file = new FileInputStream("loginInfo.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            loginInfo = (LoginInfo) in.readObject();
            in.close();
            file.close();

        } catch (IOException | ClassNotFoundException ex) {
            printIoException();
            ex.printStackTrace();

        }


        return loginInfo;

    }

    static boolean checkLoginInfoExists() {

        String filePathString = "loginInfo.ser";
        File f = new File(filePathString);
        return f.exists() && !f.isDirectory();

    }

    private static void printIoException() {
        System.out.println("FileOperations I/O error encountered while trying to read/write:");
    }

    private static void printException() {
        System.out.println("FileOperations Exception:");
    }

}
