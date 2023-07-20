public class Name{
  private String first, last;

  public Name(String first, String last){
    this.first = first;
    this.last = last;
  }
  public String getFirst(){
    return first;
  }

  public String getLast(){
    return last;
  }
  @Override
  public String toString(){

    return first + " " + last;
  }

  @Override
  public boolean equals(Object o){
    if (o instanceof Name){
      Name other = (Name) o;
      return this.first.equals(other.first) && this.last.equals(other.last);
    }else {
      return false;
    }
  }
}