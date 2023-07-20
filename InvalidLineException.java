public class InvalidLineException extends Exception{
  private String[] tokens;

  public InvalidLineException(String[] tokens){
    this.tokens = tokens;
  }
  @Override
  public String getMessage(){
    String result = "";
    for(String s: tokens){
      result += s + " ";
    }
    
    return result;
  }
}