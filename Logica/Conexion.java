package Logica;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Observable;

public class Conexion extends Observable implements Runnable {
	private MulticastSocket socket;
	private final int PORT = 5000;
	private final String GROUP_ADDRESS = "224.2.2.2";
	private InetAddress group_ia;
	private int identifier;
	private boolean identified;

	public Conexion() {
		// Initialization
		try {
			System.out.println("Starting socket at port " + PORT);
			socket = new MulticastSocket(PORT);
			System.out.println("Joining to gruop " + GROUP_ADDRESS);
			group_ia = InetAddress.getByName(GROUP_ADDRESS);
			socket.joinGroup(group_ia);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// AutoID calculation
		// 1. Greet the group
		greetGroup();

		// Wait for members responses

		try {
			// Define how much are you going to wait
			socket.setSoTimeout(2000); // 2 seconds
			while (!identified) {
				receiveGreetResponses();
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void greetGroup() {
		System.out.println("Sending greeting");
		MensajeAutoID message = new MensajeAutoID("Hi i'm a new member");
		byte[] bytes = serialize(message);
		try {
			sendMessage(bytes, group_ia, PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void receiveGreetResponses() throws IOException {
		try {
			// Receive
			DatagramPacket receivedPacket = receiveMessage();
			// Deserialize
			Object receivedObject = deserialize(receivedPacket.getData());

			// Detect if the received message is an AutoIDMessage Object
			if (receivedObject instanceof MensajeAutoID) {
				MensajeAutoID message = (MensajeAutoID) receivedObject;
				String messageContent = message.getContent();

				// If it is a greet response
				if (messageContent.contains("I am:")) {
					System.out.println("received data was a greeting answer");
					System.out.println("recalculatig my AutoID");
					String[] partes = messageContent.split(":");
					int externalID = Integer.parseInt(partes[1]);
					if (externalID >= identifier) {
						identifier = externalID + 1;
					}
				}
			}

		} catch (SocketTimeoutException e) {
			System.out.println("AutoID time finished");
			if (identifier == 0) {
				identifier = 1;
			}
			identified = true;
			socket.setSoTimeout(0); // Reset wiating time to forever
			System.out.println("My AutoID is: " + identifier);
		}

	}

	private void answerGreeting() {
		System.out.println("Sending greeting answer");
		MensajeAutoID message = new MensajeAutoID("Hi, I am:" + identifier);
		byte[] bytes = serialize(message);
		try {
			sendMessage(bytes, group_ia, PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private byte[] serialize(Object data) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(data);
			bytes = baos.toByteArray();

			// Close streams
			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	private Object deserialize(byte[] bytes) {
		Object data = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			data = ois.readObject();

			// close streams
			ois.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void sendMessage(byte[] data, InetAddress destAddress, int destPort) throws IOException {
		DatagramPacket packet = new DatagramPacket(data, data.length, destAddress, destPort);

		System.out.println("Sending data to " + destAddress.getHostAddress() + ":" + destPort);
		socket.send(packet);
		System.out.println("Data was sent");

	}

	public DatagramPacket receiveMessage() throws IOException {
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		System.out.println("Data received from " + packet.getAddress() + ":" + packet.getPort());
		return packet;

	}

	@Override
	public void run() {
		System.out.println("Listening...");
		while (true) {
			// Control that the socket is still listening
			if (socket != null) {
				try {
					// Receive
					DatagramPacket receivedPacked = receiveMessage();
					// Deserialize
					Object receivedObject = deserialize(receivedPacked.getData());

					/*
					 * Validate that there are no errors with the data because
					 * the deserialization process could return null
					 */
					if (receivedObject != null) {
						// If receivedObject is an AutoIDMessage
						if (receivedObject instanceof MensajeAutoID) {
							MensajeAutoID message = (MensajeAutoID) receivedObject;
							String messageContent = message.getContent();

							// We are interested only on new members that
							// haven't been identified
							if (messageContent.contains("new member")) {
								System.out.println("received data was a greeting");
								answerGreeting();
							}
						}

						// If we need to validate other kind of objects this is
						// the moment

						// Notify the observers that new data has arrived and
						// pass
						// the data to them
						setChanged();
						notifyObservers(receivedObject);
						clearChanged();
					} else {
						System.out.println("A null Object was received");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public int getIdentifier() {
		// TODO Auto-generated method stub
		return this.identifier;
	}

	public void sendObjectMessage(Object data) {
		byte[] bytes = serialize(data);
		try {
			sendMessage(bytes, group_ia, PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}