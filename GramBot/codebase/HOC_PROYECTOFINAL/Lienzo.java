import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Lienzo extends Canvas{
    ArrayList<String> Direcciones = new ArrayList<String>();
    int anteriorx1;
    int anteriory1;
    int anteriorx2;
    int anteriory2;
    String siguiente;
    
    public Lienzo(ArrayList<String> Direcciones) {
        this.Direcciones=Direcciones;
    }

    public void paint(Graphics g){
		int flag=0, i;
        //x1,y1,x2,y2
		g.setColor(new Color(230, 230, 230));
        for(i=1; i<=40; i++)
        {
            g.drawLine(0, 25*i, 1000, 25*i);
            g.drawLine(25*i, 0, 25*i, 1000);
        }

        g.setColor(new Color(255, 255, 255, 200));
        g.drawLine(0,500,1000,500);
        g.drawLine(500,0,500,1000);
        g.setColor(Color.black);
        g.fillOval(500-10/2, 500-10/2, 10,10);

		
        g.setColor(Color.gray);
        g.drawLine(0,500,1000,500);
        g.drawLine(500,0,500,1000);
        
        g.setColor(new Color(80,212,51));
		
		for(i=0; i<Direcciones.size();i++ ){
            switch(Direcciones.get(i)){
                case "c":
                    anteriorx1=500;
                    anteriory1=500;
                    anteriorx2=500;
                    anteriory2=500;
                    g.drawLine(anteriorx1,anteriory1,anteriorx2,anteriory2);
                    break;
                case "n":
                    if(!Direcciones.get(i-1).equalsIgnoreCase("s")){
                        anteriory1=anteriory2;
                        anteriory2=anteriory2-25;
                        anteriorx1=anteriorx2;
                        g.drawLine(anteriorx1,anteriory1,anteriorx2,anteriory2);
						
                    }
                    break;
                case "s":
                    if(!Direcciones.get(i-1).equalsIgnoreCase("n")){
                        anteriory1=anteriory2;
                        anteriory2=anteriory2+25;
                        anteriorx1=anteriorx2;
                        g.drawLine(anteriorx1,anteriory1,anteriorx2,anteriory2);
                    }
                    break;
                case "e":
                    if(!Direcciones.get(i-1).equalsIgnoreCase("o")){
                        anteriorx1=anteriorx2;
                        anteriorx2=anteriorx2+25;
                        anteriory1=anteriory2;
                        g.drawLine(anteriorx1,anteriory1,anteriorx2,anteriory2);
                    }
                    break;
                case "o":
                    if(!Direcciones.get(i-1).equalsIgnoreCase("e")){
                        anteriorx1=anteriorx2;
                        anteriorx2=anteriorx2-25;
                        anteriory1=anteriory2;
                        g.drawLine(anteriorx1,anteriory1,anteriorx2,anteriory2);
                    }
                    break;
                case "no":
                    if(!Direcciones.get(i-1).equalsIgnoreCase("se")){
                        anteriorx1=anteriorx2;
                        anteriorx2=anteriorx2-25;
                        anteriory1=anteriory2;
                        anteriory2=anteriory2-25;
                        g.drawLine(anteriorx1,anteriory1,anteriorx2,anteriory2);
                    }
                    break;
                    
                 case "so":
                    if(!Direcciones.get(i-1).equalsIgnoreCase("ne")){
                        anteriory1=anteriory2;
                        anteriory2=anteriory2+25;
                        anteriorx1=anteriorx2;
                        anteriorx2=anteriorx2-25;
                        g.drawLine(anteriorx1,anteriory1,anteriorx2,anteriory2);
                    }
                    break;
                    
                case "se":
                    if(!Direcciones.get(i-1).equalsIgnoreCase("no")){
                        anteriory1=anteriory2;
                        anteriory2=anteriory2+25;
                        anteriorx1=anteriorx2;
                        anteriorx2=anteriorx2+25;
						//if(flag==0)
                        g.drawLine(anteriorx1,anteriory1,anteriorx2,anteriory2);
                    }
                    break;
                    
                case "ne":
                    if(!Direcciones.get(i-1).equalsIgnoreCase("so")){
                        anteriory1=anteriory2;
                        anteriory2=anteriory2-25;
                        anteriorx1=anteriorx2;
                        anteriorx2=anteriorx2+25;
                        g.drawLine(anteriorx1,anteriory1,anteriorx2,anteriory2);
                    }
                    break;
                    
				case "u":
                    int alpha = 0; //opacidad
                    Color myColour = new Color(255, 255, 255, alpha);
                    g.setColor(myColour);
					break;
				case "d":
                    g.setColor(Color.orange);
					break;
                default:
                    break;
            }
        }
      
    }
}
