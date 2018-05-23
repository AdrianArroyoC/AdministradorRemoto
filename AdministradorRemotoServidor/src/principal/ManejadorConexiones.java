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
            puerto = 9090;
    
    private
        Thread
            Hilo;
    
    private
        VentanaServidor
            Ventana;

    public ManejadorConexiones(VentanaServidor Ventana) {
        /* Para permitir control de ventanas */
        this.Ventana = Ventana;
        
        /* Inicializar el contenedor de conexiones */        
        this.Conexiones = new ArrayList<Conexion>();
        
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
    
    /*
        Método para recuperar un arreglo de todas las conexiones
    */
    public Conexion[] getConexiones(){
        return this.Conexiones.toArray(
            new Conexion[this.Conexiones.size()]
        );
    }

    @Override
    public void run() {
        Conexion
            NuevaConexion;

        while(true){
            NuevaConexion = new Conexion(puerto);
            
            /* Nuevo cliente se ha conectado. Añadir a la lista */
            this.Conexiones.add(NuevaConexion);
            
            /* Llamar a la ventana para actualizar interfaz */
            this.Ventana.agregarQuitarBotones();
        }
    }
}
