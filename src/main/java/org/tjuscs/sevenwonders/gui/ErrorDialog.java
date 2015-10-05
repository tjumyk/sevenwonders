package org.tjuscs.sevenwonders.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.tjuscs.sevenwonders.LocalMessages;
import org.tjuscs.sevenwonders.Manager;
import org.tjuscs.sevenwonders.kernel.KernelManager;
import org.tjuscs.sevenwonders.kernel.RecManager;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class ErrorDialog extends Stage {
    boolean reallyContinue = false;
    private static ErrorDialog errorDialog;
    private static TextArea exceptions, log, gameData, platformData, envData;
    // private static StringBuffer text;
    private static Tooltip tip;
    private static ClipboardContent content;
    private static TabPane tabs;
    private static OutputStreamWriter writer;
    private static boolean writeAvailable;
    private static File file;
    private static Thread sendThread;

    public static void report(final Throwable error) {
        if (errorDialog == null) {
            errorDialog = new ErrorDialog();
            content = new ClipboardContent();
            // GUIManager.capture();
            GUIManager.disable(true);
            BorderPane root = new BorderPane();
            errorDialog.setScene(new Scene(root, 700, 400));
            Toolkit.getDefaultToolkit().beep();
            errorDialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    Toolkit.getDefaultToolkit().beep();
                    event.consume();
                }
            });
            errorDialog.setTitle(LocalMessages.getString("ErrorReport"));
            errorDialog.getIcons().add(ResManager.getImage("error.png"));

            // text = new StringBuffer(getInfo(error));
            exceptions = new TextArea();
            exceptions.setEditable(false);
            log = new TextArea();
            log.setEditable(false);
            gameData = new TextArea();
            gameData.setEditable(false);
            platformData = new TextArea();
            platformData.setEditable(false);
            envData = new TextArea();
            envData.setEditable(false);
            // textArea.setOnKeyReleased(new EventHandler<KeyEvent>() {
            // @Override
            // public void handle(KeyEvent event) {
            // event.consume();
            // }
            // });
            // text.setWrapText(true);
            // text.setFill(Color.RED);
            // text.setFont(GUIManager.bigFont);

            tabs = new TabPane();
            Tab tab1 = new Tab(LocalMessages.getString("Exceptions"));
            tab1.setContent(exceptions);
            tab1.setClosable(false);
            tabs.getTabs().add(tab1);
            root.setCenter(tabs);
            BorderPane.setMargin(tabs, new Insets(5));

            // Tab tab2 = new Tab("Log Info");
            // tab2.setContent(log);
            // tab2.setClosable(false);
            // tabs.getTabs().add(tab2);

            Tab tab3 = new Tab(LocalMessages.getString("GameRecord"));
            tab3.setContent(gameData);
            tab3.setClosable(false);
            tabs.getTabs().add(tab3);

            Tab tab4 = new Tab(LocalMessages.getString("PlatformInfo"));
            tab4.setContent(platformData);
            tab4.setClosable(false);
            tabs.getTabs().add(tab4);

            Tab tab5 = new Tab(LocalMessages.getString("EnvironmentInfo"));
            tab5.setContent(envData);
            tab5.setClosable(false);
            tabs.getTabs().add(tab5);

            String tempData = KernelManager.getManager().getTempRec();
            gameData.setText(tempData == null ? LocalMessages.getString("NoDataAvailable") : tempData);
            StringBuffer platData = new StringBuffer();
            Properties p = System.getProperties();
            for (Entry<Object, Object> entry : p.entrySet()) {
                platData.append(entry.getKey() + " = " + entry.getValue() + "\n");
            }
            platformData.setText(platData.toString());
            StringBuffer envStr = new StringBuffer();
            Map<String, String> envMap = System.getenv();
            for (Entry<String, String> entry : envMap.entrySet()) {
                envStr.append(entry.getKey() + " = " + entry.getValue() + "\n");
            }
            envData.setText(envStr.toString());
            writeAvailable = true;
            try {
                File dir = new File(System.getProperty("user.dir") + File.separator + "crash");
                if (!dir.exists())
                    dir.mkdir();
                file = File.createTempFile("crash", ".xml", dir);
                // file.deleteOnExit();
                if (file == null)
                    Manager.warn("Record file create failure!");
                writer = new FileWriter(file, true);
                writer.write("<!-- Game Crash Report of 7Wonders(" + LocalMessages.getString("Version").split("\\(")[0]
                        + ") -->\n");
                writer.write("<!-- Generated in " + new Date().toString() + " -->\n\n");
                writer.write("<!-- Platform Info\n");
                writer.write(platformData.getText());
                writer.write("\n -->\n\n");
                writer.write("<!-- Environment Info\n");
                writer.write(envData.getText());
                writer.write("\n -->\n\n");
                writer.write("<!-- Game Record -->\n");
                String data = gameData.getText();
                if (data.equals(LocalMessages.getString("NoDataAvailable"))) {
                    writer.write("<!-- No Record Available -->\n");
                    writer.write(new RecManager.GameInfo().toString());
                } else
                    writer.write(data);
                writer.write("\n\n");
                writer.write("<!-- Exceptions -->\n");
                // writer.close();
                // setTip(LocalMessages.getString("CopyFinished"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            addInfo(error);

            HBox bot = new HBox(20);
            bot.setAlignment(Pos.CENTER);
            root.setBottom(bot);
            BorderPane.setMargin(bot, new Insets(5));

            Label head = new Label(LocalMessages.getString("Error"));
            head.setTextFill(Color.CHOCOLATE);
            head.setGraphic(new ImageView(ResManager.getImage("cry.png")));
            head.setFont(new Font(Font.getDefault().getName(), 20));
            head.setWrapText(true);
            root.setTop(head);
            BorderPane.setMargin(head, new Insets(5));

            tip = new Tooltip();
            tip.setAutoHide(true);
            tip.setAutoFix(true);
            tip.setFont(new Font(Font.getDefault().getName(), 16));

            Button exit = new Button(LocalMessages.getString("Exit"));
            exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Manager.exit(1);
                }
            });
            bot.getChildren().add(exit);

            final Button ctn = new Button(LocalMessages.getString("Continue"));
            ctn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if (errorDialog.reallyContinue) {
                        errorDialog.close();
                        errorDialog = null;
                        GUIManager.disable(false);
                    } else {
                        errorDialog.reallyContinue = true;
                        setTip(LocalMessages.getString("SureToContinue"));
                    }
                }
            });
            bot.getChildren().add(ctn);

            // final Button copy = new
            // Button(LocalMessages.getString("CopyToClipboard"));
            // copy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            // @Override
            // public void handle(MouseEvent event) {
            // //Clipboard clipboard = Clipboard.getSystemClipboard();
            // //JOptionPane.showMessageDialog(null, text.toString());
            // //content.put(new DataFormat("text/html"), text.toString());
            // //clipboard.setContent(content);
            // //exceptions.selectAll();
            // //exceptions.copy();
            // Clipboard clipboard = Clipboard.getSystemClipboard();
            // //JOptionPane.showMessageDialog(null, text.toString());
            // StringBuffer str = new StringBuffer();
            //
            // str.append(exceptions.getText());
            // str.append(gameData.getText());
            //
            // content.put(new DataFormat("text/plain"), str.toString());
            // clipboard.setContent(content);
            // setTip(LocalMessages.getString("CopyFinished"));
            // }
            // });
            // bot.getChildren().add(copy);
            // final Button send = new
            // Button(LocalMessages.getString("SendToUs"));
            // send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //
            //
            // @Override
            // public void handle(MouseEvent event) {
            // try {
            // writeAvailable = false;
            // writer.close();
            // sendThread = new Thread() {
            // public void run() {
            // try {
            // FileReader reader = new FileReader(file);
            // StringBuffer str = new StringBuffer();
            // char[] cbuf = new char[2048];
            // int n = 0;
            // while ((n = reader.read(cbuf)) != -1) {
            // str.append(cbuf);
            // }
            // reader.close();
            // HTTPClient login = new HTTPClient(
            // "http://dev.yzcyx.com/sevenwonders/bug_report.php");
            // login.addInput("Report", str.toString());
            // if(login.login())
            // setTip(LocalMessages.getString("SendSuccess"));
            // else
            // setTip(LocalMessages.getString("SendFail"));
            // } catch (Exception e) {
            // setTip(LocalMessages.getString("SendFail"));
            // e.printStackTrace();
            // }
            // }
            // };
            // Platform.runLater(sendThread);
            // } catch (IOException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            //
            // }
            // });
            // bot.getChildren().add(send);

            Button contact = new Button(LocalMessages.getString("ContactUs"));
            contact.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    String qqURL = "http://wpa.qq.com/msgrd?v=3&uin=773534839&site=qq&menu=yes";
                    if (Manager.isApplet) {
                        Manager.getManager().getHostServices().showDocument(qqURL);
                    } else {
                        try {
                            setTip(LocalMessages.getString("ConnectMYKByQQ"));
                            Desktop desktop = Desktop.getDesktop();
                            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                                URI uri = new URI(qqURL);
                                desktop.browse(uri);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            bot.getChildren().add(contact);

            errorDialog.centerOnScreen();
            errorDialog.show();
        } else {
            if (writeAvailable)
                addInfo(error);

            // text.append("<p>"+info+"</p>");
            // exceptions.appendText(info);
        }
    }

    public static void addInfo(Throwable error) {
        // May be not thread safe.........
        StringWriter str = new StringWriter();
        Throwable e = error;
        e.printStackTrace(new PrintWriter(str));
        exceptions.setText(exceptions.getText() + "\n" + str.toString());
        try {

            writer.write("<!-- \n");
            writer.write(str.toString());
            writer.write(" -->\n");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private static void setTip(String tipText) {
        tip.setText(tipText);
        tip.show(errorDialog);
        tip.setX(errorDialog.getX() + (errorDialog.getWidth() - tip.getWidth()) / 2);
        tip.setY(errorDialog.getY() + (errorDialog.getHeight() - tip.getHeight()) / 2);
    }

}
