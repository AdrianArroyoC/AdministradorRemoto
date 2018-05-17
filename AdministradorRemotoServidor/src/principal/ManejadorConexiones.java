package principal;

import java.util.ArrayList;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class ManejadorConexiones {
    private
        ArrayList<Conexion>
            Conexiones;
    
    private
        int
            puerto = 4907;

    public ManejadorConexiones() {
        Conexion
            NuevaConexion;
        
        this.Conexiones = new ArrayList<Conexion>();
        
        while(true){
            NuevaConexion = new Conexion(puerto);
            
            /* Nuevo cliente se ha conectado. Continuar */
            this.Conexiones.add(NuevaConexion);
        }
    }
    
    /*
        Método para recuperar una conexión
    */
    public Conexion getConexion(int indice){
        return this.Conexiones.get(indice);
    }
}
