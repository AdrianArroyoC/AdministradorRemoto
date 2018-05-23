/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        BufferedReader
            FlujoEntrada;
    
    public ReceptorDatos(Socket Zocalo, VentanaCliente Ventana) {
        /* Asignar socket */
        this.Zocalo = Zocalo;
        
        /* Asignar ventana para control bidireccional */
        this.Ventana = Ventana;
        
        /* Crear nuevo hilo */
        this.Hilo = new Thread(this);
        
        /* Iniciar proceso */
        this.Hilo.start();
    }

    @Override
    public void run() {
        String
            Respuesta;
        
        try{
            this.FlujoEntrada = new BufferedReader(
                new InputStreamReader(
                    this.Zocalo.getInputStream()
                )
            );
            
            while((Respuesta = this.FlujoEntrada.readLine()) != null){
                if(Respuesta == "-1"){
                    break;
                }else{
                    continue;
                }
            }
            
            /* Servidor cerró conexión */
            this.FlujoEntrada.close();
            
            /* Mostrar la ventana */
            this.Ventana.mostrarVentana(true);
            
        } catch(Exception e) {
            System.out.println("No fue posible iniciar el flujo de salida: " + e.getMessage());
        }
    }    
}
