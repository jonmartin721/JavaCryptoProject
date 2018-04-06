/*
This class contains useful console tools for this project. We need tools because we want this project
to be easy to use and the interface to be uncluttered.
*/

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbase.v2.CoinbaseExchange;
import org.knowm.xchange.coinbase.v2.dto.CoinbasePrice;
import org.knowm.xchange.coinbase.v2.service.CoinbaseMarketDataService;
import org.knowm.xchange.currency.Currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;


class ConsoleUtils {

    //TODO reorder methods here, there are many are they aren't organized well
    // Adds a nice separator to different activities, doesn't clear the screen
    private static void lineBreak() {

        System.out.println("\n-----------------------------------------------------------");
        System.out.println("///////////////////////////////////////////////////////////");
        System.out.println("-----------------------------------------------------------\n");

    }

    // After authentication or account creation, the program comes here.
    private static void menu(Wallet wallet) {

        //creates objects and variables for menu system
        int selection;
        Scanner keyboard = new Scanner(System.in);

        //outputs the menu options
        ConsoleUtils.lineBreak();
        ConsoleUtils.title();
        System.out.println("\nWelcome " + wallet.getFirstName() + ", or should I say: " + wallet.getUsername());
        System.out.println("\nChoose an option below by typing the number:");
        System.out.println("1) View Wallet");
        System.out.println("2) Browse Currencies and Trade");
        System.out.println("3) Goals and Performance");
        System.out.println("4) Deposit USD");
        System.out.println("5) Withdraw USD");
        System.out.println("6) Change Password");
        System.out.println("7) Help");
        System.out.println("0) Save & Exit");
        System.out.println("\nUSD Balance: " + outputMoneyFormat(wallet.getUSDBalance()));

        //captures the user selection
        System.out.print("\nSelection? ");

        selection = keyboard.nextInt();

        switch (selection) {

            case 1:
                ConsoleUtils.lineBreak();
                viewWallet(wallet);
                break;

            case 2:
                ConsoleUtils.lineBreak();
                browseCryptocurrencies(wallet);
                break;

            case 3:
                ConsoleUtils.lineBreak();
                goalsAndPerformance(wallet);
                break;

            case 4:
                ConsoleUtils.lineBreak();
                depositUSD(wallet);
                break;

            case 5:
                ConsoleUtils.lineBreak();
                withdrawUSD(wallet);
                break;

            case 6:
                ConsoleUtils.lineBreak();
                changePassword(wallet);
                break;

            case 7:
                ConsoleUtils.lineBreak();
                help(wallet);
                break;

            case 0:
                ConsoleUtils.lineBreak();
                System.out.println("Saving wallet to file...");

                //saves the wallet
                if (FileOperations.saveWallet(wallet)) {
                    System.out.println("Wallet saved!");
                } else {
                    System.out.println("Wallet could not be saved.");
                    System.exit(0);
                }

                System.out.println("Exiting program...");
                System.exit(0);
                break;

            default:
                System.out.println("\nEnter a valid choice!");
                System.exit(0);
                break;

        }


    }

    private static void viewWallet(Wallet wallet) {

    }

    //TODO implement password changing
    // Changes a user's password.
    private static boolean changePassword(Wallet wallet) {
        return false;
    }

    // Returns a properly formatted currency string depending on locale.
    private static String outputMoneyFormat(BigDecimal n) {
        return NumberFormat.getCurrencyInstance().format(n);
    }

    // Withdraws USD from the wallet
    private static void withdrawUSD(Wallet wallet) {

        /*System.out.println("######################");
        System.out.println("#       Withdraw     #");
        System.out.println("######################");*/

        actionMessageBox("Withdraw");

        BigDecimal previousBalance = wallet.getUSDBalance();
        System.out.println("\nUSD Balance: " + wallet.getUSDBalance());
        System.out.print("Enter amount to withdraw: ");

        Scanner keyboard = new Scanner(System.in);
        BigDecimal amountWithdraw = keyboard.nextBigDecimal();
        if (wallet.withdraw(amountWithdraw)) {
            System.out.println("Amount withdrawn successfully!");
            System.out.println("\nBefore: " + outputMoneyFormat(previousBalance));
            System.out.println("Withdrawn: " + outputMoneyFormat(amountWithdraw));
            System.out.println("After: " + outputMoneyFormat(wallet.getUSDBalance()));
            promptEnterKey();
        } else {
            System.out.println("Amount not withdrawn, incorrect amount specified. Try again later.");
        }

        //saving the wallet for safety
        FileOperations.saveWallet(wallet);
        menu(wallet);
    }

