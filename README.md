**Tanıtım:**

Bu uygulamanın iki temel fonksiyonu vardır.

1. MS Word ortamında &quot;HVL-TEMP-TPr&quot; şablonunun 4. Bölümü altında verilen TEST&#39;leri JİRA&#39;da güncelleme/yaratma işlemlerini yapabilecek .csv dosyalarını oluşturmak.
2. MS Excel&#39;de sırası ile (içleri BOŞ da olsa) aşağıdaki sütunlardan oluşan Test Prosedürlerini Jira&#39;ya import edebilecek .csv dosyalarını oluşturmaktır.
  1. Test Durumu No
  2. Test Durumu Adı
  3. Adım No
  4. Test Adımı
  5. Beklenen Test Sonuçları
  6. Açıklamalar
  7. Sonuç(G/K/UD)
  8. Gereksinim No
  9. Ön Koşul
  10. Test Girdileri
  11. Varsayımlar ve Kısıtlamalar

**ÖNEMLİ:**

1. Word &quot;parse&quot; işlemi sonucunda iki .csv oluşmaktadır. (abc.docx dosyası ile işlem yapıldığı varsayıldığında)
  1. abc\_TBU.csv dosyası:

Daha önce Jira&#39;da bulunan &quot;TEST&quot;lerin Word ortamında güncellendikleri varsayımıı ile oluşturulmaktadır. ADMİN yetkileri ile JİRA&#39;da (U)pdate yapılabilmektedir.

  1. abc\_TBC.csv dosyası:

&quot;TEST&quot;lerin daha önce JİRA&#39;da olmayan, Word dosyası üzerindeki çalışmalar sırasında oluşturulan &quot;TEST&quot;ler oldukları varsayılmaktadır. Bunlar Normal kullanıcı yetkileri ile JİRA&#39;da (C)reate edilebilmektedir.

1. Excel &quot;parse&quot; işlemi sonucunda iki .csv oluşmaktadır. (abc.xlsx dosyası ile işlem yapıldığı varsayıldığında)
  1. abc\_Tests\_TBC.csv dosyası:

&quot;Create&quot; edilecek TEST&#39;leri içeren .csv dosyasıdır. Bunlar Normal kullanıcı yetkileri ile JİRA&#39;da (C)reate edilebilmektedir.

  1. abc\_TBC\_Test\_Sets.csv dosyası:

&quot;Create&quot; edilecek TEST SET&#39;leri içeren .csv dosyasıdır. Bunlar Normal kullanıcı yetkileri ile JİRA&#39;da (C)reate edilebilmektedir.

**Kullanım**

1. Çift tıklayarak jar dosyasını çalıştırınız.
2. &quot;Select TPr to be converted] için [browse] butonuna tıklayarak, csv dosyasına çevirmek istediğiniz Excel veya Word dosyasını seçiniz. (Dosyaların sahip olması gereken içerik ve formatlar yukarıda tanımlanmıştır)
3. Excel Dosyası seçtiyseniz;
  1. &quot;Replace &quot;paragraph&quot; character (\n) with&quot; yanındaki seçeneklerden, dokümanın içerisinde geçen &quot;\n&quot; karakterini değiştirmek istediğiniz karakteri seçiniz.

Not: Bu seçimi yaparken dokümanın içinde zaten olması gereken bir karakteri tercih etmeyiniz. Jira&#39;ya import sonrasında \n yerine kullanılanlar ile normalde olması gerekenleri ayırt etmeniz zorlaşabilir.

  1. Dil Seçimi:
    1. English ise çeşitli adımlar için yazılmış bilgiler Jira&#39;ya For\_Step-XXXX:….. şeklinde aktarılır.
    2. Türkçe ise bu bilgiler XXXX numaralı Test Adımı için; …….. şeklinde aktarılır.
1. Word dosyası seçtiyseniz;
  1. &quot;Replace &quot;paragraph&quot; character (\n) with&quot; yanındaki seçeneklerden, dokümanın içerisinde geçen &quot;\n&quot; karakterini değiştirmek istediğiniz karakteri seçiniz.

Not: Bu seçimi yaparken dokümanın içinde zaten olması gereken bir karakteri tercih etmeyiniz. Jira&#39;ya import sonrasında \n yerine kullanılanlar ile normalde olması gerekenleri ayırt etmeniz zorlaşabilir.

  1. &quot;Please Set Issue Key Prefix&quot;&#39;den, Jira&#39;daki Issue Key&#39;lerin prefix&#39;ini giriniz. (Örn: TBGTH,KRBN)
  2. Dil Seçimi:
    1. English ise; HVL-TEMP-TPr&#39;nin İngilizce başlıklarına göre parse işlemi yapılır.
    2. Türkçe ise; HVL-TEMP-TPr&#39;nin Türkçe başlıklarına göre parse işlemi yapılır.
1. .csv dosyasının oluşturulacağı klasörü seçiniz.

Not: Herhangi bir seçim yapılmaz ise excel belgesinin olduğu konuma oluşturulacaktır.

1. Create .csv butonu ile csv dönüştürme işlemini başlatabilirsiniz.

**Arayüzdeki Diğer Hususlar**

1. Uygulamanın bir sonraki açılışında &quot;browse&quot; butonları ile yaptığını seçimlere göre açılmasını isterseniz &quot;close&quot; butonu ile çıkış yapınız.
2. &quot;browse&quot; ile yaptığını seçimlerin &quot;default&quot; konumlara dönmesi için &quot;Clear All&quot; butonunu kullanabilirsiniz.

