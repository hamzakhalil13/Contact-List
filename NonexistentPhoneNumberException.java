public class NonexistentPhoneNumberException extends Exception{

  public NonexistentPhoneNumberException(Name name, PhoneNumber phoneNumber){
    super("phone number " + phoneNumber + " was not found for name " + name);
  }
}