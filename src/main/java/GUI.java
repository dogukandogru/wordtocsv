import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.prefs.*;


public class GUI extends JFrame implements Runnable{
    private static final String version = "20200717_11:35"; // Version info
    private static final String username = System.getProperty("user.name"); // Username of user
    private static GraphicsConfiguration gc;
    private static PlaceholderTextField filePathLabel;
    private static PlaceholderTextField saveFilePathLabel;
    private static PlaceholderTextField issueKeyPrefixLabel;
    private static JLabel notification;
    private static JComboBox charToReplace;
    private static final FileDialog fileDialog = new FileDialog((Frame)null, "Select TPR to be converted");
    private static final FileDialog saveFileDialog = new FileDialog((Frame)null, "Select place to create .csv file");
    private static final Preferences userPreferences = Preferences.userRoot(); // To save config
    private static boolean doNotShowThisTipAgain = false;
    private static JFrame frame; // Main frame
    private static String language = "Turkish";
    private static String convertType = "Word";
    /* This part may be useful in future
    private static JFileChooser fileChooser = new JFileChooser();
    private static JFileChooser saveFileChooser = new JFileChooser();
    */
    private static JLabel replaceCharacterLabel = new JLabel("Replace \"paragraph\" character (\\n) with");
    private static JLabel issueKeyPrefix = new JLabel("Please Set Issue Key Prefix");
    private static JLabel languageLabel = new JLabel("TPR Language");
    private static JToggleButton trButton =new JToggleButton("TÜRKÇE");
    private static JToggleButton engButton = new JToggleButton("ENGLISH");



    public static void main(String[] args){
        mainGUI();
    }

