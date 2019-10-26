package dominio.unitaria;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import dominio.GarantiaExtendida;
import dominio.Producto;
import testdatabuilder.ProductoTestDataBuilder;

public class GarantiaExtendidaTest {
	
	private static final int DIAS_GARANTIA_SI_SOBREPASA_EL_VALOR_ESTABLECIDO = 200;
	private static final int DIAS_GARANTIA_NO_SOBREPASA_EL_VALOR_ESTABLECIDO = 100;
	private static final double PORCENTAJE_SI_SOBREPASA_EL_VALOR_ESTABLECIDO = 0.2;
	private static final double PORCENTAJE_NO_SOBREPASA_EL_VALOR_ESTABLECIDO = 0.1;
	private static final int PRECIO_MAYOR_PRODUCTO_TEST = 780000;
	private static final int PRECIO_MENOR_PRODUCTO_TEST = 440000;
	
	@Test
	public void precioEsMayorAlEstablecidoTest() {
		
		//Arrange
		Producto producto;
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		producto = productoTestDataBuilder.conPrecio(PRECIO_MAYOR_PRODUCTO_TEST).build();
		GarantiaExtendida garantiaExtendida = new GarantiaExtendida(producto);
				
		//Act
		boolean result = garantiaExtendida.precioEsMayorAlEstablecido();
				
		//Assert
		assertTrue(result);
	}
	
	@Test
	public void precioEsMenorAlEstablecidoTest() {		
		//Arrange
		Producto producto;
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		producto = productoTestDataBuilder.conPrecio(PRECIO_MENOR_PRODUCTO_TEST).build();

		GarantiaExtendida garantiaExtendida = new GarantiaExtendida(producto);
		
		//Act
		boolean result = garantiaExtendida.precioEsMayorAlEstablecido();
		
		//Assert
		assertFalse(result);
	}
	
	@Test
	public void diasDeGarantiaSiPrecioEsMayorTest() {
		
		//Arrange
			Producto producto;
			ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
			producto = productoTestDataBuilder.conPrecio(PRECIO_MAYOR_PRODUCTO_TEST).build();
			GarantiaExtendida garantiaExtendida = new GarantiaExtendida(producto);
		
		//Act
			int result = garantiaExtendida.obtenerDiasDeGarantia(); 
			
		//Assert
			assertEquals(DIAS_GARANTIA_SI_SOBREPASA_EL_VALOR_ESTABLECIDO,result);
	}
	
	@Test
	public void diasDeGarantiaSiPrecioEsMenorTest() {
		
			//Arrange
			Producto producto;
			ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
			producto = productoTestDataBuilder.conPrecio(PRECIO_MENOR_PRODUCTO_TEST).build();
			GarantiaExtendida garantiaExtendida = new GarantiaExtendida(producto);
			
			//Act 
			int result = garantiaExtendida.obtenerDiasDeGarantia();
			
			//Assert
			assertEquals(DIAS_GARANTIA_NO_SOBREPASA_EL_VALOR_ESTABLECIDO,result);
	}
	
	@Test
	public void precioGarantiaSiPrecioEsMayorEntoncesDevuelvePorcentajeMayorTest() {
		
		//Arrange
			double cantidadEsperada = PRECIO_MAYOR_PRODUCTO_TEST * PORCENTAJE_SI_SOBREPASA_EL_VALOR_ESTABLECIDO;
			Producto producto;
			ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
			producto = productoTestDataBuilder.conPrecio(PRECIO_MAYOR_PRODUCTO_TEST).build();
			GarantiaExtendida garantiaExtendida = new GarantiaExtendida(producto);
			
			garantiaExtendida = Mockito.spy(garantiaExtendida);
			Mockito.doReturn(true).when(garantiaExtendida).precioEsMayorAlEstablecido();
				
		//Act
			garantiaExtendida.calcularPrecioGarantia();
				
		//Assert
			assertEquals(cantidadEsperada, garantiaExtendida.getPrecioGarantia(), 0);
	
	}
	
	@Test
	public void precioGarantiaSiPrecioEsMenorEntoncesDevuelvePorcentajeMenorTest(){
		
		//Arrange
		double cantidadEsperada = PRECIO_MENOR_PRODUCTO_TEST * PORCENTAJE_NO_SOBREPASA_EL_VALOR_ESTABLECIDO;
		Producto producto;
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		producto = productoTestDataBuilder.conPrecio(PRECIO_MENOR_PRODUCTO_TEST).build();
		GarantiaExtendida garantiaExtendida = new GarantiaExtendida(producto);
		garantiaExtendida = Mockito.spy(garantiaExtendida);
		Mockito.doReturn(false).when(garantiaExtendida).precioEsMayorAlEstablecido();
		
		//Act
		garantiaExtendida.calcularPrecioGarantia();
		
		//Assert
		assertEquals(cantidadEsperada, garantiaExtendida.getPrecioGarantia(), 0);		
	}
}
