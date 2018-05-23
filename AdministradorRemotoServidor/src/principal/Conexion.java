package principal;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Robot;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class Conexion {
    private
        ServerSocket
            ZocaloServidor;
    
    private
        ReceptorComandos
            Cliente;
    
    private
        EnviadorDatos
            Enviador;
    
    private
        DataInputStream
            FlujoEntrada;
    
    private
        DataOutputStream
            FlujoSalida;
    
    private
        boolean
            vivo;

    public Conexion(int puerto){
        Socket
            Zocalo;
        
        Robot
            Automata;

        GraphicsDevice
            DispositivoGraficos;

        try{
            System.out.println("Esperando conexión del cliente...");
            
            /* Crear socket del servidor */
            this.ZocaloServidor = new ServerSocket(puerto);
            
            /* Obtener el dispositivo de gráficos a controlar */
            DispositivoGraficos = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

            /* Crear el autómata con el dispositivo */
            Automata = new Robot(DispositivoGraficos);

            /* Esperar cliente */
            Zocalo = this.ZocaloServidor.accept();

            /* Cliente se ha conectado. */
            System.out.println("Cliente conectado...");
            
            /* Crear el flujo de entrada */
            this.FlujoEntrada = new DataInputStream(
                Zocalo.getInputStream()
            );
            
            /* Crear el flujo de salida */
            this.FlujoSalida = new DataOutputStream(
                Zocalo.getOutputStream()
            );

            /* Crear el nuevo receptor de comandos */
            this.Cliente = new ReceptorComandos(
                this.FlujoEntrada,
                this.FlujoSalida,
                Automata
            );
            
            /* Crear el nuevo enviador de datos */
            this.Enviador = new EnviadorDatos(
                this.FlujoEntrada,
                this.FlujoSalida
            );
        }catch (Exception e){
            /* Error. Conexión muerta */
            this.vivo = false;

            /* Informar en consola */
            System.out.println("Hubo un error en la creación de conexión: " + e.getMessage());
        }
    }
    
    /*
        Verificar que conexión está viva
    */
    public boolean isVivo(){
        /* Si los enviadores están vivos, esta conexión está viva */
        if(!this.Cliente.isVivo() || !this.Enviador.isVivo()){
            this.vivo = false;
        }
        
        /* Retornar */
        return this.vivo;
    }
    
    /*
        Método para recuperar el nombre del cliente
    */
    public String getNombre(){
        return this.Cliente.getNombre();
    }
    
    /*
        Método para recuperar los nombres inciales
    */
    public String getNombres(){
        return this.Cliente.getNombres();
    }
    
    /*
        Método para recuperar los apellidos
    */
    public String getApellidos(){
        return this.Cliente.getApellidos();
    }
    
    /*
        Método para recuperar el código
    */
    public String getCodigo(){
        return this.Cliente.getCodigo();
    }
    
    /*
        Método para recuperar la IP
    */
    public String getDireccionIP(){
        return this.Cliente.getDireccionIP();
    }
    
    /*
        Recuperar el cliente de esta conexión para control bidireccional
    */
    public ReceptorComandos getCliente(){
        return this.Cliente;
    }
}

