/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testprojekt;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.ServiceRegistry;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


//BAZA danych zrobiona na MYSQL
//na Xampie
//plik bazy danych dostepny w folderze projektu: baza_fryzjer.sql

public class Testprojekt extends Application {
    static Configuration configuration = new Configuration().configure("cfg\\hibernateMySQL.cfg.xml");
    static ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

    static final TableView<Usluga> tablica_uslug = new TableView();
    static final TableView<Klient> tablica_klientow = new TableView();
    static final TableView<Fryzjer> tablica_fryzjerow = new TableView();
    static final TableView<Oddzial> tablica_oddzialow = new TableView();

    static ObservableList<Usluga> dane_uslug = uslugi();
    static ObservableList<Klient> dane_klientow = klienci();
    static ObservableList<Fryzjer> dane_fryzjerow = fryzjerzy();
    static ObservableList<Oddzial> dane_oddzialow = oddzialy();


    @Override
    public void start(Stage primaryStage) {
        HBox hbox = new HBox();
        TabPane tabPane = new TabPane();
        tabPane.setMinWidth(1200);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab tab1 = new Tab("Usługa", tworzTabeleUslug());
        Tab tab2 = new Tab("Klienci", tworzTabeleKlientow());
        Tab tab3 = new Tab("Fryzjerzy", tworzTabeleFryzjerow());
        Tab tab4 = new Tab("Oddziały", tworzTabeleOddzialow());

        tab1.getStyleClass().add("tab");

        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
        hbox.getChildren().add(tabPane);

        Scene scene = new Scene(hbox, 1200, 650);
        primaryStage.setResizable(false);
        scene.getStylesheets().add("style.css");

        primaryStage.setTitle("Projekt JavaFX + Hibernate");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }

    static ObservableList uslugi() {
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();

        Criteria c = session.createCriteria(Usluga.class);
        ArrayList<Usluga> uslugi = (ArrayList<Usluga>) c.list();
        ObservableList<Usluga> dane_uslug = FXCollections.observableArrayList(uslugi);
        session.close();
        factory.close();
        return dane_uslug;
    }

    static ObservableList fryzjerzy() {
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();

        Criteria c2 = session.createCriteria(Fryzjer.class);
        ArrayList<Fryzjer> fryzjerzy = (ArrayList<Fryzjer>) c2.list();
        ObservableList<Fryzjer> dane_fryzjerow = FXCollections.observableArrayList(fryzjerzy);
        session.close();
        factory.close();

        return dane_fryzjerow;
    }

    static ObservableList klienci() {
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();

        Criteria c3 = session.createCriteria(Klient.class);
        ArrayList<Klient> klienci = (ArrayList<Klient>) c3.list();
        ObservableList<Klient> dane_klientow = FXCollections.observableArrayList(klienci);
        session.close();
        factory.close();

        return dane_klientow;
    }

    static ObservableList oddzialy() {
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();

        Criteria c3 = session.createCriteria(Oddzial.class);
        ArrayList<Oddzial> oddzialy = (ArrayList<Oddzial>) c3.list();
        ObservableList<Oddzial> dane_oddzialow = FXCollections.observableArrayList(oddzialy);
        session.close();
        factory.close();

        return dane_oddzialow;
    }

