package dominio;

import java.util.Calendar;
import java.util.Date;

public class GarantiaExtendida {
	
	private static final int VALOR_ESTABLECIDO_PARA_DETERMINAR_GARANTIA = 500000;
	private static final int DIAS_GARANTIA_SI_SOBREPASA_EL_VALOR_ESTABLECIDO = 200;
	private static final int DIAS_GARANTIA_NO_SOBREPASA_EL_VALOR_ESTABLECIDO = 100;
	private static final double PORCENTAJE_SI_SOBREPASA_EL_VALOR_ESTABLECIDO = 0.2;
	private static final double PORCENTAJE_NO_SOBREPASA_EL_VALOR_ESTABLECIDO = 0.1;
	private static final int DIA_EQUIVALENTE_A_DOMINGO = 1;
	private static final int DIA_EQUIVALENTE_A_LUNES = 2;

    private Producto producto;
    private Date fechaSolicitudGarantia;
    private Date fechaFinGarantia;
    private double precioGarantia;
    private String nombreCliente;

    public GarantiaExtendida(Producto producto) {
        this.fechaSolicitudGarantia = new Date();
        this.producto = producto;
    }
    
	public GarantiaExtendida(Producto producto, String nombreCliente) {
	    this.fechaSolicitudGarantia = new Date();
	    this.producto = producto;
	    this.nombreCliente = nombreCliente;
	 }

    public GarantiaExtendida(Producto producto, Date fechaSolicitudGarantia, Date fechaFinGarantia,
            double precioGarantia, String nombreCliente) {

        this.producto = producto;
        this.fechaSolicitudGarantia = fechaSolicitudGarantia;
        this.fechaFinGarantia = fechaFinGarantia;
        this.precioGarantia = precioGarantia;
        this.nombreCliente = nombreCliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public Date getFechaSolicitudGarantia() {
        return fechaSolicitudGarantia;
    }

    public Date getFechaFinGarantia() {
        return fechaFinGarantia;
    }

    public double getPrecioGarantia() {
        return precioGarantia;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public boolean precioEsMayorAlEstablecido() {
    	return producto.getPrecio() > VALOR_ESTABLECIDO_PARA_DETERMINAR_GARANTIA;
    }
    
    public int obtenerDiasDeGarantia() {
    	int diasDeGarantia = 0;
    	diasDeGarantia = precioEsMayorAlEstablecido() ? DIAS_GARANTIA_SI_SOBREPASA_EL_VALOR_ESTABLECIDO : DIAS_GARANTIA_NO_SOBREPASA_EL_VALOR_ESTABLECIDO;    
    	return diasDeGarantia;
    }
    
    public void calcularPrecioGarantia() {
    	double precioProducto = producto.getPrecio();
    	double porcentajeGarantia = precioEsMayorAlEstablecido() ? PORCENTAJE_SI_SOBREPASA_EL_VALOR_ESTABLECIDO : PORCENTAJE_NO_SOBREPASA_EL_VALOR_ESTABLECIDO;
    	this.precioGarantia = precioProducto * porcentajeGarantia;
    } 
    
    public void calcularFechaFinGarantia() {
    	int contadorDiasGarantia = obtenerDiasDeGarantia();
		int diaDeLaSemana = 0;
		int contadorDias = 0;
		
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaSolicitudGarantia);
		
		while (contadorDias < contadorDiasGarantia) {
			diaDeLaSemana = calendario.get(Calendar.DAY_OF_WEEK);
			if (DIA_EQUIVALENTE_A_LUNES != diaDeLaSemana) {
				contadorDias++;
			}
			calendario.add(Calendar.DATE, 1);
		}
		
		diaDeLaSemana = calendario.get(Calendar.DAY_OF_WEEK);
		
		if (DIA_EQUIVALENTE_A_DOMINGO == diaDeLaSemana) {
			//El dos está porque si cae domingo, al lunes no cuenta por reglas de negocio, tocaría el martes.
			calendario.add(Calendar.DATE, 2);
		}
		
		this.fechaFinGarantia = calendario.getTime();
    }

}
