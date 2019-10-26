package dominio;

import dominio.repositorio.RepositorioProducto;
import dominio.excepcion.GarantiaExtendidaException;
import dominio.repositorio.RepositorioGarantiaExtendida;

public class Vendedor {

    private static final int NUMERO_VOCALES_NO_PERMITIDAS = 3;

    private RepositorioProducto repositorioProducto;
    private RepositorioGarantiaExtendida repositorioGarantia;

    public Vendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
        this.repositorioProducto = repositorioProducto;
        this.repositorioGarantia = repositorioGarantia;
    }

    public void generarGarantia(String codigo, String nombreCliente) {
    	existeProducto(codigo);
    	
    	if(tieneGarantia(codigo)) {
    		throw new GarantiaExtendidaException(GarantiaExtendidaException.EL_PRODUCTO_TIENE_GARANTIA);
    	}
    	if (contieneCantidadVocales(codigo) == NUMERO_VOCALES_NO_PERMITIDAS) {
    		throw new GarantiaExtendidaException(GarantiaExtendidaException.PRODUCTO_NO_CUENTA_CON_GARANTIA_EXTENDIDA);
    	}
    	
	    Producto producto = repositorioProducto.obtenerPorCodigo(codigo);
	    GarantiaExtendida garantia = new GarantiaExtendida(producto, nombreCliente);
		garantia.calcularFechaFinGarantia();
		garantia.calcularPrecioGarantia();
		repositorioGarantia.agregar(garantia);
    }

    public boolean tieneGarantia(String codigo) {
        return repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo) != null;
    }
    
    public void existeProducto(String codigo){
    	if (repositorioProducto.obtenerPorCodigo(codigo) == null) {
    		throw new GarantiaExtendidaException(GarantiaExtendidaException.EL_PRODUCTO_NO_EXISTE);
    	}
    }
    
    public int contieneCantidadVocales(String codigo){
    	int contadorVocales = 0;
    	codigo = codigo.toLowerCase();
    	for(int i = 0; i < codigo.length(); i++) {
    		if ((codigo.charAt(i) == 'a') || (codigo.charAt(i) == 'e') || (codigo.charAt(i) == 'i') || (codigo.charAt(i) == 'o')
    				|| (codigo.charAt(i) == 'u')) {
    			contadorVocales ++;
    		}
    	}
    	return contadorVocales;
    }
}
