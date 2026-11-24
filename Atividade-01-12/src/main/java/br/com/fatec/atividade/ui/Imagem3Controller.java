package br.com.fatec.atividade.ui;

import br.com.fatec.atividade.dao.MascaraDAO;
import br.com.fatec.atividade.dao.ColecionadorDAO;
import br.com.fatec.atividade.dao.ExposicaoDAO;
import br.com.fatec.atividade.model.Mascara;
import br.com.fatec.atividade.model.Colecionador;
import br.com.fatec.atividade.model.Exposicao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;

public final class Imagem3Controller {
    @FXML private TextField mascaraOrigem;
    @FXML private TextField mascaraMaterial;
    @FXML private TextField mascaraEstilo;
    @FXML private TableView<Mascara> mascaraTable;
    @FXML private Button mascaraSaveButton;
    @FXML private Label mascaraStatus;
    private final ObservableList<Mascara> mascaraRecords = FXCollections.observableArrayList();
    private Mascara selectedMascara;

    @FXML private TextField colecionadorNome;
    @FXML private TextField colecionadorQuantidadePecas;
    @FXML private TextField colecionadorEspecialidade;
    @FXML private TableView<Colecionador> colecionadorTable;
    @FXML private Button colecionadorSaveButton;
    @FXML private Label colecionadorStatus;
    private final ObservableList<Colecionador> colecionadorRecords = FXCollections.observableArrayList();
    private Colecionador selectedColecionador;

    @FXML private TextField exposicaoNome;
    @FXML private TextField exposicaoLocal;
    @FXML private TextField exposicaoQuantidadeMascara;
    @FXML private TableView<Exposicao> exposicaoTable;
    @FXML private Button exposicaoSaveButton;
    @FXML private Label exposicaoStatus;
    private final ObservableList<Exposicao> exposicaoRecords = FXCollections.observableArrayList();
    private Exposicao selectedExposicao;

    @FXML
    private void initialize() {
        setupMascaraTable();
        setupColecionadorTable();
        setupExposicaoTable();
    }

