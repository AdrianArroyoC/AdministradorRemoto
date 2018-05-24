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
public class ReceptorDatos implements Runnable{
    private
        Thread
            Hilo;

    private
        Socket
            Zocalo;
    
    private
        VentanaCliente
            Ventana;
    
    private
        DataOutputStream
            FlujoSalida;
    
    private
        DataInputStream
            FlujoEntrada;
    
    private volatile
        boolean
            vivo,
            listo;
    
    public ReceptorDatos(Socket Zocalo, VentanaCliente Ventana) {
        /* Pasar socket */
        this.Zocalo = Zocalo;
        
        /* Asignar ventana para control bidireccional */
        this.Ventana = Ventana;
        
        /* Crear nuevo hilo */
        this.Hilo = new Thread(this);
        
        /* Iniciar proceso */
        this.Hilo.start();
    }
    
    /*
        Método para identificar que la clase está lista
    */
    public boolean isListo(){
        return this.listo;
    }

    @Override
    public void run() {
        int
            respuesta;
        
        /* Iniciar función */
        this.vivo = true;
        
        try{
            /* Establecer los flujos de entrada y salida desde la conexión */
            this.FlujoEntrada = new DataInputStream(
                Zocalo.getInputStream()
            );

            this.FlujoSalida = new DataOutputStream(
                Zocalo.getOutputStream()
            );
            
            /* Hilo preparado */
            this.listo = true;
            
            while(this.vivo){
                respuesta = this.FlujoEntrada.readInt();
                
                if(respuesta == -1){
                    break;
                }
            }
            
            /* Servidor cerró conexión. Cerrar flujos */
            this.FlujoEntrada.close();
            this.FlujoSalida.close();
            
            /* Mostrar la ventana */
            this.Ventana.mostrarVentana(true);
        } catch(Exception e) {
            System.out.println(this.getClass() + ": No fue posible continuar: " + e.getMessage());
            
            e.printStackTrace();
        }
    }    
}
