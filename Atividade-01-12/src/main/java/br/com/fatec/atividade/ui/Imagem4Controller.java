package br.com.fatec.atividade.ui;

import br.com.fatec.atividade.dao.ClienteDAO;
import br.com.fatec.atividade.dao.CorteDAO;
import br.com.fatec.atividade.dao.CabeleireiroDAO;
import br.com.fatec.atividade.model.Cliente;
import br.com.fatec.atividade.model.Corte;
import br.com.fatec.atividade.model.Cabeleireiro;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;

public final class Imagem4Controller {
    @FXML private TextField clienteNome;
    @FXML private TextField clienteTelefone;
    @FXML private TextField clienteTipoCabelo;
    @FXML private TableView<Cliente> clienteTable;
    @FXML private Button clienteSaveButton;
    @FXML private Label clienteStatus;
    private final ObservableList<Cliente> clienteRecords = FXCollections.observableArrayList();
    private Cliente selectedCliente;

    @FXML private TextField corteEstilo;
    @FXML private TextField corteComprimento;
    @FXML private TextField cortePreco;
    @FXML private TableView<Corte> corteTable;
    @FXML private Button corteSaveButton;
    @FXML private Label corteStatus;
    private final ObservableList<Corte> corteRecords = FXCollections.observableArrayList();
    private Corte selectedCorte;

    @FXML private TextField cabeleireiroNome;
    @FXML private TextField cabeleireiroEspecialidade;
    @FXML private TextField cabeleireiroAnosExperiencia;
    @FXML private TableView<Cabeleireiro> cabeleireiroTable;
    @FXML private Button cabeleireiroSaveButton;
    @FXML private Label cabeleireiroStatus;
    private final ObservableList<Cabeleireiro> cabeleireiroRecords = FXCollections.observableArrayList();
    private Cabeleireiro selectedCabeleireiro;

    @FXML
    private void initialize() {
        setupClienteTable();
        setupCorteTable();
        setupCabeleireiroTable();
    }

