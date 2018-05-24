package principal;

import java.awt.Robot;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

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
    
    private volatile
        boolean
            listo = false,
            continuar, /* Bandera para especificar si es necesario detener control */
            vivo; /* Bandera para mantener el hilo vivo. Ponerlo en falso acabará con la sesión del usuario */

    public ReceptorComandos(Socket Zocalo, Robot Automata) {
        /* Pasar socket */
        this.Zocalo = Zocalo;
        
        /* Obtener el robot para uso */
        this.Automata = Automata;
        
        /* Iniciar variables de flujo */
        this.continuar = true;
        this.vivo = true;
        
        /* Iniciar hilo */
        this.Hilo = new Thread(this, "ReceptorComandos");
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
        this.continuar = !continuar;
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
    
    /*
        Método para verificar que la clase está lista
    */
    public boolean isListo(){       
        return this.listo;
    }
    
    @Override
    public void run() {
        int
            comando,
            dato1,
            dato2;

        try {
            /* Establecer los flujos de entrada y salida desde la conexión */
            this.FlujoEntrada = new DataInputStream(
                this.Zocalo.getInputStream()
            );

            this.FlujoSalida = new DataOutputStream(
                this.Zocalo.getOutputStream()
            );
            
            /* Obtener los datos primero */
            this.DireccionIP = this.FlujoEntrada.readUTF();
            this.Codigo = this.FlujoEntrada.readUTF();
            this.Nombres = this.FlujoEntrada.readUTF();
            this.Apellidos = this.FlujoEntrada.readUTF();
            
            /* Datos poblados */
            this.listo = true;
            
            /* Iniciar como bloqueada */
            this.bloquear(true);
            
            /* Hasta que la conexión sea detenida */
            while(this.vivo){
                /* Si está especificado que el usuario está bloqueado, esperar */
                
                comando = this.FlujoEntrada.readInt();

                /* Ejecutar comando de acuerdo a identificación */
                switch(comando){
                    case Comandos.MOUSE_PRESS:
                        dato1 = this.FlujoEntrada.readInt();
                        if(this.continuar){
                            this.Automata.mousePress(dato1);
                        }
                        break;
                    case Comandos.MOUSE_RELEASE:
                        dato1 = this.FlujoEntrada.readInt();
                        if(this.continuar){
                            this.Automata.mouseRelease(dato1);
                        }
                        break;
                    case Comandos.KEY_PRESS:
                        dato1 = this.FlujoEntrada.readInt();
                        if(this.continuar){
                            this.Automata.keyPress(dato1);
                        }
                        break;
                    case Comandos.KEY_RELEASE:
                        dato1 = this.FlujoEntrada.readInt();
                        if(this.continuar){
                            this.Automata.keyRelease(dato1);
                        }
                        break;
                    default:
                        dato1 = this.FlujoEntrada.readInt();
                        dato2 = this.FlujoEntrada.readInt();
                        if(this.continuar){
                            this.Automata.mouseMove(
                                dato1,
                                dato2
                            );
                        }
                        break;
                }
            }

            /* Mandar información al flujo de salida sobre la desconexión del usuario */
            this.FlujoSalida.writeInt(-1);
            this.FlujoSalida.flush();

            /* Cliente ya no puede volver a ejecutar. Matar los flujos de entrada y salida */
            this.FlujoEntrada.close();;
            this.FlujoSalida.close();
        }catch(SocketException se){
            System.out.println("Conexión ha muerto");
        }catch(IOException ioe){
            System.out.println("Flujos han sido cerrados");
        }catch(Exception e){
            System.out.println("Error al procesar comando recibido: " + e.getMessage());
        }
        
        /* Cerrar hilo */
        this.vivo = false;
    }
}
