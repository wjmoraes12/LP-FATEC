package br.com.fatec.atividade.ui;

import br.com.fatec.atividade.dao.CiclistaDAO;
import br.com.fatec.atividade.dao.BicicletaDAO;
import br.com.fatec.atividade.dao.TrilhaDAO;
import br.com.fatec.atividade.model.Ciclista;
import br.com.fatec.atividade.model.Bicicleta;
import br.com.fatec.atividade.model.Trilha;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;

public final class Imagem2Controller {
    @FXML private TextField ciclistaNome;
    @FXML private TextField ciclistaNivel;
    @FXML private TextField ciclistaVelocidade;
    @FXML private TableView<Ciclista> ciclistaTable;
    @FXML private Button ciclistaSaveButton;
    @FXML private Label ciclistaStatus;
    private final ObservableList<Ciclista> ciclistaRecords = FXCollections.observableArrayList();
    private Ciclista selectedCiclista;

    @FXML private TextField bicicletaMarca;
    @FXML private TextField bicicletaAro;
    @FXML private TextField bicicletaTipoFreio;
    @FXML private TableView<Bicicleta> bicicletaTable;
    @FXML private Button bicicletaSaveButton;
    @FXML private Label bicicletaStatus;
    private final ObservableList<Bicicleta> bicicletaRecords = FXCollections.observableArrayList();
    private Bicicleta selectedBicicleta;

    @FXML private TextField trilhaNome;
    @FXML private TextField trilhaDistancia;
    @FXML private TextField trilhaDificuldade;
    @FXML private TableView<Trilha> trilhaTable;
    @FXML private Button trilhaSaveButton;
    @FXML private Label trilhaStatus;
    private final ObservableList<Trilha> trilhaRecords = FXCollections.observableArrayList();
    private Trilha selectedTrilha;

    @FXML
    private void initialize() {
        setupCiclistaTable();
        setupBicicletaTable();
        setupTrilhaTable();
    }

