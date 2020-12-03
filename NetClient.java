import java.util.*;
import java.io.*;
import java.net.*;

public class NetClient implements Runnable {
    
    private int sleepTime = 0;
    private Vector<Player> netPlayers;
    private int myId;
    private Human human;
    TagView view;
    private Boolean marker = false;

    public NetClient(Vector<Player> netPlayers, Human human, int sleepTime, TagView view ){
        this.sleepTime = sleepTime;
        this.netPlayers = netPlayers;
        this.human = human;
        this.view = view;
        myId = (int) (Math.random() * 100000);
        netPlayers.add(human);
    }
    public void run(){
        try{
            while(true){

            //Do work here
            //Tell the server out current position
            //String movecmd = "move:"+myId+":"+ human.getX() +":" + human.getY();
            //sendRecieve(movecmd);
            //Get the position of all players (including me)
            updateNetPlayers();
 
            Thread.sleep(sleepTime);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
    }
    public boolean join(Player p){
        boolean ret = false;

        String cmd = "join:"+myId+":"+p.getX()+":"+p.getY();
        if(sendRecieve(cmd).equals("ok")){
            ret = true;
        }
        else{ret = false;}

        return ret;
    }

    public boolean move(int x, int y){
        boolean ret = false;
        String cmd="move:"+myId+":"+x+":"+y;
        if(sendRecieve(cmd).equals("ok")){ret =  true;}
        return ret; 
    }
    public String sendRecieve(String send){
        String ret ="notok";
        try {
            System.out.println("Client: "+ send);
            Socket s = new Socket("67.135.220.132", 80);
            BufferedReader in = new BufferedReader(new InputStreamReader( s.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter( s.getOutputStream()));
            out.write(send+ "\n");
            out.flush();
            String received = in.readLine();
            System.out.println("Server: "+received);
            ret = received;
            s.close();

        } catch (Exception e) {
            System.err.println(e);
        }
        return ret;
    }
    public void updateNetPlayers(){
        //ask for the count
        //ask for the id at index
        //ask for the info for the id
        move(human.getX(),human.getY());
        netPlayers.clear();
        //System.out.println(netPlayers.size());

        /*for(int count = 0; count < getNetPlayerCount(); count++){
            if(getIntIndex(count) == myId){
                view.paintPlayer(requestId(getIntIndex(count)));
                System.out.println(requestId(getIntIndex(count)));
                netPlayers.add(requestId(getIntIndex(count)));
                view.paintAllPlayers(netPlayers);
            }
        }*/
        updatePlayers();
        if(marker == true){
            view.paintAllPlayers(netPlayers);
            marker = false;
        }
    }
    public void updatePlayers(){
        for(int count = 0; count < getNetPlayerCount(); count++){
            //if(getIntIndex(count) == myId){
                //view.paintPlayer(requestId(getIntIndex(count)));
                //System.out.println(requestId(getIntIndex(count)));
                netPlayers.add(requestId(getIntIndex(count)));
                //view.paintAllPlayers(netPlayers);
            //}
            
        }
        marker = true;
    }
    public int getNetPlayerCount(){
        int ret = 0;
        String cmd = "idcount";
        String result = sendRecieve(cmd);
        try{
            StringTokenizer st = new StringTokenizer(result, ":");
            //System.out.println("result: "+result);
            //System.out.println(st.countTokens());
            if(st.countTokens() == 2){
                String act = st.nextToken();
                ret = Integer.valueOf(st.nextToken());
            
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return ret;
    }
    public int getIntIndex(int index){
        int ret = 0;
        String cmd = "index:"+ index;
        String result = sendRecieve(cmd);
        try{
            StringTokenizer st = new StringTokenizer(result, ":");
            if(st.countTokens() == 2){
                String act = st.nextToken();
                ret = Integer.valueOf(st.nextToken());
            
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return ret;
    }

    public Player requestId(int id){
        Human ret = null;
        String cmd = "request:"+ id;
        String result = sendRecieve(cmd);
        try{
            StringTokenizer st = new StringTokenizer(result, ":");
            if(st.countTokens() == 5){
                String act = st.nextToken();
                int remoteId = Integer.valueOf(st.nextToken());
                int x = Integer.valueOf(st.nextToken());
                int y = Integer.valueOf(st.nextToken());
                boolean it = Boolean.valueOf(st.nextToken());
                ret = new Human(id, x, y, it);
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return ret;
    }
}