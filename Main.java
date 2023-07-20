import java.util.*;
import java.io.*;

public class Main {

  public static void main(String[] args) {
    Scanner keyboardScanner = new Scanner(System.in);
    String inputFileName;

    if (args.length == 0) {
      System.out.print("input file name: ");
      inputFileName = keyboardScanner.next();
    } else {
      inputFileName = args[0];
    }

    File file = new File(inputFileName);

    while (!file.exists()) {
      System.out.print("file doesn't exist; enter new file name: ");
      inputFileName = keyboardScanner.next();
      file = new File(inputFileName);

      Phonebook phonebook = new Phonebook(file);

      int choice;

      do {
        printMenu();
        choice = getChoice(keyboardScanner);

        switch (choice) {
          case 0:
            break;
          case 1:
            processChoice1(phonebook, keyboardScanner);
            break;
          case 2:
            processChoice2(phonebook, keyboardScanner);
            break;
          case 3:
            processChoice3(phonebook, keyboardScanner);
            break;
          case 4:
            processChoice4(phonebook, keyboardScanner);
            break;
          case 5:
            processChoice5(phonebook);
            break;
          case 6:
            processChoice6(phonebook, keyboardScanner);
            break;
          case 7:
            processChoice7(phonebook, keyboardScanner);
            break;
          default:
            System.out.println("Invalid choice. Try again.");
        }
        System.out.println();
      } while (choice != 0);

      System.out.print("\nFile to print to: ");
      String outputFileName = keyboardScanner.next();
      phonebook.printToFile(outputFileName);
    }
  }

  public static int getChoice(Scanner keyboard) {
    try {
      System.out.print("Enter choice (0-7): ");
      int choice = keyboard.nextInt();
      return choice;
    } catch (InputMismatchException ex) {
      keyboard.nextLine();
      return -1;
    }
  }

  public static void printMenu() {
    String menu = "\n" + "OPTIONS:\n" + "0. Quit\n" + "1. Add an entry\n"
        + "2. Add a phone number to an existing entry\n" + "3. Delete an entry\n" + "4. Delete a number from an entry\n"
        + "5. Display all entries on the screen\n" + "6. Lookup: get all phone numbers for a name\n"
        + "7. Reverse lookup: get all names for a phone number";
    System.out.println(menu);
  }

  public static void processChoice1(Phonebook phonebook, Scanner keyboard) {
    System.out.println("Add a new entry.");
    System.out.print("first name: ");
    String first = keyboard.next();
    System.out.print("last name: ");
    String last = keyboard.next();
    Name name = new Name(first, last);
    PhonebookEntry phonebookEntry = null;
    try {
      phonebookEntry = new PhonebookEntry(name, new ArrayList<>());
    } catch (DuplicatePhoneNumberException ex) {
    }
    System.out.print("add a phone number? (y/n)");

    char charChoice = keyboard.next().charAt(0);
    while (charChoice == 'y') {
      try {
        System.out.print("type of phone number: ");
        String type = keyboard.next();
        System.out.print("the actual phone number (nnn-nnn-nnnn): ");
        String number = keyboard.next();
        PhoneNumber phoneNumber = new PhoneNumber(type, number);
        boolean added = phonebookEntry.addPhoneNumber(phoneNumber);
        if (!added) {
          System.out.println("Duplicate phone number. Try again.");
        }
      } catch (InvalidNumberException ex) {
        System.out.println(ex);
        System.out.println("Try again.");
      }
      System.out.print("add a phone number? (y/n) ");
      charChoice = keyboard.next().charAt(0);
    }

    boolean added = phonebook.addEntry(phonebookEntry);
    if (!added) {
      System.out.println("Duplicate name; entry not added. Try again.");
    } else {
      System.out.println("Added new entry: " + phonebookEntry);
    }
  }

  public static void processChoice2(Phonebook phonebook, Scanner keyboard) {
    try {
      System.out.println("Add a number to an entry.");
      System.out.print("first name: ");
      String first = keyboard.next();
      System.out.print("last name: ");
      String last = keyboard.next();
      Name name = new Name(first, last);
      System.out.print("type of phone number: ");
      String type = keyboard.next();
      System.out.print("the actual phone number (nnn-nnn-nnnn): ");
      String number = keyboard.next();
      PhoneNumber phoneNumber = new PhoneNumber(type, number);
      boolean added = phonebook.addNumberToEntry(name, phoneNumber);
      if (!added) {
        System.out.println("Duplicate number. Try again.");
      } else {
        System.out.println("Phone number " + phoneNumber + " added for name " + name);
      }
    } catch (Exception ex) {
      System.out.println(ex);
      System.out.println("Try again.");
    }
  }

  public static void processChoice3(Phonebook phonebook, Scanner keyboard) {
    System.out.println("Delete an entry.");
    System.out.print("first name: ");
    String first = keyboard.next();
    System.out.print("last name: ");
    String last = keyboard.next();
    Name name = new Name(first, last);
    boolean deleted = phonebook.deleteEntry(name);
    if (!deleted) {
      System.out.println("Deletion unsuccessful, as name " + name + " wasn't found.");
    } else {
      System.out.println("Entry for name " + name + " deleted");
    }
  }

  public static void processChoice4(Phonebook phonebook, Scanner keyboard) {
    try {
      System.out.println("Delete a phone number from an entry.");
      System.out.print("first name: ");
      String first = keyboard.next();
      System.out.print("last name: ");
      String last = keyboard.next();
      Name name = new Name(first, last);
      System.out.print("type of phone number: ");
      String type = keyboard.next();
      System.out.print("the actual phone number (nnn-nnn-nnnn): ");
      String number = keyboard.next();
      PhoneNumber phoneNumber = new PhoneNumber(type, number);
      phonebook.deletePhoneNumber(name, phoneNumber);
      System.out.println("Phone number " + phoneNumber + " deleted for name " + name);
    } catch (Exception ex) {
      System.out.println(ex);
      System.out.println("Try again.");
    }

  }

  public static void processChoice5(Phonebook phonebook) {
    System.out.println("Displaying the entire phonebook.");
    System.out.println(phonebook);

  }

  public static void processChoice6(Phonebook phonebook, Scanner keyboard) {
    try {
      System.out.println("Display all phone numbers for a name.");
      System.out.print("first name: ");
      String first = keyboard.next();
      System.out.print("last name: ");
      String last = keyboard.next();
      Name name = new Name(first, last);
      ArrayList<PhoneNumber> phoneNumbers = phonebook.lookup(name);
      System.out.println("Phone numbers: " + phoneNumbers);
    } catch (NonexistentNameException ex) {
      System.out.println(ex);
      System.out.println("Try again.");
    }
  }

  public static void processChoice7(Phonebook phonebook, Scanner keyboard) {
    try {
      System.out.println("Reverse lookup: get all names for a given phone number");
      System.out.print("type of phone number: ");
      String type = keyboard.next();
      System.out.print("the actual phone number (nnn-nnn-nnnn): ");
      String number = keyboard.next();
      PhoneNumber phoneNumber = new PhoneNumber(type, number);
      ArrayList<Name> names = phonebook.reverseLookup(phoneNumber);
      System.out.println("Names: " + names);
    } catch (InvalidNumberException ex) {
      System.out.println(ex);
      System.out.println("Try again.");
    }
  }
}