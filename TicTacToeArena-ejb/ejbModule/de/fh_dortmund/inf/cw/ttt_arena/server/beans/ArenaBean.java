package de.fh_dortmund.inf.cw.ttt_arena.server.beans;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotificationType;

@Stateless
public class ArenaBean {
	
	@Inject
	private JMSContext context;
	@Resource(lookup = "java:global/jms/ObserverTopic")
	private Topic observerTopic;
	
	@Resource
	private TimerService timerService;
	private boolean isFinished = false;
	
	public void startTimer() {
		TimerConfig singleAction = new TimerConfig("Single Timer", true);
		timerService.createSingleActionTimer(60000, singleAction);
	}
	
	@Timeout
	public void timeOut(Timer timer) {
		System.out.println("Match Timer abgelaufen");
		sendTopic(ClientNotificationType.DRAW, "Timeout");
	}
	
	public void clearTimer() {
		for(Timer timer: timerService.getTimers()) {
			System.out.println(timer.getInfo());
			timer.cancel();
		}
	}
	
	public void set(char[] arena, int index, String name, char token) throws Exception { 
		
		if(index < 1 || index > 9) {
			throw new ArrayIndexOutOfBoundsException("Ein Ziffer zwischen 1-9 wurde erwartet, aber" + index + " wurde eingegeben");
		}else {
			if(arena[index -1] == 0) {
				arena[index -1] = token;
				
				checkIfGameIsFinished(arena, name);
				switchPlayerTurn();
			}else {
				System.out.println("Feld ist schon besetzt");
			}
		}
		
	}
	
	public void checkIfGameIsFinished(char[] arena, String name){
        if (hasSameToken(arena, topRowSquares)
            || hasSameToken(arena, middleRowSquares)
            || hasSameToken(arena, bottomRowSquares)
            || hasSameToken(arena, leftColumnSquares)
            || hasSameToken(arena, middleColumnSquares)
            || hasSameToken(arena, rightColumnSquares)
            || hasSameToken(arena, downRightDiagonalSquares)
            || hasSameToken(arena, downLeftDiagonalSquares))
        {
            finishAsVictory(name);
            clearTimer();
            sendTopic(ClientNotificationType.WIN, name);
        }

        // Kein Gewinner gefunden, checkt nun ob Feld vollgefühlt ist
        if (isFull(arena))
        {
            finishAsDraw();
            clearTimer();
            sendTopic(ClientNotificationType.DRAW, "System");
        }
    }
	
   /**
    * Finishes the game as a Victory
    * @param player
    */
	public String finishAsVictory(String name){
        this.isFinished = true;
		return name;
    }

    // Finishes the game as a Draw
	public void finishAsDraw(){
        this.isFinished = true;
    }

    // Switches the current Player
	public void switchPlayerTurn(){

    }
	
	public boolean isFull(char[] arena) {
		int taken = 0;
		
		for(char c: arena) {
			if(c != 0)
				taken++;
		}
		if(taken == 9) {
			System.out.println("Taken: " + taken);
			sendTopic(ClientNotificationType.DRAW, "System");
		}
		return taken == 9;
	}
	
	public int[] topRowSquares =  {1, 2, 3};
	public int[] middleRowSquares = {4, 5, 6};
	public int[] bottomRowSquares = {7, 8, 9};
	public int[] leftColumnSquares = {1, 4, 7};
	public int[] middleColumnSquares = {2, 5, 8};
	public int[] rightColumnSquares = {3, 6, 9};
	public int[] downRightDiagonalSquares = {1, 5, 9};
	public int[] downLeftDiagonalSquares = {3, 5, 7};

    public boolean hasSameToken(char[] arena, int[] squares)
    {
        char prevMark = 0;
        
        for(int s: squares) {
        	if(arena[s - 1] == 0) return false;
        	
        	if(prevMark == 0) {
        		prevMark = arena[s - 1];
        		continue;
        	}
        	
        	if(arena[s - 1] != prevMark) return false;
        	
        	prevMark = arena[s - 1];
        }
        
        return true;
    }
    
    public void sendTopic(ClientNotificationType notificationType, String player) {
		try {
			
			ClientNotification notification = new ClientNotification(notificationType, player, new Date());
			
			ObjectMessage  om = context.createObjectMessage();
			om.setObject(notification);
			context.createProducer().send(observerTopic, om);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
