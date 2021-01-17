package de.fh_dortmund.inf.cw.ttt_arena.server.shared;

public enum NotificationType {
	
	TEXT(0), REGISTER(1), LOGIN(2), LOGOUT(3), DISCONNECT(4), STATISTIC(5);
	
	private int value;
	
	private NotificationType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static NotificationType getChatMessageType(int value) {
		NotificationType chatMessageType = null;
		
		for(NotificationType tempChatMessageType : values()) {
			if(tempChatMessageType.getValue() == value) {
				chatMessageType = tempChatMessageType;
			}
		}
		
		return chatMessageType;
	}
}