    // Text of exception list
    private static String getExceptionList(){
        return "Bu uygulamanın mevcut sürümünde WORD formatındaki TPR’nin “parse” edilmesiyle ilgili \"handle\" EDİLEMEYEN \"Exception\"lar aşağıdaki gibidir;\n" +
                "\n" +
                " \n" +
                "\n" +
                "1-      Issue key \"|\" işaretleri arasına olmalıdır. Aksi takdirde bu testler yeni oluşturulmuş varsayılacaktır.\n" +
                "\n" +
                "2-      Bir “Test”in, “Test” olduğunun anlaşılabilmesi için 4.X.X seviyesindeki başlığın “<issue prefix> - ” ile başlaması gerekmektedir. (tırnak işaretleri olmadan)\n" +
                "\n" +
                "a.       <issue prefix>, ilgili proje’nin Jira’daki kısaltması olmalıdır.\n" +
                "\n" +
                "b.       Uygulama’da da aynı <issue prefix> girilmesi gerekecektir.\n" +
                "\n" +
                "c.       <issue prefix> CASE SENSITIVE’dir.\n" +
                "\n" +
                "3-      Uygulama Word dosyasındaki halihazırda kullanılan bütün \";\" işaretlerini \":\" ile \"replace\" etmektedir, çünkü \"delimiter\" olarak \";\" kullanılması gerekmektedir.\n" +
                "\n" +
                "4-      IV. (4.) Bölüm kapsamında BAŞLIK olmayan yerlerde , \"Test Girdileri\", \"Varsayımlar ve Kısıtlamalar\", \"Ön Koşullar\", \"Test Adımları\" ifadelerinin tek başına yer almaması gerekmektedir.\n" +
                "\n" +
                "Örn:\n" +
                "\n" +
                "4.1 Geçici Kabul Gözle Muayene\n" +
                "\n" +
                "4.1.1 |PROJE-0001| – Gözle Kontroller\n" +
                "\n" +
                "4.1.1.1 Ön Koşullar\n" +
                "\n" +
                "Gözle kontrol edilecek cihazların ilgili sahalarda kurulumlarının tamamlanmış olması gerekmektedir.\n" +
                "\n" +
                "4.1.1.2 Test Girdileri\n" +
                "\n" +
                "Test Başlangıç Tarihi-Saati:\n" +
                "\n" +
                "Test Bitiş Tarihi-Saati:\n" +
                "\n" +
                "Yüklenici Temsilcisi:\n" +
                "\n" +
                "Müşteri Temsilcisi:\n" +
                "\n" +
                "Detaylı Test Girdileri, Test Adımları'nda verilmiştir.\n" +
                "\n" +
                "4.1.1.3 Varsayımlar ve Kısıtlamalar\n" +
                "\n" +
                "Ön Koşullar'daki ihtiyaçların karşılanacağı varsayılmaktadır. Bahse konu test sahada devam eden operasyonu etkilemeyecek şekilde koşulması sağlanacaktır.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Yukarıda; Varsayımlar ve Kısıtlamalar başlığı altında geçen \"Ön Koşullar'daki ….\" ifadesi Test Girdileri başlığı altında geçen \"Detaylı Test Girdileri, Test Adımları'nda verilmiştir\" ifadesi SORUN YARATMAZ.\n" +
                "\n" +
                "Fakat aşağıdaki örnekte görülen; Herhangi bir başlığın altında yanlışlıkla yanına bir şey yazılmadan bırakılmış \"Ön Koşullar\", \"Test Adımları\" ifadeleri .csv'de sorun yaratacaktır.\n" +
                "\n" +
                " \n" +
                "\n" +
                "4.2 Geçici Kabul Gözle Muayene\n" +
                "\n" +
                "4.2.1 |PROJE-0001| – Gözle Kontroller\n" +
                "\n" +
                "4.2.1.1 Ön Koşullar\n" +
                "\n" +
                "Gözle kontrol edilecek cihazların ilgili sahalarda kurulumlarının tamamlanmış olması gerekmektedir.\n" +
                "\n" +
                "4.2.1.2 Test Girdileri\n" +
                "\n" +
                "Test Adımları\n" +
                "\n" +
                "4.2.1.3 Varsayımlar ve Kısıtlamalar\n" +
                "\n" +
                "Ön Koşullar\n" +
                "\n" +
                " \n" +
                "\n" +
                "5-      Test Adımları bölümünün 32760 karakterden uzun olmaması gerekmektedir.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Dokümanda böyle bir durum varsa, bu karakter sayısına ulaşılan noktadan sonraki adımlar .csv’ye alınamamaktadır.\n" +
                "\n" +
                "Bu durumda .csv belgesinde \"Manuel_Test_Step_Import_Needed_Label\" sütununun altında \"Manuel_Test_Step_Import_Needed\" ifadesi belirecektir.\n" +
                "\n" +
                "Bu label’in görüldüğü testler Jira’ya eklendikten/güncellendikten sonra “Test” issue’sinin içinden Test Adımları import yöntemi kullanılarak eksik kalan adımlar Jira’ya aktarılabilir.\n" +
                "\n" +
                " \n" +
                "\n" +
                "6-      Summary kısmının 255 karakterden uzun olması.\n" +
                "\n" +
                "Dokümanda böyle bir durum varsa, Summary 255’inci karakterden sonra kesilerek .csv’ye alınmaktadır.\n" +
                "\n" +
                "Bu durumda .csv belgesinde \"Summary_Trimmed_Label\" sütununun altında \"Summary_Trimmed\" ifadesi belirecektir.\n" +
                "\n" +
                " \n" +
                "\n" +
                "7-      Test açıklamaları başlamadan önce 4. TEST AÇIKLAMALARI başlığı mutlaka olmalıdır.\n" +
                "\n" +
                "8-      Test aralarında testleri konu başlıklarına bölen kısımların issue key prefix'i ile başlaması\n" +
                "\n" +
                "ÖRN :\n" +
                "\n" +
                "4.1 TBGTH Test Adımları (Exception Çıkaracaktır.)\n" +
                "\n" +
                "4.1 Test Adımları TBGTH (Exception Çıkarmayacaktır.)\n" +
                "\n" +
                " \n" +
                "\n" +
                "9-      Test \"test adımları\" kısmı dışında tablo bulunması durumunda \"OLE_Objects_Needs_Manuel_Processing_Label\" sütununun altında \"OLE_Objects_Needs_Manuel_Processing_Needed\" ifadesi belirecektir.\n" +
                "\n" +
                "(Bu tabloda test adımlarında yer alan sütunların birebir aynı sıra ve yazılarda yer almaması gerekmektedir.)\n" +
                "\n" +
                " \n" +
                "\n" +
                "ÖRN:\n" +
                "\n" +
                " \n" +
                "\n" +
                "| Adım No | Test Adımı | Beklenen Test Sonuçları | Açıklamalar | Sonuç | Gereksinim No |\n" +
                "\n" +
                " \n" +
                "\n" +
                "Şeklinde sütunları olan bir tablonun dokümanın farklı yerlerinde geçmesi durumunda tablonun WORD'den silinmesi ve daha sonra Jira'ya visual mod kullanılarak eklenmesi gerekir.\n" +
                "\n" +
                " \n" +
                "\n" +
                "10-   Dokümanın herhangi bir yerinde bir resim kullanılması durumunda resim yok sayılarak parse işlemi devam edecektir. Fakat resimlerin yok sayıldığı bilgisi henüz excel dosyasında belirtilememektedir.";
    }

    //Exception menu
    private static void exceptions(){
        JFrame exceptionsframe = new JFrame(gc);
        exceptionsframe.setTitle("Exceptions");
        exceptionsframe.setSize(800,600);
        exceptionsframe.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        exceptionsframe.setLocation(dim.width/2-exceptionsframe.getSize().width/2, dim.height/2-exceptionsframe.getSize().height/2);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);

        JTextArea exceptions = new JTextArea(getExceptionList());
        JScrollPane scroll = new JScrollPane();
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        exceptions.setEditable(false);
        exceptions.setBounds(20,20,750,540);
        exceptions.setLineWrap(true);
        exceptions.setWrapStyleWord(true);
        exceptions.setBorder(new LineBorder(Color.black));

        scroll.setBounds(20,20,750,540);
        scroll.getViewport().add(exceptions);
        scroll.getViewport().setBackground(Color.white);

        exceptions.setFont(exceptions.getFont().deriveFont(15f));

