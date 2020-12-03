public abstract class Player {

    private int xLocation = 0;
    private int yLocation = 0;
    private int id = 0;
    private boolean it = false;
    private boolean debug =  false;
    private boolean canMove = true; 


    public Player(int id, int initialX, int  initialY, boolean initialIt){

        this.id = id;
        this.xLocation = initialX;
        this.yLocation = initialY;
        this.it  = initialIt;

    }

    public int getX(){return xLocation;}
    public int setX(int x){return this.xLocation = x;}
    public int getY(){return yLocation;}
    public int setY(int y){return this.yLocation = y;}
    public boolean getIt(){return it;}
    public boolean setIt(boolean it){return this.it = it;}
    public boolean getCanMove(){return  canMove;}
    public boolean setCanMove(boolean canMove){return this.canMove = canMove;}
    public String toString(){
        return "Player: " + id + ":" + xLocation + ":" + yLocation;
    }

    
}
