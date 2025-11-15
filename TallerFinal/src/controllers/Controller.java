package controllers;

import models.*;
import persistence.Configuration;
import views.VentanaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.file.Path;
import java.nio.file.Paths;
import utilities.Utilities;

public class Controller {

    private VentanaPrincipal vista;
    private GestorTareas gestor;
    private Configuration config;

    private final Path archivoTareas = Paths.get(Utilities.FILE_PATH_JSON);
    private final Path archivoConfig = Paths.get(Utilities.FILE_PATH_PROPERTIES);

    public Controller() {
        gestor = new GestorTareas();
        config = new Configuration(archivoConfig.toString());
    }

    public void init() {

        gestor.cargar(archivoTareas);

        vista = new VentanaPrincipal();

        cargarConfiguracion();

        cargarTabla();

        configurarEventos();

        vista.setVisible(true);
    }

    private void cargarConfiguracion() {
        String usuario = config.get("usuario", "Usuario");
        int tema = Integer.parseInt(config.get("tema", "0"));

        vista.getLblUsuario().setText("Usuario: " + usuario);
        vista.setIndiceTema(tema);
    }

    private void guardarConfiguracion() {
        config.set("usuario", vista.getLblUsuario().getText().replace("Usuario: ", ""));
        config.set("tema", String.valueOf(vista.getIndiceTema()));
    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getTablaTareas().getModel();
        model.setRowCount(0);

        for (Tarea t : gestor.getTareas()) {
            model.addRow(new Object[]{
                t.getTitulo(),
                t.getFechaLimite(),
                t.getEstado()
            });
        }
    }

    private void agregarTarea() {
        String titulo = vista.getTxtTitulo().getText().trim();
        String desc = vista.getTxtDescripcion().getText().trim();
        String fecha = vista.getTxtFechaLimite().getText().trim();

        if (titulo.equals("Título") || titulo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar un título.");
            return;
        }

        if (fecha.equals("Fecha límite (AAAA-MM-DD)") || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar una fecha.");
            return;
        }

        Tarea nueva = new Tarea(titulo, desc, fecha, vista.getEstadoSeleccionado());

        gestor.agregarTarea(nueva);
        gestor.guardar(archivoTareas);

        cargarTabla();
        vista.limpiarCampos();

        JOptionPane.showMessageDialog(vista, "Tarea agregada.");
    }

    private void eliminarTarea() {
        int fila = vista.getTablaTareas().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una tarea.");
            return;
        }

        gestor.eliminarTarea(fila);
        gestor.guardar(archivoTareas);
        cargarTabla();
    }

    private void cargarTareaEnCampos() {
        int fila = vista.getTablaTareas().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una tarea.");
            return;
        }

        Tarea t = gestor.getTareas().get(fila);

        vista.getTxtTitulo().setText(t.getTitulo());
        vista.getTxtTitulo().setForeground(java.awt.Color.BLACK);

        vista.getTxtDescripcion().setText(t.getDescripcion());
        vista.getTxtDescripcion().setForeground(java.awt.Color.BLACK);

        vista.getTxtFechaLimite().setText(t.getFechaLimite());
        vista.getTxtFechaLimite().setForeground(java.awt.Color.BLACK);

        switch (t.getEstado()) {
            case PENDIENTE ->
                vista.getRPendiente().setSelected(true);
            case EN_PROGRESO ->
                vista.getRProgreso().setSelected(true);
            case COMPLETADA ->
                vista.getRCompletada().setSelected(true);
        }
    }

    private void guardarCambios() {
        int fila = vista.getTablaTareas().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una tarea.");
            return;
        }

        String titulo = vista.getTxtTitulo().getText().trim();
        String desc = vista.getTxtDescripcion().getText().trim();
        String fecha = vista.getTxtFechaLimite().getText().trim();

        Tarea nueva = new Tarea(titulo, desc, fecha, vista.getEstadoSeleccionado());

        gestor.editarTarea(fila, nueva);
        gestor.guardar(archivoTareas);

        cargarTabla();
        vista.limpiarCampos();

        JOptionPane.showMessageDialog(vista, "Cambios guardados.");
    }

    private void cambiarUsuario() {
        String nuevo = JOptionPane.showInputDialog(vista, "Nuevo usuario:");
        if (nuevo != null && !nuevo.trim().isEmpty()) {
            vista.getLblUsuario().setText("Usuario: " + nuevo.trim());
            guardarConfiguracion();
        }
    }

    private void cambiarTema() {
        vista.setIndiceTema((vista.getIndiceTema() + 1) % 5);
        guardarConfiguracion();
    }

    private void configurarEventos() {

        vista.getBtnAgregar().addActionListener(e -> agregarTarea());
        vista.getBtnEliminar().addActionListener(e -> eliminarTarea());
        vista.getBtnEditar().addActionListener(e -> cargarTareaEnCampos());
        vista.getBtnGuardar().addActionListener(e -> guardarCambios());
        vista.getBtnLimpiar().addActionListener(e -> vista.limpiarCampos());
        vista.getBtnCambiarUsuario().addActionListener(e -> cambiarUsuario());
        vista.getBtnCambiarTema().addActionListener(e -> cambiarTema());
    }
}
