
Bu uygulama HVL-TEMP-TPr şablonunun 4. Bölümü altında verilen TEST’leri JİRA’da güncelleme/yaratma işlemlerini yapabilecek .csv dosyalarını oluşturmak üzere geliştirilmiştir.

***Kullanım***

1-Çift tıklayarak jar dosyasını çalıştırın. 

2-Üst tarafta bulunan browse butonu ile csv dosyasına çevirmek istediğiniz word belgesini seçiniz.

3-Daha sonra bir alt sütundan dökümanın içerisinde geçen "\n" karakterini değiştirmek istediğiniz karakteri girin (Nedeni ise "\n" karakterinin .csv dosyalarına yazılırken sütunların hizasını bozması).

4-Sağ taraftan çevireceğiniz test belgesinin Issue Key Prefix'ini giriniz. (Örn: TBGTH,KRBN)

5-Daha sonra csv dosyasının oluşturulacağı klasörü seçiniz. Eğer seçim yapılmaz ise word belgesinin olduğu konuma oluşturulacaktır.

6- Create .csv butonu ile csv dönüştürme işlemini başlatabilirsiniz.

7- Jar dosyasının dönüştürdüğünüz dosyanın bulunduğu klasörü kaydetmesini isterseniz uygulamayı kapatırken Close butonu ile kapatınız. Bu hem docx dosyasının hemde csv dosyasının directory'lerini kaydedecektir.

8- Bu directory'lerin temizlenmesi için Clear All butonunu kullanabilirsiniz.

(Directory'ler default olarak Windows'un Downloads kısmı olarak belirlenmiştir.)

9- Sağ üstteki çarpı butonu ile karşınıza 3 seçenek çıkacaktır. Bunlardan exit direk uygulamayı kapatır, Save&Exit docx ve csv dosyasının directory'lerini kaydederek sonraki kullanımda o directory'den başlayacak şekilde uygulamayı kapatacaktır. Cancel seçeneği uygulamayı kapatmayacaktır.



Bu uygulamanın mevcut sürümü kapsamında “Handle” edilemeyen “Exception”lar aşağıdaki gibidir;

***1-***	4. Bölüm kapsamında BAŞLIK olmayan yerlerde , "Test Girdileri", "Varsayımlar ve Kısıtlamalar", "Ön Koşullar", “Test Adımları” ifadelerinin tek başına yer alması. 

Örn:

4.1	Geçici Kabul Gözle Muayene
4.1.1	|PROJE-0001| – Gözle Kontroller
4.1.1.1	Ön Koşullar
Gözle kontrol edilecek cihazların ilgili sahalarda kurulumlarının tamamlanmış olması gerekmektedir.
4.1.1.2	Test Girdileri
Test Başlangıç Tarihi-Saati:
Test Bitiş Tarihi-Saati:
Yüklenici Temsilcisi:
Müşteri Temsilcisi: 
Detaylı Test Girdileri, Test Adımları’nda verilmiştir.
4.1.1.3	Varsayımlar ve Kısıtlamalar
Ön Koşullar’daki ihtiyaçların karşılanacağı varsayılmaktadır.
Bahse konu test sahada devam eden operasyonu etkilemeyecek şekilde koşulması sağlanacaktır.

Yukarıda;
Varsayımlar ve Kısıtlamalar başlığı altında geçen “Ön Koşullar’daki ….” ifadesi Test Girdileri başlığı altında geçen “Detaylı Test Girdileri, Test Adımları’nda verilmiştir” ifadesi Sorun yaratmazken, aşağıdaki örnekte görülen; 
Herhangi bir başlığın altında yanlışlıkla yanına bir şey yazılmadan bırakılmış “Ön Koşullar”, “Test Adımları” ifadeleri .csv’de sorun yaratacaktır.

4.2	Geçici Kabul Gözle Muayene
4.2.1	|PROJE-0001| – Gözle Kontroller
4.2.1.1	Ön Koşullar
Gözle kontrol edilecek cihazların ilgili sahalarda kurulumlarının tamamlanmış olması gerekmektedir.
4.2.1.2	Test Girdileri
Test Adımları
4.2.1.3	Varsayımlar ve Kısıtlamalar
Ön Koşullar


***2-***	Test Adımları bölümünün 32760 karakterden uzun olması.

Bu durumda .csv belgesinde "Manuel_Test_Step_Import_Needed_Label" sütununun altında "Manuel_Test_Step_Import_Needed_Label_Needed" ifadesi belirecektir.

***3-***	Summary kısmının 255 karakterden uzun olması. 

Bu durumda .csv belgesinde "Summary_Trimmed_Label" sütununun altında "Summary_Trimmed" ifadesi belirecektir.

***4-***	Test açıklamaları başlamadan önce 4. TEST AÇIKLAMALARI başlığı mutlaka olmalıdır.

***5-*** Test aralarında testleri konu başlıklarına bölen kısımların issue key prefix'i ile başlaması

ÖRN : 
4.1 TBGTH Test Adımları (Exception Çıkaracaktır.)
4.1 Test Adımları TBGTH (Exception Çıkarmayacaktır.)

***6-***  Test "test adımları" kısmı dışında tablo bulunması durumunda "OLE_Objects_Needs_Manuel_Processing_Label" sütununun altında "OLE_Objects_Needs_Manuel_Processing_Needed" ifadesi belirecektir. 

(Bu tabloda test adımlarında yer alan sütunların birebir aynı sıra ve yazılarda yer almaması gerekmektedir.)

ÖRN:

| **Adım No** | **Test Adımı** | **Beklenen Test Sonuçları** | **Açıklamalar** | **Sonuç** | **Gereksinim No** |

Şeklinde sütunları olan bir tablonun dökümanın farklı yerlerde geçmesi durumunda tablonun daha sonra Jira'ya visual mod kullanılarak eklenmesi gerekir.

***7-***	Issue key "|" işaretleri arasına alınmalıdır. Aksi takdirde bu testler yeni oluşturulmuş varsayılacaktır.

***8-*** Dökümanın herhangi bir yerinde bir resim kullanılması durumunda resim yok sayılarak parse işlemi devam edecektir. Fakat resimlerin yok sayıldığı bilgisi henüz excel dosyasında belirtilememektedir.