import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class TagView extends JPanel implements KeyListener, MouseMotionListener, ActionListener {// we get keypresses,
                                                                                                 // mouse positions, and
                                                                                                 // button clicks

    private boolean debug = false;
    private int lastKey = 0;
    private Graphics osg;
    private Image osi;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private String lastEvent;
    private int playerSize = 10;
    JButton stop;
    JButton start;
    JButton reset;

    //make the frame
    public TagView(final boolean debug, final int windowWidth, final int windowHeight) {
        this.debug = debug;//sets debug instance variable to the value passed into it by TagController
        final JFrame window = new JFrame();
        window.setSize(new Dimension(windowWidth, windowHeight));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.requestFocus();
        window.setLayout(null);
        window.setResizable(false);
        //add all the event listeners
        window.addKeyListener(this);
        this.setSize(new Dimension(windowWidth, windowHeight - 100)); // – 100 to allow room for buttons
        window.getContentPane().add(this);
        this.addMouseMotionListener(this);

        // make the start button
        start = new JButton("Start");
        final Rectangle rstart = new Rectangle(windowWidth / 4 * 1, windowHeight - 90, 80, 40);
        start.setBounds(rstart);
        start.addActionListener(this);
        window.getContentPane().add(start);
        start.setFocusable(false);

        // make the stop button
        stop = new JButton("Stop");
        final Rectangle rstop = new Rectangle((windowWidth / 4) * 2, windowHeight - 90, 80, 40);
        stop.setBounds(rstop);
        stop.addActionListener(this);
        window.getContentPane().add(stop);
        stop.setFocusable(false);

        // make the reset button
        reset = new JButton("Reset");
        final Rectangle rreset = new Rectangle((windowWidth / 4) * 3, windowHeight - 90, 80, 40);
        reset.setBounds(rreset);
        reset.addActionListener(this);
        window.getContentPane().add(reset);
        reset.setFocusable(false);

        osi = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_RGB);
        osg = osi.getGraphics();
        
       repaint();
        
    
    }
    public int getLastKey(){
        return lastKey; 
    }
    public int getLastMouseX(){
        return lastMouseX;
    }
    public int getLastMouseY(){
        return lastMouseY;
    }
    public String getLastEvent(){
        return lastEvent;
    }
    public String setLastEvent(String lastEvent){
        return this.lastEvent = lastEvent;
    }
    public int getPlayerSize(){
        return playerSize;
    }

    protected void paintComponent(final Graphics g) // repaints the screen

    {
        super.paintComponent(g); 
        g.drawImage(osi,0,0,null);
        //osg.clearRect(0, 0, this.getWidth(), this.getHeight());
        
    }

    public void paintPlayer( Player p)
    {
        
        boolean playerIt =  p.getIt(); //paints the player red if they are it. Blue if not it
        if(playerIt == true){
            osg.setColor(Color.RED);
            osg.fillOval(p.getX(),p.getY(),playerSize,playerSize);
        }
        else{
            osg.setColor(Color.BLUE);
            osg.drawOval(p.getX(),p.getY(),playerSize,playerSize);
        }
        //repaint(); 
    }
    public void paintAllPlayers(Vector<Player> Players){
        osg.clearRect(0, 0, this.getWidth(), this.getHeight());
        //System.out.println(Players.size());
        for(int i = 0; i < Players.size(); i++){
            paintPlayer(Players.elementAt(i));
            //System.out.println(Players.elementAt(i).toString());
        }
        repaint();
    }
    public void drawScore(String Score){
        System.out.println(Score);
        //osg.drawString(Score, 10, 10);
    }
    @Override
    public void mouseDragged(final MouseEvent e) { //called when the mouse position changes while click is held
        if (debug)
            System.out.println("Mouse: " + e.getPoint());
    }

    @Override
    public void mouseMoved(final MouseEvent e) { //called when the mouse position changes without clicking
        lastMouseX = e.getX();
        lastMouseY = e.getY();

    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

    @Override
    public void keyPressed(final KeyEvent e) { //called when a key is pressed
        if (debug){
            System.out.println("Key Pressed:" + e.getKeyCode());
         }
        lastKey = e.getKeyCode(); 
        
    }

    @Override
    public void keyReleased(final KeyEvent e) { //take a guess
    }

    @Override
    public void actionPerformed(final ActionEvent e) { //gets called when an onscreen button is clicked
        if( e.getSource() == start){
            lastEvent = "start";
        }
        if( e.getSource() == stop){
            lastEvent = "stop";
        }
        if( e.getSource() == reset){
            lastEvent = "reset";
        }
    }

}