    private void setupCiclistaTable() {
        ciclistaTable.setItems(ciclistaRecords);
        ciclistaTable.getColumns().clear();
        TableColumn<Ciclista, String> ciclistaIdColumn = new TableColumn<>("ID");
        ciclistaIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        ciclistaTable.getColumns().add(ciclistaIdColumn);
        TableColumn<Ciclista, String> ciclistaNomeColumn = new TableColumn<>("Nome");
        ciclistaNomeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getNome())));
        ciclistaTable.getColumns().add(ciclistaNomeColumn);
        TableColumn<Ciclista, String> ciclistaNivelColumn = new TableColumn<>("Nível");
        ciclistaNivelColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getNivel())));
        ciclistaTable.getColumns().add(ciclistaNivelColumn);
        TableColumn<Ciclista, String> ciclistaVelocidadeColumn = new TableColumn<>("Velocidade (km/h)");
        ciclistaVelocidadeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getVelocidade())));
        ciclistaTable.getColumns().add(ciclistaVelocidadeColumn);
        ciclistaTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedCiclista = selected;
            if (selected == null) {
                ciclistaSaveButton.setText("Cadastrar registro");
                return;
            }
            ciclistaNome.setText(String.valueOf(selected.getNome()));
            ciclistaNivel.setText(String.valueOf(selected.getNivel()));
            ciclistaVelocidade.setText(String.valueOf(selected.getVelocidade()));
            ciclistaSaveButton.setText("Atualizar registro");
            ciclistaStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshCiclista();
    }

    @FXML
    private void onSaveCiclista() {
        try {
            String message;
            if (selectedCiclista == null) {
                CiclistaDAO.inserir(required(ciclistaNome), required(ciclistaNivel), decimal(ciclistaVelocidade));
                message = "Registro cadastrado.";
            } else {
                message = CiclistaDAO.atualizar(selectedCiclista.getId(), required(ciclistaNome), required(ciclistaNivel), decimal(ciclistaVelocidade))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearCiclista();
            refreshCiclista();
            ciclistaStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(ciclistaStatus, e.getMessage());
        } catch (SQLException e) {
            showError(ciclistaStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteCiclista() {
        if (selectedCiclista == null) {
            showError(ciclistaStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedCiclista.getId())) return;
        try {
            CiclistaDAO.excluir(selectedCiclista.getId());
            clearCiclista();
            refreshCiclista();
            ciclistaStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(ciclistaStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearCiclista() {
        clearCiclista();
        ciclistaStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshCiclista() {
        refreshCiclista();
    }

    private void clearCiclista() {
        ciclistaTable.getSelectionModel().clearSelection();
        selectedCiclista = null;
        ciclistaNome.clear();
        ciclistaNivel.clear();
        ciclistaVelocidade.clear();
        ciclistaSaveButton.setText("Cadastrar registro");
    }

    private void refreshCiclista() {
        try {
            ciclistaRecords.setAll(CiclistaDAO.listar());
            ciclistaStatus.setText(ciclistaRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            ciclistaRecords.clear();
            ciclistaStatus.setText("Banco indisponível: " + e.getMessage());
        }
    }

    private void setupBicicletaTable() {
        bicicletaTable.setItems(bicicletaRecords);
        bicicletaTable.getColumns().clear();
        TableColumn<Bicicleta, String> bicicletaIdColumn = new TableColumn<>("ID");
        bicicletaIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        bicicletaTable.getColumns().add(bicicletaIdColumn);
        TableColumn<Bicicleta, String> bicicletaMarcaColumn = new TableColumn<>("Marca");
        bicicletaMarcaColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getMarca())));
        bicicletaTable.getColumns().add(bicicletaMarcaColumn);
        TableColumn<Bicicleta, String> bicicletaAroColumn = new TableColumn<>("Aro");
        bicicletaAroColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getAro())));
        bicicletaTable.getColumns().add(bicicletaAroColumn);
        TableColumn<Bicicleta, String> bicicletaTipoFreioColumn = new TableColumn<>("Tipo de freio");
        bicicletaTipoFreioColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getTipoFreio())));
        bicicletaTable.getColumns().add(bicicletaTipoFreioColumn);
        bicicletaTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedBicicleta = selected;
            if (selected == null) {
                bicicletaSaveButton.setText("Cadastrar registro");
                return;
            }
            bicicletaMarca.setText(String.valueOf(selected.getMarca()));
            bicicletaAro.setText(String.valueOf(selected.getAro()));
            bicicletaTipoFreio.setText(String.valueOf(selected.getTipoFreio()));
            bicicletaSaveButton.setText("Atualizar registro");
            bicicletaStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshBicicleta();
    }

    @FXML
    private void onSaveBicicleta() {
        try {
            String message;
            if (selectedBicicleta == null) {
                BicicletaDAO.inserir(required(bicicletaMarca), integer(bicicletaAro), required(bicicletaTipoFreio));
                message = "Registro cadastrado.";
            } else {
                message = BicicletaDAO.atualizar(selectedBicicleta.getId(), required(bicicletaMarca), integer(bicicletaAro), required(bicicletaTipoFreio))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearBicicleta();
            refreshBicicleta();
            bicicletaStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(bicicletaStatus, e.getMessage());
        } catch (SQLException e) {
            showError(bicicletaStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteBicicleta() {
        if (selectedBicicleta == null) {
            showError(bicicletaStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedBicicleta.getId())) return;
        try {
            BicicletaDAO.excluir(selectedBicicleta.getId());
            clearBicicleta();
            refreshBicicleta();
            bicicletaStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(bicicletaStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearBicicleta() {
        clearBicicleta();
        bicicletaStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshBicicleta() {
        refreshBicicleta();
    }

    private void clearBicicleta() {
        bicicletaTable.getSelectionModel().clearSelection();
        selectedBicicleta = null;
        bicicletaMarca.clear();
        bicicletaAro.clear();
        bicicletaTipoFreio.clear();
        bicicletaSaveButton.setText("Cadastrar registro");
    }

    private void refreshBicicleta() {
        try {
            bicicletaRecords.setAll(BicicletaDAO.listar());
            bicicletaStatus.setText(bicicletaRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            bicicletaRecords.clear();
            bicicletaStatus.setText("Banco indisponível: " + e.getMessage());
        }
    }

    private void setupTrilhaTable() {
        trilhaTable.setItems(trilhaRecords);
        trilhaTable.getColumns().clear();
        TableColumn<Trilha, String> trilhaIdColumn = new TableColumn<>("ID");
        trilhaIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        trilhaTable.getColumns().add(trilhaIdColumn);
        TableColumn<Trilha, String> trilhaNomeColumn = new TableColumn<>("Nome");
        trilhaNomeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getNome())));
        trilhaTable.getColumns().add(trilhaNomeColumn);
        TableColumn<Trilha, String> trilhaDistanciaColumn = new TableColumn<>("Distância (km)");
        trilhaDistanciaColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getDistancia())));
        trilhaTable.getColumns().add(trilhaDistanciaColumn);
        TableColumn<Trilha, String> trilhaDificuldadeColumn = new TableColumn<>("Dificuldade");
        trilhaDificuldadeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getDificuldade())));
        trilhaTable.getColumns().add(trilhaDificuldadeColumn);
        trilhaTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedTrilha = selected;
            if (selected == null) {
                trilhaSaveButton.setText("Cadastrar registro");
                return;
            }
            trilhaNome.setText(String.valueOf(selected.getNome()));
            trilhaDistancia.setText(String.valueOf(selected.getDistancia()));
            trilhaDificuldade.setText(String.valueOf(selected.getDificuldade()));
            trilhaSaveButton.setText("Atualizar registro");
            trilhaStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshTrilha();
    }

    @FXML
    private void onSaveTrilha() {
        try {
            String message;
            if (selectedTrilha == null) {
                TrilhaDAO.inserir(required(trilhaNome), decimal(trilhaDistancia), required(trilhaDificuldade));
                message = "Registro cadastrado.";
            } else {
                message = TrilhaDAO.atualizar(selectedTrilha.getId(), required(trilhaNome), decimal(trilhaDistancia), required(trilhaDificuldade))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearTrilha();
            refreshTrilha();
            trilhaStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(trilhaStatus, e.getMessage());
        } catch (SQLException e) {
            showError(trilhaStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteTrilha() {
        if (selectedTrilha == null) {
            showError(trilhaStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedTrilha.getId())) return;
        try {
            TrilhaDAO.excluir(selectedTrilha.getId());
            clearTrilha();
            refreshTrilha();
            trilhaStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(trilhaStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearTrilha() {
        clearTrilha();
        trilhaStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshTrilha() {
        refreshTrilha();
    }

    private void clearTrilha() {
        trilhaTable.getSelectionModel().clearSelection();
        selectedTrilha = null;
        trilhaNome.clear();
        trilhaDistancia.clear();
        trilhaDificuldade.clear();
        trilhaSaveButton.setText("Cadastrar registro");
    }

    private void refreshTrilha() {
        try {
            trilhaRecords.setAll(TrilhaDAO.listar());
            trilhaStatus.setText(trilhaRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            trilhaRecords.clear();
            trilhaStatus.setText("Banco indisponível: " + e.getMessage());
        }
    }

    private static String required(TextField field) {
        String value = field.getText().trim();
        if (value.isBlank()) throw new IllegalArgumentException("Preencha todos os campos.");
        return value;
    }

    private static int integer(TextField field) {
        return Integer.parseInt(required(field));
    }

    private static double decimal(TextField field) {
        return Double.parseDouble(required(field).replace(',', '.'));
    }

    private static boolean confirmDelete(Long id) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Excluir registro");
        confirmation.setHeaderText("Excluir registro " + id + "?");
        confirmation.setContentText("Essa ação não pode ser desfeita.");
        return confirmation.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private static void showError(Label status, String message) {
        status.setText(message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Não foi possível concluir a operação");
        alert.setHeaderText("Confira os dados e a conexão com o banco.");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