        panel.add(scroll);
        exceptionsframe.add(panel);
        exceptionsframe.setVisible(true);
    }

    // Text of user manual
    private static String getUserManual(){
        return "Tanıtım:\n" +
                "Bu uygulamanın iki temel fonksiyonu vardır.\n" +
                "1-MS Word ortamında “HVL-TEMP-TPr” şablonunun 4. Bölümü altında verilen TEST’leri JİRA’da güncelleme/yaratma işlemlerini yapabilecek .csv dosyalarını oluşturmak.\n" +
                "2-MS Excel’de sırası ile (içleri BOŞ da olsa) aşağıdaki sütunlardan oluşan Test Prosedürlerini Jira’ya import edebilecek .csv dosyalarını oluşturmaktır.\n" +
                "\ta.Test Durumu No\n" +
                "\tb.Test Durumu Adı\n" +
                "\tc.Adım No\n" +
                "\td.Test Adımı\n" +
                "\te.Beklenen Test Sonuçları\n" +
                "\tf.Açıklamalar\n" +
                "\tg.Sonuç(G/K/UD)\n" +
                "\th.Gereksinim No\n" +
                "\ti.Ön Koşul\n" +
                "\tj.Test Girdileri\n" +
                "\tk.Varsayımlar ve Kısıtlamalar\n" +
                "ÖNEMLİ: \n" +
                "1-Word “parse” işlemi sonucunda iki .csv oluşmaktadır. (abc.docx dosyası ile işlem yapıldığı varsayıldığında)\n" +
                "\ta.abc_TBU.csv dosyası:\n" +
                "Daha önce Jira’da bulunan “TEST”lerin Word ortamında güncellendikleri varsayımıı ile oluşturulmaktadır. ADMİN yetkileri ile JİRA’da (U)pdate yapılabilmektedir.\n" +
                "\tb.abc_TBC.csv dosyası:\n" +
                "“TEST”lerin daha önce JİRA’da olmayan, Word dosyası üzerindeki çalışmalar sırasında oluşturulan “TEST”ler oldukları varsayılmaktadır. Bunlar Normal kullanıcı yetkileri ile JİRA’da (C)reate edilebilmektedir.\n" +
                "\n" +
                "2-Excel “parse” işlemi sonucunda iki .csv oluşmaktadır. (abc.xlsx dosyası ile işlem yapıldığı varsayıldığında)\n" +
                "\ta.abc_Tests_TBC.csv dosyası:\n" +
                "“Create” edilecek TEST’leri içeren .csv dosyasıdır. Bunlar Normal kullanıcı yetkileri ile JİRA’da (C)reate edilebilmektedir.\n" +
                "\tb.abc_TBC_Test_Sets.csv dosyası:\n" +
                "“Create” edilecek TEST SET’leri içeren .csv dosyasıdır. Bunlar Normal kullanıcı yetkileri ile JİRA’da (C)reate edilebilmektedir.\n" +
                " \n" +
                "Kullanım\n" +
                "1-Çift tıklayarak jar dosyasını çalıştırınız.\n" +
                "2-“Select TPr to be converted] için [browse] butonuna tıklayarak, csv dosyasına çevirmek istediğiniz Excel veya Word dosyasını seçiniz. (Dosyaların sahip olması gereken içerik ve formatlar yukarıda tanımlanmıştır)\n" +
                "3-Excel Dosyası seçtiyseniz; \n" +
                "\ta.“Replace “paragraph” character (\\n) with” yanındaki seçeneklerden, dokümanın içerisinde geçen \"\\n\" karakterini değiştirmek istediğiniz karakteri seçiniz.\n" +
                "Not: Bu seçimi yaparken dokümanın içinde zaten olması gereken bir karakteri tercih etmeyiniz. Jira’ya import sonrasında \\n yerine kullanılanlar ile normalde olması gerekenleri ayırt etmeniz zorlaşabilir.\n" +
                "\tb.Dil Seçimi:\n" +
                "\ti.English ise çeşitli adımlar için yazılmış bilgiler Jira’ya For_Step-XXXX:….. şeklinde aktarılır.\n" +
                "\tii.Türkçe ise bu bilgiler XXXX numaralı Test Adımı için; …….. şeklinde aktarılır.\n" +
                "4-Word dosyası seçtiyseniz;\n" +
                "\ta.“Replace “paragraph” character (\\n) with” yanındaki seçeneklerden, dokümanın içerisinde geçen \"\\n\" karakterini değiştirmek istediğiniz karakteri seçiniz.\n" +
                "Not: Bu seçimi yaparken dokümanın içinde zaten olması gereken bir karakteri tercih etmeyiniz. Jira’ya import sonrasında \\n yerine kullanılanlar ile normalde olması gerekenleri ayırt etmeniz zorlaşabilir.\n" +
                "\tb.“Please Set Issue Key Prefix”’den, Jira’daki Issue Key’lerin prefix'ini giriniz. (Örn: TBGTH,KRBN)\n" +
                "\tc.Dil Seçimi:\n" +
                "\ti.English ise; HVL-TEMP-TPr’nin İngilizce başlıklarına göre parse işlemi yapılır.\n" +
                "\tii.Türkçe ise; HVL-TEMP-TPr’nin Türkçe başlıklarına göre parse işlemi yapılır.\n" +
                "5-.csv dosyasının oluşturulacağı klasörü seçiniz.\n" +
                "Not: Herhangi bir seçim yapılmaz ise excel belgesinin olduğu konuma oluşturulacaktır.\n" +
                "6-Create .csv butonu ile csv dönüştürme işlemini başlatabilirsiniz.\n" +
                " \n" +
                "Arayüzdeki Diğer Hususlar\n" +
                "1-Uygulamanın bir sonraki açılışında “browse” butonları ile yaptığını seçimlere göre açılmasını isterseniz “close” butonu ile çıkış yapınız.\n" +
                "2-“browse” ile yaptığını seçimlerin “default” konumlara dönmesi için “Clear All” butonunu kullanabilirsiniz.\n" +
                "Not: “Default” “directory”ler, “Windows”un ilgili kullanıcıya ait “Downloads” alanlarıdır. \n" +
                "3-Sağ üstteki çarpı butonu ile karşınıza 3 seçenek çıkacaktır. \n" +
                "\ta.Exit: direk uygulamayı kapatır, \n" +
                "\tb.Save&Exit docx ve csv dosyasının directory'lerini kaydederek sonraki kullanımda o directory'den başlayacak şekilde uygulamayı kapatacaktır. \n" +
                "\tc.Cancel seçeneği uygulamayı kapatmayacaktır.\n";
    }

    //User Manual menu
    private static void usermanual(){
        JFrame usermanualframe = new JFrame(gc);
        usermanualframe.setTitle("User Manual");
        usermanualframe.setSize(800,600);

        usermanualframe.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        usermanualframe.setLocation(dim.width/2-usermanualframe.getSize().width/2, dim.height/2-usermanualframe.getSize().height/2);



        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);


        JTextArea usermanual = new JTextArea(getUserManual());
        JScrollPane scroll = new JScrollPane();
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        usermanual.setEditable(false);
        usermanual.setBounds(20,20,750,540);
        usermanual.setLineWrap(true);
        usermanual.setWrapStyleWord(true);
        usermanual.setBorder(new LineBorder(Color.black));
        scroll.setBounds(20,20,750,540);
        scroll.getViewport().add(usermanual);
        scroll.getViewport().setBackground(Color.white);
        usermanual.setFont(usermanual.getFont().deriveFont(15f));


        panel.add(scroll);
        usermanualframe.add(panel);
        usermanualframe.setVisible(true);
    }

    //Help menu
    public static void help(){
        JFrame helpframe = new JFrame(gc);
        helpframe.setTitle("Help");
        helpframe.setSize(400,150);
        helpframe.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        helpframe.setLocation(dim.width/2-helpframe.getSize().width/2, dim.height/2-helpframe.getSize().height/2);
        JPanel panel = new JPanel();

        panel.setLayout(null);
        panel.setBackground(Color.white);

        JButton exceptionsButton=new JButton("Exceptions");
        exceptionsButton.setBounds(40,25,100,50);
        exceptionsButton.setFont(exceptionsButton.getFont().deriveFont(12f));
        exceptionsButton.setBackground(Color.WHITE);
        exceptionsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                exceptions();
            }
        });

        JButton usermanualButton=new JButton("User Manual");
        usermanualButton.setBounds(150,25,110,50);
        usermanualButton.setFont(exceptionsButton.getFont().deriveFont(12f));
        usermanualButton.setBackground(Color.WHITE);
        usermanualButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                usermanual();
            }
        });


        JButton aboutButton=new JButton("About");
        aboutButton.setBounds(270,25,80,50);
        aboutButton.setFont(exceptionsButton.getFont().deriveFont(12f));
        aboutButton.setBackground(Color.WHITE);
        aboutButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(
                        null,
                        version,
                        "Version",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });


        panel.add(exceptionsButton);
        panel.add(usermanualButton);
        panel.add(aboutButton);
        helpframe.add(panel);
        helpframe.setVisible(true);
    }

    //Tips menu
    public static void tips(){
        frame.setEnabled(false);
        userPreferences.putBoolean("doNotShowThisTipAgain",doNotShowThisTipAgain);
        JFrame tipframe= new JFrame(gc);
        tipframe.setTitle("User Tips");
        tipframe.setSize(600, 770);
        tipframe.setResizable(false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        tipframe.setLocation(dim.width/2-tipframe.getSize().width/2, dim.height/2-tipframe.getSize().height/2);
        tipframe.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(null);

        JTextArea exceptions = new JTextArea(getExceptionList());
        JScrollPane scroll = new JScrollPane();
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        exceptions.setEditable(false);
        exceptions.setBounds(20,20,555,570);
        exceptions.setLineWrap(true);
        exceptions.setWrapStyleWord(true);
        exceptions.setBorder(new LineBorder(Color.black));
        scroll.setBounds(20,20,555,570);
        scroll.getViewport().add(exceptions);
        scroll.getViewport().setBackground(Color.white);
        exceptions.setFont(exceptions.getFont().deriveFont(15f));

        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);

        JCheckBox iHaveReadChxBox = new JCheckBox("I’ve read the “exceptions” of application ");
        iHaveReadChxBox.setBounds(20,600, 700,20);
        iHaveReadChxBox.setBackground(Color.white);
        iHaveReadChxBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                    continueButton.setEnabled(true);
                else
                    continueButton.setEnabled(false);
            }
        });

        JCheckBox doNotShowChxBox = new JCheckBox("Do not show this tip again.");
        doNotShowChxBox.setBounds(20,630, 700,20);
        doNotShowChxBox.setBackground(Color.white);

        continueButton.setBounds(470,670,100,50);
        continueButton.setBorder(new RoundedBorder(0));
        continueButton.setFont(continueButton.getFont().deriveFont(12f));
        continueButton.setBackground(Color.WHITE);
        continueButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(iHaveReadChxBox.isSelected()){
                    tipframe.dispose();
                    frame.setEnabled(true);
                    frame.setVisible(true);
                }
                if(doNotShowChxBox.isSelected()){
                    doNotShowThisTipAgain = true;
                    userPreferences.putBoolean("doNotShowThisTipAgain",doNotShowThisTipAgain);

                }
            }
        });

        panel.add(continueButton);
        panel.add(iHaveReadChxBox);
        panel.add(doNotShowChxBox);
        panel.add(scroll);
        tipframe.add(panel);
        tipframe.setVisible(true);
    }

    //Main Word GUI
    public static void mainGUI(){
        //userPreferences.remove("doNotShowThisTipAgain");  // Remove the comment line and run it once to see the tips menu, then you can comment again.


        FileFilter ff = new FileFilter(){
            public boolean accept(File f){
                if(f.isDirectory()) return true;
                else if(f.getName().endsWith(".xlsx")
                        || f.getName().endsWith(".xls")
                        || f.getName().endsWith(".doc")
                        || f.getName().endsWith(".docx")) return true;
                else return false;
            }
            public String getDescription(){
                return "TPR types (.xlsx, .xls, .docx, .doc)";
            }
        };

        // This part also may be useful in future.
        /*fileChooser.setFileFilter(ff);
        saveFileChooser.setFileFilter(ff);
        fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
        saveFileChooser.removeChoosableFileFilter(saveFileChooser.getAcceptAllFileFilter());*/

        //Setting fileDialogs as Downloads folder
        fileDialog.setDirectory("C:\\Users\\"+username+"\\Downloads");
        fileDialog.setMultipleMode(true);
        saveFileDialog.setDirectory("C:\\Users\\"+username+"\\Downloads");

        //fileChooser.setCurrentDirectory(new File("C:\\Users\\"+username+"\\Downloads"));



        // Frame settings
        frame= new JFrame(gc);
        frame.setTitle("HVL-TEMP-TPR-to-HVL-GO Exportable");
        frame.setSize(1000, 520);
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);


        //Close menu
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                String[] options = {"Exit", "Save & Exit", "Cancel"};
                int answer = JOptionPane.showOptionDialog(null, "Application will be closed without saving",
                        "Close Application ?",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                switch (answer){
                    case 0:
                        System.exit(1);
                        break;
                    case 1:
                        saveConfig();
                        System.exit(1);
                        break;
                    case 2:
                        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        break;
                }
            }
        });



        //adding panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);



        JButton helpButton=new JButton("Help");
        helpButton.setBounds(880,20,80,50);
        helpButton.setBorder(new RoundedBorder(0));
        helpButton.setFont(helpButton.getFont().deriveFont(12f));
        helpButton.setBackground(Color.WHITE);
        helpButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                help();
            }
        });



        JLabel browseLabel = new JLabel("Select TPR to be converted");
        browseLabel.setBorder(new RoundedBorder(10));
        browseLabel.setBackground(Color.white);
        browseLabel.setVerticalAlignment(JLabel.CENTER);
        browseLabel.setHorizontalAlignment(JLabel.CENTER);
        browseLabel.setBounds(20, 100, 300, 50);
        browseLabel.setOpaque(true);
        browseLabel.setFont(browseLabel.getFont().deriveFont(15f));

        filePathLabel = new PlaceholderTextField("");
        filePathLabel.setPlaceholder("File path");
        filePathLabel.setBounds(350, 100, 500, 50);
        filePathLabel.setEditable(true);
        filePathLabel.setOpaque(true);
        filePathLabel.setFont(filePathLabel.getFont().deriveFont(15f));

        JButton searchButton=new JButton("Browse");
        searchButton.setBounds(860,100,100,50);
        searchButton.setBorder(new RoundedBorder(25));
        searchButton.setFont(searchButton.getFont().deriveFont(12f));
        searchButton.setBackground(Color.WHITE);
        searchButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileExplorer();
            }
        });




        languageLabel.setBorder(new RoundedBorder(10));
        languageLabel.setBackground(Color.white);
        languageLabel.setVerticalAlignment(JLabel.CENTER);
        languageLabel.setHorizontalAlignment(JLabel.CENTER);
        languageLabel.setBounds(20, 180, 300, 50);
        languageLabel.setOpaque(true);
        languageLabel.setFont(languageLabel.getFont().deriveFont(15f));

        trButton.setSelected(true);
        trButton.setBounds(350,180,100,50);
        trButton.setBackground(Color.white);
        trButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engButton.setSelected(false);
                trButton.setSelected(true);
                language = "Turkish";
            }
        });

        engButton.setBounds(470,180,100,50);
        engButton.setBackground(Color.white);
        engButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engButton.setSelected(true);
                trButton.setSelected(false);
                language = "English";
            }
        });



        replaceCharacterLabel.setBorder(new RoundedBorder(10));
        replaceCharacterLabel.setBackground(Color.white);
        replaceCharacterLabel.setVerticalAlignment(JLabel.CENTER);
        replaceCharacterLabel.setHorizontalAlignment(JLabel.CENTER);
        replaceCharacterLabel.setBounds(20, 260, 300, 50);
        replaceCharacterLabel.setOpaque(true);
        replaceCharacterLabel.setFont(replaceCharacterLabel.getFont().deriveFont(13f));


        String[] chars = {"#","$","æ","€"};
        charToReplace = new JComboBox(chars);
        charToReplace.setBounds(350, 260, 100, 50);
        charToReplace.setFont(charToReplace.getFont().deriveFont(15f));
        charToReplace.setBackground(Color.white);


        issueKeyPrefix.setBorder(new RoundedBorder(10));
        issueKeyPrefix.setBackground(Color.white);
        issueKeyPrefix.setVerticalAlignment(JLabel.CENTER);
        issueKeyPrefix.setHorizontalAlignment(JLabel.CENTER);
        issueKeyPrefix.setBounds(530, 260, 300, 50);
        issueKeyPrefix.setOpaque(true);
        issueKeyPrefix.setFont(replaceCharacterLabel.getFont().deriveFont(13f));



        issueKeyPrefixLabel = new PlaceholderTextField("");
        issueKeyPrefixLabel.setPlaceholder("");
        issueKeyPrefixLabel.setBounds(860, 260, 100, 50);
        issueKeyPrefixLabel.setEditable(true);
        issueKeyPrefixLabel.setOpaque(true);
        issueKeyPrefixLabel.setFont(issueKeyPrefixLabel.getFont().deriveFont(15f));





        JLabel saveLabel = new JLabel("Select place to create .csv file");
        saveLabel.setBorder(new RoundedBorder(10));
        saveLabel.setBackground(Color.white);
        saveLabel.setVerticalAlignment(JLabel.CENTER);
        saveLabel.setHorizontalAlignment(JLabel.CENTER);
        saveLabel.setBounds(20, 340, 300, 50);
        saveLabel.setOpaque(true);
        saveLabel.setFont(saveLabel.getFont().deriveFont(15f));

        saveFilePathLabel = new PlaceholderTextField("");
        saveFilePathLabel.setPlaceholder("File Path");
        saveFilePathLabel.setBounds(350, 340, 500, 50);
        saveFilePathLabel.setEditable(true);
        saveFilePathLabel.setOpaque(true);
        saveFilePathLabel.setFont(saveFilePathLabel.getFont().deriveFont(15f));

        JButton saveSearchButton=new JButton("Browse");
        saveSearchButton.setFont(saveSearchButton.getFont().deriveFont(12f));
        saveSearchButton.setBorder(new RoundedBorder(25));
        saveSearchButton.setBackground(Color.WHITE);
        saveSearchButton.setBounds(860,340,100,50);
        saveSearchButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                saveFileExplorer();
            }
        });



        notification = new JLabel("");
        notification.setBounds(20, 420, 600, 50);
        notification.setOpaque(true);
        notification.setFont(notification.getFont().deriveFont(15f));
        notification.setBackground(Color.white);
        notification.setVisible(false);

        JButton clearAll=new JButton("Clear All");
        clearAll.setFont(clearAll.getFont().deriveFont(12f));
        clearAll.setBorder(new RoundedBorder(18));
        clearAll.setBackground(Color.WHITE);
        clearAll.setBounds(640,420,100,50);
        clearAll.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                userPreferences.remove("filePath");
                userPreferences.remove("saveFilePath");
                charToReplace.setSelectedItem(0);
                filePathLabel.setText("C:\\Users\\"+username+"\\Downloads");
                saveFilePathLabel.setText("C:\\Users\\"+username+"\\Downloads");
                fileDialog.setDirectory("C:\\Users\\"+username+"\\Downloads");
                saveFileDialog.setDirectory("C:\\Users\\"+username+"\\Downloads");

                notification.setText("Fields cleared.");
                Thread t1 = new Thread(new GUI());
                t1.start();
            }
        });


        JButton createCsv=new JButton("Create\n.csv");
        createCsv.setFont(createCsv.getFont().deriveFont(12f));
        createCsv.setBorder(new RoundedBorder(18));
        createCsv.setBackground(Color.WHITE);
        createCsv.setBounds(750,420,100,50);
        createCsv.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String notificationText="";
                String filePath = filePathLabel.getText();
                String saveFilePath = saveFilePathLabel.getText();
                try{
                    String c = charToReplace.getSelectedItem().toString();
                    if(filePath.equals("") || (!filePath.contains(".docx") && !filePath.contains(".xlsx") && !filePath.contains(".XLSX") && !filePath.contains(".xls") && !filePath.contains(".doc"))){
                        notificationText = "TPR file must be declared.";
                        return;
                    }
                    if(charToReplace.equals("")){
                        notificationText = "Replace character must be typed.";
                        return;
                    }
                    if(saveFilePath.equalsIgnoreCase("") || !saveFilePath.contains(".csv")){
                        notificationText = "The location where the CSV file will be created must be selected.";
                        return;
                    }

                    if(filePath.endsWith(".docx") || filePath.endsWith(".doc")){
                        convertType = "Word";
                    }
                    else if(filePath.endsWith(".xls") || filePath.endsWith(".xlsx")){
                        convertType = "Excel";
                    }

                    String saveFileNameTBU = saveFilePath;
                    String saveFileNameTBC = saveFileNameTBU.substring(0,saveFileNameTBU.lastIndexOf("_")+3)+"C"+".csv";
                    File fileTBU = new File(saveFileNameTBU);
                    File fileTBC = new File(saveFileNameTBC);
                    boolean isTBUExist = fileTBU.exists();
                    boolean isTBCExist = fileTBC.exists();
                    String alertString;
                    if(isTBUExist && isTBCExist) alertString = "TBU file and TBC file are exist.";
                    else if(isTBUExist)  alertString = "TBU file is exist.";
                    else if(isTBCExist)  alertString = "TBC file is exist.";
                    else alertString = "";
                    String issueKeyPrefix = issueKeyPrefixLabel.getText();

                    File[] fileNames = fileDialog.getFiles();
                    if(fileNames.length > 1){
                        generateMultipleFiles(fileNames,c,issueKeyPrefix);
                        return;
                    }


                    if(issueKeyPrefix.equals("") && convertType.equals("Word")){
                        notificationText = "Please set issue key prefix.";
                        return;
                    }
                    if(isTBUExist || isTBCExist){
                        String[] options = {"Abort Process", "Continue and Overwrite"};
                        int answer = JOptionPane.showOptionDialog(null, "Files with same name and extensions available, these files will be overwritten, If you want to keep old files please move them to another folder.",
                                alertString,
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                        switch (answer){
                            case 0:
                                break;
                            case 1:
                                if(convertType.equals("Word"))
                                        notificationText = WordToCSV.generateCsv(filePath,c,saveFilePath,issueKeyPrefix,language);
                                else if(convertType.equals("Excel"))
                                        notificationText = ExcelToJira.generateCsv(filePath,c,saveFilePath,issueKeyPrefix,language);
                        }
                    }
                    else{
                        if(convertType.equals("Word"))
                            notificationText = WordToCSV.generateCsv(filePath,c,saveFilePath,issueKeyPrefix,language);
                        else if(convertType.equals("Excel"))
                            notificationText = ExcelToJira.generateCsv(filePath,c,saveFilePath,issueKeyPrefix,language);
                    }
                }
                finally {
                    notification.setText(notificationText);
                    Thread t1 = new Thread(new GUI());
                    t1.start();
                    frame.setCursor(Cursor.getDefaultCursor());
                }
            }
        });


        JButton close=new JButton("Close");
        close.setFont(close.getFont().deriveFont(12f));
        close.setBorder(new RoundedBorder(18));
        close.setBackground(Color.WHITE);
        close.setBounds(860,420,100,50);
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                saveConfig();
                System.exit(1);
            }
        });


        //adding elements to panel
        panel.setLayout(null);
        panel.add(filePathLabel);
        panel.add(searchButton);
        panel.add(charToReplace);
        panel.add(saveFilePathLabel);
        panel.add(saveLabel);
        panel.add(saveSearchButton);
        panel.add(replaceCharacterLabel);
        panel.add(createCsv);
        panel.add(browseLabel);
        panel.add(notification);
        panel.add(clearAll);
        panel.add(close);
        panel.add(issueKeyPrefix);
        panel.add(issueKeyPrefixLabel);
        panel.add(helpButton);
        panel.add(languageLabel);
        panel.add(trButton);
        panel.add(engButton);

        frame.add(panel);
        frame.setVisible(true);

        loadConfig();

        if(!userPreferences.getBoolean("doNotShowThisTipAgain",false)){
            tips();
        }
    }

    public static void generateMultipleFiles(File[] files, String c, String issueKeyPrefix){
        ArrayList<String> fileNames = new ArrayList<>();
        for(File file : files){
            String fileName = file.toString();
            File folder  = new File(fileName.substring(0,fileName.lastIndexOf("\\")+1)+issueKeyPrefix+"_Project");
            folder.mkdir();
            String onlyFileName = fileName.substring(fileName.lastIndexOf("\\"), fileName.lastIndexOf("."));
            String saveFilePath = fileName.substring(0,fileName.lastIndexOf("\\")+1)+issueKeyPrefix+"_Project"+ onlyFileName +"_MULTIPLE.csv";
            if(convertType.equals("Word"))
                WordToCSV.generateCsv(fileName,c,saveFilePath,issueKeyPrefix,language);
            else if(convertType.equals("Excel"))
                ExcelToJira.generateCsv(fileName,c,saveFilePath,issueKeyPrefix,language);
            fileNames.add(onlyFileName.substring(1));
        }


        try {
            //TBU writing
            FileWriter myWriter = new FileWriter(files[0].toString().substring(0,files[0].toString().lastIndexOf("\\")+1)+issueKeyPrefix+"_Project\\Test_Set.csv");
            myWriter.write("Issue key;");
            myWriter.write("Issue id;");
            myWriter.write("Summary;");
            for(String fileName : fileNames){
                myWriter.write("Test Set;");
                myWriter.write(fileName+";");
                myWriter.write(fileName+";");
                myWriter.write("\n");
            }

            myWriter.close();

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
        }

    }



    /**
     * This method saves config
     */
    public static void saveConfig(){
        userPreferences.put("filePath",filePathLabel.getText());
        userPreferences.put("saveFilePath",saveFilePathLabel.getText());
        userPreferences.put("replaceChar",charToReplace.getSelectedItem().toString());
    }

    /**
     *  This method loads config
     */
    public static void loadConfig(){
        String filePath = userPreferences.get("filePath","");
        filePathLabel.setText(filePath);

        String saveFilePath = userPreferences.get("saveFilePath","");
        saveFilePathLabel.setText(saveFilePath);

        String replaceChar = userPreferences.get("replaceChar","");
        charToReplace.setSelectedItem(replaceChar);
    }

    /**
     * This method update notification
     */
    public static void updateNotification(){
        try{ ;
            if(!notification.getText().equals("")){
                JOptionPane.showMessageDialog(null,notification.getText(),"Notification",JOptionPane.INFORMATION_MESSAGE);
            }
            Thread.sleep(3000);
            notification.setText("");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void fileExplorer(){
        // This part may be useful in future
        /*
        fileChooser.showOpenDialog(null);

        if(fileChooser.getSelectedFile() != null){
            filePathLabel.setText(fileChooser.getSelectedFile().toString());

            if(filePathLabel.getText().endsWith(".xls") || filePathLabel.getText().endsWith(".xlsx"))
                convertType = "Excel";
            else if(filePathLabel.getText().endsWith(".doc") || filePathLabel.getText().endsWith(".docx"))
                convertType = "Word";

            if(convertType.equals("Excel"))
                saveFilePathLabel.setText(fileChooser.getSelectedFile().toString().substring(0,fileChooser.getSelectedFile().toString().lastIndexOf("."))+"_TBC.csv");
            if(convertType.equals("Word"))
                saveFilePathLabel.setText(fileChooser.getSelectedFile().toString().substring(0,fileChooser.getSelectedFile().toString().lastIndexOf("."))+"_TBU.csv");
            updateFrameByConversionType();
        }
        */

        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setVisible(true);


        if(fileDialog.getDirectory() != null && fileDialog.getFile() != null){
                filePathLabel.setText(fileDialog.getDirectory()+"\\"+fileDialog.getFile());
            if(convertType.equals("Word")){
                saveFileDialog.setFile(fileDialog.getFile().substring(0,fileDialog.getFile().lastIndexOf("."))+"_TBC.csv");
                saveFilePathLabel.setText(saveFileDialog.getDirectory()  + "\\" + saveFileDialog.getFile());
            }
            else if(convertType.equals("Excel")){
                saveFileDialog.setFile(fileDialog.getFile().substring(0,fileDialog.getFile().lastIndexOf("."))+"_TBU.csv");
                saveFilePathLabel.setText(saveFileDialog.getDirectory()  + "\\" + saveFileDialog.getFile());
            }
            if(!saveFilePathLabel.getText().contains("."))
                if(convertType.equals("Word"))
                    saveFilePathLabel.setText(saveFilePathLabel.getText()+ "\\" + fileDialog.getFile().substring(0,fileDialog.getFile().lastIndexOf("."))+"_TBC.csv");
                else if(convertType.equals("Excel"))
                    saveFilePathLabel.setText(saveFilePathLabel.getText()+ "\\" + fileDialog.getFile().substring(0,fileDialog.getFile().lastIndexOf("."))+"_TBU.csv");

        }
        else{
            if(filePathLabel.getText().contains("."))
                fileDialog.setDirectory(filePathLabel.getText().substring(0,filePathLabel.getText().lastIndexOf("\\")));
            else
                fileDialog.setDirectory(filePathLabel.getText());
        }

        if(filePathLabel.getText().endsWith(".xls") || filePathLabel.getText().endsWith(".xlsx")){
            convertType = "Excel";
            issueKeyPrefix.setVisible(false);
            issueKeyPrefixLabel.setVisible(false);
        }
        if(filePathLabel.getText().endsWith(".doc") || filePathLabel.getText().endsWith(".docx")){
            convertType = "Word";
            issueKeyPrefix.setVisible(true);
            issueKeyPrefixLabel.setVisible(true);
        }


    }

    public static void saveFileExplorer(){
        // This part may be useful in future
        /*
        saveFileChooser.setCurrentDirectory(new File(saveFilePathLabel.getText().substring(0,saveFilePathLabel.getText().lastIndexOf("\\"))));
        saveFileChooser.showSaveDialog(null);
        saveFilePathLabel.setText(saveFileChooser.getSelectedFile().toString());
        */

        saveFileDialog.setMode(FileDialog.SAVE);
        saveFileDialog.setVisible(true);

        if(saveFileDialog.getDirectory() != null && saveFileDialog.getFile() != null)
            saveFilePathLabel.setText(saveFileDialog.getDirectory()+saveFileDialog.getFile());
        else{
            if(saveFilePathLabel.getText().contains("."))
                saveFileDialog.setFile(saveFilePathLabel.getText().substring(saveFilePathLabel.getText().lastIndexOf("\\")));
            else
                saveFileDialog.setDirectory(saveFilePathLabel.getText());
        }
    }


    /**
     * This method can be useful in future
     */
    public static void updateFrameByConversionType(){
        if(convertType.equals("Word")){
            issueKeyPrefixLabel.setVisible(true);
            issueKeyPrefix.setVisible(true);
            languageLabel.setVisible(true);
            trButton.setVisible(true);
            engButton.setVisible(true);

        }
        else if(convertType.equals("Excel")){
            issueKeyPrefixLabel.setVisible(false);
            issueKeyPrefix.setVisible(false);
            languageLabel.setVisible(false);
            trButton.setVisible(false);
            engButton.setVisible(false);
        }
    }


    @Override
    public void run() {
        updateNotification();
    }
}
