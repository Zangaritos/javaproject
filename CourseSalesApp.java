package com.example.rettur;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CourseSalesApp extends Application {

    private Stage primaryStage;

    private Scene createWelcomeScene() {
        Label welcomeLabel = new Label("Добро пожаловать в систему онлайн-обучения!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ImageView newsImage = new ImageView(new Image("https://via.placeholder.com/300x150"));
        newsImage.setFitWidth(300);
        newsImage.setFitHeight(150);

        Label newsLabel = new Label("Новости платформы:\n- Новые курсы по программированию.\n- Специальные предложения на курсы истории.");
        newsLabel.setStyle("-fx-font-size: 14px;");

        Button proceedButton = new Button("Начать");
        proceedButton.setOnAction(e -> primaryStage.setScene(createSubjectTeacherSelectionScene()));

        VBox layout = new VBox(15);
        layout.getChildren().addAll(welcomeLabel, newsImage, newsLabel, proceedButton);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        return new Scene(layout, 400, 400);
    }

    private Scene createSubjectTeacherSelectionScene() {
        ComboBox<String> subjectComboBox = new ComboBox<>();
        subjectComboBox.getItems().addAll("Математика", "Программирование", "История");
        subjectComboBox.setPromptText("Выберите предмет");

        ComboBox<String> teacherComboBox = new ComboBox<>();
        teacherComboBox.setPromptText("Выберите преподавателя");

        Label teacherInfoLabel = new Label("Информация о преподавателе:");
        teacherInfoLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ImageView teacherImageView = new ImageView();
        teacherImageView.setFitWidth(100);
        teacherImageView.setFitHeight(100);

        VBox teacherInfoBox = new VBox(10, teacherInfoLabel, teacherImageView);
        teacherInfoBox.setAlignment(Pos.CENTER);

        teacherComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case "Учитель 1":
                        teacherInfoLabel.setText("Учитель 1: Опытный преподаватель математики.");
                        teacherImageView.setImage(new Image("https://via.placeholder.com/100"));
                        break;
                    case "Учитель 2":
                        teacherInfoLabel.setText("Учитель 2: Специалист в алгебре и геометрии.");
                        teacherImageView.setImage(new Image("https://via.placeholder.com/100"));
                        break;
                    case "Преподаватель A":
                        teacherInfoLabel.setText("Преподаватель A: Эксперт в Java и Python.");
                        teacherImageView.setImage(new Image("https://via.placeholder.com/100"));
                        break;
                    case "Преподаватель B":
                        teacherInfoLabel.setText("Преподаватель B: Разработчик с опытом.");
                        teacherImageView.setImage(new Image("https://via.placeholder.com/100"));
                        break;
                    case "Профессор X":
                        teacherInfoLabel.setText("Профессор X: Историк с мировым именем.");
                        teacherImageView.setImage(new Image("https://via.placeholder.com/100"));
                        break;
                    case "Профессор Y":
                        teacherInfoLabel.setText("Профессор Y: Специалист по древним цивилизациям.");
                        teacherImageView.setImage(new Image("https://via.placeholder.com/100"));
                        break;
                }
            }
        });

        subjectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            teacherComboBox.getItems().clear();
            teacherImageView.setImage(null);
            teacherInfoLabel.setText("Информация о преподавателе:");
            switch (newValue) {
                case "Математика":
                    teacherComboBox.getItems().addAll("Учитель 1", "Учитель 2");
                    break;
                case "Программирование":
                    teacherComboBox.getItems().addAll("Преподаватель A", "Преподаватель B");
                    break;
                case "История":
                    teacherComboBox.getItems().addAll("Профессор X", "Профессор Y");
                    break;
            }
        });

        Button nextButton = new Button("Перейти к выбору курса");
        nextButton.setOnAction(e -> {
            String selectedSubject = subjectComboBox.getValue();
            String selectedTeacher = teacherComboBox.getValue();
            if (selectedSubject != null && selectedTeacher != null) {
                primaryStage.setScene(createCourseSelectionScene(selectedSubject, selectedTeacher));
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Пожалуйста, выберите предмет и преподавателя.");
                alert.show();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(subjectComboBox, teacherComboBox, teacherInfoBox, nextButton);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        return new Scene(layout, 400, 400);
    }

    private Scene createCourseSelectionScene(String subject, String teacher) {
        Label subjectLabel = new Label("Предмет: " + subject);
        Label teacherLabel = new Label("Преподаватель: " + teacher);

        ComboBox<String> durationComboBox = new ComboBox<>();
        durationComboBox.getItems().addAll("1 месяц", "3 месяца", "6 месяцев");
        durationComboBox.setPromptText("Выберите длительность");

        ComboBox<String> priceComboBox = new ComboBox<>();
        priceComboBox.getItems().addAll("Обычный курс", "Плотный курс");
        priceComboBox.setPromptText("Выберите цену");

        Label priceLabel = new Label("Цена за 1 месяц: 0 тг");
        Label totalCostLabel = new Label("Итоговая цена: 0 тг");

        Runnable updateTotalCost = () -> {
            String selectedDuration = durationComboBox.getValue();
            String selectedPrice = priceComboBox.getValue();
            if (selectedDuration != null && selectedPrice != null) {
                int pricePerMonth = selectedPrice.equals("Обычный курс") ? 10000 : 20000;
                int duration = Integer.parseInt(selectedDuration.split(" ")[0]);
                int totalCost = pricePerMonth * duration;
                totalCostLabel.setText("Итоговая цена: " + totalCost + " тг");
            }
        };

        priceComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("Обычный курс")) {
                    priceLabel.setText("Цена за 1 месяц: 10000 тг");
                } else if (newValue.equals("Плотный курс")) {
                    priceLabel.setText("Цена за 1 месяц: 20000 тг");
                }
            }
            updateTotalCost.run();
        });

        durationComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateTotalCost.run();
        });

        Button confirmButton = new Button("Подтвердить выбор");
        confirmButton.setOnAction(e -> {
            String price = priceComboBox.getValue();
            String duration = durationComboBox.getValue();

            if (price != null && duration != null) {
                primaryStage.setScene(createPaymentScene(subject, teacher, price, duration));
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Пожалуйста, выберите цену и длительность.");
                alert.show();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(subjectLabel, teacherLabel, durationComboBox, priceComboBox, priceLabel, totalCostLabel, confirmButton);

        return new Scene(layout, 400, 400);
    }

    private Scene createPaymentScene(String subject, String teacher, String price, String duration) {
        Label receiptLabel = new Label("Выбран курс: " + subject + "\n" +
                "Преподаватель: " + teacher + "\n" +
                "Цена: " + price + "\n" +
                "Длительность: " + duration);

        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("Введите номер карты");

        Button payButton = new Button("Оплатить");
        payButton.setOnAction(e -> {
            String cardNumber = cardNumberField.getText();
            if (!cardNumber.matches("\\d*") || cardNumber.length() != 16) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка: Введите корректный номер карты (16 цифр).");
                alert.show();
            } else {
                receiptLabel.setText(receiptLabel.getText() + "\nНомер карты: " + cardNumber + "\nОплата прошла успешно.");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(receiptLabel, cardNumberField, payButton);

        return new Scene(layout, 400, 400);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create the welcome scene
        Scene scene = createWelcomeScene();

        // Apply the CSS file to the scene
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Set the scene to the primary stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Онлайн обучение");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
