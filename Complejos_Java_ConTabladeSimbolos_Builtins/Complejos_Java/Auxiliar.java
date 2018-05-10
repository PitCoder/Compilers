public class Auxiliar {
  public int[] obtenerComponentes(String entrada) {
    entrada = entrada.replaceAll("\\s+", "");
    String bufferReal = "";
    String bufferImg = "";
        
    for (int i = 0; i < entrada.length(); i++) {        
      if ((int) entrada.charAt(i) > 47 && (int) entrada.charAt(i) < 58) {
        bufferReal += entrada.charAt(i);
      } else { break; }
    }
      
    if (entrada.contains("+"))
      bufferImg = entrada.substring(entrada.indexOf("+"), entrada.length() - 1);
    
    if (entrada.contains("-"))
      bufferImg = entrada.substring(entrada.indexOf("-"), entrada.length() - 1);
    
    int cmp[] = new int[2];
    cmp[0] = Integer.parseInt(bufferReal);
    cmp[1] = Integer.parseInt(bufferImg);

    return cmp;
  }

}
