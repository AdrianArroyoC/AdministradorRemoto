package principal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class Conexion {
    private
        Socket
            Zocalo;
    
    private
        EnviadorComandos
            EspacioPantalla;
    
    private
        ReceptorDatos
            RespuestaServidor;
    
    private
        VentanaCliente
            Ventana;
    
    private
        int
            servidorAncho,
            servidorAlto;
    
    private
        DataOutputStream
            FlujoSalida;
            
    private
        DataInputStream
            FlujoEntrada;
                
    public Conexion(String DireccionIPServidor, int puerto, String Nombre, String Apellidos, String Codigo, VentanaCliente Ventana){       
        int
            respuesta;
        
        try{
            /* Establecer el socket */
            this.Zocalo = new Socket(DireccionIPServidor, puerto);

            /* Establecer la ventana para control bidireccional */
            this.Ventana = Ventana;
            
            System.out.println("Conectado con el servidor...");
            
            /* Establecer el flujo de salida de datos hacia el servidor */
            this.FlujoSalida = new DataOutputStream(
                this.Zocalo.getOutputStream()
            );
            
            this.FlujoEntrada = new DataInputStream(
                this.Zocalo.getInputStream()
            );
            
            /* Enviar información del cliente */
            this.FlujoSalida.writeUTF(
                /* IP local */
                InetAddress.getLocalHost().getHostAddress()
            );
            this.FlujoSalida.writeUTF(Codigo);
            this.FlujoSalida.writeUTF(Nombre);
            this.FlujoSalida.writeUTF(Apellidos);
            
            /* Recibir información del servidor */
            respuesta = this.FlujoEntrada.readInt();

            /* La primera respuesta es el ancho */
            this.servidorAncho = Integer.valueOf(respuesta);

            respuesta = this.FlujoEntrada.readInt();       
            
            /* La segunda respuesta es el alto */
            this.servidorAlto = Integer.valueOf(respuesta);

            /* Cerrar flujos locales */
            /*this.FlujoEntrada.close()*/;
            /*this.FlujoSalida.close()*/;
            
            /* Crear un nuevo capturador de comandos que trabaje las peticiones */
            this.EspacioPantalla = new EnviadorComandos(
                this.Zocalo,
                this.Ventana,
                this.servidorAncho,
                this.servidorAlto
            );
            this.RespuestaServidor = new ReceptorDatos(
                this.Zocalo,
                this.Ventana
            );
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,  "Hubo un error al establecer la conexión:"+ e.getMessage());
            //System.out.println(this.getClass() + ": Hubo un error al establecer la conexión: " 
            return;
            //e.printStackTrace();
        }
    }
}
