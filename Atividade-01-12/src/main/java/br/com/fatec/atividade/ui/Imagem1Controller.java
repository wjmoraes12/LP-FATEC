package br.com.fatec.atividade.ui;

import br.com.fatec.atividade.dao.RoupaDAO;
import br.com.fatec.atividade.dao.ManequimDAO;
import br.com.fatec.atividade.dao.LojaDAO;
import br.com.fatec.atividade.model.Roupa;
import br.com.fatec.atividade.model.Manequim;
import br.com.fatec.atividade.model.Loja;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;

public final class Imagem1Controller {
    @FXML private TextField roupaTipo;
    @FXML private TextField roupaCor;
    @FXML private TextField roupaPreco;
    @FXML private TableView<Roupa> roupaTable;
    @FXML private Button roupaSaveButton;
    @FXML private Label roupaStatus;
    private final ObservableList<Roupa> roupaRecords = FXCollections.observableArrayList();
    private Roupa selectedRoupa;

    @FXML private TextField manequimMaterial;
    @FXML private TextField manequimCor;
    @FXML private TextField manequimRoupa;
    @FXML private TableView<Manequim> manequimTable;
    @FXML private Button manequimSaveButton;
    @FXML private Label manequimStatus;
    private final ObservableList<Manequim> manequimRecords = FXCollections.observableArrayList();
    private Manequim selectedManequim;

    @FXML private TextField lojaNome;
    @FXML private TextField lojaEndereco;
    @FXML private TextField lojaQuantidadeFuncionarios;
    @FXML private TableView<Loja> lojaTable;
    @FXML private Button lojaSaveButton;
    @FXML private Label lojaStatus;
    private final ObservableList<Loja> lojaRecords = FXCollections.observableArrayList();
    private Loja selectedLoja;

    @FXML
    private void initialize() {
        setupRoupaTable();
        setupManequimTable();
        setupLojaTable();
    }

