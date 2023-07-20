import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Phonebook {

  private ArrayList<PhonebookEntry> phonebookEntries;

  public Phonebook(File file) {
    phonebookEntries = new ArrayList<>();
    try {
      Scanner sc = new Scanner(file);
      int lineNumber = 1;

      while (sc.hasNext()) {
        try {
          String line = sc.nextLine();
          String[] tokens = line.split(" ");
          if (tokens.length < 2) {
            throw new InvalidLineException(tokens);
          }
          String first = tokens[0];
          String last = tokens[1];
          Name name = new Name(first, last);

          for (PhonebookEntry entry : phonebookEntries) {
            if (entry.getName().equals(name)) {
              throw new DuplicateNameException(name);
            }
          }
          ArrayList<PhoneNumber> emptyList = new ArrayList<>();
          PhonebookEntry phonebookEntry = new PhonebookEntry(name, emptyList);
          if (tokens.length > 2) {
            int numPhoneNumbers = Integer.parseInt(tokens[2]);
            if (tokens.length != 2 * numPhoneNumbers + 3) {
              throw new InvalidLineException(tokens);
            }
            int tokenIndex = 3;
            for (int i = 0; i < numPhoneNumbers; i++) {
              try {
                String type = tokens[tokenIndex];
                tokenIndex++;
                String number = tokens[tokenIndex];
                tokenIndex++;
                PhoneNumber phoneNumber = new PhoneNumber(type, number);
                boolean added = phonebookEntry.addPhoneNumber(phoneNumber);
                if (!added) {
                  System.out
                      .println("On line " + lineNumber + ", duplicate phone number " + phoneNumber + " not added");
                }
              } catch (InvalidNumberException ex) {
                System.out.println("On line " + lineNumber + " , skipping phone number " + ex);
              }
            }
          }
          phonebookEntries.add(phonebookEntry);
        } catch (Exception ex) {
          System.out.println(ex);
        }
        lineNumber++;
      }
    } catch (FileNotFoundException ex) {
      System.out.println(ex);
    }

  }

  public boolean addEntry(PhonebookEntry entry) {
    for (PhonebookEntry phonebookEntry : phonebookEntries) {
      if (phonebookEntry.getName().equals(entry.getName())) {
        return false;
      }
    }
    phonebookEntries.add(entry);
    return true;
  }

  public boolean addNumberToEntry(Name name, PhoneNumber phoneNumber) throws NonexistentNameException {
    for (PhonebookEntry phonebookEntry : phonebookEntries) {
      if (phonebookEntry.getName().equals(name)) {
        boolean added = phonebookEntry.addPhoneNumber(phoneNumber);
        return added;
      }
    }
    throw new NonexistentNameException(name);
  }

  public boolean deleteEntry(Name name) {
    if (phonebookEntry.getName().equals(name)) {
      phonebookEntries.remove(phonebookEntry);
      return true;
    }
    return false;
  }

  public void deletePhoneNumber(Name name, PhoneNumber phoneNumber)
      throws NonexistentPhoneNumberException, NonexistentNameException {
    boolean deleted = false;
    for (PhonebookEntry phonebookEntry : phonebookEntries) {
      if (phonebookEntry.getName().equals(name)) {
        deleted = phonebookEntry.deletePhoneNumber(phoneNumber);
        if (!deleted) {
          throw new NonexistentPhoneNumberException(name, phoneNumber);
        }
      }
    }
    if (!deleted) {
      throw new NonexistentNameException(name);
    }
  }

  public ArrayList<PhoneNumber> lookup(Name name) throws NonexistentNameException {
    for (PhonebookEntry phonebookEntry : phonebookEntries) {
      if (phonebookEntry.getName().equals(name)) {
        return phonebookEntry.getPhoneNumbers();
      }
    }
    throw new NonexistentNameException(name);
  }

  public void printToFile(String filename) {
    try (PrintWriter pw = new PrintWriter(filename)) {
      for (PhonebookEntry phonebookEntry : phonebookEntries) {
        Name name = phonebookEntry.getName();
        pw.print(name.getFirst() + " ");
        pw.print(name.getLast() + " ");

        ArrayList<PhoneNumber> phoneNumbers = phonebookEntry.getPhoneNumbers();

        if (!phoneNumbers.isEmpty()) {
          pw.print(phoneNumbers.size() + " ");
        }

        for (PhoneNumber phoneNumber : phoneNumbers) {
          pw.print(phoneNumber.getType() + " ");
          pw.print(phoneNumber.getNumber() + " ");
        }

        pw.println();
      }
    } catch (FileNotFoundException ex) {
      System.out.println(ex);
    }
  }

  }

  public ArrayList<Name> reverseLookup(PhoneNumber phoneNumber) {
    ArrayList<Name> results = new ArrayList<>();
    for (PhonebookEntry phonebookEntry : phonebookEntries) {
      if (phonebookEntry.getPhoneNumbers().contains(phoneNumber)) {
        results.add(phonebookEntry.getName());
      }
    }
    return results;
  }

  @Override
  public String toString() {
    String result = "";
    for (PhonebookEntry phonebookEntry : phonebookEntries) {
      result += phonebookEntry + "\n";
    }
    return result;
  }

}