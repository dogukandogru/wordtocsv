Bu uygulama HVL-TEMP-TPr şablonunun 4. Bölümü altında verilen TEST’leri JİRA’da güncelleme/yaratma işlemlerini yapabilecek .csv dosyalarını oluşturmak üzere geliştirilmiştir.
Bu uygulamanın mevcut sürümü kapsamında “Handle” edilemeyen “Exception”lar aşağıdaki gibidir;

1- 1-	4. Bölüm kapsamında BAŞLIK olmayan yerlerde , "Test Girdileri", "Varsayımlar ve Kısıtlamalar", "Ön Koşullar", “Test Adımları” ifadelerinin tek başına yer alması. 

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


2-	Test Adımları bölümünün 32760 karakterden uzun olması.

Bu durumda .csv belgesinde "Manuel_Test_Step_Import_Needed_Label" sütununun altında "Manuel_Test_Step_Import_Needed_Label_Needed" ifadesi belirecektir.

3-	Summary kısmının 255 karakterden uzun olması. 

Bu durumda .csv belgesinde "Summary_Trimmed_Label" sütununun altında "Summary_Trimmed" ifadesi belirecektir.

4-	Test açıklamaları başlamadan önce 4. TEST AÇIKLAMALARI başlığı mutlaka olmalıdır.

6-  Test "test adımları" kısmı dışında tablo bulunması durumunda "OLE_Objects_Needs_Manuel_Processing_Label" sütununun altında "OLE_Objects_Needs_Manuel_Processing_Needed" ifadesi belirecektir. 

(Bu tabloda test adımlarında yer alan sütunların birebir aynı sıra ve yazılarda yer almaması gerekmektedir.)

ÖRN:

| **Adım No** | **Test Adımı** | **Beklenen Test Sonuçları** | **Açıklamalar** | **Sonuç** | **Gereksinim No** |
| --- | --- | --- | --- | --- | --- |
| **G/K/UD \*** |
| 1 | … işlemi yapılır | …. Sonucu alınır. |
|
| SSS-54 |
| --- | --- | --- | --- | --- | --- |
| 2 |
| A | B | C |
| --- | --- | --- |
|
|
|
|
|
|
|
|
