package principal;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class EnviadorComandos implements Runnable, KeyListener, MouseMotionListener, MouseListener{       
    
    private Dimension Pantalla = Toolkit.getDefaultToolkit().getScreenSize();
    private int LargoPantalla = (int) Pantalla.getHeight();
    private int AnchoPantalla = (int) Pantalla.getWidth();
    
    private
        JFrame
            Capturista;
    
    private
        VentanaCliente
            Ventana;
    
    private
        Socket
            Zocalo;
    
    private
        DataOutputStream
            FlujoSalida;
    
    private
        DataInputStream
            FlujoEntrada;
    
    private
        Thread
            Hilo;
    
    private volatile
        boolean
            vivo,
            listo;
    
    private
        int
            servidorAncho,
            servidorAlto;
        
    public EnviadorComandos(Socket Zocalo, VentanaCliente Pantalla, int ancho, int alto) {
        /* Pasar socket */
        this.Zocalo = Zocalo;
        
        /* Establecer el tamaño de pantalla del servidor */
        this.servidorAncho = AnchoPantalla;
        this.servidorAlto = LargoPantalla;
        
        /* Establecer la ventana para control bidireccional */
        this.Ventana = Ventana;
                
        /* Asignar capturista */
        this.Capturista = new JFrame();
        this.Capturista.setUndecorated(true);
        //this.Capturista.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        this.Capturista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.Capturista.setSize(servidorAncho, servidorAlto);
        
        this.Capturista.setResizable(false);
        this.Capturista.setAlwaysOnTop(true);
        this.Capturista.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        /* Para que capture los eventos del teclado */
        this.Capturista.setFocusable(true);

        /* Aplicar escuchadores */
        this.Capturista.addKeyListener(this);
        this.Capturista.addMouseListener(this);
        this.Capturista.addMouseMotionListener(this);
        
        /* Crear hilo */
        this.Hilo = new Thread(this);
        
        /* Iniciar el hilo */
        this.Hilo.start();
    }

    /*
        Método para identificar que la clase está lista
    */
    public boolean isListo(){
        return this.listo;
    }
    
    
    /*
        Método para matar el hilo
    */
    public void matar(){
        this.vivo = false;
        this.listo = false;
        /*Cerrar panel de captura*/
        this.Capturista.dispose();
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
            if(this.listo){
                this.FlujoSalida.writeInt(Comandos.KEY_PRESS);       
                this.FlujoSalida.writeInt(e.getKeyCode());
                this.FlujoSalida.flush();
            }
        } catch (IOException ioe){
            System.out.println("Error en comunicación: Terminando ejecución");
            
            /* Acabar con todo */
            this.matar();

            this.vivo = false;
        } catch (Exception ex) {
            System.out.println("No fue posible enviar el evento de presión de tecla: " + ex.getMessage());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            if(this.listo){
                this.FlujoSalida.writeInt(Comandos.KEY_RELEASE);       
                this.FlujoSalida.writeInt(e.getKeyCode());
                this.FlujoSalida.flush();
            }
        } catch (IOException ioe){
            System.out.println("Error en comunicación: Terminando ejecución");
            
            /* Acabar con todo */
            this.matar();
            
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
            if(this.listo){
                this.FlujoSalida.writeInt(Comandos.MOUSE_MOVE);       
                this.FlujoSalida.writeInt(nuevasCoordenadas[0]);
                this.FlujoSalida.writeInt(nuevasCoordenadas[1]);
                this.FlujoSalida.flush();
            }
        } catch (IOException ioe){
            System.out.println("Error en comunicación: Terminando ejecución");
            
            /* Acabar con todo */
            this.matar();
            
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
            if(this.listo){
                this.FlujoSalida.writeInt(Comandos.MOUSE_PRESS);       
                this.FlujoSalida.writeInt(xBoton);
                this.FlujoSalida.flush();
            }
        } catch (IOException ioe){
            System.out.println("Error en comunicación: Terminando ejecución");
            
            /* Acabar con todo */
            this.matar();
            
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
            if(this.listo){
                this.FlujoSalida.writeInt(Comandos.MOUSE_RELEASE);       
                this.FlujoSalida.writeInt(xBoton);
                this.FlujoSalida.flush();
            }
        } catch (IOException ioe){
            System.out.println("Error en comunicación: Terminando ejecución");
            
            /* Acabar con todo */
            this.matar();
            
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
        /* Iniciar función */
        this.vivo = true;
        
        try{
            /* Establecer los flujos de entrada y salida desde la conexión */
            this.FlujoEntrada = new DataInputStream(
                this.Zocalo.getInputStream()
            );

            this.FlujoSalida = new DataOutputStream(
                this.Zocalo.getOutputStream()
            );
            
            /* Hilo preparado */
            this.listo = true;
            
            /* Mostrar pantalla cuando todo haya iniciado */
            this.Capturista.setVisible(true);
            
            /* Intentar enfocar en el panel de captura */
            this.Capturista.requestFocus();
            
            while(this.vivo){
                continue;
            }
            
            /* Cerrar flujos */
            this.FlujoEntrada.close();
            this.FlujoSalida.close();
            
            /* Mostrar ventana */
            this.Ventana.mostrarVentana(true);
            
        } catch(IOException ioe){
            System.out.println("Flujos han sido cerrados");            
        } catch(Exception e) {
            System.out.println(this.getClass() + ": Hubo un error en ejecución: " + e.getMessage());
        }
        
        /* Acabar con todo */
        this.matar();
    }
}
