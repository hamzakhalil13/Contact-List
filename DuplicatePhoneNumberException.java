public class DuplicatePhoneNumberException extends Exception{
  
  public DuplicatePhoneNumberException(PhoneNumber phoneNumber, Name name){
    super("Duplicate phone number " + phoneNumber+ "found for " + name);
  }
}