    private void setupClienteTable() {
        clienteTable.setItems(clienteRecords);
        clienteTable.getColumns().clear();
        TableColumn<Cliente, String> clienteIdColumn = new TableColumn<>("ID");
        clienteIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        clienteTable.getColumns().add(clienteIdColumn);
        TableColumn<Cliente, String> clienteNomeColumn = new TableColumn<>("Nome");
        clienteNomeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getNome())));
        clienteTable.getColumns().add(clienteNomeColumn);
        TableColumn<Cliente, String> clienteTelefoneColumn = new TableColumn<>("Telefone");
        clienteTelefoneColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getTelefone())));
        clienteTable.getColumns().add(clienteTelefoneColumn);
        TableColumn<Cliente, String> clienteTipoCabeloColumn = new TableColumn<>("Tipo de cabelo");
        clienteTipoCabeloColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getTipoCabelo())));
        clienteTable.getColumns().add(clienteTipoCabeloColumn);
        clienteTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedCliente = selected;
            if (selected == null) {
                clienteSaveButton.setText("Cadastrar registro");
                return;
            }
            clienteNome.setText(String.valueOf(selected.getNome()));
            clienteTelefone.setText(String.valueOf(selected.getTelefone()));
            clienteTipoCabelo.setText(String.valueOf(selected.getTipoCabelo()));
            clienteSaveButton.setText("Atualizar registro");
            clienteStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshCliente();
    }

    @FXML
    private void onSaveCliente() {
        try {
            String message;
            if (selectedCliente == null) {
                ClienteDAO.inserir(required(clienteNome), required(clienteTelefone), required(clienteTipoCabelo));
                message = "Registro cadastrado.";
            } else {
                message = ClienteDAO.atualizar(selectedCliente.getId(), required(clienteNome), required(clienteTelefone), required(clienteTipoCabelo))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearCliente();
            refreshCliente();
            clienteStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(clienteStatus, e.getMessage());
        } catch (SQLException e) {
            showError(clienteStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteCliente() {
        if (selectedCliente == null) {
            showError(clienteStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedCliente.getId())) return;
        try {
            ClienteDAO.excluir(selectedCliente.getId());
            clearCliente();
            refreshCliente();
            clienteStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(clienteStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearCliente() {
        clearCliente();
        clienteStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshCliente() {
        refreshCliente();
    }

    private void clearCliente() {
        clienteTable.getSelectionModel().clearSelection();
        selectedCliente = null;
        clienteNome.clear();
        clienteTelefone.clear();
        clienteTipoCabelo.clear();
        clienteSaveButton.setText("Cadastrar registro");
    }

    private void refreshCliente() {
        try {
            clienteRecords.setAll(ClienteDAO.listar());
            clienteStatus.setText(clienteRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            clienteRecords.clear();
            clienteStatus.setText("Banco indisponível: " + e.getMessage());
        }
    }

    private void setupCorteTable() {
        corteTable.setItems(corteRecords);
        corteTable.getColumns().clear();
        TableColumn<Corte, String> corteIdColumn = new TableColumn<>("ID");
        corteIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        corteTable.getColumns().add(corteIdColumn);
        TableColumn<Corte, String> corteEstiloColumn = new TableColumn<>("Estilo");
        corteEstiloColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getEstilo())));
        corteTable.getColumns().add(corteEstiloColumn);
        TableColumn<Corte, String> corteComprimentoColumn = new TableColumn<>("Comprimento");
        corteComprimentoColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getComprimento())));
        corteTable.getColumns().add(corteComprimentoColumn);
        TableColumn<Corte, String> cortePrecoColumn = new TableColumn<>("Preço (R$)");
        cortePrecoColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPreco())));
        corteTable.getColumns().add(cortePrecoColumn);
        corteTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedCorte = selected;
            if (selected == null) {
                corteSaveButton.setText("Cadastrar registro");
                return;
            }
            corteEstilo.setText(String.valueOf(selected.getEstilo()));
            corteComprimento.setText(String.valueOf(selected.getComprimento()));
            cortePreco.setText(String.valueOf(selected.getPreco()));
            corteSaveButton.setText("Atualizar registro");
            corteStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshCorte();
    }

    @FXML
    private void onSaveCorte() {
        try {
            String message;
            if (selectedCorte == null) {
                CorteDAO.inserir(required(corteEstilo), required(corteComprimento), decimal(cortePreco));
                message = "Registro cadastrado.";
            } else {
                message = CorteDAO.atualizar(selectedCorte.getId(), required(corteEstilo), required(corteComprimento), decimal(cortePreco))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearCorte();
            refreshCorte();
            corteStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(corteStatus, e.getMessage());
        } catch (SQLException e) {
            showError(corteStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteCorte() {
        if (selectedCorte == null) {
            showError(corteStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedCorte.getId())) return;
        try {
            CorteDAO.excluir(selectedCorte.getId());
            clearCorte();
            refreshCorte();
            corteStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(corteStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearCorte() {
        clearCorte();
        corteStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshCorte() {
        refreshCorte();
    }

    private void clearCorte() {
        corteTable.getSelectionModel().clearSelection();
        selectedCorte = null;
        corteEstilo.clear();
        corteComprimento.clear();
        cortePreco.clear();
        corteSaveButton.setText("Cadastrar registro");
    }

    private void refreshCorte() {
        try {
            corteRecords.setAll(CorteDAO.listar());
            corteStatus.setText(corteRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            corteRecords.clear();
            corteStatus.setText("Banco indisponível: " + e.getMessage());
        }
    }

    private void setupCabeleireiroTable() {
        cabeleireiroTable.setItems(cabeleireiroRecords);
        cabeleireiroTable.getColumns().clear();
        TableColumn<Cabeleireiro, String> cabeleireiroIdColumn = new TableColumn<>("ID");
        cabeleireiroIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        cabeleireiroTable.getColumns().add(cabeleireiroIdColumn);
        TableColumn<Cabeleireiro, String> cabeleireiroNomeColumn = new TableColumn<>("Nome");
        cabeleireiroNomeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getNome())));
        cabeleireiroTable.getColumns().add(cabeleireiroNomeColumn);
        TableColumn<Cabeleireiro, String> cabeleireiroEspecialidadeColumn = new TableColumn<>("Especialidade");
        cabeleireiroEspecialidadeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getEspecialidade())));
        cabeleireiroTable.getColumns().add(cabeleireiroEspecialidadeColumn);
        TableColumn<Cabeleireiro, String> cabeleireiroAnosExperienciaColumn = new TableColumn<>("Anos de experiência");
        cabeleireiroAnosExperienciaColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getAnosExperiencia())));
        cabeleireiroTable.getColumns().add(cabeleireiroAnosExperienciaColumn);
        cabeleireiroTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedCabeleireiro = selected;
            if (selected == null) {
                cabeleireiroSaveButton.setText("Cadastrar registro");
                return;
            }
            cabeleireiroNome.setText(String.valueOf(selected.getNome()));
            cabeleireiroEspecialidade.setText(String.valueOf(selected.getEspecialidade()));
            cabeleireiroAnosExperiencia.setText(String.valueOf(selected.getAnosExperiencia()));
            cabeleireiroSaveButton.setText("Atualizar registro");
            cabeleireiroStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshCabeleireiro();
    }

    @FXML
    private void onSaveCabeleireiro() {
        try {
            String message;
            if (selectedCabeleireiro == null) {
                CabeleireiroDAO.inserir(required(cabeleireiroNome), required(cabeleireiroEspecialidade), integer(cabeleireiroAnosExperiencia));
                message = "Registro cadastrado.";
            } else {
                message = CabeleireiroDAO.atualizar(selectedCabeleireiro.getId(), required(cabeleireiroNome), required(cabeleireiroEspecialidade), integer(cabeleireiroAnosExperiencia))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearCabeleireiro();
            refreshCabeleireiro();
            cabeleireiroStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(cabeleireiroStatus, e.getMessage());
        } catch (SQLException e) {
            showError(cabeleireiroStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteCabeleireiro() {
        if (selectedCabeleireiro == null) {
            showError(cabeleireiroStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedCabeleireiro.getId())) return;
        try {
            CabeleireiroDAO.excluir(selectedCabeleireiro.getId());
            clearCabeleireiro();
            refreshCabeleireiro();
            cabeleireiroStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(cabeleireiroStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearCabeleireiro() {
        clearCabeleireiro();
        cabeleireiroStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshCabeleireiro() {
        refreshCabeleireiro();
    }

    private void clearCabeleireiro() {
        cabeleireiroTable.getSelectionModel().clearSelection();
        selectedCabeleireiro = null;
        cabeleireiroNome.clear();
        cabeleireiroEspecialidade.clear();
        cabeleireiroAnosExperiencia.clear();
        cabeleireiroSaveButton.setText("Cadastrar registro");
    }

    private void refreshCabeleireiro() {
        try {
            cabeleireiroRecords.setAll(CabeleireiroDAO.listar());
            cabeleireiroStatus.setText(cabeleireiroRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            cabeleireiroRecords.clear();
            cabeleireiroStatus.setText("Banco indisponível: " + e.getMessage());
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
