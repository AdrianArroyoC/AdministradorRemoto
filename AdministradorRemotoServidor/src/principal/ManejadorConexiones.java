package principal;

import java.util.ArrayList;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class ManejadorConexiones implements Runnable{
    private
        ArrayList<Conexion>
            Conexiones;
    
    private
        int
            puerto = 4907;
    
    private
        Thread
            Hilo;

    public ManejadorConexiones() {
        /* Crear el hilo */
        this.Hilo = new Thread(this);
        
        /* Iniciar proceso para evitar que la ventana se trabe*/
        this.Hilo.start();
    }
    
    /*
        Método para recuperar una conexión
    */
    public Conexion getConexion(int indice){
        return this.Conexiones.get(indice);
    }

    @Override
    public void run() {
        Conexion
            NuevaConexion;
        
        this.Conexiones = new ArrayList<Conexion>();
        
        while(true){
            NuevaConexion = new Conexion(puerto);
            
            /* Nuevo cliente se ha conectado. Añadir a la lista */
            this.Conexiones.add(NuevaConexion);
        }
    }
}
