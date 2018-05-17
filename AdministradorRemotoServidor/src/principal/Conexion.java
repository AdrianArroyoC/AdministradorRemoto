/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Robot;
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
            
            System.out.println("Cliente conectado...");
            
            /* Obtener el dispositivo de gráficos a controlar */
            DispositivoGraficos = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

            /* Cliente se ha conectado. Crear el autómata con el dispositivo */
            Automata = new Robot(DispositivoGraficos);

            /*while(true){*/
            Zocalo = this.ZocaloServidor.accept();

            /* Crear el nuevo receptor de comandos */
            this.Cliente = new ReceptorComandos(Zocalo, Automata);
            /*}*/
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    /*
        Recuperar el cliente de esta conexión para control bidireccional
    */
    public ReceptorComandos getCliente(){
        return this.Cliente;
    }
}

