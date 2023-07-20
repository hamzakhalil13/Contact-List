public class PhoneNumber{
  private String number, type;

  public PhoneNumber(String type, String number) throws InvalidNumberException{
    this.type = type;
    checkNumberFormat(number);
    this.number = number;
  }
  
  private static void checkNumberFormat(String number) throws InvalidNumberException{
    if (number.length() != 12){
      throw new InvalidNumberException(number);
    }
   
    for (int i=0; i<number.length();i++){
      if (i==3 || i==7){
        if (number.charAt(i) != '-'){ 
          throw new InvalidNumberException(number);
        } else{
          if (!Character.isDigit(number.charAt(i))){ 
            throw new InvalidNumberException(number);
          }
        }
      }
    }  
  }
  @Override
  public String toString(){
    return "(" + type + ")" + " " + number;
  }
  @Override
  public boolean equals(Object o){
    
    if (o instanceof PhoneNumber){
      PhoneNumber other = (PhoneNumber) o;
      return this.type.equals(other.type) && this.number.equals(other.number);
    } else {
      return false;
    }
  }
  public String getNumber(){
    return number;
  }
  public String getType(){
    return type;
  }
}