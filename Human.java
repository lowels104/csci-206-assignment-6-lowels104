public class Human extends Player {

    private int xLocation = getX(); 
    private int yLocation = getY(); 
    public Human(int id, int initialX, int initialY, boolean initialIt) {
        super(id, initialX, initialY, initialIt);
        
    }
    /*public void moveUp(){
        yLocation = yLocation - 5;
        setY(yLocation);
    }
    public void moveLeft(){
        xLocation = xLocation - 5;
        setX(xLocation);
    }
    public void moveRight(){
        xLocation = xLocation + 5;
        setX(xLocation);
    }
    public void moveDown(){
        yLocation = yLocation + 5;
        setY(yLocation);
    }*/ 
    public void moveForward(int mouseX, int mouseY){ //moves in the direction of the mouse
        
        double yDifference = mouseY - yLocation;
        double xDifference = mouseX - xLocation;
        
        double angle = Math.atan(yDifference/xDifference);

        if(mouseX < xLocation){
            xLocation = (int) (11 * Math.cos(angle + Math.PI)) + xLocation;
            yLocation = (int) (11 * Math.sin(angle + Math.PI)) + yLocation;
            setX(xLocation);
            setY(yLocation);
        }
        else{
            xLocation = (int) (11 * Math.cos(angle)) + xLocation;
            yLocation = (int) (11 * Math.sin(angle)) + yLocation;
            setX(xLocation);
            setY(yLocation);
        }
        

    }
    
}
