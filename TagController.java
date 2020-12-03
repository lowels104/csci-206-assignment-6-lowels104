/*
    Levi Savage Lowe
    Computer Science II
    Assingment 6
    Network Tag

    Had help from Daniel from class
*/

import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class TagController extends TimerTask {

    private boolean debug = true;//prints debug info when true
    private TagView view;
    private Timer timer;
    private final int timerMS = 50;
    private final int frameWidth = 900;
    private final int frameHeight = 600;
    private Human human = new Human(12345, 100, 100, false);
    //private ComPlayer comPlayer1 = new ComPlayer(debug, 400, 100, true); 
    private boolean running = false;
    private int tagBackTimer = 50;
    private int humanScore = 0;
    private int comScore = 0;
    private String gameScore = "Human: " + humanScore + "Computer: " + comScore;

    private Vector<Player> netPlayers = new Vector<Player>();
    NetClient network;

    public static void main(String[] args) {//constructs an instance of TagController
        TagController theGame = new TagController();
    }

    public TagController() {

        timer = new Timer();
        
        view = new TagView(debug, frameWidth, frameHeight);
        startTimer();
        network = new NetClient(netPlayers, human, 1000, view);
        Thread netWorker = new Thread(network);
        netWorker.start();

    }

    public void getEvents(){
        if(view.getLastEvent() == "start"){ 
            running = true;
            network.join(human);
        }
        if(view.getLastEvent() == "stop"){
            running = false;
        }
        if(view.getLastEvent() == "reset"){ // resests players positions and stops the game from running
            
            human.setX(100);
            human.setY(100);
            //comPlayer1.setX(800);
            //comPlayer1.setY(100);
            human.setIt(false);
            //comPlayer1.setIt(true);

            humanScore = 0;
            comScore = 0;

            //view.paintPlayer(human); 
            //view.paintPlayer(comPlayer1);
            view.repaint(); 

            view.setLastEvent("stop");
        }
    }
    public void checkBoundaries(Player p){ //checks if players are in the boundaries
        int xLoc = p.getX();
        int yLoc = p.getY();
        boolean canMove = true;
        p.setCanMove(canMove); 
        

        if( xLoc > frameWidth - view.getPlayerSize() ){
            xLoc = frameWidth - view.getPlayerSize();
            p.setX(xLoc);
            canMove  = false;
            p.setCanMove(canMove);
        }
        if( xLoc < 0 ){
            xLoc = 0;
            p.setX(xLoc);
            canMove  = false;
            p.setCanMove(canMove);
        }
        if( yLoc > frameHeight  - 100 - view.getPlayerSize() ){
            yLoc = frameHeight - 100 - view.getPlayerSize();
            p.setY(yLoc);
            canMove  = false;
            p.setCanMove(canMove);
        }
        if( yLoc < 0 ){
            yLoc = 0;
            p.setY(yLoc);
            canMove  = false;
            p.setCanMove(canMove);
        }
        
    }
    /*public void checkTag(){ //checks if players are "tagging" each other
        int humanX = human.getX();
        int humanY = human.getY();
        int comPlayer1X = comPlayer1.getX();
        int comPlayer1Y = comPlayer1.getY();
        boolean humanIt = human.getIt();
        boolean comPlayer1It = comPlayer1.getIt();


        if(tagBackTimer >= 50){  //50 ticks must happen before a tag can happen again
           
            if((Math.abs(humanX - comPlayer1X) <= 10) && (Math.abs(humanY - comPlayer1Y) <= 10 )){
                 System.out.println("Human:" +  humanIt + "Com:" + comPlayer1It);
                if(humanIt == true){
                    humanIt = false;
                    human.setIt(humanIt);
                    comPlayer1It = true;
                    comPlayer1.setIt(humanIt);
                    tagBackTimer = 0;
                    humanScore = humanScore + 1;
                }
                else{
                    humanIt = true;
                    human.setIt(humanIt);
                    comPlayer1It = false;
                    comPlayer1.setIt(humanIt);
                    tagBackTimer = 0;
                    comScore = comScore + 1; 
                
                }
                System.out.println("Human:" +  humanIt + "Com:" + comPlayer1It);
                
            }
            
        }
    }*/

    public void startTimer() {//begin the timer
        timer.schedule(this, 0, timerMS);
        
    }

    public void stopTimer() {//end the timer
        
        timer.cancel();
    }

    public void run() {//called every tick
        getEvents();
        if(running){
            if(tagBackTimer < 50){
                tagBackTimer++;
            }
            
            //checkTag();
            
            //checkBoundaries(human);
            //checkBoundaries(comPlayer1);
            boolean humanIt = human.getIt();
            //boolean comPlayer1It = comPlayer1.getIt();

            

            if(human.getCanMove() == true){
                human.moveForward(view.getLastMouseX(), view.getLastMouseY());
            }
            /*if(comPlayer1.getCanMove() == true){
                if(comPlayer1It == true){    
                    comPlayer1.Chase(human.getX(), human.getY());
                }
                else{
                comPlayer1.Run(human.getX(), human.getY());
                }
            }*/
            view.paintPlayer(human); 
            //view.paintPlayer(comPlayer1);
            //gameScore = "Human: " + humanScore +"  "+ "Computer: " + comScore;
           
            //view.drawScore(gameScore); //For some printing the score to the screen causes a null pointer exception so it is just printing to the console
            
            view.repaint(); 
            

        }
        
    }

}