    // Deposits USD to the wallet
    private static void depositUSD(Wallet wallet) {

        /*System.out.println("######################");
        System.out.println("#      Deposit       #");
        System.out.println("######################");*/

        actionMessageBox("Deposit");

        BigDecimal previousBalance = wallet.getUSDBalance();
        System.out.println("\nUSD Balance: " + wallet.getUSDBalance());
        System.out.println("Enter amount to deposit: ");

        Scanner keyboard = new Scanner(System.in);
        BigDecimal amountDeposit = keyboard.nextBigDecimal();
        if (wallet.deposit(amountDeposit)) {
            System.out.println("Amount deposited successfully!");
            System.out.println("\nBefore: " + outputMoneyFormat(previousBalance));
            System.out.println("Deposited: " + outputMoneyFormat(amountDeposit));
            System.out.println("After: " + outputMoneyFormat(wallet.getUSDBalance()));
            promptEnterKey();
        } else {
            System.out.println("Amount not deposited, incorrect amount specified. Try again later.");
        }

        //saving the wallet for safety
        FileOperations.saveWallet(wallet);
        menu(wallet);
    }

    //TODO Implement a menu for people to view/edit goals and view performance.
    // This method lets users view and set goals as well as view performance.
    private static void goalsAndPerformance(Wallet wallet) {

        // Doing a nested switch statement to break out their Set goal/View goal/View performance options
        // 'GAP' represents the name of this method Goals And Performance
        Scanner keyboardGAP = new Scanner(System.in);
        int selectionGAP;
        System.out.println("Select your option below");
        System.out.println("1) Set your goals you'd like to achieve");
        System.out.println("2) View your goals as they are presently");
        System.out.println("3) View your performance");

        selectionGAP = keyboardGAP.nextInt();

        switch (selectionGAP) {

            case 1:
                // User is setting their goals here
                ConsoleUtils.lineBreak();
                System.out.println("Let's review some goals you'd like to set.");
                System.out.println("Enter the percentage of return you'd like to achieve:  ");

                Scanner keyboardGAP1 = new Scanner(System.in);
                float goal = keyboardGAP1.nextFloat();
                System.out.println("You input: " + goal + "%");
                System.out.println("To confirm, select 1-yes  0-no");
                int confirmInput = keyboardGAP1.nextInt();

                // Confirm user has input the amount of their goal
                while (confirmInput == 0) {
                    System.out.println("Re-enter the percentage of return you'd like to achieve:  ");
                    goal = keyboardGAP.nextFloat();
                    System.out.println("You input: " + goal + "%");
                    System.out.println("To confirm, select 1-yes  0-no");
                    confirmInput = keyboardGAP.nextInt();
                }
                // Basically typecasting user's goal to BigDecimal so it can be passed into setGoal() method in Wallet class
                BigDecimal confirmedUsersGoal = BigDecimal.valueOf(goal);
                wallet.setGoal(confirmedUsersGoal);
                break;

            case 2:
                // User is viewing the goals they set.
                ConsoleUtils.lineBreak();
                System.out.println("Let's view the goals you set.");
                System.out.println("These are your holdings so far:  " + wallet.getHoldings());
                // JON- I chose getHoldings() because it returns an ArrayList of the Cryptocurriences
                // which I'm thinking has all the aggregate information they are looking for?
                break;

            case 3:
                // User is viewing their performance
                ConsoleUtils.lineBreak();
                System.out.println("Let's view the your performance.");
                System.out.println("These are your holdings so far:  ");
                wallet.showTrades();
                break;

            default:
                System.out.println("\nExiting program...");
                System.exit(0);
                break;

        }


    }

    // Outputs the title and version of the program.
    private static void title() {
        System.out.println("Virtual Cryptocurrency Wallet and Trading v0.20");
    }

