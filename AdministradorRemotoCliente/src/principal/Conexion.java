package principal;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JFrame;

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
            
    public Conexion(String DireccionIPServidor, int puerto, String Nombre, String Apellidos, String Codigo, VentanaCliente Ventana){
        DataOutputStream
            FlujoSalida;
        
        JFrame
            Pantalla;
        
        try{
            /* Establecer el socket */
            this.Zocalo = new Socket(DireccionIPServidor, puerto);

            /* Establecer la ventana para control bidireccional */
            this.Ventana = Ventana;
            
            System.out.println("Conectado con el servidor...");

            /* Establecer la pantalla de captura */
            Pantalla = new JFrame();
            Pantalla.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            Pantalla.setUndecorated(true);
            Pantalla.setVisible(true);
            Pantalla.setAlwaysOnTop(true);
            
            /* Establecer el flujo de salida de datos hacia el servidor */
            FlujoSalida = new DataOutputStream(
                this.Zocalo.getOutputStream()
            );
            
            /* Enviar información del cliente */
            FlujoSalida.writeUTF(
                /* IP local */
                InetAddress.getLocalHost().getHostAddress()
            );
            FlujoSalida.writeUTF(Codigo);
            FlujoSalida.writeUTF(Nombre);
            FlujoSalida.writeUTF(Apellidos);
            
            /* Datos enviados. Cerrar el flujo */
            FlujoSalida.close();
            
            /*Crear un nuevo capturador de comandos que trabaje las peticiones */
            this.EspacioPantalla = new EnviadorComandos(this.Zocalo, this.Ventana);
            this.RespuestaServidor = new ReceptorDatos(this.Zocalo, this.Ventana);
        } catch (Exception e){
            System.out.println("Hubo un error al establecer la conexión: " + e.getMessage());
        }
    }
}
