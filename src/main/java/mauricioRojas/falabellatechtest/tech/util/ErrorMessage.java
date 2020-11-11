package mauricioRojas.falabellatechtest.tech.util;

public class ErrorMessage {
	
	public static final String ERRORCODE_NOTVALIDFORMAT = "01";
	
	public static final String ERRORLAYER_RESTLAYER = "01";

	private String errorCode;
    private String layer;
    private String message;
    private String messageException;
    

    public ErrorMessage(String errorCode, String layer, String message, String messageException) {
		super();
		this.errorCode = errorCode;
		this.layer = layer;
		this.message = message;
		this.messageException = messageException;
	}
    
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageException() {
		return messageException;
	}
	public void setMessageException(String messageException) {
		this.messageException = messageException;
	}

	@Override
	public String toString() {
		return "ErrorMessage [errorCode=" + errorCode + ", layer=" + layer + ", message=" + message
				+ ", messageException=" + messageException + "]";
	}
	
	
	
    
    
}