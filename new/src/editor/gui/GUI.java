package editor.gui;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.*;

public final class GUI extends JFrame {

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    private static final String TITLE = "Конфигуратор Bash-скриптов";

    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private final JTabbedPane tabbedPane;
    private final JEditorPane editor;
    private final UndoManager undoManager = new UndoManager();

    private File saveTo;


    public GUI() {

        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setJMenuBar(buildMenuBar());

        tabbedPane = new JTabbedPane();
        tabbedPane.setMaximumSize(new Dimension(20000,0));
        editor = new JEditorPane();
        editor.getDocument().addUndoableEditListener(undoManager);
        Font font = new Font("TimesRoman", Font.PLAIN, 20);
        editor.setFont(font);
        Box mainBox = new Box(BoxLayout.Y_AXIS);
        mainBox.add(tabbedPane);
        mainBox.add(buildScrollPane());
        add(mainBox);
        pack();
        setLocationRelativeTo(null);

    }

    private JMenuBar buildMenuBar() {

        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildFileMenu());
        menuBar.add(buildEditMenu());
        menuBar.add(buildExecuteMenu());

        return menuBar;

    }
    private JPanel buildFileCommandPanel(){
        JPanel content = new JPanel();

        JButton Button1 = new JButton("ls");
        Button1.setToolTipText("Вывод на экран списка файлов и каталогов в текущем каталоге");
        Button1.addActionListener(e -> printCommand(e,"ls"));

        JButton Button2 = new JButton("pwd");
        Button2.setToolTipText("Вывод имени текущего каталога ");
        Button2.addActionListener(e -> printCommand(e,"pwd"));

        JButton Button3 = new JButton("cd");
        Button3.setToolTipText("Изменение текущего каталога");
        Button3.addActionListener(e -> printCommand(e,"cd"));

        JButton Button4 = new JButton("cp");
        Button4.setToolTipText("Копирование файлов и каталогов");
        Button4.addActionListener(e -> printCommand(e,"cp"));

        JButton Button5 = new JButton("mv");
        Button5.setToolTipText("Перемещение и переименовать файлов и каталогов");
        Button5.addActionListener(e -> printCommand(e,"mv"));

        JButton Button6 = new JButton("ln");
        Button6.setToolTipText("Создание ссылки (псевдонима) для файла или каталога");
        Button6.addActionListener(e -> printCommand(e,"ln"));

        JButton Button7 = new JButton("rm");
        Button7.setToolTipText("Удаление файлов и каталогов");
        Button7.addActionListener(e -> printCommand(e,"rm"));

        JButton Button8 = new JButton("mkdir");
        Button8.setToolTipText("Создание нового каталога");
        Button8.addActionListener(e -> printCommand(e,"mkdir"));

        JButton Button9 = new JButton("rmdir");
        Button9.setToolTipText("Удаление пустого каталога");
        Button9.addActionListener(e -> printCommand(e,"rmdir"));

        JButton Button10 = new JButton("tar");
        Button10.setToolTipText("Утилита архивирования без сжатия");
        Button10.addActionListener(e -> printCommand(e,"tar"));

        JButton Button11 = new JButton("find");
        Button11.setToolTipText("Поиск файлов и каталогов по заданным критериям");
        Button11.addActionListener(e -> printCommand(e,"find"));

        JButton Button12 = new JButton("gzip");
        Button12.setToolTipText("Утилита сжатия файлов");
        Button12.addActionListener(e -> printCommand(e,"gzip"));

        content.add(Button1);
        content.add(Button2);
        content.add(Button3);
        content.add(Button4);
        content.add(Button5);
        content.add(Button6);
        content.add(Button7);
        content.add(Button8);
        content.add(Button9);
        content.add(Button10);
        content.add(Button11);
        content.add(Button12);

        return content;
    }
    private JPanel buildProcessCommandPanel(){
        JPanel content = new JPanel();

        JButton Button1 = new JButton("ps");
        Button1.setToolTipText("Вывод на экран списка файлов и каталогов в текущем каталоге");
        Button1.addActionListener(e -> printCommand(e,"ps"));

        JButton Button3 = new JButton("kill");
        Button3.setToolTipText("Посылка сигнала процессу");
        Button3.addActionListener(e -> printCommand(e,"kill"));

        JButton Button4 = new JButton("killall");
        Button4.setToolTipText("Посылка сигнала всем процессы по имени программы");
        Button4.addActionListener(e -> printCommand(e,"killall"));

        JButton Button2 = new JButton("pstree");
        Button2.setToolTipText("Построения дерева процессов");
        Button2.addActionListener(e -> printCommand(e,"pstree"));

        content.add(Button1);
        content.add(Button2);
        content.add(Button3);
        content.add(Button4);

        return content;
    };
    private JPanel buildRightCommandPanel(){
        JPanel content = new JPanel();

        JButton Button1 = new JButton("chmod");
        Button1.setToolTipText("Изменение прав доступа к файлам и каталогам");
        Button1.addActionListener(e -> printCommand(e,"chmod"));

        JButton Button2 = new JButton("chgrp");
        Button2.setToolTipText("Изменения группы-владельца для файлов и каталогов");
        Button2.addActionListener(e -> printCommand(e,"chgrp"));

        JButton Button3 = new JButton("chown");
        Button3.setToolTipText("Изменение владельца для файлов и каталогов");
        Button3.addActionListener(e -> printCommand(e,"chown"));

        content.add(Button1);
        content.add(Button2);
        content.add(Button3);

        return content;
    };
    private JPanel buildNetCommandPanel(){
        JPanel content = new JPanel();
        JButton Button1 = new JButton("ping");
        Button1.setToolTipText("Проверка работоспособности при помощи пакетов ICMP и UDP");
        Button1.addActionListener(e -> printCommand(e,"ping"));

        JButton Button2 = new JButton("ifconfig");
        Button2.setToolTipText("Конфигурирование сетевых интерфейсов");
        Button2.addActionListener(e -> printCommand(e,"ifconfig"));

        JButton Button3 = new JButton("arp");
        Button3.setToolTipText("Работа с таблицей ARP");
        Button3.addActionListener(e -> printCommand(e,"arp"));

        JButton Button4 = new JButton("tcpdump");
        Button4.setToolTipText("Программа сетевой анализатор");
        Button4.addActionListener(e -> printCommand(e,"tcpdump"));

        JButton Button5 = new JButton("traceroute");
        Button5.setToolTipText("Трассировка пути следования до заданного хоста");
        Button5.addActionListener(e -> printCommand(e,"traceroute"));

        JButton Button6 = new JButton("netstat");
        Button6.setToolTipText("Информация о портах и соединениях");
        Button6.addActionListener(e -> printCommand(e,"netstat"));

        JButton Button7 = new JButton("route");
        Button7.setToolTipText("Работа с таблицей маршрутизации");
        Button7.addActionListener(e -> printCommand(e,"route"));

        content.add(Button1);
        content.add(Button2);
        content.add(Button3);
        content.add(Button4);
        content.add(Button5);
        content.add(Button6);
        content.add(Button7);

        return content;
    };
    private JPanel buildTextDataCommandPanel(){
        JPanel content = new JPanel();
        JButton Button1 = new JButton("cat");
        Button1.setToolTipText("Вывод содержимого файла на стандартный вывод");
        Button1.addActionListener(e -> printCommand(e,"cat"));

        JButton Button2 = new JButton("more");
        Button2.setToolTipText("Просмотр содержимого текстового файла постранично");
        Button2.addActionListener(e -> printCommand(e,"more"));

        JButton Button3 = new JButton("less");
        Button3.setToolTipText("Просмотр содержимого текстового файла с возможностью вернуться к предыдущим страницам");
        Button3.addActionListener(e -> printCommand(e,"less"));

        JButton Button4 = new JButton("head");
        Button4.setToolTipText("Отображение первые несколько строк файла");
        Button4.addActionListener(e -> printCommand(e,"head"));

        JButton Button5 = new JButton("tail");
        Button5.setToolTipText("Отображение последние несколько строк файла");
        Button5.addActionListener(e -> printCommand(e,"tail"));

        JButton Button6 = new JButton("vi");
        Button6.setToolTipText("Текстовый редактор");
        Button6.addActionListener(e -> printCommand(e,"vi"));

        JButton Button7 = new JButton("wc");
        Button7.setToolTipText("Подсчет количество строк, слов и символов в текстовом файле");
        Button7.addActionListener(e -> printCommand(e,"wc"));

        JButton Button8 = new JButton("file");
        Button8.setToolTipText("Вывод типа данных файла");
        Button8.addActionListener(e -> printCommand(e,"file"));

        JButton Button9 = new JButton("diff");
        Button9.setToolTipText("Сравнение двух текстовых файла");
        Button9.addActionListener(e -> printCommand(e,"diff"));

        content.add(Button1);
        content.add(Button2);
        content.add(Button3);
        content.add(Button4);
        content.add(Button5);
        content.add(Button6);
        content.add(Button7);
        content.add(Button8);
        content.add(Button9);

        return content;
    };
    private JPanel buildRegularCommandPanel(){
        JPanel content = new JPanel();
        JButton Button1 = new JButton("sed");
        Button1.setToolTipText("Потоковый текстовый редактор, применяющий различные предопределённые текстовые преобразования к последовательному потоку текстовых данных");
        Button1.addActionListener(e -> printCommand(e,"sed"));

        JButton Button2 = new JButton("grep");
        Button2.setToolTipText("Поиск строк согласно заданным регулярным выражениям");
        Button2.addActionListener(e -> printCommand(e,"grep"));

        JButton Button3 = new JButton("awk");
        Button3.setToolTipText("Обработка входного потока по заданным шаблонам");
        Button3.addActionListener(e -> printCommand(e,"awk"));

        content.add(Button1);
        content.add(Button2);
        content.add(Button3);

        return content;
    };
    private JPanel buildSystemCommandPanel(){
        JPanel content = new JPanel();

        JButton Button1 = new JButton("uname");
        Button1.setToolTipText("Вывод информации о хосте");
        Button1.addActionListener(e -> printCommand(e,"uname"));

        JButton Button2 = new JButton("uptime");
        Button2.setToolTipText("Время работы системы");
        Button2.addActionListener(e -> printCommand(e,"uptime"));

        JButton Button3 = new JButton("init #");
        Button3.setToolTipText("Переход на заданный уровень выполнения");
        Button3.addActionListener(e -> printCommand(e,"init #"));

        JButton Button4 = new JButton("who");
        Button4.setToolTipText("Список активных пользователей системы");
        Button4.addActionListener(e -> printCommand(e,"who"));

        JButton Button5 = new JButton("reboot");
        Button5.setToolTipText("Перезагрузка системы");
        Button5.addActionListener(e -> printCommand(e,"reboot"));

        JButton Button6 = new JButton("shutdown");
        Button6.setToolTipText("Выключение системы");
        Button6.addActionListener(e -> printCommand(e,"shutdown"));

        JButton Button7 = new JButton("passwd");
        Button7.setToolTipText("Установка/изменение пароля");
        Button7.addActionListener(e -> printCommand(e,"passwd"));

        JButton Button8 = new JButton("groupadd");
        Button8.setToolTipText("Добавление группы");
        Button8.addActionListener(e -> printCommand(e,"groupadd"));

        JButton Button9 = new JButton("useradd");
        Button9.setToolTipText("Добавление пользователя");
        Button9.addActionListener(e -> printCommand(e,"useradd"));

        JButton Button10 = new JButton("id");
        Button10.setToolTipText("Вывод идентификатора пользователя");
        Button10.addActionListener(e -> printCommand(e,"id"));

        JButton Button11 = new JButton("mount");
        Button11.setToolTipText("Монтирование файловых систем");
        Button11.addActionListener(e -> printCommand(e,"mount"));

        JButton Button12 = new JButton("dmesg");
        Button12.setToolTipText("Сообщения о ядра ОС");
        Button12.addActionListener(e -> printCommand(e,"dmesg"));

        content.add(Button1);
        content.add(Button2);
        content.add(Button3);
        content.add(Button4);
        content.add(Button5);
        content.add(Button6);
        content.add(Button7);
        content.add(Button8);
        content.add(Button9);
        content.add(Button10);
        content.add(Button11);
        content.add(Button12);

        return content;
    };
    private JPanel buildDiagCommandPanel(){
        JPanel content = new JPanel();

        JButton Button1 = new JButton("vmstat");
        Button1.setToolTipText("Статистика использования системной памяти");
        Button1.addActionListener(e -> printCommand(e,"vmstat"));

        JButton Button2 = new JButton("free");
        Button2.setToolTipText("Информация об используемой и свободной памяти");
        Button2.addActionListener(e -> printCommand(e,"free"));

        JButton Button3 = new JButton("mpstat");
        Button3.setToolTipText("Статистика использования процессора");
        Button3.addActionListener(e -> printCommand(e,"mpstat"));

        JButton Button4 = new JButton("sar");
        Button4.setToolTipText("Статистика использования системных ресурсов");
        Button4.addActionListener(e -> printCommand(e,"sar"));

        JButton Button5 = new JButton("iostat");
        Button5.setToolTipText("Статистика использования подсистемы ввода-вывода");
        Button5.addActionListener(e -> printCommand(e,"iostat"));

        content.add(Button1);
        content.add(Button2);
        content.add(Button3);
        content.add(Button4);
        content.add(Button5);

        return content;
    };
    private JPanel buildInterpreterCommandPanel(){
        JPanel content = new JPanel();

        JButton Button1 = new JButton("export");
        Button1.setToolTipText("Определение переменных окружения");
        Button1.addActionListener(e -> printCommand(e,"export"));

        JButton Button2 = new JButton("set");
        Button2.setToolTipText("Вывод переменных окружения");
        Button2.addActionListener(e -> printCommand(e,"set"));

        JButton Button3 = new JButton("alias");
        Button3.setToolTipText("Установка псевдонима для команды");
        Button3.addActionListener(e -> printCommand(e,"alias"));

        JButton Button4 = new JButton("<>");
        Button4.setToolTipText("Перенаправления ввода или вывода программы");
        Button4.addActionListener(e -> printCommand(e,"<>"));

        JButton Button5 = new JButton("|");
        Button5.setToolTipText("Организация конвейера, когда вывод одной команды подается на вход следующей");
        Button5.addActionListener(e -> printCommand(e,"|"));

        JButton Button6 = new JButton("&");
        Button6.setToolTipText("Запуск программы в фоновом режиме");
        Button6.addActionListener(e -> printCommand(e,"&"));

        content.add(Button1);
        content.add(Button2);
        content.add(Button3);
        content.add(Button4);
        content.add(Button5);
        content.add(Button6);

        return content;
    };
    private JMenu buildExecuteMenu(){

        final JMenu view = new JMenu("Вид");

        final JMenu panel = new JMenu("Панели команд");

        String fileCommandTitle = "Команды для работы с файловой системой";
        String proccesCommandTitle = "Работа с процессами";
        String netCommandTitle = "Средства работы с сетевым окружением";
        String textDataCommandTitle = "Работа с текстовыми данными";
        String regularCommandTitle = "Работа с регулярными выражениями";
        String interpreterCommandTitle = "Работа с командным интерпретатором";
        String systemCommandTitle = "Системные утилиты";
        String diagCommandTitle = "Команды диагностики системы";
        String rightCommandTitle = "Работа с правами доступа";
        JCheckBoxMenuItem fileCommand = new JCheckBoxMenuItem(fileCommandTitle);
        JCheckBoxMenuItem proccesCommand = new JCheckBoxMenuItem(proccesCommandTitle);
        JCheckBoxMenuItem netCommand = new JCheckBoxMenuItem(netCommandTitle);
        JCheckBoxMenuItem textDataCommand = new JCheckBoxMenuItem(textDataCommandTitle);
        JCheckBoxMenuItem regularCommand = new JCheckBoxMenuItem(regularCommandTitle);
        JCheckBoxMenuItem interpreterCommand = new JCheckBoxMenuItem(interpreterCommandTitle);
        JCheckBoxMenuItem systemCommand = new JCheckBoxMenuItem(systemCommandTitle);
        JCheckBoxMenuItem diagCommand = new JCheckBoxMenuItem(diagCommandTitle);
        JCheckBoxMenuItem rightCommand = new JCheckBoxMenuItem(rightCommandTitle);



        fileCommand.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(fileCommand.getState()){
                    tabbedPane.add(fileCommandTitle, buildFileCommandPanel());
                } else {
                    removeTabWithTitle(fileCommandTitle);
                }
            }
        });

        proccesCommand.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(proccesCommand.getState()){
                    tabbedPane.add(proccesCommandTitle,buildProcessCommandPanel());
                } else {
                    removeTabWithTitle(proccesCommandTitle);
                }
            }
        });

        netCommand.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(netCommand.getState()){
                    tabbedPane.add(netCommandTitle, buildNetCommandPanel());
                } else {
                    removeTabWithTitle(netCommandTitle);
                }
            }
        });

        textDataCommand.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(textDataCommand.getState()){
                    tabbedPane.add(textDataCommandTitle, buildTextDataCommandPanel());
                } else {
                    removeTabWithTitle(textDataCommandTitle);
                }
            }
        });
        regularCommand.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(regularCommand.getState()){
                    tabbedPane.add(regularCommandTitle, buildRegularCommandPanel());
                } else {
                    removeTabWithTitle(regularCommandTitle);
                }
            }
        });

        interpreterCommand.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(interpreterCommand.getState()){
                    tabbedPane.add(interpreterCommandTitle, buildInterpreterCommandPanel());
                } else {
                    removeTabWithTitle(interpreterCommandTitle);
                }
            }
        });
        systemCommand.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(systemCommand.getState()){
                    tabbedPane.add(systemCommandTitle, buildSystemCommandPanel());
                } else {
                    removeTabWithTitle(systemCommandTitle);
                }
            }
        });
        diagCommand.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(diagCommand.getState()){
                    tabbedPane.add(diagCommandTitle, buildDiagCommandPanel());
                } else {
                    removeTabWithTitle(diagCommandTitle);
                }
            }
        });
        rightCommand.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(rightCommand.getState()){
                    tabbedPane.add(rightCommandTitle, buildRightCommandPanel());
                } else {
                    removeTabWithTitle(rightCommandTitle);
                }
            }
        });


        panel.add(fileCommand);
        panel.add(proccesCommand);
        panel.add(netCommand);
        panel.add(textDataCommand);
        panel.add(regularCommand);
        panel.add(interpreterCommand);
        panel.add(systemCommand);
        panel.add(diagCommand);
        panel.add(rightCommand);