Not: &quot;Default&quot; &quot;directory&quot;ler, &quot;Windows&quot;un ilgili kullanıcıya ait &quot;Downloads&quot; alanlarıdır.

1. Sağ üstteki çarpı butonu ile karşınıza 3 seçenek çıkacaktır.
  1. Exit: direk uygulamayı kapatır,
  2. Save&amp;Exit docx ve csv dosyasının directory&#39;lerini kaydederek sonraki kullanımda o directory&#39;den başlayacak şekilde uygulamayı kapatacaktır.
  3. Cancel seçeneği uygulamayı kapatmayacaktır.

Bu uygulamanın mevcut sürümü kapsamında &quot;Handle&quot; edilemeyen &quot;Exception&quot;lar aşağıdaki gibidir;

1. Issue key &quot;|&quot; işaretleri arasına olmalıdır. Aksi takdirde bu testler yeni oluşturulmuş varsayılacaktır.
2. Uygulama Word dosyasındaki halihazırda kullanılan bütün &quot;;&quot; işaretlerini &quot;:&quot; ile &quot;replace&quot; etmektedir, çünkü &quot;delimiter&quot; olarak &quot;;&quot; kullanılması gerekmektedir.
3. 4. Bölüm kapsamında BAŞLIK olmayan yerlerde , &quot;Test Girdileri&quot;, &quot;Varsayımlar ve Kısıtlamalar&quot;, &quot;Ön Koşullar&quot;, &quot;Test Adımları&quot; ifadelerinin tek başına yer alması.

Örn:

4.1 Geçici Kabul Gözle Muayene 4.1.1 |PROJE-0001| – Gözle Kontroller 4.1.1.1 Ön Koşullar Gözle kontrol edilecek cihazların ilgili sahalarda kurulumlarının tamamlanmış olması gerekmektedir. 4.1.1.2 Test Girdileri Test Başlangıç Tarihi-Saati: Test Bitiş Tarihi-Saati: Yüklenici Temsilcisi: Müşteri Temsilcisi: Detaylı Test Girdileri, Test Adımları&#39;nda verilmiştir. 4.1.1.3 Varsayımlar ve Kısıtlamalar Ön Koşullar&#39;daki ihtiyaçların karşılanacağı varsayılmaktadır. Bahse konu test sahada devam eden operasyonu etkilemeyecek şekilde koşulması sağlanacaktır.

Yukarıda; Varsayımlar ve Kısıtlamalar başlığı altında geçen &quot;Ön Koşullar&#39;daki ….&quot; ifadesi Test Girdileri başlığı altında geçen &quot;Detaylı Test Girdileri, Test Adımları&#39;nda verilmiştir&quot; ifadesi Sorun yaratmazken, aşağıdaki örnekte görülen; Herhangi bir başlığın altında yanlışlıkla yanına bir şey yazılmadan bırakılmış &quot;Ön Koşullar&quot;, &quot;Test Adımları&quot; ifadeleri .csv&#39;de sorun yaratacaktır.

4.2 Geçici Kabul Gözle Muayene 4.2.1 |PROJE-0001| – Gözle Kontroller 4.2.1.1 Ön Koşullar Gözle kontrol edilecek cihazların ilgili sahalarda kurulumlarının tamamlanmış olması gerekmektedir. 4.2.1.2 Test Girdileri Test Adımları 4.2.1.3 Varsayımlar ve Kısıtlamalar Ön Koşullar

1. Test Adımları bölümünün 32760 karakterden uzun olması.

Bu durumda .csv belgesinde &quot;Manuel\_Test\_Step\_Import\_Needed\_Label&quot; sütununun altında &quot;Manuel\_Test\_Step\_Import\_Needed\_Label\_Needed&quot; ifadesi belirecektir.

1. Summary kısmının 255 karakterden uzun olması.

Bu durumda .csv belgesinde &quot;Summary\_Trimmed\_Label&quot; sütununun altında &quot;Summary\_Trimmed&quot; ifadesi belirecektir.

1. Test açıklamaları başlamadan önce 4. TEST AÇIKLAMALARI başlığı mutlaka olmalıdır.
2. Test aralarında testleri konu başlıklarına bölen kısımların issue key prefix&#39;i ile başlaması

ÖRN : 4.1 TBGTH Test Adımları (Exception Çıkaracaktır.) 4.1 Test Adımları TBGTH (Exception Çıkarmayacaktır.)

1. Test &quot;test adımları&quot; kısmı dışında tablo bulunması durumunda &quot;OLE\_Objects\_Needs\_Manuel\_Processing\_Label&quot; sütununun altında &quot;OLE\_Objects\_Needs\_Manuel\_Processing\_Needed&quot; ifadesi belirecektir.

(Bu tabloda test adımlarında yer alan sütunların birebir aynı sıra ve yazılarda yer almaması gerekmektedir.)

ÖRN:

|  **Adım No**  |  **Test Adımı**  |  **Beklenen Test Sonuçları**  |  **Açıklamalar**  |  **Sonuç**  |  **Gereksinim No**  |

Şeklinde sütunları olan bir tablonun dokümanın farklı yerlerde geçmesi durumunda tablonun WORD&#39;den silinmesi ve daha sonra Jira&#39;ya visual mod kullanılarak eklenmesi gerekir.

1. Dokümanın herhangi bir yerinde bir resim kullanılması durumunda resim yok sayılarak parse işlemi devam edecektir. Fakat resimlerin yok sayıldığı bilgisi henüz excel dosyasında belirtilememektedir.

10- Update edilecek testlerde ( |issue prefix-xxxx| - summary ), yeni oluşturulan testlerde ( Issue prefix - summary ) şeklinde girmek gereklidir.