    //TABELA USLUGI
    static VBox tworzTabeleUslug() {

        Button dodaj = new Button("Wstaw nowy rekord");
        dodaj.getStyleClass().add("button_dodaj");
        Button szukaj = new Button("Szukaj...");
        szukaj.getStyleClass().add("button_dodaj");
        TextField szukajka = new TextField();
        Button wyczysc = new Button("Wyczyść kryteria wyszukiwania");
        wyczysc.getStyleClass().add("button_dodaj");
        ObservableList<Usluga> uslugi_szukane = FXCollections.observableArrayList();

        tablica_uslug.setMaxHeight(490);

        TableColumn<Usluga, Integer> LpCol = new TableColumn<>("Lp.");
        LpCol.setCellValueFactory(data -> {
            Usluga item = data.getValue();
            int index = tablica_uslug.getItems().indexOf(item) + 1;
            return Bindings.createObjectBinding(() -> index);
        });

        TableColumn<Usluga, Double> cenaCol = new TableColumn<>("Cena");
        cenaCol.setCellValueFactory(new PropertyValueFactory<>("cena"));

        TableColumn<Usluga, String> nazwaUsCol = new TableColumn<>("Rodzaj usługi");
        nazwaUsCol.setCellValueFactory(new PropertyValueFactory<>("nazwa"));

        TableColumn<Usluga, LocalDate> dataUslugiCol = new TableColumn<>("Data usługi");
        dataUslugiCol.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<LocalDate> call(TableColumn.CellDataFeatures<Usluga, LocalDate> param) {
                return Bindings.createObjectBinding(() -> param.getValue().getData_uslugi().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
        });

        TableColumn<Usluga, Time> godzinaUsCol = new TableColumn<>("Godzina usługi");
        godzinaUsCol.setCellValueFactory(new PropertyValueFactory<>("czas"));

        TableColumn<Usluga, String> imieKlientaCol = new TableColumn<>("Imię klienta");
        imieKlientaCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usluga, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usluga, String> param) {
                String imie = param.getValue().getKlient().getImie();
                return Bindings.createStringBinding(() -> imie);
            }
        });

        TableColumn<Usluga, String> nazwiskoKlientaCol = new TableColumn<>("Nazwisko klienta");
        nazwiskoKlientaCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usluga, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usluga, String> param) {
                String nazwisko = param.getValue().getKlient().getNazwisko();
                return Bindings.createStringBinding(() -> nazwisko);
            }
        });

        TableColumn<Usluga, String> imieFryzjeraCol = new TableColumn<>("Imie fryzjera");
        imieFryzjeraCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usluga, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usluga, String> param) {
                String imie = param.getValue().getFryzjer().getImie();
                return Bindings.createStringBinding(() -> imie);
            }
        });

        TableColumn<Usluga, String> nazwiskoFryzjeraCol = new TableColumn<>("Nazwisko fryzjera");
        nazwiskoFryzjeraCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usluga, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usluga, String> param) {
                String nazwisko = param.getValue().getFryzjer().getNazwisko();
                return Bindings.createStringBinding(() -> nazwisko);
            }
        });

        TableColumn<Usluga, Void> editBtn = new TableColumn("Edytuj");
        Callback<TableColumn<Usluga, Void>, TableCell<Usluga, Void>> cellFactory2 = new Callback<TableColumn<Usluga, Void>, TableCell<Usluga, Void>>() {
            @Override
            public TableCell<Usluga, Void> call(final TableColumn<Usluga, Void> param) {
                final TableCell<Usluga, Void> cell = new TableCell<Usluga, Void>() {
                    Image image = new Image(getClass().getResourceAsStream("resources/images/edit.png"), 22, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.getStyleClass().add("btn_edit");
                        btn.setOnAction((ActionEvent event) -> {
                            GridPane gridpane = new GridPane();
                            gridpane.setPadding(new Insets(5));
                            gridpane.setHgap(5);
                            gridpane.setVgap(5);

                            ColumnConstraints column1 = new ColumnConstraints(150);
                            ColumnConstraints column2 = new ColumnConstraints(150, 150, 150);
                            column2.setHgrow(Priority.ALWAYS);
                            gridpane.getColumnConstraints().addAll(column1, column2);

                            Label lcena = new Label("Cena: ");
                            Label lusluga = new Label("Nazwa usługi: ");
                            Label ldata = new Label("Data usługi: ");
                            Label lczas = new Label("Godzina uslugi: ");
                            Label lklient = new Label("Klient: ");
                            Label fryzjer = new Label("Fryzjer: ");

                            TextField cena = new TextField();
                            TextField usluga = new TextField();
                            DatePicker data_uslugi = new DatePicker();
                            TextField godzina_uslugi = new TextField();

                            Button btn = new Button("Wyślij");


                            ComboBox klient = new ComboBox(dane_klientow);
                            klient.setItems(dane_klientow);
                            klient.setConverter(new StringConverter<Klient>() {
                                @Override
                                public String toString(Klient klient) {
                                    if (klient != null)
                                        return klient.getImie() + " " + klient.getNazwisko();
                                    return "";
                                }

                                @Override
                                public Klient fromString(String string) {
                                    return null;
                                }
                            });

                            ComboBox fryzjer_combo = new ComboBox(dane_fryzjerow);
                            fryzjer_combo.setItems(dane_fryzjerow);
                            fryzjer_combo.setConverter(new StringConverter<Fryzjer>() {
                                @Override
                                public String toString(Fryzjer fryzjer) {
                                    if (fryzjer != null)
                                        return fryzjer.getImie() + " " + fryzjer.getNazwisko();
                                    return "";
                                }

                                @Override
                                public Fryzjer fromString(String string) {
                                    return null;
                                }
                            });

                            Double cena2 = getTableView().getItems().get(getIndex()).getCena();
                            String cenaa = cena2.toString();
                            cena.setText(cenaa);
                            usluga.setText(getTableView().getItems().get(getIndex()).getNazwa());
                            data_uslugi.setValue(getTableView().getItems().get(getIndex()).getData_uslugi().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            godzina_uslugi.setText(getTableView().getItems().get(getIndex()).getCzas().toString());
                            klient.setValue(getTableView().getItems().get(getIndex()).getKlient());
                            fryzjer_combo.setValue(getTableView().getItems().get(getIndex()).getFryzjer());

                            GridPane.setHalignment(lusluga, HPos.RIGHT);
                            gridpane.add(lusluga, 0, 0);
                            GridPane.setHalignment(usluga, HPos.LEFT);
                            gridpane.add(usluga, 1, 0);
                            GridPane.setHalignment(lcena, HPos.RIGHT);
                            gridpane.add(lcena, 0, 1);
                            GridPane.setHalignment(cena, HPos.LEFT);
                            gridpane.add(cena, 1, 1);
                            GridPane.setHalignment(ldata, HPos.RIGHT);
                            gridpane.add(ldata, 0, 2);
                            GridPane.setHalignment(data_uslugi, HPos.LEFT);
                            gridpane.add(data_uslugi, 1, 2);
                            GridPane.setHalignment(lczas, HPos.RIGHT);
                            gridpane.add(lczas, 0, 3);
                            GridPane.setHalignment(godzina_uslugi, HPos.LEFT);
                            gridpane.add(godzina_uslugi, 1, 3);
                            GridPane.setHalignment(lklient, HPos.RIGHT);
                            gridpane.add(lklient, 0, 4);
                            GridPane.setHalignment(klient, HPos.LEFT);
                            gridpane.add(klient, 1, 4);
                            GridPane.setHalignment(fryzjer, HPos.RIGHT);
                            gridpane.add(fryzjer, 0, 5);
                            GridPane.setHalignment(fryzjer_combo, HPos.LEFT);
                            gridpane.add(fryzjer_combo, 1, 5);
                            GridPane.setHalignment(btn, HPos.RIGHT);
                            gridpane.add(btn, 1, 6);
                            btn.setMinWidth(150);

                            VBox vbox = new VBox();
                            vbox.getChildren().add(gridpane);
                            Scene scene2 = new Scene(vbox, 400, 300);
                            Stage stage2 = new Stage();
                            scene2.getStylesheets().add("style.css");
                            vbox.getStyleClass().add("vbox_formularz");
                            stage2.setTitle("Edytuj dane");
                            stage2.setScene(scene2);
                            stage2.setResizable(false);
                            stage2.show();

                            btn.setOnAction(event1 -> {
                                try {
                                    String usluga_new = usluga.getText();
                                    double cena_new = Double.parseDouble(cena.getText());
                                    String godzina_new = godzina_uslugi.getText();
                                    LocalTime time = LocalTime.parse(godzina_new);
                                    Time time_final = Time.valueOf(time);
                                    LocalDate date = data_uslugi.getValue();
                                    Date data_final = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                                    Klient klient_new = (Klient) klient.getValue();
                                    Fryzjer fryzjer_new = (Fryzjer) fryzjer_combo.getValue();

                                    Usluga usluga_zas = new Usluga(cena_new, usluga_new, data_final, time_final);
                                    usluga_zas.setFryzjer(fryzjer_new);
                                    usluga_zas.setKlient(klient_new);
                                    int id = dane_uslug.get(getIndex()).getId_uslugi();

                                    usluga_zas.setId_uslugi(id);
                                    SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                                    Session session = factory.openSession();
                                    Transaction t = session.beginTransaction();
                                    session.update(usluga_zas);
                                    dane_uslug.set(getIndex(), usluga_zas);
                                    t.commit();
                                    session.close();
                                    factory.close();

                                } catch (NumberFormatException e) {
                                    new Alert(Alert.AlertType.ERROR, "Nieprawidłowa cena!").show();
                                }
                            });
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };
        editBtn.setCellFactory(cellFactory2);


        TableColumn<Usluga, Void> colDropBtn = new TableColumn("Usuń");
        Callback<TableColumn<Usluga, Void>, TableCell<Usluga, Void>> cellFactory = new Callback<TableColumn<Usluga, Void>, TableCell<Usluga, Void>>() {
            @Override
            public TableCell<Usluga, Void> call(final TableColumn<Usluga, Void> param) {
                final TableCell<Usluga, Void> cell = new TableCell<Usluga, Void>() {
                    Image image = new Image(getClass().getResourceAsStream("resources/images/trash2.png"), 22, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.getStyleClass().add("btn_remove");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                                Session session = factory.openSession();
                                Transaction t = session.beginTransaction();

                                int id = dane_uslug.get(getIndex()).getId_uslugi();
                                Query q = session.createQuery("delete from Usluga where id_uslugi=:id");
                                q.setParameter("id", id);
                                q.executeUpdate();
                                dane_uslug.remove(getIndex());
                                t.commit();
                                session.close();
                                factory.close();
                            } catch (ConstraintViolationException e) {
                                new Alert(Alert.AlertType.ERROR, "Nie można wykonać tej operacji!").show();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };
        colDropBtn.setCellFactory(cellFactory);

        // wyglad apki
        LpCol.setMinWidth(50);
        LpCol.setMaxWidth(50);
        editBtn.setMinWidth(75);
        editBtn.setMaxWidth(75);
        colDropBtn.setMinWidth(75);
        colDropBtn.setMaxWidth(75);
        tablica_uslug.getColumns().addAll(new TableColumn[]{LpCol, cenaCol, nazwaUsCol, dataUslugiCol, godzinaUsCol, imieKlientaCol, nazwiskoKlientaCol, imieFryzjeraCol, nazwiskoFryzjeraCol, editBtn, colDropBtn});
        tablica_uslug.setItems(dane_uslug);
        tablica_uslug.setColumnResizePolicy(tablica_uslug.CONSTRAINED_RESIZE_POLICY);

        //przycisk dodawania rekordu
        dodaj.setOnAction(event -> {
            GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(5);
            gridpane.setVgap(5);

            ColumnConstraints column1 = new ColumnConstraints(150);
            ColumnConstraints column2 = new ColumnConstraints(150, 150, 150);
            column2.setHgrow(Priority.ALWAYS);
            gridpane.getColumnConstraints().addAll(column1, column2);

            Label lcena = new Label("Cena: ");
            Label lusluga = new Label("Nazwa usługi: ");
            Label ldata = new Label("Data usługi: ");
            Label lczas = new Label("Godzina uslugi: ");
            Label lklient = new Label("Klient: ");
            Label fryzjer = new Label("Fryzjer: ");

            TextField cena = new TextField();
            TextField usluga = new TextField();
            DatePicker data_uslugi = new DatePicker();
            TextField godzina_uslugi = new TextField();

            Button btn = new Button("Wyślij");

            ComboBox klient = new ComboBox(dane_klientow);
            klient.setItems(dane_klientow);
            klient.setConverter(new StringConverter<Klient>() {
                @Override
                public String toString(Klient klient) {
                    if (klient != null)
                        return klient.getImie() + " " + klient.getNazwisko();
                    return "";
                }

                @Override
                public Klient fromString(String string) {
                    return null;
                }
            });

            ComboBox fryzjer_combo = new ComboBox(dane_fryzjerow);
            fryzjer_combo.setItems(dane_fryzjerow);
            fryzjer_combo.setConverter(new StringConverter<Fryzjer>() {
                @Override
                public String toString(Fryzjer fryzjer) {
                    if (fryzjer != null)
                        return fryzjer.getImie() + " " + fryzjer.getNazwisko();
                    return "";
                }

                @Override
                public Fryzjer fromString(String string) {
                    return null;
                }
            });

            GridPane.setHalignment(lusluga, HPos.RIGHT);
            gridpane.add(lusluga, 0, 0);
            GridPane.setHalignment(usluga, HPos.LEFT);
            gridpane.add(usluga, 1, 0);
            GridPane.setHalignment(lcena, HPos.RIGHT);
            gridpane.add(lcena, 0, 1);
            GridPane.setHalignment(cena, HPos.LEFT);
            gridpane.add(cena, 1, 1);
            GridPane.setHalignment(ldata, HPos.RIGHT);
            gridpane.add(ldata, 0, 2);
            GridPane.setHalignment(data_uslugi, HPos.LEFT);
            gridpane.add(data_uslugi, 1, 2);
            GridPane.setHalignment(lczas, HPos.RIGHT);
            gridpane.add(lczas, 0, 3);
            GridPane.setHalignment(godzina_uslugi, HPos.LEFT);
            gridpane.add(godzina_uslugi, 1, 3);
            GridPane.setHalignment(lklient, HPos.RIGHT);
            gridpane.add(lklient, 0, 4);
            GridPane.setHalignment(klient, HPos.LEFT);
            gridpane.add(klient, 1, 4);
            GridPane.setHalignment(fryzjer, HPos.RIGHT);
            gridpane.add(fryzjer, 0, 5);
            GridPane.setHalignment(fryzjer_combo, HPos.LEFT);
            gridpane.add(fryzjer_combo, 1, 5);
            GridPane.setHalignment(btn, HPos.RIGHT);
            gridpane.add(btn, 1, 6);
            btn.setMinWidth(150);

            VBox vbox = new VBox();
            vbox.getChildren().add(gridpane);
            Scene scene2 = new Scene(vbox, 400, 300);
            Stage stage2 = new Stage();
            scene2.getStylesheets().add("style.css");
            vbox.getStyleClass().add("vbox_formularz");
            stage2.setTitle("Edytuj dane");
            stage2.setScene(scene2);
            stage2.setResizable(false);
            stage2.show();

            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        String usluga_new = usluga.getText();
                        double cena_new = Double.parseDouble(cena.getText());
                        String godzina_new = godzina_uslugi.getText();
                        LocalTime time = LocalTime.parse(godzina_new);
                        Time time_final = Time.valueOf(time);
                        LocalDate date = data_uslugi.getValue();
                        Date data_final = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                        Klient klient_new = (Klient) klient.getValue();
                        Fryzjer fryzjer_new = (Fryzjer) fryzjer_combo.getValue();

                        Usluga usluga = new Usluga(cena_new, usluga_new, data_final, time_final);
                        usluga.setKlient(klient_new);
                        usluga.setFryzjer(fryzjer_new);
                        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                        Session session = factory.openSession();
                        Transaction transaction = session.beginTransaction();
                        session.save(usluga);
                        transaction.commit();
                        session.close();
                        factory.close();

                        dane_uslug.add(usluga);
                        tablica_uslug.setItems(dane_uslug);
                        tablica_uslug.refresh();
                    } catch (ConstraintViolationException e) {
                        new Alert(Alert.AlertType.ERROR, "Nie wypełniłeś wszystkich pól!").show();
                    } catch (NumberFormatException e) {
                        new Alert(Alert.AlertType.ERROR, "Cena nie jest wyrażona liczbą").show();
                    } catch (DateTimeParseException e) {
                        new Alert(Alert.AlertType.ERROR, "Niepoprawna godzina").show();
                    }
                }
            });
        });
        //przycisk wyszukiwania
        szukaj.setOnAction(event -> {
            String szukane = szukajka.getText();
            ObservableList<Usluga> szukani = FXCollections.observableArrayList();
            String[] slowa = szukane.split(" ");
            boolean n = false;
            for (int i = 0; i < tablica_uslug.getItems().size(); i++) {
                for (int j = 0; j < slowa.length; j++) {
                    if(tablica_uslug.getItems().get(i).toString().toLowerCase(Locale.ROOT).contains(slowa[j].toLowerCase(Locale.ROOT))) {
                        n = true;
                    } else {
                        n=false;
                        break;
                    }
                }

                if(n==true) {
                    szukani.add(tablica_uslug.getItems().get(i));
                    n=false;
                }
            }
            dane_uslug = szukani;
            tablica_uslug.setItems(dane_uslug);
            slowa = null;
        });

        //przywracanie podstawowych rekordów
        wyczysc.setOnAction(event -> {
            dane_uslug = uslugi();
            tablica_uslug.setItems(dane_uslug);
            tablica_uslug.refresh();
            szukajka.setText("");
        });

        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(dodaj, szukaj, szukajka,wyczysc);
        hbox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(tablica_uslug, hbox);
        return vbox;
    }

    //TABELA Klienci
    static VBox tworzTabeleKlientow() {
        tablica_klientow.setMaxHeight(490);

        Button dodaj = new Button("Wstaw nowy rekord");
        dodaj.getStyleClass().add("button_dodaj");
        Button szukaj = new Button("Szukaj...");
        szukaj.getStyleClass().add("button_dodaj");
        TextField szukajka = new TextField();
        Button wyczysc = new Button("Wyczyść kryteria wyszukiwania");
        wyczysc.getStyleClass().add("button_dodaj");
        ObservableList<Klient> klienci_szukani = FXCollections.observableArrayList();

        TableColumn<Klient, Integer> LpCol = new TableColumn<>("Lp.");
        LpCol.setCellValueFactory(data -> {
            Klient item = data.getValue();
            int index = tablica_klientow.getItems().indexOf(item) + 1;
            return Bindings.createObjectBinding(() -> index);
        });

        TableColumn<Klient, String> imieCol = new TableColumn<>("Imię");
        imieCol.setCellValueFactory(new PropertyValueFactory<>("imie"));

        TableColumn<Klient, String> nazwiskoCol = new TableColumn<>("Nazwisko");
        nazwiskoCol.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));

        TableColumn<Klient, String> telefonCol = new TableColumn<>("Telefon");
        telefonCol.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        TableColumn<Klient, Character> plecCol = new TableColumn<>("Płeć");
        plecCol.setCellValueFactory(new PropertyValueFactory<>("plec"));

        TableColumn<Klient, Void> colDropBtn = new TableColumn("Usuń");
        Callback<TableColumn<Klient, Void>, TableCell<Klient, Void>> cellFactory = new Callback<TableColumn<Klient, Void>, TableCell<Klient, Void>>() {
            @Override
            public TableCell<Klient, Void> call(final TableColumn<Klient, Void> param) {
                final TableCell<Klient, Void> cell = new TableCell<Klient, Void>() {

                    Image image = new Image(getClass().getResourceAsStream("resources/images/trash2.png"), 22, 32, false, false);
                    private final Button btn = new Button();


                    {
                        btn.setGraphic(new ImageView(image));
                        btn.getStyleClass().add("btn_remove");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                                Session session = factory.openSession();
                                Transaction t = session.beginTransaction();

                                int id = dane_klientow.get(getIndex()).getId_klienta();
                                Query q = session.createQuery("delete from Klient where id_klienta=:id");
                                q.setParameter("id", id);
                                q.executeUpdate();
                                dane_klientow.remove(getIndex());
                                t.commit();
                                session.close();
                                factory.close();
                            } catch (ConstraintViolationException e) {
                                new Alert(Alert.AlertType.ERROR, "Nie można wykonać tej operacji!").show();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };
        colDropBtn.setCellFactory(cellFactory);

        TableColumn<Klient, Void> editBtn = new TableColumn("Edytuj");
        Callback<TableColumn<Klient, Void>, TableCell<Klient, Void>> cellFactory2 = new Callback<TableColumn<Klient, Void>, TableCell<Klient, Void>>() {
            @Override
            public TableCell<Klient, Void> call(final TableColumn<Klient, Void> param) {
                final TableCell<Klient, Void> cell = new TableCell<Klient, Void>() {
                    Image image = new Image(getClass().getResourceAsStream("resources/images/edit.png"), 22, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.getStyleClass().add("btn_edit");
                        btn.setOnAction((ActionEvent event) -> {
                            GridPane gridpane = new GridPane();
                            gridpane.setPadding(new Insets(5));
                            gridpane.setHgap(5);
                            gridpane.setVgap(5);

                            ColumnConstraints column1 = new ColumnConstraints(150);
                            ColumnConstraints column2 = new ColumnConstraints(150, 150, 150);
                            column2.setHgrow(Priority.ALWAYS);
                            gridpane.getColumnConstraints().addAll(column1, column2);

                            Label limie = new Label("Imię: ");
                            Label lnazwisko = new Label("Nazwisko: ");
                            Label ltelefon = new Label("Telefon: ");
                            Label lplec = new Label("Płeć: ");

                            TextField imie = new TextField();
                            TextField nazwisko = new TextField();
                            TextField telefon = new TextField();

                            ObservableList<String> plci = FXCollections.observableArrayList(
                                    "mężczyzna",
                                    "kobieta"
                            );

                            ComboBox plec = new ComboBox(plci);
                            plec.setItems(plci);

                            Button btn = new Button("Wyślij");

                            imie.setText(getTableView().getItems().get(getIndex()).getImie());
                            nazwisko.setText(getTableView().getItems().get(getIndex()).getNazwisko());
                            telefon.setText(getTableView().getItems().get(getIndex()).getTelefon());
                            plec.setValue(getTableView().getItems().get(getIndex()).getPlec());

                            GridPane.setHalignment(limie, HPos.RIGHT);
                            gridpane.add(limie, 0, 0);
                            GridPane.setHalignment(imie, HPos.LEFT);
                            gridpane.add(imie, 1, 0);
                            GridPane.setHalignment(lnazwisko, HPos.RIGHT);
                            gridpane.add(lnazwisko, 0, 1);
                            GridPane.setHalignment(nazwisko, HPos.LEFT);
                            gridpane.add(nazwisko, 1, 1);
                            GridPane.setHalignment(ltelefon, HPos.RIGHT);
                            gridpane.add(ltelefon, 0, 2);
                            GridPane.setHalignment(telefon, HPos.LEFT);
                            gridpane.add(telefon, 1, 2);
                            GridPane.setHalignment(lplec, HPos.RIGHT);
                            gridpane.add(lplec, 0, 3);
                            GridPane.setHalignment(plec, HPos.LEFT);
                            gridpane.add(plec, 1, 3);
                            GridPane.setHalignment(btn, HPos.RIGHT);
                            gridpane.add(btn, 1, 4);
                            btn.setMinWidth(150);


                            VBox vbox = new VBox();
                            vbox.getChildren().add(gridpane);
                            Scene scene2 = new Scene(vbox, 400, 300);
                            Stage stage2 = new Stage();
                            scene2.getStylesheets().add("style.css");
                            vbox.getStyleClass().add("vbox_formularz");
                            stage2.setTitle("Wprowadź dane");
                            stage2.setScene(scene2);
                            stage2.setResizable(false);
                            stage2.show();

                            btn.setOnAction(event1 -> {
                                    String imie_new = imie.getText();
                                    String nazwisko_new = nazwisko.getText();
                                    String telefon_new = telefon.getText();
                                    Character plec_new;
                                    if (plec.getValue() == "kobieta")
                                        plec_new = 'K';
                                    else
                                        plec_new = 'M';

                                    Klient klient = new Klient(imie_new, nazwisko_new, telefon_new, plec_new);
                                    int id = dane_klientow.get(getIndex()).getId_klienta();

                                    klient.setId_klienta(id);
                                    SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                                    Session session = factory.openSession();
                                    Transaction t = session.beginTransaction();

                                    session.update(klient);
                                    dane_klientow.set(getIndex(), klient);

                                    t.commit();

                                    session.close();
                                    factory.close();

                                    dane_uslug = uslugi();
                                    tablica_uslug.setItems(dane_uslug);
                                    tablica_uslug.refresh();
                            });
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };
        editBtn.setCellFactory(cellFactory2);

        //wyglad apki
        LpCol.setMinWidth(50);
        LpCol.setMaxWidth(50);
        editBtn.setMinWidth(75);
        editBtn.setMaxWidth(75);
        colDropBtn.setMinWidth(75);
        colDropBtn.setMaxWidth(75);
        tablica_klientow.getColumns().addAll(new TableColumn[]{LpCol, imieCol, nazwiskoCol, telefonCol, plecCol, editBtn, colDropBtn});
        tablica_klientow.setItems(dane_klientow);
        tablica_klientow.setColumnResizePolicy(tablica_klientow.CONSTRAINED_RESIZE_POLICY);

        //przycisk dodawania rekordu
        dodaj.setOnAction(event -> {
            GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(5);
            gridpane.setVgap(5);

            ColumnConstraints column1 = new ColumnConstraints(150);
            ColumnConstraints column2 = new ColumnConstraints(150, 150, 150);
            column2.setHgrow(Priority.ALWAYS);
            gridpane.getColumnConstraints().addAll(column1, column2);

            Label limie = new Label("Imię: ");
            Label lnazwisko = new Label("Nazwisko: ");
            Label ltelefon = new Label("Telefon: ");
            Label lplec = new Label("Płeć: ");

            TextField imie = new TextField();
            TextField nazwisko = new TextField();
            TextField telefon = new TextField();

            ObservableList<String> plci = FXCollections.observableArrayList(
                    "mężczyzna",
                    "kobieta"
            );

            ComboBox plec = new ComboBox(plci);
            plec.setItems(plci);

            Button btn = new Button("Wyślij");

            GridPane.setHalignment(limie, HPos.RIGHT);
            gridpane.add(limie, 0, 0);
            GridPane.setHalignment(imie, HPos.LEFT);
            gridpane.add(imie, 1, 0);
            GridPane.setHalignment(lnazwisko, HPos.RIGHT);
            gridpane.add(lnazwisko, 0, 1);
            GridPane.setHalignment(nazwisko, HPos.LEFT);
            gridpane.add(nazwisko, 1, 1);
            GridPane.setHalignment(ltelefon, HPos.RIGHT);
            gridpane.add(ltelefon, 0, 2);
            GridPane.setHalignment(telefon, HPos.LEFT);
            gridpane.add(telefon, 1, 2);
            GridPane.setHalignment(lplec, HPos.RIGHT);
            gridpane.add(lplec, 0, 3);
            GridPane.setHalignment(plec, HPos.LEFT);
            gridpane.add(plec, 1, 3);
            GridPane.setHalignment(btn, HPos.RIGHT);
            gridpane.add(btn, 1, 4);
            btn.setMinWidth(150);


            VBox vbox = new VBox();
            vbox.getChildren().add(gridpane);
            Scene scene2 = new Scene(vbox, 400, 300);
            Stage stage2 = new Stage();
            scene2.getStylesheets().add("style.css");
            vbox.getStyleClass().add("vbox_formularz");
            stage2.setTitle("Edytuj dane");
            stage2.setScene(scene2);
            stage2.setResizable(false);
            stage2.show();

            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        String imie_new = imie.getText();
                        String nazwisko_new = nazwisko.getText();
                        String telefon_new = telefon.getText();
                        Character plec_new;
                        if (plec.getValue() == "kobieta")
                            plec_new = 'K';
                        else
                            plec_new = 'M';

                        Klient klient = new Klient(imie_new, nazwisko_new, telefon_new, plec_new);

                        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                        Session session = factory.openSession();
                        Transaction transaction = session.beginTransaction();
                        session.save(klient);
                        transaction.commit();
                        session.close();
                        factory.close();

                        dane_klientow.add(klient);
                        tablica_klientow.setItems(dane_klientow);
                        tablica_klientow.refresh();

                        dane_uslug = uslugi();
                        tablica_uslug.setItems(dane_uslug);
                        tablica_uslug.refresh();
                    } catch (ConstraintViolationException e) {
                        new Alert(Alert.AlertType.ERROR, "Nie wypełniłeś wszystkich pól!").show();
                    }
                }
            });
        });

        //przycisk wyszukiwania
        szukaj.setOnAction(event -> {
            String szukane = szukajka.getText();
            ObservableList<Klient> szukani = FXCollections.observableArrayList();
            String[] slowa = szukane.split(" ");
            boolean n = false;
            for (int i = 0; i < tablica_klientow.getItems().size(); i++) {
                for (int j = 0; j < slowa.length; j++) {
                    if(tablica_klientow.getItems().get(i).toString().toLowerCase(Locale.ROOT).contains(slowa[j].toLowerCase(Locale.ROOT))) {
                        n = true;
                    } else {
                        n=false;
                        break;
                    }
                }

                if(n==true) {
                    szukani.add(tablica_klientow.getItems().get(i));
                    n=false;
                }
            }
            dane_klientow = szukani;
            tablica_klientow.setItems(dane_klientow);
            slowa = null;
        });

        //przywracanie podstawowych rekordów
        wyczysc.setOnAction(event -> {
            dane_klientow = klienci();
            tablica_klientow.setItems(dane_klientow);
            tablica_klientow.refresh();
            szukajka.setText("");
        });

        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(dodaj, szukaj, szukajka,wyczysc);
        hbox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(tablica_klientow, hbox);

        return vbox;
    }

    //TABELA Fryzjerzy
    static VBox tworzTabeleFryzjerow() {
        Button dodaj = new Button("Wstaw nowy rekord");
        dodaj.getStyleClass().add("button_dodaj");
        Button szukaj = new Button("Szukaj...");
        szukaj.getStyleClass().add("button_dodaj");
        TextField szukajka = new TextField();
        Button wyczysc = new Button("Wyczyść kryteria wyszukiwania");
        wyczysc.getStyleClass().add("button_dodaj");
        ObservableList<Fryzjer> fryzjerzy_szukani = FXCollections.observableArrayList();


        tablica_fryzjerow.setMaxHeight(490);

        TableColumn<Fryzjer, Integer> LpCol = new TableColumn<>("Lp.");
        LpCol.setCellValueFactory(data -> {
            Fryzjer item = data.getValue();
            int index = tablica_fryzjerow.getItems().indexOf(item) + 1;
            return Bindings.createObjectBinding(() -> index);
        });

        TableColumn<Fryzjer, String> imieCol = new TableColumn<>("Imię");
        imieCol.setCellValueFactory(new PropertyValueFactory<>("imie"));

        TableColumn<Fryzjer, String> nazwiskoCol = new TableColumn<>("Nazwisko");
        nazwiskoCol.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));

        TableColumn<Fryzjer, String> telefonCol = new TableColumn<>("Telefon");
        telefonCol.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        TableColumn<Fryzjer, String> oddzialCol = new TableColumn<>("Oddział");
        oddzialCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Fryzjer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Fryzjer, String> param) {
                return Bindings.createObjectBinding(() -> param.getValue().getOddzial().getNazwa());
            }
        });

        TableColumn<Fryzjer, Void> colDropBtn = new TableColumn("Usuń");
        Callback<TableColumn<Fryzjer, Void>, TableCell<Fryzjer, Void>> cellFactory = new Callback<TableColumn<Fryzjer, Void>, TableCell<Fryzjer, Void>>() {
            @Override
            public TableCell<Fryzjer, Void> call(final TableColumn<Fryzjer, Void> param) {
                final TableCell<Fryzjer, Void> cell = new TableCell<Fryzjer, Void>() {

                    Image image = new Image(getClass().getResourceAsStream("resources/images/trash2.png"), 22, 32, false, false);
                    private final Button btn = new Button();
                    {
                        btn.setGraphic(new ImageView(image));
                        btn.getStyleClass().add("btn_remove");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                                Session session = factory.openSession();
                                Transaction t = session.beginTransaction();

                                int id = dane_fryzjerow.get(getIndex()).getId_fryzjera();
                                Query q = session.createQuery("delete from Fryzjer where id_fryzjera=:id");
                                q.setParameter("id", id);
                                q.executeUpdate();

                                dane_fryzjerow.remove(getIndex());
                                t.commit();
                                session.close();
                                factory.close();
                            } catch (ConstraintViolationException e) {
                                new Alert(Alert.AlertType.ERROR, "Nie można wykonać tej operacji!").show();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };
        colDropBtn.setCellFactory(cellFactory);

        TableColumn<Fryzjer, Void> editBtn = new TableColumn("Edytuj");
        Callback<TableColumn<Fryzjer, Void>, TableCell<Fryzjer, Void>> cellFactory2 = new Callback<TableColumn<Fryzjer, Void>, TableCell<Fryzjer, Void>>() {
            @Override
            public TableCell<Fryzjer, Void> call(final TableColumn<Fryzjer, Void> param) {
                final TableCell<Fryzjer, Void> cell = new TableCell<Fryzjer, Void>() {
                    Image image = new Image(getClass().getResourceAsStream("resources/images/edit.png"), 22, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.getStyleClass().add("btn_edit");
                        btn.setOnAction((ActionEvent event) -> {
                            GridPane gridpane = new GridPane();
                            gridpane.setPadding(new Insets(5));
                            gridpane.setHgap(5);
                            gridpane.setVgap(5);

                            ColumnConstraints column1 = new ColumnConstraints(150);
                            ColumnConstraints column2 = new ColumnConstraints(150, 150, 150);
                            column2.setHgrow(Priority.ALWAYS);
                            gridpane.getColumnConstraints().addAll(column1, column2);

                            Label limie = new Label("Imię: ");
                            Label lnazwisko = new Label("Nazwisko: ");
                            Label ltelefon = new Label("Telefon: ");
                            Label loddzial = new Label("Oddział: ");

                            TextField imie = new TextField();
                            TextField nazwisko = new TextField();
                            TextField telefon = new TextField();
                            Button btn = new Button("Wyślij");

                            ComboBox oddzial = new ComboBox(dane_oddzialow);
                            oddzial.setItems(dane_oddzialow);
                            oddzial.setConverter(new StringConverter<Oddzial>() {
                                @Override
                                public String toString(Oddzial oddzial) {
                                    if (oddzial != null)
                                        return oddzial.getNazwa();
                                    return "";
                                }

                                @Override
                                public Oddzial fromString(String string) {
                                    return null;
                                }
                            });

                            GridPane.setHalignment(limie, HPos.RIGHT);
                            gridpane.add(limie, 0, 0);
                            GridPane.setHalignment(imie, HPos.LEFT);
                            gridpane.add(imie, 1, 0);
                            GridPane.setHalignment(lnazwisko, HPos.RIGHT);
                            gridpane.add(lnazwisko, 0, 1);
                            GridPane.setHalignment(nazwisko, HPos.LEFT);
                            gridpane.add(nazwisko, 1, 1);
                            GridPane.setHalignment(ltelefon, HPos.RIGHT);
                            gridpane.add(ltelefon, 0, 2);
                            GridPane.setHalignment(telefon, HPos.LEFT);
                            gridpane.add(telefon, 1, 2);
                            GridPane.setHalignment(loddzial, HPos.RIGHT);
                            gridpane.add(loddzial, 0, 3);
                            GridPane.setHalignment(oddzial, HPos.LEFT);
                            gridpane.add(oddzial, 1, 3);
                            GridPane.setHalignment(btn, HPos.RIGHT);
                            gridpane.add(btn, 1, 4);
                            btn.setMinWidth(150);

                            imie.setText(getTableView().getItems().get(getIndex()).getImie());
                            nazwisko.setText(getTableView().getItems().get(getIndex()).getNazwisko());
                            telefon.setText(getTableView().getItems().get(getIndex()).getTelefon());
                            oddzial.setValue(getTableView().getItems().get(getIndex()).getOddzial());

                            VBox vbox = new VBox();
                            vbox.getChildren().add(gridpane);
                            Scene scene2 = new Scene(vbox, 400, 300);
                            Stage stage2 = new Stage();
                            scene2.getStylesheets().add("style.css");
                            vbox.getStyleClass().add("vbox_formularz");
                            stage2.setTitle("Edytuj dane");
                            stage2.setScene(scene2);
                            stage2.setResizable(false);
                            stage2.show();

                            btn.setOnAction(event1 -> {
                                String imie_new = imie.getText();
                                String nazwisko_new = nazwisko.getText();
                                String telefon_new = telefon.getText();
                                Oddzial oddzial_new = (Oddzial) oddzial.getValue();

                                Fryzjer fryzjer = new Fryzjer(imie_new, nazwisko_new, telefon_new);
                                fryzjer.setOddzial(oddzial_new);

                                int id = dane_fryzjerow.get(getIndex()).getId_fryzjera();

                                fryzjer.setId_fryzjera(id);

                                SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                                Session session = factory.openSession();
                                Transaction t = session.beginTransaction();
                                session.update(fryzjer);
                                dane_fryzjerow.set(getIndex(), fryzjer);

                                t.commit();
                                session.close();
                                factory.close();

                                dane_uslug = uslugi();
                                tablica_uslug.setItems(dane_uslug);
                                tablica_uslug.refresh();
                            });
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };
        editBtn.setCellFactory(cellFactory2);


        // wyglad apki
        LpCol.setMinWidth(50);
        LpCol.setMaxWidth(50);
        editBtn.setMinWidth(75);
        editBtn.setMaxWidth(75);
        colDropBtn.setMinWidth(75);
        colDropBtn.setMaxWidth(75);
        tablica_fryzjerow.getColumns().addAll(new TableColumn[]{LpCol, imieCol, nazwiskoCol, telefonCol, oddzialCol, editBtn, colDropBtn});
        tablica_fryzjerow.setItems(dane_fryzjerow);
        tablica_fryzjerow.setColumnResizePolicy(tablica_fryzjerow.CONSTRAINED_RESIZE_POLICY);

        //przycisk dodawania rekordu
        dodaj.setOnAction(event -> {
            GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(5);
            gridpane.setVgap(5);

            ColumnConstraints column1 = new ColumnConstraints(150);
            ColumnConstraints column2 = new ColumnConstraints(150, 150, 150);
            column2.setHgrow(Priority.ALWAYS);
            gridpane.getColumnConstraints().addAll(column1, column2);

            Label limie = new Label("Imię: ");
            Label lnazwisko = new Label("Nazwisko: ");
            Label ltelefon = new Label("Telefon: ");
            Label loddzial = new Label("Oddział: ");

            TextField imie = new TextField();
            TextField nazwisko = new TextField();
            TextField telefon = new TextField();
            Button btn = new Button("Wyślij");

            ComboBox oddzial = new ComboBox(dane_oddzialow);
            oddzial.setItems(dane_oddzialow);
            oddzial.setConverter(new StringConverter<Oddzial>() {
                @Override
                public String toString(Oddzial oddzial) {
                    if (oddzial != null)
                        return oddzial.getNazwa();
                    return "";
                }

                @Override
                public Oddzial fromString(String string) {
                    return null;
                }
            });


            GridPane.setHalignment(limie, HPos.RIGHT);
            gridpane.add(limie, 0, 0);
            GridPane.setHalignment(imie, HPos.LEFT);
            gridpane.add(imie, 1, 0);
            GridPane.setHalignment(lnazwisko, HPos.RIGHT);
            gridpane.add(lnazwisko, 0, 1);
            GridPane.setHalignment(nazwisko, HPos.LEFT);
            gridpane.add(nazwisko, 1, 1);
            GridPane.setHalignment(ltelefon, HPos.RIGHT);
            gridpane.add(ltelefon, 0, 2);
            GridPane.setHalignment(telefon, HPos.LEFT);
            gridpane.add(telefon, 1, 2);
            GridPane.setHalignment(loddzial, HPos.RIGHT);
            gridpane.add(loddzial, 0, 3);
            GridPane.setHalignment(oddzial, HPos.LEFT);
            gridpane.add(oddzial, 1, 3);
            GridPane.setHalignment(btn, HPos.RIGHT);
            gridpane.add(btn, 1, 4);
            btn.setMinWidth(150);


            VBox vbox = new VBox();
            vbox.getChildren().add(gridpane);
            Scene scene2 = new Scene(vbox, 400, 300);
            Stage stage2 = new Stage();
            stage2.setTitle("Wprowadź dane");
            scene2.getStylesheets().add("style.css");
            vbox.getStyleClass().add("vbox_formularz");
            stage2.setScene(scene2);
            stage2.setResizable(false);
            stage2.show();

            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        String imie_new = imie.getText();
                        String nazwisko_new = nazwisko.getText();
                        String telefon_new = telefon.getText();
                        Oddzial oddzial_new = (Oddzial) oddzial.getValue();

                        Fryzjer fryzjer = new Fryzjer(imie_new, nazwisko_new, telefon_new);
                        fryzjer.setOddzial(oddzial_new);

                        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                        Session session = factory.openSession();
                        Transaction transaction = session.beginTransaction();
                        session.save(fryzjer);
                        transaction.commit();
                        session.close();
                        factory.close();

                        dane_fryzjerow.add(fryzjer);
                        tablica_fryzjerow.setItems(dane_fryzjerow);
                        tablica_fryzjerow.refresh();

                        dane_uslug = uslugi();
                        tablica_uslug.refresh();
                    } catch (ConstraintViolationException e) {
                        new Alert(Alert.AlertType.ERROR, "Nie wypełniłeś wszystkich pól!").show();
                    }
                }
            });
        });

//przycisk wyszukiwania
        szukaj.setOnAction(event -> {
            String szukane = szukajka.getText();
            String[] slowa = szukane.split(" ");
            ObservableList<Fryzjer> szukani = FXCollections.observableArrayList();
            boolean n = false;
            for (int i = 0; i < tablica_fryzjerow.getItems().size(); i++) {
                for (int j = 0; j < slowa.length; j++) {
                    if(tablica_fryzjerow.getItems().get(i).toString().toLowerCase(Locale.ROOT).contains(slowa[j].toLowerCase(Locale.ROOT))) {
//                        System.out.println(tablica_fryzjerow.getItems().get(i).toString().toLowerCase(Locale.ROOT));
                        n = true;
                    } else {
                        n=false;
                        break;
                    }
                }

                if(n==true) {
                    szukani.add(tablica_fryzjerow.getItems().get(i));
                    n=false;
                }
            }
            dane_fryzjerow = szukani;
            tablica_fryzjerow.setItems(dane_fryzjerow);

            slowa = null;
        });

        //przywracanie podstawowych rekordów
        wyczysc.setOnAction(event -> {
            dane_fryzjerow = fryzjerzy();
            tablica_fryzjerow.setItems(dane_fryzjerow);
            tablica_fryzjerow.refresh();
            szukajka.setText("");
        });


        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(dodaj, szukaj, szukajka,wyczysc);
        hbox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(20);

        vbox.getChildren().addAll(tablica_fryzjerow, hbox);

        return vbox;
    }

    //TABELA oddzialy i adresy w niej
    static VBox tworzTabeleOddzialow() {
        Button dodaj = new Button("Wstaw nowy rekord");
        dodaj.getStyleClass().add("button_dodaj");
        Button szukaj = new Button("Szukaj...");
        szukaj.getStyleClass().add("button_dodaj");
        TextField szukajka = new TextField();
        Button wyczysc = new Button("Wyczyść kryteria wyszukiwania");
        wyczysc.getStyleClass().add("button_dodaj");

        TableColumn<Oddzial, Integer> LpCol = new TableColumn<>("Lp.");
        LpCol.setCellValueFactory(data -> {
            Oddzial item = data.getValue();
            int index = tablica_oddzialow.getItems().indexOf(item) + 1;
            return Bindings.createObjectBinding(() -> index);
        });

        TableColumn<Oddzial, String> nazwaCol = new TableColumn<>("Nazwa oddzialu");
        nazwaCol.setCellValueFactory(new PropertyValueFactory<>("nazwa"));

        TableColumn<Oddzial, String> kodCol = new TableColumn<>("Kod pocztowy");
        kodCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Oddzial, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Oddzial, String> param) {
                return Bindings.createStringBinding(() -> param.getValue().getAdres_oddzialu().getKodpocztowy());
            }
        });

        TableColumn<Oddzial, String> miastoCol = new TableColumn<>("Miasto");
        miastoCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Oddzial, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Oddzial, String> param) {
                return Bindings.createStringBinding(() -> param.getValue().getAdres_oddzialu().getMiasto());
            }
        });

        TableColumn<Oddzial, String> ulicaCol = new TableColumn<>("Ulica");
        ulicaCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Oddzial, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Oddzial, String> param) {
                return Bindings.createStringBinding(() -> param.getValue().getAdres_oddzialu().getUlica());
            }
        });

        TableColumn<Oddzial, String> numerCol = new TableColumn<>("Numer budynku");
        numerCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Oddzial, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Oddzial, String> param) {
                return Bindings.createStringBinding(() -> param.getValue().getAdres_oddzialu().getNumer_domu());
            }
        });

        TableColumn<Oddzial, Void> colDropBtn = new TableColumn("Usuń");
        Callback<TableColumn<Oddzial, Void>, TableCell<Oddzial, Void>> cellFactory = new Callback<TableColumn<Oddzial, Void>, TableCell<Oddzial, Void>>() {
            @Override
            public TableCell<Oddzial, Void> call(final TableColumn<Oddzial, Void> param) {
                final TableCell<Oddzial, Void> cell = new TableCell<Oddzial, Void>() {

                    Image image = new Image(getClass().getResourceAsStream("resources/images/trash2.png"), 22, 32, false, false);
                    private final Button btn = new Button();


                    {
                        btn.setGraphic(new ImageView(image));
                        btn.getStyleClass().add("btn_remove");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                                Session session = factory.openSession();
                                Transaction t = session.beginTransaction();

                                int id = dane_oddzialow.get(getIndex()).getOddzial_id();

                                int id_adresu = dane_oddzialow.get(getIndex()).getAdres_oddzialu().getId_adresu();
                                Query q = session.createQuery("delete from Oddzial where oddzial_id=:id");
                                Query q2 = session.createQuery("delete from Adres where id_adresu=:idd");
                                q.setParameter("id", id);
                                q2.setParameter("idd", id_adresu);
                                q.executeUpdate();  //najpierw usuwam oddzial
                                q2.executeUpdate(); //a potem jego adres
                                dane_oddzialow.remove(getIndex());
                                t.commit();
                                session.close();
                                factory.close();
                            } catch (ConstraintViolationException e) {
                                new Alert(Alert.AlertType.ERROR, "Nie można wykonać tej operacji!").show();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };
        colDropBtn.setCellFactory(cellFactory);

        TableColumn<Oddzial, Void> editBtn = new TableColumn("Edytuj");
        Callback<TableColumn<Oddzial, Void>, TableCell<Oddzial, Void>> cellFactory2 = new Callback<TableColumn<Oddzial, Void>, TableCell<Oddzial, Void>>() {
            @Override
            public TableCell<Oddzial, Void> call(final TableColumn<Oddzial, Void> param) {
                final TableCell<Oddzial, Void> cell = new TableCell<Oddzial, Void>() {
                    Image image = new Image(getClass().getResourceAsStream("resources/images/edit.png"), 22, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.getStyleClass().add("btn_edit");
                        btn.setOnAction((ActionEvent event) -> {
                            GridPane gridpane = new GridPane();
                            gridpane.setPadding(new Insets(5));
                            gridpane.setHgap(5);
                            gridpane.setVgap(5);

                            ColumnConstraints column1 = new ColumnConstraints(150);
                            ColumnConstraints column2 = new ColumnConstraints(150, 150, 150);
                            column2.setHgrow(Priority.ALWAYS);
                            gridpane.getColumnConstraints().addAll(column1, column2);

                            Label lnazwa = new Label("Nazwa: ");
                            Label lkod = new Label("Kod Pocztowy: ");
                            Label lmiasto = new Label("Miasto: ");
                            Label lulica = new Label("Ulica: ");
                            Label lnumer = new Label("Numer lokalu: ");

                            TextField nazwa = new TextField();
                            TextField kod = new TextField();
                            TextField miasto = new TextField();
                            TextField ulica = new TextField();
                            TextField numer = new TextField();
                            Button btn = new Button("Wyślij");

                            GridPane.setHalignment(lnazwa, HPos.RIGHT);
                            gridpane.add(lnazwa, 0, 0);
                            GridPane.setHalignment(nazwa, HPos.LEFT);
                            gridpane.add(nazwa, 1, 0);

                            GridPane.setHalignment(lkod, HPos.RIGHT);
                            gridpane.add(lkod, 0, 1);
                            GridPane.setHalignment(kod, HPos.LEFT);
                            gridpane.add(kod, 1, 1);

                            GridPane.setHalignment(lmiasto, HPos.RIGHT);
                            gridpane.add(lmiasto, 0, 2);
                            GridPane.setHalignment(miasto, HPos.LEFT);
                            gridpane.add(miasto, 1, 2);

                            GridPane.setHalignment(lulica, HPos.RIGHT);
                            gridpane.add(lulica, 0, 3);
                            GridPane.setHalignment(ulica, HPos.LEFT);
                            gridpane.add(ulica, 1, 3);

                            GridPane.setHalignment(lnumer, HPos.RIGHT);
                            gridpane.add(lnumer, 0, 4);
                            GridPane.setHalignment(numer, HPos.LEFT);
                            gridpane.add(numer, 1, 4);

                            GridPane.setHalignment(btn, HPos.RIGHT);
                            gridpane.add(btn, 1, 5);
                            btn.setMinWidth(150);

                            nazwa.setText(getTableView().getItems().get(getIndex()).getNazwa());
                            kod.setText(getTableView().getItems().get(getIndex()).getAdres_oddzialu().getKodpocztowy());
                            miasto.setText(getTableView().getItems().get(getIndex()).getAdres_oddzialu().getMiasto());
                            ulica.setText(getTableView().getItems().get(getIndex()).getAdres_oddzialu().getUlica());
                            numer.setText(getTableView().getItems().get(getIndex()).getAdres_oddzialu().getNumer_domu());


                            VBox vbox = new VBox();
                            vbox.getChildren().add(gridpane);
                            Scene scene2 = new Scene(vbox, 400, 300);
                            Stage stage2 = new Stage();
                            scene2.getStylesheets().add("style.css");
                            vbox.getStyleClass().add("vbox_formularz");
                            stage2.setTitle("Edytuj dane");
                            stage2.setScene(scene2);
                            stage2.setResizable(false);
                            stage2.show();

                            btn.setOnAction(event1 -> {
                                String nazwa_new = nazwa.getText();
                                String kod_new = kod.getText();
                                String miasto_new = miasto.getText();
                                String ulica_new = ulica.getText();
                                String numer_new = numer.getText();

                                Adres adres = new Adres(kod_new, miasto_new, ulica_new, numer_new);
                                Oddzial oddzial = new Oddzial(nazwa_new, adres);
                                adres.setOddzial(oddzial);

                                int id_ad = dane_oddzialow.get(getIndex()).getAdres_oddzialu().getId_adresu();
                                int id_od = dane_oddzialow.get(getIndex()).getOddzial_id();

                                adres.setId_adresu(id_ad);
                                oddzial.setOddzial_id(id_od);

                                SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                                Session session = factory.openSession();
                                Transaction t = session.beginTransaction();
                                session.update(adres);
                                session.update(oddzial);
                                t.commit();
                                session.close();
                                factory.close();

                                dane_oddzialow.set(getIndex(), oddzial);

                                dane_fryzjerow = fryzjerzy();
                                tablica_fryzjerow.setItems(dane_fryzjerow);
                                tablica_fryzjerow.refresh();
                            });
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };
        editBtn.setCellFactory(cellFactory2);

        // wyglad apki
        LpCol.setMinWidth(50);
        LpCol.setMaxWidth(50);
        editBtn.setMinWidth(75);
        editBtn.setMaxWidth(75);
        colDropBtn.setMinWidth(75);
        colDropBtn.setMaxWidth(75);
        tablica_oddzialow.getColumns().addAll(new TableColumn[]{LpCol, nazwaCol, kodCol, miastoCol, ulicaCol, numerCol, editBtn, colDropBtn});
        tablica_oddzialow.setItems(dane_oddzialow);
        tablica_oddzialow.setColumnResizePolicy(tablica_oddzialow.CONSTRAINED_RESIZE_POLICY);

        //przycisk dodawania rekordu
        dodaj.setOnAction(event -> {
            GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(5);
            gridpane.setVgap(5);

            ColumnConstraints column1 = new ColumnConstraints(150);
            ColumnConstraints column2 = new ColumnConstraints(150, 150, 150);
            column2.setHgrow(Priority.ALWAYS);
            gridpane.getColumnConstraints().addAll(column1, column2);

            Label lnazwa = new Label("Nazwa: ");
            Label lkod = new Label("Kod Pocztowy: ");
            Label lmiasto = new Label("Miasto: ");
            Label lulica = new Label("Ulica: ");
            Label lnumer = new Label("Numer lokalu: ");

            TextField nazwa = new TextField();
            TextField kod = new TextField();
            TextField miasto = new TextField();
            TextField ulica = new TextField();
            TextField numer = new TextField();
            Button btn = new Button("Wyślij");

            GridPane.setHalignment(lnazwa, HPos.RIGHT);
            gridpane.add(lnazwa, 0, 0);
            GridPane.setHalignment(nazwa, HPos.LEFT);
            gridpane.add(nazwa, 1, 0);

            GridPane.setHalignment(lkod, HPos.RIGHT);
            gridpane.add(lkod, 0, 1);
            GridPane.setHalignment(kod, HPos.LEFT);
            gridpane.add(kod, 1, 1);

            GridPane.setHalignment(lmiasto, HPos.RIGHT);
            gridpane.add(lmiasto, 0, 2);
            GridPane.setHalignment(miasto, HPos.LEFT);
            gridpane.add(miasto, 1, 2);

            GridPane.setHalignment(lulica, HPos.RIGHT);
            gridpane.add(lulica, 0, 3);
            GridPane.setHalignment(ulica, HPos.LEFT);
            gridpane.add(ulica, 1, 3);

            GridPane.setHalignment(lnumer, HPos.RIGHT);
            gridpane.add(lnumer, 0, 4);
            GridPane.setHalignment(numer, HPos.LEFT);
            gridpane.add(numer, 1, 4);

            GridPane.setHalignment(btn, HPos.RIGHT);
            gridpane.add(btn, 1, 5);
            btn.setMinWidth(150);

            VBox vbox = new VBox();
            vbox.getChildren().add(gridpane);

            Scene scene2 = new Scene(vbox, 400, 300);
            Stage stage2 = new Stage();

            scene2.getStylesheets().add("style.css");
            vbox.getStyleClass().add("vbox_formularz");
            stage2.setTitle("Wprowadź dane");
            stage2.setScene(scene2);
            stage2.setResizable(false);
            stage2.show();

            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        String nazwa_new = nazwa.getText();
                        String kod_new = kod.getText();
                        String miasto_new = miasto.getText();
                        String ulica_new = ulica.getText();
                        String numer_new = numer.getText();

                        Adres adres = new Adres(kod_new, miasto_new, ulica_new, numer_new);
                        Oddzial oddzial = new Oddzial(nazwa_new, adres);
                        adres.setOddzial(oddzial);

                        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
                        Session session = factory.openSession();
                        Transaction transaction = session.beginTransaction();
                        session.save(adres);
                        session.save(oddzial);
                        transaction.commit();
                        session.close();
                        factory.close();

                        dane_oddzialow.add(oddzial);
                        tablica_oddzialow.setItems(dane_oddzialow);
                        tablica_oddzialow.refresh();

                        dane_fryzjerow = fryzjerzy();
                        tablica_fryzjerow.setItems(dane_fryzjerow);
                        tablica_fryzjerow.refresh();
                    } catch (ConstraintViolationException e) {
                        new Alert(Alert.AlertType.ERROR, "Nie wypełniłeś wszystkich pól!").show();
                    }
                }
            });
        });

        //przycisk wyszukiwania
        szukaj.setOnAction(event -> {
            String szukane = szukajka.getText();
            String[] slowa = szukane.split(" ");
            boolean n = false;
            ObservableList<Oddzial> szukani = FXCollections.observableArrayList();
            for (int i = 0; i < tablica_oddzialow.getItems().size(); i++) {
                for (int j = 0; j < slowa.length; j++) {
                    if(tablica_oddzialow.getItems().get(i).toString().toLowerCase(Locale.ROOT).contains(slowa[j].toLowerCase(Locale.ROOT))) {
                        n = true;
                    } else {
                        n=false;
                        break;
                    }
                }

                if(n==true) {
                    szukani.add(tablica_oddzialow.getItems().get(i));
                    n=false;
                }
            }
            dane_oddzialow = szukani;
            tablica_oddzialow.setItems(dane_oddzialow);
            slowa = null;
        });

        //przywracanie podstawowych rekordów
        wyczysc.setOnAction(event -> {
            dane_oddzialow = oddzialy();
            tablica_oddzialow.setItems(dane_oddzialow);
            tablica_oddzialow.refresh();
            szukajka.setText("");
        });

        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(dodaj, szukaj, szukajka,wyczysc);
        hbox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(20);

        vbox.getChildren().addAll(tablica_oddzialow, hbox);

        return vbox;
    }


}