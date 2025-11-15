
package views;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import models.TareaEstado;
/**
 * @author Yulian Alexis Tobar Rios
 *  @author Hellen Valeria Melo Cubides
 */

public class VentanaPrincipal extends JFrame {

    public JTextField txtTitulo, txtFechaLimite;
    public JTextArea txtDescripcion;
    public JRadioButton rPendiente, rProgreso, rCompletada;
    public ButtonGroup grupoEstado;

    public JTable tablaTareas;
    public JScrollPane scrollTabla;

    public JButton btnAgregar, btnLimpiar, btnEditar, btnEliminar, btnGuardar, btnCambiarUsuario, btnCambiarTema;
    public JLabel lblUsuario, lblTema, lblTituloVentana;

    private Theme[] temas;
    private int indiceTema = 0;

    private JPanel panelSuperior;

    public VentanaPrincipal() {
        setTitle("MiGestorDeTareas");
        setSize(720, 820);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        inicializarTemas();
        inicializarComponentes();
        construirInterfaz();
        aplicarEstilos();
        aplicarTemaActual();
    }

    private void inicializarTemas() {
        temas = new Theme[]{
            new Theme("Azul Aguamarina", new Color(200, 240, 240), new Color(235, 250, 250), new Color(140, 200, 200), new Color(20, 40, 40), new Color(150, 210, 210)),
            new Theme("Rojo", new Color(255, 225, 225), new Color(255, 240, 240), new Color(235, 160, 160), new Color(80, 25, 25), new Color(220, 170, 170)),
            new Theme("Verde", new Color(220, 245, 225), new Color(240, 255, 240), new Color(175, 225, 180), new Color(25, 60, 40), new Color(160, 190, 170)),
            new Theme("Morado", new Color(235, 225, 245), new Color(250, 245, 255), new Color(200, 180, 230), new Color(45, 25, 70), new Color(190, 170, 210)),
            new Theme("Azul Oscuro", new Color(210, 225, 245), new Color(240, 245, 255), new Color(160, 190, 230), new Color(25, 35, 70), new Color(150, 180, 220))
        };
    }

    private void inicializarComponentes() {
        lblTituloVentana = new JLabel("Mi Gestor De Tareas");
        lblTituloVentana.setFont(new Font("Segoe UI", Font.BOLD, 30));

        lblUsuario = new JLabel("Usuario: Juan");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTema = new JLabel("Tema: Azul Aguamarina");
        lblTema.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtTitulo = crearTextFieldConPlaceholder("Título");
        txtFechaLimite = crearTextFieldConPlaceholder("Fecha límite (AAAA-MM-DD)");

        txtDescripcion = new JTextArea("Descripción", 4, 20);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDescripcion.setForeground(Color.GRAY);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtDescripcion.getText().equals("Descripción")) {
                    txtDescripcion.setText("");
                    txtDescripcion.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (txtDescripcion.getText().isEmpty()) {
                    txtDescripcion.setText("Descripción");
                    txtDescripcion.setForeground(Color.GRAY);
                }
            }
        });

        rPendiente = new JRadioButton("Pendiente");
        rProgreso = new JRadioButton("En progreso");
        rCompletada = new JRadioButton("Completada");
        grupoEstado = new ButtonGroup();
        grupoEstado.add(rPendiente);
        grupoEstado.add(rProgreso);
        grupoEstado.add(rCompletada);
        rPendiente.setSelected(true);

        btnAgregar = new JButton("Agregar Tarea");
        btnLimpiar = new JButton("Limpiar Campos");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnGuardar = new JButton("Guardar Cambios");
        btnCambiarUsuario = new JButton("Cambiar Usuario");
        btnCambiarTema = new JButton("Cambiar Tema");

        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{"Título", "Fecha límite", "Estado"}) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTareas = new JTable(model);
        tablaTareas.setFillsViewportHeight(true);
        tablaTareas.setRowHeight(34);
        tablaTareas.setShowGrid(true);
        tablaTareas.setGridColor(new Color(220, 225, 230));
        tablaTareas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        scrollTabla = new JScrollPane(tablaTareas);
