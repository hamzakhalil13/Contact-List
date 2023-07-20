import java.util.ArrayList;
public class PhonebookEntry{

  private Name name;
  private ArrayList<PhoneNumber> phoneNumbers;

  public PhonebookEntry(Name name, ArrayList<PhoneNumber> phoneNumbers) throws DuplicatePhoneNumberException{
    
    this.name = name;

    for(int i = 0; i< phoneNumbers.size(); i++){
      for(int j= i+1; j< phoneNumbers.size(); i++){
        if (phoneNumbers.get(i).equals(phoneNumbers.get(j))){
          
          throw new DuplicatePhoneNumberException(phoneNumbers.get(i), name);//why do we add this parameter
        }
      }

    }
    this.phoneNumbers = phoneNumbers;
  }
  public boolean addPhoneNumber(PhoneNumber phoneNumber){
    for (PhoneNumber num1 : phoneNumbers){
      if(num1.equals(phoneNumber)){
        return false;
      }
    }
    phoneNumbers.add(phoneNumber);
    return true;

  }
  private boolean containsPhonenumber(PhoneNumber number){
    for (PhoneNumber num1 : phoneNumbers){
      if(num1.equals(number)){
        return false;
      }
    }
    return true;
  }
  public boolean deletePhoneNumber(PhoneNumber phoneNumber){
    if(containsPhonenumber(phoneNumber)){
      phoneNumbers.remove(phoneNumber);
      return true;
    }else{
      return false;
    }

  }
  @Override
  public boolean equals(Object o){
    if (o instanceof PhonebookEntry){
      PhonebookEntry other = (PhonebookEntry) o;
      return this.name.equals(other.name) && this.getPhoneNumbers().equals(other.getPhoneNumbers());
    }else{
      return false;
    }

  }
  public Name getName(){
    return name;
  }
  public ArrayList<PhoneNumber> getPhoneNumbers(){
    return phoneNumbers;
  }
  @Override
  public String toString(){
    return name + ": " + phoneNumbers;
  }
}