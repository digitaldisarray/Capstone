package server;

public class Protocol {

	private String message = "";

	public String packetID(int id) {
		message = "ID" + id;
		return message;
	}

	public String NewClientPacket(int x, int y, int dir, int id) {
		message = "NewClient" + x + "," + y + "-" + dir + "|" + id;
		return message;
	}

}
