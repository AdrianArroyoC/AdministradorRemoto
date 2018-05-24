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
                
    public Conexion(String DireccionIPServidor, int puerto, String Nombre, String Apellidos, String Codigo, VentanaCliente Ventana){
        DataOutputStream
            FlujoSalida;
    
        DataInputStream
            FlujoEntrada;
        
        int
            respuesta;
        
        try{
            /* Establecer el socket */
            this.Zocalo = new Socket(DireccionIPServidor, puerto);

            /* Establecer la ventana para control bidireccional */
            this.Ventana = Ventana;
            
            System.out.println("Conectado con el servidor...");
            
            /* Establecer el flujo de salida de datos hacia el servidor */
            FlujoSalida = new DataOutputStream(
                this.Zocalo.getOutputStream()
            );
            
            FlujoEntrada = new DataInputStream(
                this.Zocalo.getInputStream()
            );
            
            /* Enviar información del cliente */
            FlujoSalida.writeUTF(
                /* IP local */
                InetAddress.getLocalHost().getHostAddress()
            );
            FlujoSalida.writeUTF(Codigo);
            FlujoSalida.writeUTF(Nombre);
            FlujoSalida.writeUTF(Apellidos);
            
            /* Recibir información del servidor */
            respuesta = FlujoEntrada.readInt();

            /* La primera respuesta es el ancho */
            this.servidorAncho = Integer.valueOf(respuesta);

            respuesta = FlujoEntrada.readInt();       
            
            /* La segunda respuesta es el alto */
            this.servidorAlto = Integer.valueOf(respuesta);

            /* Crear un nuevo capturador de comandos que trabaje las peticiones */
            this.EspacioPantalla = new EnviadorComandos(
                FlujoEntrada,
                FlujoSalida,
                this.Ventana,
                this.servidorAncho,
                this.servidorAlto
            );
            this.RespuestaServidor = new ReceptorDatos(
                FlujoEntrada,
                FlujoSalida,
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