    private void setupMascaraTable() {
        mascaraTable.setItems(mascaraRecords);
        mascaraTable.getColumns().clear();
        TableColumn<Mascara, String> mascaraIdColumn = new TableColumn<>("ID");
        mascaraIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        mascaraTable.getColumns().add(mascaraIdColumn);
        TableColumn<Mascara, String> mascaraOrigemColumn = new TableColumn<>("Origem");
        mascaraOrigemColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getOrigem())));
        mascaraTable.getColumns().add(mascaraOrigemColumn);
        TableColumn<Mascara, String> mascaraMaterialColumn = new TableColumn<>("Material");
        mascaraMaterialColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getMaterial())));
        mascaraTable.getColumns().add(mascaraMaterialColumn);
        TableColumn<Mascara, String> mascaraEstiloColumn = new TableColumn<>("Estilo");
        mascaraEstiloColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getEstilo())));
        mascaraTable.getColumns().add(mascaraEstiloColumn);
        mascaraTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedMascara = selected;
            if (selected == null) {
                mascaraSaveButton.setText("Cadastrar registro");
                return;
            }
            mascaraOrigem.setText(String.valueOf(selected.getOrigem()));
            mascaraMaterial.setText(String.valueOf(selected.getMaterial()));
            mascaraEstilo.setText(String.valueOf(selected.getEstilo()));
            mascaraSaveButton.setText("Atualizar registro");
            mascaraStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshMascara();
    }

    @FXML
    private void onSaveMascara() {
        try {
            String message;
            if (selectedMascara == null) {
                MascaraDAO.inserir(required(mascaraOrigem), required(mascaraMaterial), required(mascaraEstilo));
                message = "Registro cadastrado.";
            } else {
                message = MascaraDAO.atualizar(selectedMascara.getId(), required(mascaraOrigem), required(mascaraMaterial), required(mascaraEstilo))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearMascara();
            refreshMascara();
            mascaraStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(mascaraStatus, e.getMessage());
        } catch (SQLException e) {
            showError(mascaraStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteMascara() {
        if (selectedMascara == null) {
            showError(mascaraStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedMascara.getId())) return;
        try {
            MascaraDAO.excluir(selectedMascara.getId());
            clearMascara();
            refreshMascara();
            mascaraStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(mascaraStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearMascara() {
        clearMascara();
        mascaraStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshMascara() {
        refreshMascara();
    }

    private void clearMascara() {
        mascaraTable.getSelectionModel().clearSelection();
        selectedMascara = null;
        mascaraOrigem.clear();
        mascaraMaterial.clear();
        mascaraEstilo.clear();
        mascaraSaveButton.setText("Cadastrar registro");
    }

    private void refreshMascara() {
        try {
            mascaraRecords.setAll(MascaraDAO.listar());
            mascaraStatus.setText(mascaraRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            mascaraRecords.clear();
            mascaraStatus.setText("Banco indisponível: " + e.getMessage());
        }
    }

    private void setupColecionadorTable() {
        colecionadorTable.setItems(colecionadorRecords);
        colecionadorTable.getColumns().clear();
        TableColumn<Colecionador, String> colecionadorIdColumn = new TableColumn<>("ID");
        colecionadorIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        colecionadorTable.getColumns().add(colecionadorIdColumn);
        TableColumn<Colecionador, String> colecionadorNomeColumn = new TableColumn<>("Nome");
        colecionadorNomeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getNome())));
        colecionadorTable.getColumns().add(colecionadorNomeColumn);
        TableColumn<Colecionador, String> colecionadorQuantidadePecasColumn = new TableColumn<>("Quantidade de peças");
        colecionadorQuantidadePecasColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getQuantidadePecas())));
        colecionadorTable.getColumns().add(colecionadorQuantidadePecasColumn);
        TableColumn<Colecionador, String> colecionadorEspecialidadeColumn = new TableColumn<>("Especialidade");
        colecionadorEspecialidadeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getEspecialidade())));
        colecionadorTable.getColumns().add(colecionadorEspecialidadeColumn);
        colecionadorTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedColecionador = selected;
            if (selected == null) {
                colecionadorSaveButton.setText("Cadastrar registro");
                return;
            }
            colecionadorNome.setText(String.valueOf(selected.getNome()));
            colecionadorQuantidadePecas.setText(String.valueOf(selected.getQuantidadePecas()));
            colecionadorEspecialidade.setText(String.valueOf(selected.getEspecialidade()));
            colecionadorSaveButton.setText("Atualizar registro");
            colecionadorStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshColecionador();
    }

    @FXML
    private void onSaveColecionador() {
        try {
            String message;
            if (selectedColecionador == null) {
                ColecionadorDAO.inserir(required(colecionadorNome), integer(colecionadorQuantidadePecas), required(colecionadorEspecialidade));
                message = "Registro cadastrado.";
            } else {
                message = ColecionadorDAO.atualizar(selectedColecionador.getId(), required(colecionadorNome), integer(colecionadorQuantidadePecas), required(colecionadorEspecialidade))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearColecionador();
            refreshColecionador();
            colecionadorStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(colecionadorStatus, e.getMessage());
        } catch (SQLException e) {
            showError(colecionadorStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteColecionador() {
        if (selectedColecionador == null) {
            showError(colecionadorStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedColecionador.getId())) return;
        try {
            ColecionadorDAO.excluir(selectedColecionador.getId());
            clearColecionador();
            refreshColecionador();
            colecionadorStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(colecionadorStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearColecionador() {
        clearColecionador();
        colecionadorStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshColecionador() {
        refreshColecionador();
    }

    private void clearColecionador() {
        colecionadorTable.getSelectionModel().clearSelection();
        selectedColecionador = null;
        colecionadorNome.clear();
        colecionadorQuantidadePecas.clear();
        colecionadorEspecialidade.clear();
        colecionadorSaveButton.setText("Cadastrar registro");
    }

    private void refreshColecionador() {
        try {
            colecionadorRecords.setAll(ColecionadorDAO.listar());
            colecionadorStatus.setText(colecionadorRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            colecionadorRecords.clear();
            colecionadorStatus.setText("Banco indisponível: " + e.getMessage());
        }
    }

    private void setupExposicaoTable() {
        exposicaoTable.setItems(exposicaoRecords);
        exposicaoTable.getColumns().clear();
        TableColumn<Exposicao, String> exposicaoIdColumn = new TableColumn<>("ID");
        exposicaoIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        exposicaoTable.getColumns().add(exposicaoIdColumn);
        TableColumn<Exposicao, String> exposicaoNomeColumn = new TableColumn<>("Nome");
        exposicaoNomeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getNome())));
        exposicaoTable.getColumns().add(exposicaoNomeColumn);
        TableColumn<Exposicao, String> exposicaoLocalColumn = new TableColumn<>("Local");
        exposicaoLocalColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getLocal())));
        exposicaoTable.getColumns().add(exposicaoLocalColumn);
        TableColumn<Exposicao, String> exposicaoQuantidadeMascaraColumn = new TableColumn<>("Quantidade de máscaras");
        exposicaoQuantidadeMascaraColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getQuantidadeMascara())));
        exposicaoTable.getColumns().add(exposicaoQuantidadeMascaraColumn);
        exposicaoTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedExposicao = selected;
            if (selected == null) {
                exposicaoSaveButton.setText("Cadastrar registro");
                return;
            }
            exposicaoNome.setText(String.valueOf(selected.getNome()));
            exposicaoLocal.setText(String.valueOf(selected.getLocal()));
            exposicaoQuantidadeMascara.setText(String.valueOf(selected.getQuantidadeMascara()));
            exposicaoSaveButton.setText("Atualizar registro");
            exposicaoStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshExposicao();
    }

    @FXML
    private void onSaveExposicao() {
        try {
            String message;
            if (selectedExposicao == null) {
                ExposicaoDAO.inserir(required(exposicaoNome), required(exposicaoLocal), integer(exposicaoQuantidadeMascara));
                message = "Registro cadastrado.";
            } else {
                message = ExposicaoDAO.atualizar(selectedExposicao.getId(), required(exposicaoNome), required(exposicaoLocal), integer(exposicaoQuantidadeMascara))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearExposicao();
            refreshExposicao();
            exposicaoStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(exposicaoStatus, e.getMessage());
        } catch (SQLException e) {
            showError(exposicaoStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteExposicao() {
        if (selectedExposicao == null) {
            showError(exposicaoStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedExposicao.getId())) return;
        try {
            ExposicaoDAO.excluir(selectedExposicao.getId());
            clearExposicao();
            refreshExposicao();
            exposicaoStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(exposicaoStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearExposicao() {
        clearExposicao();
        exposicaoStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshExposicao() {
        refreshExposicao();
    }

    private void clearExposicao() {
        exposicaoTable.getSelectionModel().clearSelection();
        selectedExposicao = null;
        exposicaoNome.clear();
        exposicaoLocal.clear();
        exposicaoQuantidadeMascara.clear();
        exposicaoSaveButton.setText("Cadastrar registro");
    }

    private void refreshExposicao() {
        try {
            exposicaoRecords.setAll(ExposicaoDAO.listar());
            exposicaoStatus.setText(exposicaoRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            exposicaoRecords.clear();
            exposicaoStatus.setText("Banco indisponível: " + e.getMessage());
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
