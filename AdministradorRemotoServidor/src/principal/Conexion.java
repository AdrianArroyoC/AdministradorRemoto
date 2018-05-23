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
    
    private
        String
            Nombres = "Nombre",
            Apellidos = "Apellido";

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

            /* Cliente se ha conectado. Crear el autómata con el dispositivo */
            Automata = new Robot(DispositivoGraficos);

            /*while(true){*/
            Zocalo = this.ZocaloServidor.accept();

            System.out.println("Cliente conectado...");

            /* Crear el nuevo receptor de comandos */
            this.Cliente = new ReceptorComandos(Zocalo, Automata);
            /*}*/
        }catch (Exception e){
            System.out.println("Hubo un error en la creación de conexión: " + e.getMessage());
        }
    }
    
    /*
        Método provisional para recuperar el nombre del cliente
    */
    public String getNombre(){
        return (this.Nombres + " " + this.Apellidos);
    }
    
    /*
        Método provisional para recuperar los nombres inciales
    */
    public String getNombres(){
        return this.Nombres;
    }
    
    /*
        Método provisional para recuperar los apellidos
    */
    public String getApellidos(){
        return this.Apellidos;
    }
    
    /*
        Recuperar el cliente de esta conexión para control bidireccional
    */
    public ReceptorComandos getCliente(){
        return this.Cliente;
    }
}

