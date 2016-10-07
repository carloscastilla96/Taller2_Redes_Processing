package Comun;

import java.io.Serializable;

/**
 * Created by 1144090943 on 19/09/2016.
 */

public class MensajeAndroid implements Serializable {

	private String mensaje;
	private static final long serialVersionUID = 1L;

	public MensajeAndroid(String mensaje) {

		this.mensaje = mensaje;

	}

	public String getMensaje() {
		return mensaje;
	}

}