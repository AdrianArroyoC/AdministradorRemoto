/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
        CapturadorComandos
            EspacioPantalla;
            
    public Conexion(String DireccionIPServidor, int puerto, String DireccionIPCliente, String Nombre, String Apellidos, String Codigo){
        DataOutputStream
            FlujoSalida;
        
        try{
            /* Establecer el socket */
            this.Zocalo = new Socket(DireccionIPServidor, puerto);
            
            System.out.println("Conectado con el servidor...");
            
            /* Establecer el flujo de salida de datos hacia el servidor */
            FlujoSalida = new DataOutputStream(
                this.Zocalo.getOutputStream()
            );
            
            /* Enviar información del cliente */
            FlujoSalida.writeUTF(DireccionIPCliente);
            FlujoSalida.writeUTF(Codigo);
            FlujoSalida.writeUTF(Nombre);
            FlujoSalida.writeUTF(Apellidos);
            
            /* Datos enviados. Cerrar el flujo */
            FlujoSalida.close();
            
            /*Crear un nuevo capturador de comandos que trabaje las peticiones */
            this.EspacioPantalla = new CapturadorComandos(this.Zocalo);
        } catch (Exception e){
            System.out.println("Hubo un error al establecer la conexión: " + e.getMessage());
        }
    }
}
