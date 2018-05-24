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
import java.net.Socket;

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
    
    private
        boolean
            vivo;
    
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

            while(this.vivo){
                continue;
            }
            
            /* Hilo debe morir. Informar a cliente */
            this.FlujoSalida.writeInt(-1);
            this.FlujoSalida.flush();
            
            /* Cerrar flujos */
            /*this.FlujoEntrada.close()*/;
            /*this.FlujoSalida.close()*/;
        } catch (Exception e) {
            System.out.println(this.getClass() + ": No fue posible continuar: " + e.getMessage());
        }
        
        /* Cerrar hilo */
        this.vivo = false;
    }
    
    
}
