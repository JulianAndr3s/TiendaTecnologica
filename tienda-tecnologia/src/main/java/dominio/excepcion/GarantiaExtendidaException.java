package dominio.excepcion;

public class GarantiaExtendidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";
	public static final String EL_PRODUCTO_NO_EXISTE = "Este producto no existe";
	public static final String PRODUCTO_NO_CUENTA_CON_GARANTIA_EXTENDIDA = "Este producto no cuenta con garantía extendida";
	
	public GarantiaExtendidaException(String message) {
		super(message);
	}
}
