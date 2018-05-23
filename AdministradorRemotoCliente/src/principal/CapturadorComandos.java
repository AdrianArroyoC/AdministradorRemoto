/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.net.Socket;
import javax.swing.JFrame;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class CapturadorComandos extends JFrame{
    
    private
        EnviadorComandos
            Servidor;

    public CapturadorComandos(Socket Zocalo) {
        /* Detener al cerrar */
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        /* Establecer el enviador para el teclado y el mouse */
        this.Servidor = new EnviadorComandos(Zocalo);
    }
    
}
