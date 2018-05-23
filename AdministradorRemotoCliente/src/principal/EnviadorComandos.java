/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;

/**
 *
 * @author Carlos González <carlos85g at gmail.com>
 */
public class EnviadorComandos implements KeyListener, MouseMotionListener, MouseListener{    
    private
        Socket
            Zocalo;
    
    private
        Robot
            Automata;
    
    private
        JFrame
            Capturista;
    
    private
        PrintWriter
            FlujoSalida;

    public EnviadorComandos(Socket Zocalo) {
        
        /* Establecer el socket */
        this.Zocalo = Zocalo;
        
        /* Asignar capturista */
        this.Capturista = new JFrame();
        this.Capturista.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.Capturista.setUndecorated(true);
        this.Capturista.setVisible(true);
        this.Capturista.setAlwaysOnTop(true);

        /* Para que capture los eventos del teclado */
        this.Capturista.setFocusable(true);

        /* Aplicar escuchadores */
        this.Capturista.addKeyListener(this);
        this.Capturista.addMouseListener(this);
        this.Capturista.addMouseMotionListener(this);
        
        /* Iniciar flujo de salida */
        try{
            this.FlujoSalida = new PrintWriter(
                this.Zocalo.getOutputStream()
            );
        } catch(Exception e) {
            System.out.println("No fue posible iniciar el flujo de salida: " + e.getMessage());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.FlujoSalida.println(Comandos.KEY_PRESS);
        this.FlujoSalida.println(e.getKeyCode());
        this.FlujoSalida.flush();
    }

    @Override
    public void keyReleased(KeyEvent e) {
       this.FlujoSalida.println(Comandos.KEY_RELEASE);
        this.FlujoSalida.println(e.getKeyCode());
        this.FlujoSalida.flush(); 
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.FlujoSalida.println(Comandos.MOUSE_MOVE);
        this.FlujoSalida.println(e.getX());
        this.FlujoSalida.println(e.getY());
        this.FlujoSalida.flush();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int
            boton = e.getButton(),
            xBoton = (boton == 3)? 4 : 16;
        
        this.FlujoSalida.println(Comandos.MOUSE_PRESS);       
        this.FlujoSalida.println(xBoton);
        this.FlujoSalida.flush();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int
            boton = e.getButton(),
            xBoton = (boton == 3)? 4 : 16;

        this.FlujoSalida.println(Comandos.MOUSE_RELEASE);       
        this.FlujoSalida.println(xBoton);
        this.FlujoSalida.flush();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
