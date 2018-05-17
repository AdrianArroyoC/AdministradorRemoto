/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Estudiantes mis Cuvalles.
 */
public class VentanaCliente extends JFrame {

    JLabel LabelNombre;
    JLabel LabelApellidos;
    JLabel LabelCodigo;
    JLabel LabelIpServidor;
    JLabel LabelStatus;
    JLabel LabelEspaciador;

    public VentanaCliente() {
        LabelNombre = new JLabel();
        LabelNombre.setText("Nombre:           ");
        JTextField TextoNombre = new JTextField(15);
        LabelApellidos = new JLabel();
        LabelApellidos.setText("Apellidos:         ");
        JTextField TextoApellidos = new JTextField(15);
        LabelCodigo = new JLabel();
        LabelCodigo.setText("Código:              ");
        JTextField TextoCodigo = new JTextField(15);
        LabelIpServidor = new JLabel();
        LabelIpServidor.setText("Ip del Servidor:");
        JTextField TextoIpServidor = new JTextField(15);
        LabelStatus=new JLabel();
        LabelStatus.setText("Estado: Pendiente del servidor");
        LabelEspaciador = new JLabel();
        LabelStatus.setHorizontalAlignment(JTextField.CENTER);

        //TextStatus.setText("Pendiente del servidor");
        JButton ButtonIniciar = new JButton();
        ButtonIniciar.setText("Iniciar");
        ButtonIniciar.setLocation(200, 200);
        add(LabelNombre);
        add(TextoNombre);
        add(LabelApellidos);
        add(TextoApellidos);
        add(LabelCodigo);
        add(TextoCodigo);
        add(LabelIpServidor);
        add(TextoIpServidor);
        add(ButtonIniciar);
        add(LabelEspaciador);
        //add(LabelStatus);
        ButtonIniciar.addActionListener(
                new ActionListener() {

            public void actionPerformed(
                    ActionEvent e) {

                try {
                         JOptionPane.showMessageDialog(null,"no llenaste los datos correctamente!");
                         setState(JFrame.ICONIFIED);
                } catch (InputMismatchException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error al conectarse al server",
                            JOptionPane.ERROR_MESSAGE);
                }

            }

        }
        );
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
