public class DuplicateNameException extends Exception{

  public DuplicateNameException(Name name){
    super(name.toString);
   
  }
}