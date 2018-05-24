package principal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class EnviadorComandos implements Runnable, KeyListener, MouseMotionListener, MouseListener{       
    private
        JFrame
            Capturista;
    
    private
        VentanaCliente
            Ventana;
    
    private
        DataOutputStream
            FlujoSalida;
    
    private
        DataInputStream
            FlujoEntrada;
    
    private
        Thread
            Hilo;
    
    private
        boolean
            vivo;
    
    private
        int
            servidorAncho,
            servidorAlto;
        
    public EnviadorComandos(DataInputStream FlujoEntrada, DataOutputStream FlujoSalida, VentanaCliente Pantalla, int ancho, int alto) {
        /* Establecer los flujos de entrada y salida desde la conexión */
        this.FlujoEntrada = FlujoEntrada;
        this.FlujoSalida = FlujoSalida;
        
        /* Establecer el tamaño de pantalla del servidor */
        this.servidorAncho = ancho;
        this.servidorAlto = alto;
        
        /* Establecer la ventana para control bidireccional */
        this.Ventana = Ventana;
                
        /* Asignar capturista */
        this.Capturista = new JFrame();
        
        this.Capturista.setSize(800, 600);
        this.Capturista.setResizable(false);
        /*
        this.Capturista.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.Capturista.setUndecorated(true);
        */
        this.Capturista.setVisible(true);
        this.Capturista.setAlwaysOnTop(true);

        /* Para que capture los eventos del teclado */
        this.Capturista.setFocusable(true);

        /* Aplicar escuchadores */
        this.Capturista.addKeyListener(this);
        this.Capturista.addMouseListener(this);
        this.Capturista.addMouseMotionListener(this);

        /* Iniciar función */
        this.vivo = true;
        
        /* Crear hilo */
        this.Hilo = new Thread(this);
        
        /* Iniciar el hilo */
        this.Hilo.start();
    }

    /*
        Método privado para convertir las coordenadas del capturador a coordenadas del servidor en la pantalla
    */
    private int[] convertirACoordenadasServidor(int x, int y){
        int
            nuevoX = (this.servidorAncho / this.Capturista.getWidth()) * x,
            nuevoY = (this.servidorAlto / this.Capturista.getHeight()) * y;
        
        return new int[]{
            nuevoX,
            nuevoY
        };
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            this.FlujoSalida.writeInt(Comandos.KEY_PRESS);       
            this.FlujoSalida.writeInt(e.getKeyCode());
            this.FlujoSalida.flush();
        } catch (IOException ioe){
            System.out.println("Error en comunicación: Terminando ejecución");
            this.vivo = false;
        } catch (Exception ex) {
            System.out.println("No fue posible enviar el evento de presión de tecla: " + ex.getMessage());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            this.FlujoSalida.writeInt(Comandos.KEY_RELEASE);       
            this.FlujoSalida.writeInt(e.getKeyCode());
            this.FlujoSalida.flush();
        } catch (IOException ioe){
            System.out.println("Error en comunicación: Terminando ejecución");
            this.vivo = false;
        } catch (Exception ex) {
            System.out.println("No fue posible enviar el evento de liberación de tecla: " + ex.getMessage());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int[]
            nuevasCoordenadas = convertirACoordenadasServidor(
                e.getX(),
                e.getY()
            );

        try {
            this.FlujoSalida.writeInt(Comandos.MOUSE_MOVE);       
            this.FlujoSalida.writeInt(nuevasCoordenadas[0]);
            this.FlujoSalida.writeInt(nuevasCoordenadas[1]);
            this.FlujoSalida.flush();
        } catch (IOException ioe){
            System.out.println("Error en comunicación: Terminando ejecución");
            this.vivo = false;
        } catch (Exception ex) {
            System.out.println("No fue posible enviar el evento de movimiento de mouse: " + ex.getMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int
            boton = e.getButton(),
            xBoton = (boton == 3)? 4 : 16;
        
        try {
            this.FlujoSalida.writeInt(Comandos.MOUSE_PRESS);       
            this.FlujoSalida.writeInt(xBoton);
            this.FlujoSalida.flush();
        } catch (IOException ioe){
            System.out.println("Error en comunicación: Terminando ejecución");
            this.vivo = false;
        } catch (Exception ex) {
            System.out.println("No fue posible enviar el evento de presión de botón de mouse: " + ex.getMessage());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int
            boton = e.getButton(),
            xBoton = (boton == 3)? 4 : 16;

        try {
            this.FlujoSalida.writeInt(Comandos.MOUSE_RELEASE);       
            this.FlujoSalida.writeInt(xBoton);
            this.FlujoSalida.flush();
        } catch (IOException ioe){
            System.out.println("Error en comunicación: Terminando ejecución");
            this.vivo = false;
        } catch (Exception ex) {
            System.out.println("No fue posible enviar el evento de liberación de botón de mouse: " + ex.getMessage());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void run() {
        /* Iniciar flujo de salida */
        try{            
            while(this.vivo){
                continue;
            }
            
            /* Cerrar flujos */
            this.FlujoEntrada.close();
            this.FlujoSalida.close();
            
            /* Mostrar ventana */
            this.Ventana.mostrarVentana(true);
            
        } catch(Exception e) {
            System.out.println(this.getClass() + ": No fue posible cerrar el flujo de salida: " + e.getMessage());
        }
    }
}
