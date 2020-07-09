This jar file converts word files to csv filesBu uygulama HVL-TEMP-TPr şablonunun 4. Bölümü altında verilen TEST&#39;leri JİRA&#39;da güncelleme/yaratma işlemlerini yapabilecek .csv dosyalarını oluşturmak üzere geliştirilmiştir.

Bu uygulamanın mevcut sürümü kapsamında &quot;Handle&quot; edilemeyen &quot;Exception&quot;lar aşağıdaki gibidir;

Handle edilmemiş exceptionlar:

1. 1- 4. Bölüm kapsamında BAŞLIK olmayan yerlerde içerisinde, &quot;Test Girdileri&quot;, &quot;Varsayımlar ve Kısıtlamalar&quot;, &quot;Ön Koşullar&quot;, &quot;Test Adımları&quot; ifadelerinin tek başına yer alması. (Metin içerisinde geçebilir.)

**Örn:**

  1.
## _Geçici Kabul Gözle Muayene_

    1.
### _|PROJE-0001| – Gözle Kontroller_

      1.
#### **Ön Koşullar**

_Gözle kontrol edilecek cihazların ilgili sahalarda kurulumlarının tamamlanmış olması gerekmektedir._

      1.
#### **Test Girdileri**

_Test Başlangıç Tarihi-Saati:
 Test Bitiş Tarihi-Saati:
 Yüklenici Temsilcisi:
 Müşteri Temsilcisi:_

_Detaylı_ _Test Girdileri, Test Adımları__&#39;nda verilmiştir._

      1.
#### **Varsayımlar ve Kısıtlamalar**

_Ön Koşullar__&#39;daki ihtiyaçların karşılanacağı varsayılmaktadır._

_Bahse konu test sahada devam eden operasyonu etkilemeyecek şekilde koşulması sağlanacaktır._

Yukarıda;

Varsayımlar ve Kısıtlamalar başlığı altında geçen &quot;Ön Koşullar&#39;daki ….&quot; ifadesi

Test Girdileri başlığı altında geçen &quot;Detaylı Test Girdileri, Test Adımları&#39;nda verilmiştir&quot; ifadesi

Sorun yaratmazken, aşağıdaki örnekte görülen;

Herhangi bir başlığın altında yanlışlıkla yanına bir şey yazılmadan bırakılmış &quot;Ön Koşullar&quot;, &quot;Test Adımları&quot; ifadeleri .csv&#39;de sorun yaratacaktır.

  1.
## _Geçici Kabul Gözle Muayene_

    1.
### _|PROJE-0001| – Gözle Kontroller_

      1.
#### **Ön Koşullar**

_Gözle kontrol edilecek cihazların ilgili sahalarda kurulumlarının tamamlanmış olması gerekmektedir._

      1.
#### **Test Girdileri**

_Test Adımları_

      1.
#### **Varsayımlar ve Kısıtlamalar**

_Ön Koşullar_

1. 2- Dökümanın herhangi bir yerinde &quot;Test Adımları&quot; ifadesinin tek başına yer alması. (Metin içerisinde geçebilir.)
2. 3- Test Adımları bölümünün 32760 karakterden uzun olması.

Bu durumda .csv belgesinde &quot;Manuel\_Test\_Step\_Import\_Needed\_Label&quot; sütununun altında &quot;Manuel\_Test\_Step\_Import\_Needed\_Label\_Needed&quot; ifadesi belirecektir.

1. 4- Summary kısmının 255 karakterden uzun olması.

Bu durumda .csv belgesinde &quot;Summary\_Trimmed\_Label&quot; sütununun altında &quot;Summary\_Trimmed&quot; ifadesi belirecektir.

1. 5- Test açıklamaları başlamadan önce 4. TEST AÇIKLAMALARI Sayfası başlığı mutlaka olmalıdır.
2.
3. 6- Test aralarında testleri konu başlıklarına bölen kısımlarda issue key&#39;in prefix&#39;inin geçmesi durumu.

1. 7- Test &quot;test adımları&quot; kısmı dışında tablo bulunması durumunda &quot;OLE\_Objects\_Needs\_Manuel\_Processing\_Label&quot; sütununun altında &quot;OLE\_Objects\_Needs\_Manuel\_Processing\_Label\_Needed&quot; ifadesi belirecektir.

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

Verileri girili yapılır | …. Sonucu alınır. |

 |

 | SSS-55 |
| 3 | … işlemi yapılır | …. Sonucu alınır. |

 |

 | SSS-252 |

Şeklindeki bir TEST için

Tablo hariç bilgiler .csv&#39;ye aktarılmaktadır.

Tablo&#39;nun daha sonra Jira&#39;ya visual mod kullanılarak eklenmesi gerekir

Fakat yapılan bir hata yüzünden beklenmedik bir yerdeki &quot;test adımları&quot; tablosu .csv&#39;yi bozacaktır.

| **Adım No** | **Test Adımı** | **Beklenen Test Sonuçları** | **Açıklamalar** | **Sonuç** | **Gereksinim No** |
| --- | --- | --- | --- | --- | --- |
| **G/K/UD \*** |
| 1 | … işlemi yapılır | …. Sonucu alınır. |
 |
 | SSS-54 |
| --- | --- | --- | --- | --- | --- |
| 2 |

| **Adım No** | **Test Adımı** | **Beklenen Test Sonuçları** | **Açıklamalar** | **Sonuç** | **Gereksinim No** |
| --- | --- | --- | --- | --- | --- |
| **G/K/UD \*** |
| 1 | … işlemi yapılır | …. Sonucu alınır. |
 |
 | SSS-54 |

Verileri girili yapılır | …. Sonucu alınır. |

 |

 | SSS-55 |
| 3 | … işlemi yapılır | …. Sonucu alınır. |

 |

 | SSS-252 |

1. 8- Issue key prefix&#39;leri &quot;|&quot; işaretleri arasına alınmalıdır. Aksi takdirde bu testler yeni oluşturulmuş varsayılacaktır.