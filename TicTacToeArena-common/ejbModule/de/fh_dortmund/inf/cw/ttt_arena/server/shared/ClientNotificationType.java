package de.fh_dortmund.inf.cw.ttt_arena.server.shared;

public enum ClientNotificationType {
	
	TOKEN(0), REGISTER(1), LOGIN(2), LOGOUT(3), DISCONNECT(4), STATISTIC(5), WIN(6), DRAW(7);
	
	private int value;
	
	private ClientNotificationType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static ClientNotificationType getNotificationType(int value) {
		ClientNotificationType notificationType = null;
		
		for(ClientNotificationType tempNotificationType : values()) {
			if(tempNotificationType.getValue() == value) {
				notificationType = tempNotificationType;
			}
		}
		
		return notificationType;
	}
}
