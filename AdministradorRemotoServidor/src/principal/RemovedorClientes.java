/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class RemovedorClientes implements Runnable{
private
        Thread
            Hilo;
    
    private
        ManejadorConexiones
            CarteraClientes;

    public RemovedorClientes(ManejadorConexiones CarteraClientes) {
        /* Trabajar con el manejador */
        this.CarteraClientes = CarteraClientes;
        
        /* Crear un nuevo hilo */
        this.Hilo = new Thread(this, "RemovedorClientes");
        
        /* Iniciar hilo */
        this.Hilo.start();
    }

    @Override
    public void run() {
        while(true){
            this.CarteraClientes.purgarConexiones();
        }
    }
}
