package pe.edu.upeu.sysalmacenfx.control;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sysalmacenfx.componente.ColumnInfo;
import pe.edu.upeu.sysalmacenfx.componente.ReportAlert;
import pe.edu.upeu.sysalmacenfx.componente.TableViewHelper;
import pe.edu.upeu.sysalmacenfx.componente.Toast;
import pe.edu.upeu.sysalmacenfx.modelo.Actor;
import pe.edu.upeu.sysalmacenfx.modelo.Genero;
import pe.edu.upeu.sysalmacenfx.modelo.Pelicula;
import pe.edu.upeu.sysalmacenfx.servicio.ActorService;
import pe.edu.upeu.sysalmacenfx.servicio.GeneroService;
import pe.edu.upeu.sysalmacenfx.servicio.PeliculaService;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class PeliculaController {


    @Autowired
    private PeliculaService peliculaService;
    private JasperPrint jasperPrint;

    @Autowired
    private GeneroService generoService;

    @FXML
    TextField txtNombre, txtPorcentaje, txtUnitario, txtStock, txtGenero;
    @FXML
    private TableView<Pelicula> tableView;

    @FXML
    private AnchorPane miContenedor;

    Stage stage;

    ObservableList<Pelicula> listarPelicula;
    Pelicula formulario;
    private Validator validator;
    @FXML
    Label lbnMsg;
    @FXML
    private ComboBox<Genero> cmbGenero;

    Integer idPelicula = 0;
    @Autowired
    private GeneroController generoController;

    public void cancelarAccion() {
        clearForm();
        limpiarError();
    }


    public void initialize() {

        // Cargar géneros en el ComboBox
        ObservableList<Genero> generos = FXCollections.observableArrayList(generoService.list());
        cmbGenero.setItems(generos);

        // Configurar cómo se muestra el nombre del género en el ComboBox
        cmbGenero.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Genero genero, boolean empty) {
                super.updateItem(genero, empty);
                if (empty || genero == null) {
                    setText(null);
                } else {
                    setText(genero.getNombre());
                }
            }
        });
        cmbGenero.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Genero genero, boolean empty) {
                super.updateItem(genero, empty);
                if (empty || genero == null) {
                    setText(null);
                } else {
                    setText(genero.getNombre());
                }
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), event -> {
            stage = (Stage) miContenedor.getScene().getWindow();
            if (stage != null) {
                System.out.println("El título del stage es: " + stage.getTitle());
            } else {
                System.out.println("Stage aún no disponible.");
            }
        }));
        timeline.setCycleCount(1);
        timeline.play();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        TableViewHelper<Pelicula> tableViewHelper = new TableViewHelper<>();
        LinkedHashMap<String, ColumnInfo> columns = new LinkedHashMap<>();
        columns.put("Id", new ColumnInfo("idPelicula", 60.0));
        columns.put("Pelicula", new ColumnInfo("nombre", 200.0));
        columns.put("Porce Util", new ColumnInfo("porceUtil", 100.0));
        columns.put("Pre Unitario", new ColumnInfo("preUnitario", 100.0));
        columns.put("Stock", new ColumnInfo("stock", 100.0));
        columns.put("Genero", new ColumnInfo("generoNombre", 100.0));


        Consumer<Pelicula> updateAction = (Pelicula pelicula) -> {
            editForm(pelicula);

        };
        Consumer<Pelicula> deleteAction = (Pelicula pelicula) -> {
            peliculaService.delete(pelicula.getIdPelicula());
            double with = stage.getWidth() / 1.5;
            double h = stage.getHeight() / 2;
            Toast.showToast(stage, "Se eliminó correctamente!!", 2000, with, h);
            listar();
        };
        tableViewHelper.addColumnsInOrderWithSize(tableView, columns, updateAction, deleteAction);

        tableView.setTableMenuButtonVisible(true);
        listar();
    }

    public void clearForm() {
        txtNombre.setText("");
        txtPorcentaje.setText("");
        txtUnitario.setText("");
        txtStock.setText("");
        cmbGenero.getSelectionModel().clearSelection(); // Limpiar selección del ComboBox
        limpiarError();
    }

    private void editForm(Pelicula pelicula) {
        txtNombre.setText(pelicula.getNombre());
        txtPorcentaje.setText(String.valueOf(pelicula.getPorceUtil()));
        txtUnitario.setText(pelicula.getPreUnitario().toString());
        txtStock.setText(String.valueOf(pelicula.getStock()));
        cmbGenero.setValue(pelicula.getGenero()); // Seleccionar el género actual
        limpiarError();
    }

    public void limpiarError() {
        txtNombre.getStyleClass().remove("text-field-error");
    }

    public void listar() {
        try {
            tableView.getItems().clear();
            listarPelicula = FXCollections.observableArrayList(peliculaService.list());
            tableView.getItems().addAll(listarPelicula);
            // Agregar un listener al campo de texto txtFiltroDato para filtrar los productos
            // txtFiltroDato.textProperty().addListener((observable, oldValue, newValue) -> {
            //    filtrarProductos(newValue);
            //});
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void validarFormulario() {
        formulario = new Pelicula();
        formulario.setNombre(txtNombre.getText());
        System.out.println("===========================");
        System.out.println(txtPorcentaje.getText());
        System.out.println("============================");
        formulario.setPorceUtil(Double.parseDouble(txtPorcentaje.getText()));
        formulario.setPreUnitario(new BigDecimal(txtUnitario.getText()));
        formulario.setStock(Integer.parseInt(txtStock.getText()));
        Genero generoSeleccionado = cmbGenero.getValue();
        if (generoSeleccionado == null) {
            lbnMsg.setText("Debe seleccionar un género");
            lbnMsg.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            return;
        }
        formulario.setGenero(generoSeleccionado);

        Set<ConstraintViolation<Pelicula>> violaciones = validator.validate(formulario);
        List<ConstraintViolation<Pelicula>> violacionesOrdenadasPorPropiedad = violaciones.stream()
                .sorted((v1, v2) -> v1.getPropertyPath().toString().compareTo(v2.getPropertyPath().toString()))
                .collect(Collectors.toList());
        if (violacionesOrdenadasPorPropiedad.isEmpty()) {
            lbnMsg.setText("Formulario válido");
            lbnMsg.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            limpiarError();
            double with = stage.getWidth() / 1.5;
            double h = stage.getHeight() / 2;
            if (idPelicula != 0 && idPelicula > 0) {
                formulario.setIdPelicula(idPelicula);
                peliculaService.update(formulario);
                Toast.showToast(stage, "Se actualizó correctamente!!", 2000, with, h);
                clearForm();

            } else {
                peliculaService.save(formulario);
                Toast.showToast(stage, "Se guardo correctamente!!", 2000, with, h);
                clearForm();
            }
            listar();


        } else {
            validarCampos(violacionesOrdenadasPorPropiedad);
        }

    }

    private void validarCampos(List<ConstraintViolation<Pelicula>> violacionesOrdenadasPorPropiedad) {
        LinkedHashMap<String, String> erroresOrdenados = new LinkedHashMap<>();
        for (ConstraintViolation<Pelicula> violacion : violacionesOrdenadasPorPropiedad) {
            String campo = violacion.getPropertyPath().toString();
            if (campo.equals("nombre")) {
                erroresOrdenados.put("nombre", violacion.getMessage());
                txtNombre.getStyleClass().add("text-field-error");

            }
            Map.Entry<String, String> primerError = erroresOrdenados.entrySet().iterator().next();
            lbnMsg.setText(primerError.getValue()); // Mostrar el mensaje del primer error
            lbnMsg.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        }
    }


    public void imprimir(ActionEvent actionEvent) {
        try {
            jasperPrint = peliculaService.runReport();
            Platform.runLater(() -> {
                ReportAlert reportAlert = new ReportAlert(jasperPrint);
                reportAlert.show();

            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}