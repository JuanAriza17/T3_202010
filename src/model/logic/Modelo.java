package model.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.IListaEncadenada;
import model.data_structures.ListaEncadenada;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo { 
	
	/**
	 * Atributos del modelo del mundo.
	 */
	private IListaEncadenada<Comparendo> listaComparendos;
	
		
	/**
	 * Constructor del modelo del mundo con capacidad predefinida.
	 * @post: Inicializa la lista de comparendos vac�a.
	 */
	public Modelo()
	{
		listaComparendos = new ListaEncadenada<Comparendo>();
	}

	/**
	 * M�todo que retorna la lista de comparendos.
	 * @return Lista de comaparendos.
	 */
	public IListaEncadenada<Comparendo> darLista()
	{
		return listaComparendos;
	}
	
	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo .
	 * @return numero de elementos presentes en el modelo.
	 */
	public int darLongitud()
	{
		return listaComparendos.darLongitud();
	}
		
	/**
	 * Agregar dato al inicio.
	 * @param dato Comparendo que llega por par�metro.
	 */
	public void agregarInicio(Comparendo dato)
	{	
		listaComparendos.agregarInicio(dato);
	}
	
	/**
	 * Agregar dato al final.
	 * @param dato Comparendo que llega por par�metro.
	 */
	public void agregarFinal(Comparendo dato)
	{
		listaComparendos.agregarFinal(dato);
	}
	
	/**
	 * Requerimiento de agregar dato.
	 * @param dato Comparendo que llega por par�metro.
	 */
	public void agregar(Comparendo dato)
	{
		listaComparendos.agregar(dato);
	}
	
	
	/**
	 * Requerimiento buscar dato.
	 * @param dato Dato a buscar.
	 * @return dato Comparendo encontrado.
	 */
	public Comparendo buscar(Comparendo dato)
	{
		return listaComparendos.buscar(dato);
	}
	
	
	/**
	 * Elimina un dato.
	 * @param dato Dato a eliminar.
	 * @return dato Comparendo eliminado.
	 */
	public Comparendo eliminar(Comparendo dato)
	{
		return listaComparendos.eliminar(dato);
	}
	
	/**
	 * Elimina el �ltimo dato
	 * @return dato eliminado
	 */
	public Comparendo eliminarUltimo()
	{
		return listaComparendos.eliminarUltimo();
	}
	
	/**
	 * Elimina el primer dato
	 * @return dato eliminado
	 */
	public Comparendo eliminarPrimero()
	{
		return listaComparendos.eliminarPrimero();
	}
	
	/**
	 * Da el primer comparendo de la lista
	 * @return primer dato
	 */
	public Comparendo darPrimerComparendo()
	{
		return listaComparendos.darPrimero().darElemento();
	}
	
	/**
	 * Da el ultimo comparendo de la lista
	 * @return ultimo dato
	 */
	public Comparendo darUltimoComparendo()
	{
		return listaComparendos.darUltimo().darElemento();
	}
	
	/**
	 * M�todo que retorna el arreglo de elementos. Dicho arreglo retornado ser� comparable.
	 * @return Arreglo de elementos que es comparable.
	 */
	public Comparable[] copiarComparendos()
	{		
		return listaComparendos.darArreglo();
	}
	
	/**
	 * M�todo que ordena por el algoritmo de ShellSort el arreglo comparable que llega por par�metro.
	 * @param a Arreglo de elementos que es comparable y llega por par�metro.
	 */
	public void shellSort(Comparable[] a)
	{
		Ordenamientos.shellSort(a);
	}
	
	/**
	 * M�todo que ordena por el algoritmo de MergeSort el arreglo comparable que llega por par�metro.
	 * @param a Arreglo de elementos que es comparable y llega por par�metro.
	 */
	public void mergeSort(Comparable[] a)
	{
		Ordenamientos.mergeSort(a);
	}
	
	/**
	 * M�todo que ordena por el algoritmo de QuickSort el arreglo comparable que llega por par�metro.
	 * @param a Arreglo de elementos que es comparable y llega por par�metro.
	 */
	public void quickSort(Comparable[] a)
	{
		Ordenamientos.quickSort(a);
	}
	/**
	 * M�todo que carga los comparendos
	 * @param ruta Rita archivo con los comparendos
	 * @throws FileNotFoundException si no encuentra el archivo
	 */
	public void cargarComparendos(String ruta) throws FileNotFoundException, ParseException 
	{
		 File archivo = new File(ruta);
		 
		 
		 JsonReader lector = new JsonReader(new FileReader(archivo));
		 JsonObject obj = JsonParser.parseReader(lector).getAsJsonObject();
		 
		 JsonArray arregloComparendos = obj.get("features").getAsJsonArray();  
		 
		 listaComparendos = new ListaEncadenada<Comparendo>();
		 
		 SimpleDateFormat parser = new SimpleDateFormat("yyyy/MM/dd");
		 
		 for (JsonElement e: arregloComparendos) 	
		 {
			
			JsonObject propiedades = e.getAsJsonObject().get("properties").getAsJsonObject();
			
			int id = propiedades.get("OBJECTID").getAsInt();
			String f = propiedades.get("FECHA_HORA").getAsString();
			Date fecha = parser.parse(f);
			String vehiculo = propiedades.get("CLASE_VEHI").getAsString();
			String servicio = propiedades.get("TIPO_SERVI").getAsString();
			String infraccion = propiedades.get("INFRACCION").getAsString();
			String descripcion = propiedades.get("DES_INFRAC").getAsString();
			String localidad = propiedades.get("LOCALIDAD").getAsString();

			JsonObject geometria = e.getAsJsonObject().get("geometry").getAsJsonObject();
			JsonArray coords = geometria.get("coordinates").getAsJsonArray();
			double[] coordenadas = new double[2];
			
			for (int j = 0; j < coordenadas.length; j++) 
			{
				coordenadas[j]=coords.get(j).getAsDouble();
			}
						
			Comparendo comparendo = new Comparendo(id, fecha, vehiculo, servicio, infraccion, descripcion, localidad,coordenadas);
			
			agregarFinal(comparendo);
		 }
	}

}