    private void setupRoupaTable() {
        roupaTable.setItems(roupaRecords);
        roupaTable.getColumns().clear();
        TableColumn<Roupa, String> roupaIdColumn = new TableColumn<>("ID");
        roupaIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        roupaTable.getColumns().add(roupaIdColumn);
        TableColumn<Roupa, String> roupaTipoColumn = new TableColumn<>("Tipo");
        roupaTipoColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getTipo())));
        roupaTable.getColumns().add(roupaTipoColumn);
        TableColumn<Roupa, String> roupaCorColumn = new TableColumn<>("Cor");
        roupaCorColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCor())));
        roupaTable.getColumns().add(roupaCorColumn);
        TableColumn<Roupa, String> roupaPrecoColumn = new TableColumn<>("Preço (R$)");
        roupaPrecoColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPreco())));
        roupaTable.getColumns().add(roupaPrecoColumn);
        roupaTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedRoupa = selected;
            if (selected == null) {
                roupaSaveButton.setText("Cadastrar registro");
                return;
            }
            roupaTipo.setText(String.valueOf(selected.getTipo()));
            roupaCor.setText(String.valueOf(selected.getCor()));
            roupaPreco.setText(String.valueOf(selected.getPreco()));
            roupaSaveButton.setText("Atualizar registro");
            roupaStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshRoupa();
    }

    @FXML
    private void onSaveRoupa() {
        try {
            String message;
            if (selectedRoupa == null) {
                RoupaDAO.inserir(required(roupaTipo), required(roupaCor), decimal(roupaPreco));
                message = "Registro cadastrado.";
            } else {
                message = RoupaDAO.atualizar(selectedRoupa.getId(), required(roupaTipo), required(roupaCor), decimal(roupaPreco))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearRoupa();
            refreshRoupa();
            roupaStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(roupaStatus, e.getMessage());
        } catch (SQLException e) {
            showError(roupaStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteRoupa() {
        if (selectedRoupa == null) {
            showError(roupaStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedRoupa.getId())) return;
        try {
            RoupaDAO.excluir(selectedRoupa.getId());
            clearRoupa();
            refreshRoupa();
            roupaStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(roupaStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearRoupa() {
        clearRoupa();
        roupaStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshRoupa() {
        refreshRoupa();
    }

    private void clearRoupa() {
        roupaTable.getSelectionModel().clearSelection();
        selectedRoupa = null;
        roupaTipo.clear();
        roupaCor.clear();
        roupaPreco.clear();
        roupaSaveButton.setText("Cadastrar registro");
    }

    private void refreshRoupa() {
        try {
            roupaRecords.setAll(RoupaDAO.listar());
            roupaStatus.setText(roupaRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            roupaRecords.clear();
            roupaStatus.setText("Banco indisponível: " + e.getMessage());
        }
    }

    private void setupManequimTable() {
        manequimTable.setItems(manequimRecords);
        manequimTable.getColumns().clear();
        TableColumn<Manequim, String> manequimIdColumn = new TableColumn<>("ID");
        manequimIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        manequimTable.getColumns().add(manequimIdColumn);
        TableColumn<Manequim, String> manequimMaterialColumn = new TableColumn<>("Material");
        manequimMaterialColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getMaterial())));
        manequimTable.getColumns().add(manequimMaterialColumn);
        TableColumn<Manequim, String> manequimCorColumn = new TableColumn<>("Cor");
        manequimCorColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCor())));
        manequimTable.getColumns().add(manequimCorColumn);
        TableColumn<Manequim, String> manequimRoupaColumn = new TableColumn<>("Roupa exibida");
        manequimRoupaColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getRoupa())));
        manequimTable.getColumns().add(manequimRoupaColumn);
        manequimTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedManequim = selected;
            if (selected == null) {
                manequimSaveButton.setText("Cadastrar registro");
                return;
            }
            manequimMaterial.setText(String.valueOf(selected.getMaterial()));
            manequimCor.setText(String.valueOf(selected.getCor()));
            manequimRoupa.setText(String.valueOf(selected.getRoupa()));
            manequimSaveButton.setText("Atualizar registro");
            manequimStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshManequim();
    }

    @FXML
    private void onSaveManequim() {
        try {
            String message;
            if (selectedManequim == null) {
                ManequimDAO.inserir(required(manequimMaterial), required(manequimCor), required(manequimRoupa));
                message = "Registro cadastrado.";
            } else {
                message = ManequimDAO.atualizar(selectedManequim.getId(), required(manequimMaterial), required(manequimCor), required(manequimRoupa))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearManequim();
            refreshManequim();
            manequimStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(manequimStatus, e.getMessage());
        } catch (SQLException e) {
            showError(manequimStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteManequim() {
        if (selectedManequim == null) {
            showError(manequimStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedManequim.getId())) return;
        try {
            ManequimDAO.excluir(selectedManequim.getId());
            clearManequim();
            refreshManequim();
            manequimStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(manequimStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearManequim() {
        clearManequim();
        manequimStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshManequim() {
        refreshManequim();
    }

    private void clearManequim() {
        manequimTable.getSelectionModel().clearSelection();
        selectedManequim = null;
        manequimMaterial.clear();
        manequimCor.clear();
        manequimRoupa.clear();
        manequimSaveButton.setText("Cadastrar registro");
    }

    private void refreshManequim() {
        try {
            manequimRecords.setAll(ManequimDAO.listar());
            manequimStatus.setText(manequimRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            manequimRecords.clear();
            manequimStatus.setText("Banco indisponível: " + e.getMessage());
        }
    }

    private void setupLojaTable() {
        lojaTable.setItems(lojaRecords);
        lojaTable.getColumns().clear();
        TableColumn<Loja, String> lojaIdColumn = new TableColumn<>("ID");
        lojaIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        lojaTable.getColumns().add(lojaIdColumn);
        TableColumn<Loja, String> lojaNomeColumn = new TableColumn<>("Nome");
        lojaNomeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getNome())));
        lojaTable.getColumns().add(lojaNomeColumn);
        TableColumn<Loja, String> lojaEnderecoColumn = new TableColumn<>("Endereço");
        lojaEnderecoColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getEndereco())));
        lojaTable.getColumns().add(lojaEnderecoColumn);
        TableColumn<Loja, String> lojaQuantidadeFuncionariosColumn = new TableColumn<>("Quantidade de funcionários");
        lojaQuantidadeFuncionariosColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getQuantidadeFuncionarios())));
        lojaTable.getColumns().add(lojaQuantidadeFuncionariosColumn);
        lojaTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            selectedLoja = selected;
            if (selected == null) {
                lojaSaveButton.setText("Cadastrar registro");
                return;
            }
            lojaNome.setText(String.valueOf(selected.getNome()));
            lojaEndereco.setText(String.valueOf(selected.getEndereco()));
            lojaQuantidadeFuncionarios.setText(String.valueOf(selected.getQuantidadeFuncionarios()));
            lojaSaveButton.setText("Atualizar registro");
            lojaStatus.setText("Registro " + selected.getId() + " selecionado.");
        });
        refreshLoja();
    }

    @FXML
    private void onSaveLoja() {
        try {
            String message;
            if (selectedLoja == null) {
                LojaDAO.inserir(required(lojaNome), required(lojaEndereco), integer(lojaQuantidadeFuncionarios));
                message = "Registro cadastrado.";
            } else {
                message = LojaDAO.atualizar(selectedLoja.getId(), required(lojaNome), required(lojaEndereco), integer(lojaQuantidadeFuncionarios))
                        ? "Registro atualizado."
                        : "Registro não encontrado para atualização.";
            }
            clearLoja();
            refreshLoja();
            lojaStatus.setText(message);
        } catch (IllegalArgumentException e) {
            showError(lojaStatus, e.getMessage());
        } catch (SQLException e) {
            showError(lojaStatus, "Falha ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteLoja() {
        if (selectedLoja == null) {
            showError(lojaStatus, "Selecione um registro na tabela para excluir.");
            return;
        }
        if (!confirmDelete(selectedLoja.getId())) return;
        try {
            LojaDAO.excluir(selectedLoja.getId());
            clearLoja();
            refreshLoja();
            lojaStatus.setText("Registro excluído.");
        } catch (SQLException e) {
            showError(lojaStatus, "Falha ao excluir no banco: " + e.getMessage());
        }
    }

    @FXML
    private void onClearLoja() {
        clearLoja();
        lojaStatus.setText("Campos limpos.");
    }

    @FXML
    private void onRefreshLoja() {
        refreshLoja();
    }

    private void clearLoja() {
        lojaTable.getSelectionModel().clearSelection();
        selectedLoja = null;
        lojaNome.clear();
        lojaEndereco.clear();
        lojaQuantidadeFuncionarios.clear();
        lojaSaveButton.setText("Cadastrar registro");
    }

    private void refreshLoja() {
        try {
            lojaRecords.setAll(LojaDAO.listar());
            lojaStatus.setText(lojaRecords.size() + " registro(s) carregado(s).");
        } catch (SQLException | IllegalArgumentException e) {
            lojaRecords.clear();
            lojaStatus.setText("Banco indisponível: " + e.getMessage());
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
