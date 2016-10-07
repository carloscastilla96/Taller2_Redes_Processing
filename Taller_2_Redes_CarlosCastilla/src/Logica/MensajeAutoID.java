package Logica;

import java.io.Serializable;

public class MensajeAutoID implements Serializable {

	private static final long serialVersionUID = 1L;

	private String content;

	public MensajeAutoID(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
