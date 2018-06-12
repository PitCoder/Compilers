public class VerifyType{
  public VerifyType() {
  }

  String verify(Object obj){
    try{
      int i = (Integer) obj;
      return "int";
    }
    catch(ClassCastException e){
        return "polynomial";
    }
  }
}