scrollTabla.setPreferredSize(new Dimension(600, 240));    }

    private void construirInterfaz() {
    panelSuperior = new JPanel(new BorderLayout());
    panelSuperior.setBorder(new EmptyBorder(16, 22, 16, 22));

    JPanel panelInfo = new JPanel(new GridLayout(2, 1));
    panelInfo.setOpaque(false);
    panelInfo.add(lblUsuario);
    panelInfo.add(lblTema);

    panelSuperior.add(lblTituloVentana, BorderLayout.WEST);
    panelSuperior.add(panelInfo, BorderLayout.EAST);

    JPanel panelNuevaTarea = new JPanel(new GridBagLayout());
   panelNuevaTarea.setPreferredSize(new Dimension(680, 260));

    panelNuevaTarea.setBackground(Color.WHITE);
    panelNuevaTarea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(temas[indiceTema].border, 1, true),
            new EmptyBorder(18, 18, 18, 18)
    ));

    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(8, 8, 8, 8);
    c.anchor = GridBagConstraints.WEST;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1.0;

    int y = 0;

    JLabel lblNuevaTarea = new JLabel("Nueva Tarea");
    lblNuevaTarea.setFont(new Font("Segoe UI", Font.BOLD, 18));
    c.gridx = 0;
    c.gridy = y;
    c.gridwidth = 3;
    panelNuevaTarea.add(lblNuevaTarea, c);

y++;
c.gridy = y;
c.gridwidth = 3;

c.fill = GridBagConstraints.HORIZONTAL;
txtTitulo.setPreferredSize(new Dimension(400, 28)); 
panelNuevaTarea.add(txtTitulo, c);

y++;
c.gridy = y;
txtFechaLimite.setPreferredSize(new Dimension(400, 28)); 
panelNuevaTarea.add(txtFechaLimite, c);

y++;
c.gridy = y;
c.fill = GridBagConstraints.BOTH; 
c.weighty = 1.0; 
txtDescripcion.setLineWrap(true);
txtDescripcion.setWrapStyleWord(true);

JScrollPane descScroll = new JScrollPane(txtDescripcion);
descScroll.setPreferredSize(new Dimension(400, 120));
panelNuevaTarea.add(descScroll, c);

c.fill = GridBagConstraints.HORIZONTAL;
c.weighty = 0;

y++;
c.gridy = y;

JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
panelEstado.setOpaque(false);

panelEstado.add(rPendiente);
panelEstado.add(rProgreso);
panelEstado.add(rCompletada);

panelNuevaTarea.add(panelEstado, c);

y++;
c.gridy = y;

JPanel panelBtnsNuevaTarea = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 10));
panelBtnsNuevaTarea.setOpaque(false);

panelBtnsNuevaTarea.add(btnAgregar);
panelBtnsNuevaTarea.add(btnLimpiar);