    // Uses Coinbase exchange to output information
    private static void browseCryptocurrencies(Wallet wallet) {


        System.out.println("######################");
        System.out.println("#   View and Trade   #");
        System.out.println("######################");

        System.out.println("\nThe information below is from the Coinbase exchange, a widely trusted exchange. It may change very quickly.");
        System.out.println("There are MANY currencies, not just cryptocurrencies, organized by symbol.");
        System.out.println("Type the symbol to " +
                "trade or see more info about it, 'r' to reload all data, or 'q' to return to main menu.");

        //Init XChange resources w/ Coinbase
        Exchange coinbaseExchange =
                ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
        CoinbaseMarketDataService marketDataService =
                (CoinbaseMarketDataService) coinbaseExchange.getMarketDataService();

        //Here we have a list of popular cryptos
        ArrayList<String> cryptoList = new ArrayList<>();
        cryptoList.add("BTC");
        cryptoList.add("ETH");
        cryptoList.add("LTC");
        cryptoList.add("BCH");

        //setup table header
        String leftAlignFormat = "| %-20s     | %-6s     | %-13s |%n";
        System.out.format("\nExchange Rates:\n");
        System.out.format("+--------------------------+------------+---------------+%n");
        System.out.format("| Name                     | Symbol     | Value         |%n");
        System.out.format("+--------------------------+------------+---------------+%n");


        for (String aCryptoList : cryptoList) {
            Currency thisCurrency = new Currency(aCryptoList);

            //loop exchange data
            try {
                CoinbasePrice spotRate = marketDataService.getCoinbaseSpotRate(thisCurrency, Currency.USD);
                System.out.format(leftAlignFormat, thisCurrency.getDisplayName(), thisCurrency.getSymbol(), spotRate);
            } catch (IOException e) {
                System.out.println("Error: couldn't get info for this exchange.");
                e.printStackTrace();
            }
        }

        //ending line
        System.out.format("+--------------------------+------------+---------------+%n");

        System.out.println("Query: ");
        Scanner keyboard = new Scanner(System.in);
        String query = keyboard.next();

        //TODO process queries here
        //need to process queries here and have it loop until they press q


        System.out.println("\n");
        promptEnterKey();
        menu(wallet);

    }

    // Information about the program and cryptocurrencies
    private static void help(Wallet wallet) {

        lineBreak();
        title();
        /*System.out.println("######################");
        System.out.println("#        Help        #");
        System.out.println("######################");*/

        actionMessageBox("Help");

        System.out.println("\nThis application is a VIRTUAL trading application that is both a proof of concept, and " +
                "a working trade application. This application was created for our Java class, but can eventually be" +
                " adapted to perform a wider variety of tasks.");
        System.out.println("\nResources used:");
        System.out.println("- XChange");
        System.out.println("- Coinbase Exchange API");
        System.out.println("- Lots of Google!");
        System.out.println("\n###Group members###" +
                "\nJonathan Martin - Chief Programmer" +
                "\nAmee Stevenson - All Purpose Role" +
                "\nBhagyalakshmi Muthucumar - Documentation and Implementation");


        //saving the wallet for safety
        FileOperations.saveWallet(wallet);
        promptEnterKey();
        menu(wallet);

    }

    // This makes the method continue when enter is pressed.
    private static void promptEnterKey() {

        System.out.print("Press enter to continue...");
        Scanner enterKey = new Scanner(System.in);
        enterKey.nextLine();
    }

    // This method is called by menu before showing the menu to make sure the user has an account (viewWallet)
    // If not, they can create it here.
    // This is where Driver points to.
    static void launchScreen() {


        title();

        //if the loginInfo.ser file doesn't exist, create a blank one, save it, and send user to account creation
        if (!FileOperations.checkLoginInfoExists()) {

            LoginInfo loginInfo = new LoginInfo();
            FileOperations.saveLoginInfo(loginInfo);
            createNewAccount();
        }


        Scanner keyboard = new Scanner(System.in);
        System.out.println("1) Login");
        System.out.println("2) Create Wallet");

        int response = keyboard.nextInt();

        //Input validation
        while (response != 1 && response != 2) {

            lineBreak();
            title();
            System.out.println("Invalid response!");
            System.out.println("1) Login");
            System.out.println("2) Create Wallet");

            response = keyboard.nextInt();
        }

        //If they pass input validation, they will be directed to the next area.

        // Send launchScreen to the right place depending on their input
        if (response == 1) {
            logIn();
        } else {
            createNewAccount();
        }

    }

