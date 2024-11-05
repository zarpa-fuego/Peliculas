package pe.edu.upeu.sysalmacenfx.control;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sysalmacenfx.componente.ColumnInfo;
import pe.edu.upeu.sysalmacenfx.componente.TableViewHelper;
import pe.edu.upeu.sysalmacenfx.componente.Toast;
import pe.edu.upeu.sysalmacenfx.modelo.Actor;
import pe.edu.upeu.sysalmacenfx.servicio.ActorService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class ActorControl {


    @Autowired
    private ActorService actorService;

    @FXML
    TextField txtNombre, txtApellido;
    @FXML
    private TableView<Actor> tableView;

    @FXML
    private AnchorPane miContenedor;

    Stage stage;

    ObservableList<Actor> listarActor;
    Actor formulario;
    private Validator validator;
    @FXML
    Label lbnMsg;

    Integer idActor = 0;

    public void cancelarAccion() {
        clearForm();
        limpiarError();
    }


    public void initialize() {
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

        TableViewHelper<Actor> tableViewHelper = new TableViewHelper<>();
        LinkedHashMap<String, ColumnInfo> columns = new LinkedHashMap<>();
        columns.put("Id", new ColumnInfo("idActor", 60.0));
        columns.put("Nombre", new ColumnInfo("nombre", 200.0));
        columns.put("Apellido", new ColumnInfo("apellido", 200.0));

        Consumer<Actor> updateAction = (Actor actor) -> {
            editForm(actor);
        };
        Consumer<Actor> deleteAction = (Actor actor) -> {
            actorService.delete(actor.getIdActor());
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
        txtApellido.setText("");
        limpiarError();
    }

    private void editForm(Actor actor) {
        txtNombre.setText(actor.getNombre());
        txtApellido.setText(actor.getApellido());
        limpiarError();
    }

    public void limpiarError() {
        txtNombre.getStyleClass().remove("text-field-error");
        txtApellido.getStyleClass().remove("text-field-error");
    }

    public void listar() {
        try {
            tableView.getItems().clear();
            listarActor = FXCollections.observableArrayList(actorService.list());
            tableView.getItems().addAll(listarActor);
            // Agregar un listener al campo de texto txtFiltroDato para filtrar los productos
            // txtFiltroDato.textProperty().addListener((observable, oldValue, newValue) -> {
            //    filtrarProductos(newValue);
            //});
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void validarFormulario() {
        formulario = new Actor();
        formulario.setNombre(txtNombre.getText());
        formulario.setApellido(txtApellido.getText());
        Set<ConstraintViolation<Actor>> violaciones = validator.validate(formulario);
        List<ConstraintViolation<Actor>> violacionesOrdenadasPorPropiedad = violaciones.stream()
                .sorted((v1, v2) -> v1.getPropertyPath().toString().compareTo(v2.getPropertyPath().toString()))
                .collect(Collectors.toList());
        if (violacionesOrdenadasPorPropiedad.isEmpty()) {
            lbnMsg.setText("Formulario válido");
            lbnMsg.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            limpiarError();
            double with = stage.getWidth() / 1.5;
            double h = stage.getHeight() / 2;
            if (idActor != 0 && idActor > 0) {
                formulario.setIdActor(idActor);
                actorService.update(formulario);
                Toast.showToast(stage, "Se actualizó correctamente!!", 2000, with, h);
                clearForm();

            } else {
                actorService.save(formulario);
                Toast.showToast(stage, "Se guardo correctamente!!", 2000, with, h);
                clearForm();
            }
            listar();


        } else {
            validarCampos(violacionesOrdenadasPorPropiedad);
        }

    }

    private void validarCampos(List<ConstraintViolation<Actor>> violacionesOrdenadasPorPropiedad) {
        LinkedHashMap<String, String> erroresOrdenados = new LinkedHashMap<>();
        for (ConstraintViolation<Actor> violacion : violacionesOrdenadasPorPropiedad) {
            String campo = violacion.getPropertyPath().toString();
            if (campo.equals("nombre")) {
                erroresOrdenados.put("nombre", violacion.getMessage());
                txtNombre.getStyleClass().add("text-field-error");
            } else if (campo.equals("apellido")) {
                erroresOrdenados.put("apellido", violacion.getMessage());
                txtApellido.getStyleClass().add("text-field-error");
            }

        }
        Map.Entry<String, String> primerError = erroresOrdenados.entrySet().iterator().next();
        lbnMsg.setText(primerError.getValue()); // Mostrar el mensaje del primer error
        lbnMsg.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
    }
}
