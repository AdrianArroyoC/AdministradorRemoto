package principal;

import java.awt.Robot;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
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
        Robot
            Automata;
    
    private    
        DataInputStream
            FlujoEntrada;
    
    private    
        DataOutputStream
            FlujoSalida;
    
    private
        String
            Nombres,
            Apellidos,
            Codigo,
            DireccionIP;
    
    private
        boolean
            continuar, /* Bandera para especificar si es necesario detener control */
            vivo; /* Bandera para mantener el hilo vivo. Ponerlo en falso acabará con la sesión del usuario */

    public ReceptorComandos(DataInputStream FlujoEntrada, DataOutputStream FlujoSalida, Robot Automata) {
        /* Establecer los flujos de entrada y salida desde la conexión */
        this.FlujoEntrada = FlujoEntrada;
        this.FlujoSalida = FlujoSalida;
        
        /* Obtener el robot para uso */
        this.Automata = Automata;
        
        /* Iniciar variables de flujo */
        this.continuar = true;
        this.vivo = true;
        
        /* Iniciar hilo */
        this.Hilo = new Thread(this);
        this.Hilo.start();
    }
    
    /*
        Método para recuperar el nombre del cliente
    */
    public String getNombre(){
        return (this.Nombres + " " + this.Apellidos);
    }
    
    /*
        Método para recuperar los nombres inciales
    */
    public String getNombres(){
        return this.Nombres;
    }
    
    /*
        Método para recuperar los apellidos
    */
    public String getApellidos(){
        return this.Apellidos;
    }
    
    /*
        Método para recuperar el código
    */
    public String getCodigo(){
        return this.Codigo;
    }
    
    /*
        Método para recuperar la IP
    */
    public String getDireccionIP(){
        return this.DireccionIP;
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
    
    /*
        Método para verificar que la clase sigue viva
    */
    public boolean isVivo(){
        return this.vivo;
    }
    
    @Override
    public void run() {
        int
            comando;

        try {
            /* Obtener los datos primero */
            this.DireccionIP = FlujoEntrada.readUTF();
            this.Codigo = FlujoEntrada.readUTF();
            this.Nombres = FlujoEntrada.readUTF();
            this.Apellidos = FlujoEntrada.readUTF();
            
            /* Para debug */
            System.out.println("IP: " + this.DireccionIP);
            System.out.println("Código: " + this.Codigo);
            System.out.println("Nombres: " + this.Nombres);
            System.out.println("Apellidos: " + this.Apellidos);

            /* Hasta que la conexión sea detenida */
            while(this.vivo){
                /* Si está especificado que el usuario está bloqueado, esperar */
                if(this.continuar){
                    comando = this.FlujoEntrada.readInt();

                    /* Ejecutar comando de acuerdo a identificación */
                    switch(comando){
                        case Comandos.MOUSE_PRESS:
                            this.Automata.mousePress(FlujoEntrada.readInt());
                            break;
                        case Comandos.MOUSE_RELEASE:
                            this.Automata.mouseRelease(FlujoEntrada.readInt());
                            break;
                        case Comandos.KEY_PRESS:
                            this.Automata.keyPress(FlujoEntrada.readInt());
                            break;
                        case Comandos.KEY_RELEASE:
                            this.Automata.keyRelease(FlujoEntrada.readInt());
                            break;
                        default:
                            this.Automata.mouseMove(
                                FlujoEntrada.readInt(),
                                FlujoEntrada.readInt()
                            );
                            break;
                    }
                }
            }

            /* Mandar información al flujo de salida sobre la desconexión del usuario */
            FlujoSalida.writeInt(-1);
            FlujoSalida.flush();

            /* Cliente ya no puede volver a ejecutar. Matar los flujos de entrada y salida */
            FlujoEntrada.close();
            FlujoSalida.close();
        }catch(Exception e){
            System.out.println("Error al procesar comando recibido: " + e.getMessage());
        }
        
        /* Cerrar hilo */
        this.vivo = false;
    }
}
