This jar file converts word files to csv files 
Kullanım : 

Çift tıklayarak jar dosyasını çalıştırın. Üst tarafta bulunan browse butonu ile çevirmek istediğiniz word belgesini seçiniz. Daha sonra excel'e girip replace all "\n" , "æ" diyebilmek için "\n" karakterinin hangi karakter ile değiştirmek istediğinizi seçiniz. Test dosyasının Issue Key Prefix'ini yandaki boşluğa girdikten sonra Create .csv butonuna basabilirsiniz. Eğer alttaki browse butonundan oluşturalacak dosyaların file path'ini değiştirmediyseniz, word belgesi ile aynı yere duruma göre bir veya iki adet .csv dosyası oluşturulmuş olacaktır.

Handle edilmemiş exceptionlar:

1- 4. Bölüm içerisinde, "Test Girdileri", "Varsayımlar ve Kısıtlamalar", "Ön Koşullar" ifadelerinin tek başına yer alması. (Metin içerisinde geçebilir.)

2- Dökümanın herhangi bir yerinde "Test Adımları" ifadesinin tek başına yer alması. (Metin içerisinde geçebilir.)

3- Test Adımları bölümünün 32760 karakterden uzun olması. Bu durumda .csv belgesinde "Manuel_Test_Step_Import_Needed_Label" sütununun altında "Manuel_Test_Step_Import_Needed_Label_Needed" ifadesi belirecektir.

4- Summary kısmının 255 karakterden uzun olması. Bu durumda .csv belgesinde "Summary_Trimmed_Label" sütununun altında "Summary_Trimmed" ifadesi belirecektir.

5- Test açıklamaları başlamadan önce TEST AÇIKLAMALARI Sayfası mutlaka olmalıdır.

6- Test aralarında testleri konu başlıklarına bölen kısımlarda issue key prefix'inin geçmesi durumu.

7- Test "test adımları" kısmı dışında tablo bulunması durumunda "OLE_Objects_Needs_Manuel_Processing_Label" sütununun altında "OLE_Objects_Needs_Manuel_Processing_Label_Needed" ifadesi belirecektir. (Bu tabloda test adımlarında yer alan sütunların birebir aynı sıra ve yazılarda yer almaması gerekmektedir.)

8- Issue key prefix'leri "|" işaretleri arasına alınmalıdır. Aksi takdirde bu testler yeni oluşturulmuş varsayılacaktır.