panelNuevaTarea.add(panelBtnsNuevaTarea, c);



    JPanel panelTabla = new JPanel(new BorderLayout());
    panelTabla.setPreferredSize(new Dimension(680, 340));

    panelTabla.setBackground(Color.WHITE);
    panelTabla.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(temas[indiceTema].border, 1, true),
            new EmptyBorder(12, 12, 12, 12)
    ));

    JLabel lblLista = new JLabel("Lista de Tareas Guardadas");
    lblLista.setFont(new Font("Segoe UI", Font.BOLD, 18));
    lblLista.setBorder(new EmptyBorder(5, 0, 12, 0));
    panelTabla.add(lblLista, BorderLayout.NORTH);
    panelTabla.add(scrollTabla, BorderLayout.CENTER);

    JPanel panelBtnsTabla = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 10));
    panelBtnsTabla.setOpaque(false);
    panelBtnsTabla.add(btnEditar);
    panelBtnsTabla.add(btnEliminar);
    panelBtnsTabla.add(btnGuardar);
    panelTabla.add(panelBtnsTabla, BorderLayout.SOUTH);

    JPanel panelConfig = new JPanel();
    panelConfig.setLayout(new BoxLayout(panelConfig, BoxLayout.Y_AXIS));
    panelConfig.setBackground(Color.WHITE);
    panelConfig.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(temas[indiceTema].border, 1, true),
            new EmptyBorder(12, 16, 12, 16)
    ));

    JPanel panelTituloConfig = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    panelTituloConfig.setOpaque(false);
    JLabel lblConfig = new JLabel("Configuración");
    lblConfig.setFont(new Font("Segoe UI", Font.BOLD, 18));
    panelTituloConfig.add(lblConfig);
    panelConfig.add(panelTituloConfig);
    panelConfig.add(Box.createVerticalStrut(10));

    JPanel panelBtnsConfig = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 5));
    panelBtnsConfig.setOpaque(false);
    panelBtnsConfig.add(btnCambiarUsuario);
    panelBtnsConfig.add(btnCambiarTema);
    panelConfig.add(panelBtnsConfig);

    JPanel panelCentro = new JPanel();
    panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
    panelCentro.setBorder(new EmptyBorder(14, 22, 18, 22));

    panelCentro.add(panelNuevaTarea);
    panelCentro.add(Box.createVerticalStrut(18));
    panelCentro.add(panelTabla);
    panelCentro.add(Box.createVerticalStrut(18));
    panelCentro.add(panelConfig);

    add(panelSuperior, BorderLayout.NORTH);
    add(panelCentro, BorderLayout.CENTER);

   
}

    private JTextField crearTextFieldConPlaceholder(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
        return field;
    }

    private void aplicarEstilos() {
        JButton[] botones = {btnAgregar, btnLimpiar, btnEditar, btnEliminar, btnGuardar, btnCambiarUsuario, btnCambiarTema};
        for (JButton b : botones) {
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.BOLD, 14));
            b.setPreferredSize(new Dimension(180, 40));
            b.setOpaque(true);
        }

        for (JRadioButton r : new JRadioButton[]{rPendiente, rProgreso, rCompletada}) {
            r.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            r.setOpaque(false);
        }

        LineBorder roundedBorder = new LineBorder(new Color(200, 210, 230), 1, true);
        txtTitulo.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(8, 8, 8, 8)));
        txtFechaLimite.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(8, 8, 8, 8)));
        txtDescripcion.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(8, 8, 8, 8)));

        tablaTareas.setSelectionBackground(new Color(220, 220, 220));
        tablaTareas.setSelectionForeground(Color.BLACK);
    }

    private void aplicarTemaActual() {
        Theme t = temas[indiceTema];
        lblTema.setText("Tema: " + t.name);

        panelSuperior.setBackground(t.primary);
        lblTituloVentana.setForeground(t.text);
        lblUsuario.setForeground(t.text);
        lblTema.setForeground(t.text);

        getContentPane().setBackground(t.background);

        JButton[] botones = {btnAgregar, btnLimpiar, btnEditar, btnEliminar, btnGuardar, btnCambiarUsuario, btnCambiarTema};
        for (JButton b : botones) {
            b.setBackground(t.button);
            b.setForeground(t.text);
            b.setBorder(new CompoundBorder(new LineBorder(t.border, 1, true), new EmptyBorder(6, 12, 6, 12)));
        }

        tablaTareas.getTableHeader().setBackground(t.primary);
        tablaTareas.getTableHeader().setForeground(t.text);
        scrollTabla.setBorder(new LineBorder(t.border, 1, true));

        revalidate();
        repaint();
    }

    private static class Theme {

        String name;
        Color primary, background, button, text, border;

        Theme(String n, Color p, Color bg, Color b, Color tx, Color br) {
            name = n;
            primary = p;
            background = bg;
            button = b;
            text = tx;
            border = br;
        }
    }
    public JTextField getTxtTitulo() {
    return txtTitulo;
}

public JTextField getTxtFechaLimite() {
    return txtFechaLimite;
}

public JTextArea getTxtDescripcion() {
    return txtDescripcion;
}
public JRadioButton getRPendiente() {
    return rPendiente;
}

public JRadioButton getRProgreso() {
    return rProgreso;
}

public JRadioButton getRCompletada() {
    return rCompletada;
}

public TareaEstado getEstadoSeleccionado() {
    if (rPendiente.isSelected()) return TareaEstado.PENDIENTE;
    if (rProgreso.isSelected()) return TareaEstado.EN_PROGRESO;
    return TareaEstado.COMPLETADA;
}
public JTable getTablaTareas() {
    return tablaTareas;
}
public JButton getBtnAgregar() {
    return btnAgregar;
}

public JButton getBtnLimpiar() {
    return btnLimpiar;
}

public JButton getBtnEditar() {
    return btnEditar;
}

public JButton getBtnEliminar() {
    return btnEliminar;
}

public JButton getBtnGuardar() {
    return btnGuardar;
}

public JButton getBtnCambiarUsuario() {
    return btnCambiarUsuario;
}

public JButton getBtnCambiarTema() {
    return btnCambiarTema;
}

public JLabel getLblUsuario() {
    return lblUsuario;
}

public JLabel getLblTema() {
    return lblTema;
}
 public int getIndiceTema() {
    return indiceTema;
}

public void setIndiceTema(int indice) {
    this.indiceTema = indice;
    aplicarTemaActual();
}
public void limpiarCampos() {
    txtTitulo.setText("Título");
    txtTitulo.setForeground(Color.GRAY);

    txtFechaLimite.setText("Fecha límite (AAAA-MM-DD)");
    txtFechaLimite.setForeground(Color.GRAY);

    txtDescripcion.setText("Descripción");
    txtDescripcion.setForeground(Color.GRAY);

    rPendiente.setSelected(true);
}

   
}