    // This method creates a new viewWallet
    private static void createNewAccount() {

        Scanner keyboard = new Scanner(System.in);

        // Gather information about the viewWallet
        lineBreak();
        System.out.println("Let's create a Wallet for you.");
        System.out.println("Please enter the following information:");
        System.out.print("First Name: ");
        String firstName = keyboard.next();
        System.out.print("Last Name: ");
        String lastName = keyboard.next();
        System.out.println("\nThank you. Now enter a username and password; this will be what you use to login in the future.");
        System.out.print("Username: ");
        String username = keyboard.next();
        System.out.print("Password: ");
        String password = keyboard.next();

        // Add the login pair to the LoginInfo object
        LoginInfo loginInfo = FileOperations.loadLoginInfo();
        loginInfo.addUserAndPassword(username, password);

        // Save the LoginInfo object
        if (FileOperations.saveLoginInfo(loginInfo)) {
            System.out.println("Login information saved.");
        } else {
            System.out.println("Login information could not be saved.");
            System.exit(0);
        }

        // Create the viewWallet and send the user to the menu
        Wallet wallet = new Wallet(firstName, lastName, username);
        System.out.println("\nThanks " + firstName + ", wallet created!");
        System.out.println("You can set goals, trade, and browse currencies at the main menu. Going there now..");
        promptEnterKey();


        menu(wallet);
    }

    // This class handles logging in and is run before the menu is displayed.
    private static void logIn() {


        lineBreak();
        int loginAttempts = 1;
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Username: ");
        String username = keyboard.next();
        System.out.print("Password: ");
        String password = keyboard.next();

        loginAttempts++;

        // Load login info
        LoginInfo loginInfo = FileOperations.loadLoginInfo();

        // Not authenticated
        while (!loginInfo.isAuthenticated(username, password)) {

            if (loginAttempts > 3) {

                // If they enter a wrong combo 3 times, kick them out
                System.out.println("Too many attempts, exiting program.");
                System.exit(0);
            }

            System.out.println("Not authenticated, try again.");
            System.out.println("Attempt " + loginAttempts + " of 3.");
            System.out.print("Username: ");
            username = keyboard.next();
            System.out.print("Password: ");
            password = keyboard.next();
            loginAttempts++;
        }

        // Authenticated
        System.out.println("Authenticated!");
        System.out.println("Going to the menu now...");
        promptEnterKey();


        //Load the wallet matching their username
        Wallet wallet = null;
        try {
            wallet = FileOperations.loadWallet(username);
        } catch (IOException | ClassNotFoundException e) {
            FileOperations.printException();
            e.printStackTrace();
            System.out.println("Can't find a wallet file, sending you to create a new account...");
            createNewAccount();
        }


        if (wallet != null) {
            menu(wallet);
        } else {
            System.out.println("Could not load wallet from username: " + username);
        }


    }

    private static void actionMessageBox(String d){

        int responseLength1, responseLength2, numOfSpaceOnEachSide,leftSide, rightSide, totalLength;
        responseLength1 = d.length();

        responseLength2 = responseLength1 + 18;
        numOfSpaceOnEachSide = ((responseLength2-responseLength1)/2);

        leftSide =numOfSpaceOnEachSide + responseLength1;
        //System.out.println("The length of your response is:  " + leftSide);

        String space1 = String.format("#" + "%"+ leftSide +"s", d);
        rightSide = leftSide;
        rightSide = ((rightSide/5)*2);
        String space2 = String.format("%" + rightSide + "s", "#");
        String space3 = space1 + space2;
        totalLength = space3.length();

        // Here is the formatted output
        for(int x = 0; x < totalLength; x++){
            System.out.print("#");
        }
        System.out.println();
        System.out.println(space3);
        for(int x = 0; x < totalLength; x++){
            System.out.print("#");
        }
        System.out.println();
    }
}

