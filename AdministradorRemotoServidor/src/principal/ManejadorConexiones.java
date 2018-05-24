package principal;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class ManejadorConexiones{
    private
        ServerSocket
            ZocaloServidor;

    private
        ArrayList<Conexion>
            Conexiones;
    
    private
        int
            puerto = 9090;
    
    private
        VentanaServidor
            Ventana;
    
    private
        AgregadorClientes
            Agregador;
    
    private
        RemovedorClientes
            Removedor;

    public ManejadorConexiones(VentanaServidor Ventana) {
        /* Para permitir control de ventanas */
        this.Ventana = Ventana;
        
        /* Inicializar el contenedor de conexiones */        
        this.Conexiones = new ArrayList<Conexion>();
        
        /* Inicializar los modificadores */
        this.Agregador = new AgregadorClientes(this);
        this.Removedor = new RemovedorClientes(this);
    }

    /*
        Método para recuperar una conexión. Retorna null si no existe
    */
    public Conexion getConexion(String DireccionIP){
        Conexion
            ClienteTemp,
            Cliente = null;

        Iterator
            Iterador = this.Conexiones.iterator();
        
        /* Buscar por la IP */        
        while(Iterador.hasNext()){
            ClienteTemp = (Conexion)Iterador.next();
            
            /* Si la IP coincide, retornar */
            if(DireccionIP.equals(ClienteTemp.getDireccionIP())){
                Cliente = ClienteTemp;
            }
        }

        return Cliente;
    }
    
    /*
        Método para recuperar un arreglo de todas las conexiones
    */
    public Conexion[] getConexiones(){
        return this.Conexiones.toArray(
            new Conexion[this.Conexiones.size()]
        );
    }

    /*
        Buscar conexiones inactivas y actualizar
    */
    public void purgarConexiones(){
        Conexion
            ClienteTemp;
        
        boolean
            actualizar;
       
        ArrayList<Conexion>
            CopiaConexiones;
        
        Iterator
            Iterador;
        
        while(true){
            actualizar = false;
            
            /* Copiar conexiones actuales */
            CopiaConexiones = (ArrayList<Conexion>)this.Conexiones.clone();
        
            /* Obtener iterador */
            Iterador = CopiaConexiones.iterator();

            synchronized (this.Conexiones) {
                while(Iterador.hasNext()){
                    ClienteTemp = (Conexion)Iterador.next();

                    /* Si el cliente no está vivo, remover */
                    if(!ClienteTemp.isVivo()){
                        /* Necesario volver a cargar botones */
                        actualizar = true;

                        /* Remover actual */
                        Iterador.remove();
                    }
                }
            }

            if(actualizar){
                this.Conexiones = CopiaConexiones;

                /* Llamar a la ventana para actualizar interfaz */
                this.Ventana.agregarQuitarBotones();
            }
        }
    }
    
    /*
        Buscar conexiones nuevas y actualizar
    */
    public void agregarConexiones(){
        Conexion
            NuevaConexion;

        try {
            /* Crear socket del servidor */
            this.ZocaloServidor = new ServerSocket(this.puerto);

            /* Reusar socket */
            this.ZocaloServidor.setReuseAddress(true);


            while(true){
                NuevaConexion = new Conexion(this.ZocaloServidor);

                /* Esperar a que esté listo */
                while(!NuevaConexion.isListo()){
                    continue;
                }
                
                /* Conexión está lista */
                if(this.getConexion(NuevaConexion.getDireccionIP()) == null){
                    /* Nuevo cliente se ha conectado. Añadir a la lista */
                    synchronized (this.Conexiones) {
                        this.Conexiones.add(NuevaConexion);
                    }
                    
                    /* Llamar a la ventana para actualizar interfaz */
                    this.Ventana.agregarQuitarBotones();
                }
            }
        } catch (Exception e) {
            System.out.println(this.getClass() + ": No pudo ser creada una nueva conexión: " + e.getMessage());
        }
    }
}
