package principal;

import java.awt.Robot;
import java.net.Socket;
import java.util.Scanner;

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
    
    private
        boolean
            continuar, /* Bandera para especificar si es necesario detener control */
            vivo; /* Bandera para mantener el hilo vivo. Ponerlo en falso acabará con la sesión del usuario */

    public ReceptorComandos(Socket Zocalo, Robot Automata) {
        this.Zocalo = Zocalo;
        this.Automata = Automata;
        this.Hilo = new Thread(this);
        this.continuar = true;
        this.vivo = true;
        
        this.Hilo.start();
    }
    
    /*
        Método para detener el control ejercido por esta conexión.
    */
    public void bloquear(boolean continuar){
        this.continuar = continuar;
    }

    /*
        Método para matar el hilo.
    */
    public void matar(){
        this.vivo = false;
    }
    
    @Override
    public void run() {
        Scanner
            FlujoEntrada;
        
        int
            comando;

        try {
            /* Establecer el flujo de entrada */
            FlujoEntrada = new Scanner(
                this.Zocalo.getInputStream()
            );

            /* Hasta que la conexión sea detenida */
            while(this.vivo){
                /* Si está especificado que el usuario está bloqueado, esperar */
                if(this.continuar){
                    comando = FlujoEntrada.nextInt();

                    /* Ejecutar comando de acuerdo a identificación */
                    switch(comando){
                        case Comandos.MOUSE_PRESS:
                            this.Automata.mousePress(FlujoEntrada.nextInt());
                            break;
                        case Comandos.MOUSE_RELEASE:
                            this.Automata.mouseRelease(FlujoEntrada.nextInt());
                            break;
                        case Comandos.KEY_PRESS:
                            this.Automata.keyPress(FlujoEntrada.nextInt());
                            break;
                        case Comandos.KEY_RELEASE:
                            this.Automata.keyRelease(FlujoEntrada.nextInt());
                            break;
                        default:
                            this.Automata.mouseMove(
                                FlujoEntrada.nextInt(),
                                FlujoEntrada.nextInt()
                            );
                            break;
                    }
                }
            }
            
            /* Cliente ya no puede volver a ejecutar. Matar el flujo de entrada */
            FlujoEntrada.close();
            
            /* Cerrar puerto de servidor */
            this.Zocalo.close();
        }catch(Exception e){
            System.out.println("Error al procesar comando recibido: " + e.getMessage());
        }
    }
}