//        panel.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ProcessBuilder pb = new ProcessBuilder();
//                pb.command(editor.getText());
//                //pb.command(this.jTextField1.getText());
//
//                try {
//                    Process process = pb.start();
//                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                    String line;
//                    String previous = null;
//
//                    while ((line = br.readLine()) != null) {
//                        if (!line.equals(previous)) {
//                            previous = line;
//
//                            editor.setText(editor.getText() + line + "\n");
//                            //this.jTextArea1.append(line + "\n");
//                        }
//                    }
//
//                    process.waitFor();
//
//                } catch (InterruptedException | IOException var7) {
//                    editor.setText(editor.getText() + "\n\n!! ERROR: Can't start given script! !!\n");
//                    //throw new RuntimeException(var7);
//                }
//            }
//        });

        view.add(panel);
        return view;
    }
    private JMenu buildFileMenu() {

        final JMenu fileMenu = new JMenu("Файл");

        final JMenuItem newFile = new JMenuItem("Создать");
        newFile.addActionListener(this::newFile);
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));

        final JMenuItem open = new JMenuItem("Открыть");
        open.addActionListener(this::openFile);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));

        final JMenuItem save = new JMenuItem("Сохранить");
        save.addActionListener(this::saveFile);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));

        final JMenuItem saveAs = new JMenuItem("Сохранить как");
        saveAs.addActionListener(this::saveFileAs);
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));

        final JMenuItem quit = new JMenuItem("Выход");
        quit.addActionListener(this::quit);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));

        fileMenu.add(newFile);
        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(saveAs);
        fileMenu.addSeparator();
        fileMenu.add(quit);

        return fileMenu;

    }
    private JMenu buildEditMenu() {

        final JMenu editMenu = new JMenu("Правка");

        final JMenuItem undo = new JMenuItem("Отменить");
        undo.addActionListener(this::undo);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));

        final JMenuItem redo = new JMenuItem("Вернуть");
        redo.addActionListener(this::redo);
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));

        final JMenuItem cut = new JMenuItem("Вырезать");
        cut.addActionListener(this::cut);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

        final JMenuItem copy = new JMenuItem("Копировать");
        copy.addActionListener(this::copy);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));

        final JMenuItem paste = new JMenuItem("Вставить");
        paste.addActionListener(this::paste);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));

        final JMenuItem delete = new JMenuItem("Удалить");
        delete.addActionListener(this::delete);

        final JMenuItem selectAll = new JMenuItem("Выделить все");
        selectAll.addActionListener(this::selectAll);
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));

        editMenu.add(undo);
        editMenu.add(redo);
        editMenu.addSeparator();
        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(delete);
        editMenu.addSeparator();
        editMenu.add(selectAll);

        return editMenu;

    }
    private JScrollPane buildScrollPane() {

        final JScrollPane scrollPane = new JScrollPane(editor);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;

    }


    private void newFile(final ActionEvent event) {

        editor.setText(null);

        saveTo = null;

    }

    private void openFile(final ActionEvent event) {

        final JFileChooser fileChooser = new JFileChooser();
        final int action = fileChooser.showOpenDialog(this);

        if (action == JFileChooser.APPROVE_OPTION) {

            try {

                editor.setPage(fileChooser.getSelectedFile().toURI().toURL());

                saveTo = fileChooser.getSelectedFile();

            } catch (final IOException exception) {

                exception.printStackTrace();

            }

        }

    }

    private void saveFile(final ActionEvent event) {

        if (saveTo != null) {

            try {

                final FileWriter writer = new FileWriter(saveTo);
                writer.write(editor.getText());
                writer.flush();
                writer.close();

            } catch (final IOException exception) {

                exception.printStackTrace();

            }

        } else {

            saveFileAs(event);

        }

    }

    private void saveFileAs(final ActionEvent event) {

        final JFileChooser fileChooser = new JFileChooser();
        final int action = fileChooser.showSaveDialog(this);

        if (action == JFileChooser.APPROVE_OPTION) {

            saveTo = fileChooser.getSelectedFile();

            saveFile(event);

        }

    }

    private void quit(final ActionEvent event) {

        System.exit(0);

    }

    private void undo(final ActionEvent event) {

        undoManager.undo();

    }

    private void redo(final ActionEvent event) {

        undoManager.redo();

    }

    private void cut(final ActionEvent event) {

        clipboard.setContents(new StringSelection(editor.getSelectedText()), null);
        editor.replaceSelection("");

    }

    private void copy(final ActionEvent event) {

        clipboard.setContents(new StringSelection(editor.getSelectedText()), null);

    }

    private void paste(final ActionEvent event) {

        try {

            editor.replaceSelection(String.valueOf(clipboard.getData(DataFlavor.stringFlavor)));

        } catch (final UnsupportedFlavorException | IOException exception) {

            exception.printStackTrace();

        }

    }

    private void delete(final ActionEvent event) {

        editor.replaceSelection("");

    }

    private void selectAll(final ActionEvent event) {

        editor.selectAll();

    }

    private void printCommand(final ActionEvent event, String command) {

        String text = editor.getText();
        StringBuffer sb = new StringBuffer(text);
        sb.insert(editor.getCaretPosition(),command+" ");

        editor.setText(sb.toString());
    }

    private void removeTabWithTitle(String tabTitleToRemove) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            String tabTitle = tabbedPane.getTitleAt(i);
            if (tabTitle.equals(tabTitleToRemove)) {
                tabbedPane.remove(i);
                break;
            }
        }
    }


}

