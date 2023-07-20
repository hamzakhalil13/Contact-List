public class NonexistentNameException extends Exception{

  public NonexistentNameException(Name name){
    super(name.toString());
    
  }
}