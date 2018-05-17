package principal;

import java.awt.Robot;
import java.net.Socket;

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
