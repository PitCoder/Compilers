import java.util.ArrayList;
import java.util.List;
import domain.Polynomial;

public class Sum{
  public Sum(){
  }

  public Polynomial sum(Polynomial a, Polynomial b){
    Polynomial result = new Polynomial();
    List<Polynomial> polynomials = new ArrayList<Polynomial>();
    polynomials.add(a);
    polynomials.add(b);

    for(int i=0;i<polynomials.size();i++){
      List<Term> terms = polynomials.get(i).getTerms();
      Iterator iterator = terms.iterator();
      while(iterator.hasNext()){
        result.addTerm((Term)iterator.next());
      }
    }
    result.reduce();
    if(result.getTerms.size()==0){
      result.addTerm(0,1,0);
    }
    return result;
  }
}
