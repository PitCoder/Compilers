import java.util.ArrayList;

public class Test{
  public static void main(String[] args) {
    ArrayList<Term> a_terms = new ArrayList<>();
    ArrayList<Term> b_terms = new ArrayList<>();

    System.out.println("Running Tests...");
    System.out.println("Test no. 1 ==== ADICION ====");

    //Representando el polinomio 2x³ + 5x -3
    a_terms.add(new Term(2,1,3));
    a_terms.add(new Term(5,1,1));
    a_terms.add(new Term(-3,1,0));

    //Representando el polinomio 4x - 3x² + 2x³
    b_terms.add(new Term(4,1,1));
    b_terms.add(new Term(-3,1,2));
    b_terms.add(new Term(2,1,3));

    Polynomial a = new Polynomial(a_terms);
    System.out.println("Polinomio a: " + a.toString());
    Polynomial b = new Polynomial(b_terms);
    System.out.println("Polinomio b: " + b.toString());

    Sum s = new Sum();
    Polynomial sumab = s.sum(a,b);
    System.out.println("Resultado: " + sumab.toString());

    a_terms.clear();
    b_terms.clear();

    //Representando el polinomio x-¹ - 2x + x²
    a_terms.add(new Term(1,1,-1));
    a_terms.add(new Term(-2,1,1));
    a_terms.add(new Term(1,1,3));

    //Representando el polinomio x² - 2/3x + 3x-¹
    b_terms.add(new Term(1,1,2));
    b_terms.add(new Term(-2,3,1));
    b_terms.add(new Term(3,1,-1));

    a = new Polynomial(a_terms);
    System.out.println("Polinomio a: " + a.toString());
    b = new Polynomial(b_terms);
    System.out.println("Polinomio b: " + b.toString());

    sumab = s.sum(a,b);
    System.out.println("Resultado: " + sumab.toString());


    System.out.println("Test no. 2 ==== SUBSTRACCION ====");

    a_terms.clear();
    b_terms.clear();

    //Representando el polinomio 2x³ + 5x -3
    a_terms.add(new Term(2,1,3));
    a_terms.add(new Term(5,1,1));
    a_terms.add(new Term(-3,1,0));

    //Representando el polinomio 4x - 3x² + 2x³
    b_terms.add(new Term(4,1,1));
    b_terms.add(new Term(-3,1,2));
    b_terms.add(new Term(2,1,3));

    a = new Polynomial(a_terms);
    System.out.println("Polinomio a: " + a.toString());
    b = new Polynomial(b_terms);
    System.out.println("Polinomio b: " + b.toString());

    Sub sb = new Sub();
    Polynomial subab = sb.sub(a,b);
    System.out.println("Resultado: " + subab.toString());

    a_terms.clear();
    b_terms.clear();

    //Representando el polinomio x-¹ - 2x + x²
    a_terms.add(new Term(1,1,-1));
    a_terms.add(new Term(-2,1,1));
    a_terms.add(new Term(1,1,2));

    //Representando el polinomio x² - 2/3x + 3x-¹
    b_terms.add(new Term(1,1,2));
    b_terms.add(new Term(-2,3,1));
    b_terms.add(new Term(3,1,-1));

    a = new Polynomial(a_terms);
    System.out.println("Polinomio a: " + a.toString());
    b = new Polynomial(b_terms);
    System.out.println("Polinomio b: " + b.toString());

    subab = sb.sub(a,b);
    System.out.println("Resultado: " + subab.toString());


    System.out.println("Test no. 3 ==== MULTIPLICACION ====");

    a_terms.clear();
    b_terms.clear();

    //Representando el polinomio 2x² - 3
    a_terms.add(new Term(2,1,2));
    a_terms.add(new Term(-3,1,0));

    //Representando el polinomio 2x³ - 3x² + 4x
    b_terms.add(new Term(4,1,1));
    b_terms.add(new Term(-3,1,2));
    b_terms.add(new Term(2,1,3));

    a = new Polynomial(a_terms);
    System.out.println("Polinomio a: " + a.toString());
    b = new Polynomial(b_terms);
    System.out.println("Polinomio b: " + b.toString());

    Mul mul = new Mul();
    Polynomial mulab = mul.multiplyTwo(a,b);
    System.out.println("Resultado: " + mulab.toString());

    a_terms.clear();
    b_terms.clear();

    //Representando el polinomio x-¹ - 2x + x²
    a_terms.add(new Term(1,1,-1));
    a_terms.add(new Term(-2,1,1));
    a_terms.add(new Term(1,1,2));

    //Representando el polinomio x² - 2/3x + 3x-¹
    b_terms.add(new Term(1,1,2));
    b_terms.add(new Term(-2,3,1));
    b_terms.add(new Term(3,1,-1));

    a = new Polynomial(a_terms);
    System.out.println("Polinomio a: " + a.toString());
    b = new Polynomial(b_terms);
    System.out.println("Polinomio b: " + b.toString());

    mulab = mul.multiplyTwo(a,b);
    System.out.println("Resultado: " + mulab.toString());


    System.out.println("Test no. 4 ==== DIVISION ====");

    a_terms.clear();
    b_terms.clear();

    //Representando el polinomio x² - 2x + 1 divisor
    a_terms.add(new Term(1,1,2));
    a_terms.add(new Term(-2,1,1));
    a_terms.add(new Term(1,1,0));

    //Representando el polinomio x⁵ + 2x³ -x - 8 dividendo
    b_terms.add(new Term(1,1,5));
    b_terms.add(new Term(2,1,3));
    b_terms.add(new Term(-1,1,1));
    b_terms.add(new Term(-8,1,0));

    a = new Polynomial(a_terms);
    System.out.println("Polinomio a: " + a.toString());
    b = new Polynomial(b_terms);
    System.out.println("Polinomio b: " + b.toString());

    Div div = new Div();
    Polynomial[] divab = div.division(a,b);
    System.out.println("Resultado:\n Cociente: "+ divab[0].toString()
    + "\nResiduo: " + divab[1].toString() + "/(" + divab[2].toString() + ")");

    System.out.println("Test no. 6 ==== DERIVATIVE ====");

    a_terms.clear();
    b_terms.clear();

    //Representando el polinomio x² + 2x - 1
    a_terms.add(new Term(1,1,2));
    a_terms.add(new Term(2,1,1));
    a_terms.add(new Term(-1,1,0));

    a = new Polynomial(a_terms);
    System.out.println("Polinomio a: " + a.toString());

    Derivative derivative = new Derivative();
    Polynomial deva = derivative.indefinite(a);
    int[] defdeva = derivative.definite(1, 3, a);

    System.out.println("Resultado Indefinido: " + deva.toString());
    System.out.println("Resultado Definido: " + defdeva[0] + "/" + defdeva[1]);

    System.out.println("Test no. 7 ==== INTEGRAL ====");

    a_terms.clear();
    b_terms.clear();

    //Representando el polinomio x² + 2x - 1
    a_terms.add(new Term(1,1,2));
    a_terms.add(new Term(2,1,1));
    a_terms.add(new Term(-1,1,0));
    
    a = new Polynomial(a_terms);
    System.out.println("Polinomio a: " + a.toString());

    Integral integral = new Integral();
    Polynomial inta = integral.indefinite(a);
    int[] definta = integral.definite(1, 3, a);

    System.out.println("Resultado Indefinido: " + inta.toString());
    System.out.println("Resultado Definido: " + definta[0] + "/" + definta[1]);


    System.out.println("Test no. 8 ==== BINOMIAL DE NEWTON ====");

    Binomial bi = new Binomial();

    //Representando el desarrollo (x + 1)⁴ = x⁴ + 4x³ + 6x² + 4x + 1
    Polynomial pbinomial = bi.newtonBinomial('p', 4);
    System.out.println("Polinomio desarrollado: " + pbinomial.toString());

    //Representando el desarrollo (x + 2)³ = x³ + 24x² + 32x + 16
    pbinomial = bi.newtonBinomial('p', 2, 3);
    System.out.println("Polinomio desarrollado: " + pbinomial.toString());

    //Representando el desarrollo (x - 1)² = x² - 2x + 1
    Polynomial nbinomial = bi.newtonBinomial('n', 2);
    System.out.println("Polinomio desarrollado: " + nbinomial.toString());

    //Representando el desarrollo (x - 2)³ = x³ - 24x² + 32x - 16
    nbinomial = bi.newtonBinomial('n', 2, 3);
    System.out.println("Polinomio desarrollado: " + nbinomial.toString());


    System.out.println("Test no. 9 ==== SERIE GEOMETRICA ====");

    Geometric geo = new Geometric();

    //Representando el desarrollo donde a = 3, ###Para graficar r = 1/2
    Polynomial pgeometric = geo.geometricSerie(3, 8);
    System.out.println("Polinomio desarrollado: " + pgeometric.toString());
  }
}
