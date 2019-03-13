public class Coordenada
{
	public int y;
    public int x;
 
    //Constructor
    public Coordenada(int x, int y) {
        this.x=x;
        this.y=y;
    }
	

    
    //Métodos - getters&setters
 
    public void setX(int x) {
        this.x=x;
    }
 
    public void setY(int y) {
        this.y=y;
    }
 
    public int getX() {
        return x;
    }
 
    public int getY() {
        return y;
    }
 
    public Coordenada norte(){
    	Coordenada Coord=new Coordenada(0,1);
    	return Coord;
    }
    public Coordenada sur(){
    	Coordenada Coord=new Coordenada(1,0);
    	return Coord;
    }
    public Coordenada este(){
    	Coordenada Coord=new Coordenada(-1,0);
    	return Coord;
    }
    public Coordenada oeste(){
    	Coordenada Coord=new Coordenada(0,-1);
    	return Coord;
    }

    public Coordenada comienza() {
		Coordenada c=new Coordenada(0,0);
		return c;
}

    
    
}