package principal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

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
        
        String
            Respuesta;
        
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

            System.out.println("Recibido");
            
            /* Recibir información del servidor */
            if((Respuesta = FlujoEntrada.readUTF()) != null){
                /* La primera respuesta es el ancho */
                this.servidorAncho = Integer.valueOf(Respuesta);
                
                /* Volver a solicitar */
                Respuesta = FlujoEntrada.readUTF();

                /* La segunda respuesta es el alto */
                this.servidorAlto = Integer.valueOf(Respuesta);
                
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
            }else{
                /* Respuesta no válida. Cerrar flujos */
                FlujoEntrada.close();
                FlujoSalida.close();
                
                /* Mostrar ventana de cliente */
                this.Ventana.mostrarVentana(true);
            }
        } catch (Exception e){
            System.out.println(this.getClass() + ": Hubo un error al establecer la conexión: " + e.getMessage());
            
            e.printStackTrace();
        }
    }
}
