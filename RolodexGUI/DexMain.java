package RolodexGUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DexMain extends Application {

    private boolean inContact = false;
    private ArrayList<Contacts> people = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group group = new Group();

        Scene scene = new Scene(group, 600, 400);

        HBox hb_contactButtons = new HBox();
        Button btn_save = new Button("Save");
        Button btn_new = new Button("New");
        hb_contactButtons.getChildren().addAll(btn_save, btn_new);
        VBox vb_contactButtons2 = new VBox();
        Button btn_saveChanges = new Button("Save Changes");
        Button btn_deleteContact = new Button("Delete Contact");
        vb_contactButtons2.getChildren().addAll(btn_saveChanges, btn_deleteContact);

        //scroll pane
        //ScrollPane sp = new ScrollPane();
        //sp.setPrefSize(200,400);
        //sp.setContent(contacts);

        TextField firstName = new TextField();
        Label first = new Label("First Name            ");
        HBox hb_first = new HBox();
        hb_first.getChildren().addAll(first, firstName);
        TextField lastName = new TextField();
        Label last = new Label("Last Name            ");
        HBox hb_last = new HBox();
        hb_last.getChildren().addAll(last, lastName);
        TextField phoneNumber = new TextField();
        Label phone = new Label("Phone                   ");
        HBox hb_phone = new HBox();
        hb_phone.getChildren().addAll(phone, phoneNumber);
        TextField address = new TextField();
        Label place = new Label("Address                ");
        HBox hb_address = new HBox();
        hb_address.getChildren().addAll(place, address);
        ListView<String> lv_contacts = new ListView<>();
        lv_contacts.setPrefSize(200, 400);

        VBox vb_textBoxes = new VBox();
        vb_textBoxes.getChildren().addAll(hb_first, hb_last, hb_phone, hb_address, hb_contactButtons);
        vb_textBoxes.setSpacing(20);
        vb_textBoxes.setPadding(new Insets(10, 75, 10, 10));

        VBox vb_info = new VBox();
        vb_info.getChildren().addAll(vb_textBoxes);

        VBox vb_contacts = new VBox();
        vb_contacts.getChildren().addAll(lv_contacts);

        HBox hb_allBoxes = new HBox();
        hb_allBoxes.getChildren().addAll(vb_contacts, vb_info);


        //EventHandler eventHandler = new EventHandler() {
        //            @Override
        //            public void handle(Event event) {
        //
        //            }
        //        };

        //.setOnAction(event);

        lv_contacts.setOnMousePressed(event -> {
            if (lv_contacts.getSelectionModel().getSelectedItem() != null) {
                inContact = true;
                firstName.setText(people.get(lv_contacts.getSelectionModel().getSelectedIndex()).getFirstName());
                lastName.setText(people.get(lv_contacts.getSelectionModel().getSelectedIndex()).getLastName());
                phoneNumber.setText(people.get(lv_contacts.getSelectionModel().getSelectedIndex()).getPhoneNumber());
                address.setText(people.get(lv_contacts.getSelectionModel().getSelectedIndex()).getAddress());
            }

            if (inContact) {
                vb_textBoxes.getChildren().set(vb_textBoxes.getChildren().size() - 1, vb_contactButtons2);
            } else {
                vb_textBoxes.getChildren().set(vb_textBoxes.getChildren().size() - 1, hb_contactButtons);
            }
        });


        btn_save.setOnAction(event -> {

            if (!firstName.getText().equals("") && !lastName.getText().equals("")) {
                lv_contacts.getItems().add(lastName.getText() + ", " + firstName.getText());
                people.add(new Contacts(firstName.getText(), lastName.getText(), phoneNumber.getText(), address.getText()));

                firstName.setText("");
                lastName.setText("");
                phoneNumber.setText("");
                address.setText("");

            } else {
                new Alert(Alert.AlertType.ERROR, "First and Last Name must be filled before saving").showAndWait();
            }

        });

        btn_new.setOnAction(event -> {
            firstName.setText("");
            lastName.setText("");
            phoneNumber.setText("");
            address.setText("");
        });

        btn_saveChanges.setOnAction(event -> {
            if (!firstName.getText().equals("") && !lastName.getText().equals("")) {
                people.get(lv_contacts.getSelectionModel().getSelectedIndex()).setFirstName(firstName.getText());
                people.get(lv_contacts.getSelectionModel().getSelectedIndex()).setLastName(lastName.getText());
                people.get(lv_contacts.getSelectionModel().getSelectedIndex()).setPhoneNumber(phoneNumber.getText());
                people.get(lv_contacts.getSelectionModel().getSelectedIndex()).setAddress(address.getText());
                lv_contacts.getItems().set(lv_contacts.getSelectionModel().getSelectedIndex(), lastName.getText() + ", " + firstName.getText());

                inContact = false;
                firstName.setText("");
                lastName.setText("");
                phoneNumber.setText("");
                address.setText("");

                if (inContact) {
                    vb_textBoxes.getChildren().set(vb_textBoxes.getChildren().size() - 1, vb_contactButtons2);
                } else {
                    vb_textBoxes.getChildren().set(vb_textBoxes.getChildren().size() - 1, hb_contactButtons);
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "First and Last Name must be filled before saving").showAndWait();
            }


        });

        btn_deleteContact.setOnAction(event -> {
            people.remove(lv_contacts.getSelectionModel().getSelectedIndex());
            lv_contacts.getItems().remove(lv_contacts.getSelectionModel().getSelectedIndex());

            firstName.setText("");
            lastName.setText("");
            phoneNumber.setText("");
            address.setText("");

            inContact = false;
            if (inContact) {
                vb_textBoxes.getChildren().set(vb_textBoxes.getChildren().size() - 1, vb_contactButtons2);
            } else {
                vb_textBoxes.getChildren().set(vb_textBoxes.getChildren().size() - 1, hb_contactButtons);
            }
        });


        group.getChildren().addAll(hb_allBoxes);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}


