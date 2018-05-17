package principal;

import java.awt.Robot;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class ReceptorComandos implements Runnable{
    private
        Thread
            Hilo;
    
    private
        Socket
            Zocalo;
    
    private
        Robot
            Automata;

    public ReceptorComandos(Socket Zocalo, Robot Automata) {
        this.Zocalo = Zocalo;
        this.Automata = Automata;
        this.Hilo = new Thread(this);
        
        this.Hilo.start();
    }

    
    
    @Override
    public void run() {
        
    }
    
}
