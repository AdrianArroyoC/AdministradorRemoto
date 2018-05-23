/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.net.Socket;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class Conexion {
    Socket
        Zocalo;
    
    EnviadorComandos
        Servidor;
            
    public Conexion(String DireccionIp, int puerto){
        try{
            /* Establecer el socket */
            this.Zocalo = new Socket(DireccionIp, puerto);
            
            System.out.println("Conectado con el servidor...");
            
            /* Establecer el enviador */
            this.Servidor = new EnviadorComandos(this.Zocalo);
        } catch (Exception e){
            System.out.println("Hubo un error al establecer la conexión: " + e.getMessage());
        }
    }
}
