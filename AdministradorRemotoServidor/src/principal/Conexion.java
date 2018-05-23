/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        String
            Nombres = "Nombre",
            Apellidos = "Apellido",
            Codigo = "01234567890",
            DireccionIP = "127.0.0.1";

    public Conexion(int puerto){
        Socket
            Zocalo;
        
        Robot
            Automata;

        GraphicsDevice
            DispositivoGraficos;
        
        DataInputStream
            FlujoEntrada;
        
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
            
            /* Crear el flujo de salida */
            FlujoEntrada = new DataInputStream(
                Zocalo.getInputStream()
            );

            /* Obtener los datos */
            this.DireccionIP = FlujoEntrada.readUTF();
            this.Codigo = FlujoEntrada.readUTF();
            this.Nombres = FlujoEntrada.readUTF();
            this.Apellidos = FlujoEntrada.readUTF();            

            /* Datos obtenidos. Cerrar el flujo */
            FlujoEntrada.close();

            /* Crear el nuevo receptor de comandos */
            this.Cliente = new ReceptorComandos(Zocalo, Automata);
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
        Método provisional para recuperar el código
    */
    public String getCodigo(){
        return this.Codigo;
    }
    
    /*
        Método provisional para recuperar la IP
    */
    public String getDireccionIP(){
        return this.DireccionIP;
    }
    
    /*
        Recuperar el cliente de esta conexión para control bidireccional
    */
    public ReceptorComandos getCliente(){
        return this.Cliente;
    }
}

