/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class EnviadorDatos implements Runnable{
    private
        Thread
            Hilo;

    private
        Socket
            Zocalo;
        
    private    
        DataInputStream
            FlujoEntrada;
    
    private    
        DataOutputStream
            FlujoSalida;
    
    private volatile 
        boolean
            vivo = true,
            listo = false,
            continuar = true; /* Bandera para especificar si es necesario detener control */
    
    public EnviadorDatos(Socket Zocalo) {
        /* Pasar socket */
        this.Zocalo = Zocalo;
        
        /* Crear nuevo hilo */
        this.Hilo = new Thread(this, "EnviadorDatos");
        
        /* Iniciar proceso */
        this.Hilo.start();
    }

    /*
        Método para verificar que la clase sigue viva
    */
    public boolean isVivo(){
        return this.vivo;
    }
    
    /*
        Método para detener el control ejercido por esta conexión.
    */
    public void bloquear(boolean continuar){
        this.continuar = !continuar;
    }
    
    /*
        Método para verificar que la clase está lista
    */
    public boolean isListo(){        
        return this.listo;
    }

    @Override
    public void run() {
        Dimension
            Pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        
        int
            ancho = (int)Pantalla.getWidth(),
            alto = (int)Pantalla.getHeight();
        
        try {
            /* Establecer los flujos de entrada y salida desde la conexión */
            this.FlujoEntrada = new DataInputStream(
                this.Zocalo.getInputStream()
            );

            this.FlujoSalida = new DataOutputStream(
                this.Zocalo.getOutputStream()
            );
            
            /* Enviar el tamaño de pantalla */
            this.FlujoSalida.writeInt(ancho);            
            this.FlujoSalida.writeInt(alto);
            this.FlujoSalida.flush();
            
            /* Datos enviados. Clase lista */
            this.listo = true;
            
            /* Iniciar como bloqueada */
            this.bloquear(true);
            
            while(this.vivo){
                if(this.continuar){
                    continue;
                }
            }
            
            /* Hilo debe morir. Informar a cliente */
            this.FlujoSalida.writeInt(-1);
            this.FlujoSalida.flush();
            
            /* Cerrar flujos */
            this.FlujoEntrada.close();
            this.FlujoSalida.close();
        }catch(SocketException se){
            System.out.println("Conexión ha muerto");
        }catch(IOException ioe){
            System.out.println("Flujos han sido cerrados");
        }catch (Exception e) {
            System.out.println(this.getClass() + ": No fue posible continuar: " + e.getMessage());
        }
        
        /* Cerrar hilo */
        this.vivo = false;
    }
    
    
}
