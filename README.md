# Advanced Programming EShop
**Nama:**   &nbsp; Stefanus Tan Jaya<br>
**NPM:**    &nbsp;&ensp; 2306152456<br>
**Kelas:**  &nbsp; Pemrograman Lanjut A<br>

### Module Reflection
- [Modul 1](#modul-1)

## Modul 1
### Reflection 1.1
Dalam mengerjakan modul ini, saya mempelajari ilmu baru mengenai implementasi *clean coding* dan *secure coding*. Dari *progress* yang sudah saya kerjakan (terhitung saat saya menulis refleksi ini), saya telah menerapkan prinsip *clean coding*, seperti  nama variabel dan *function* yang jelas dan deskriptif serta mengurangi komentar yang tidak perlu. Saya mencoba menggambarkan apa yang dikerjakan suatu *function* tanpa perlu menjelaskannya kembali dalam komentar. Prinsip lain yang sudah saya terapkan adalah memastikan satu *function* mengerjakan satu fungsi, seperti membuat fungsi masing-masing untuk membuat, mengedit, dan menghapus produk.

Saya sadar bahwa program EShop ini masih memiliki kekurangan. Dari segi *clean code*, saya belum menerapkan *error handling* secara menyeluruh. Dari segi *secure coding*, program masih belum dapat memvalidasi *input* dengan baik, contohnya ketika memasukkan bilangan *non-integer* pada kolom Quantity. Selain itu, bagian autentikasi dan autorisasi untuk *user* juga belum terimplementasi. Akibatnya, produk yang terbuat masih belum terikat dengan *user* pembuatnya dan Eshop masih rawan terhadap serangan siber. Fitur-fitur tersebut akan menjadi rencana pengembangan Eshop selanjutnya.

### Reflection 1.2
Sebelumnya, saya mohon izin *update* perkembangan EShop dari rencana sebelumnya. Saya sudah mengimplementasikan validasi *input* pada kolom Name dan Quantity. Hasilnya, kedua kolom tidak menerima nilai Null dan kolom Quantity wajib dalam *integer*. Selain itu, saya juga *encode* semua *output* yang memungkinkan dengan bawaan `Thymeleaf`. Tujuan *update* untuk menanggulangi serangan, seperti SQL Injection dan XSS. 

1. * Setelah menulis *unit test*, saya merasa terbantu karena dengan *unit test*, saya bisa mengecek jika kode saya masih meninggalkan *bug* saat dijalankan. Menurut saya, hal ini berguna untuk memastikan bahwa program yang akan di-*push* atau *deploy* bisa berfungsi dengan baik.
   * Menurut saya, tidak ada batasan dalam membuat *unit test*, namun sebaiknya dibuat secukupnya saja. *Unit test* difokuskan untuk menguji suatu *class*, baik positif maupun negatif serta *edge case* bila diperlukan.
   * Untuk mengetahui apakah semua *unit test* yang dibuat sudah cukup menjamin kode kita, mempelajari *code coverage* bisa menjadi solusi. *Code coverage* menyatakan persentase seberapa banyak baris kode yang dieksekusi saat menjalankan *unit test*. Namun, tingkat *code coverage* 100% tidak menjamin bahwa kode bebas dari *bug*. Bisa saja ada kemungkinan *edge case* yang terlewat. "All input is evil, until proven otherwise." ― Michael Howard & David LeBlanc, Writing Secure Code.
2. Menurut saya, jika diminta membuat *file* baru dengan *class* untuk melakukan tes pada jumlah *item* di dalam Product List dengan *setup* dan *instance* variabel yang sama dengan `CreateProductFunctionalTest.java`, justru akan melanggar prinsip *clean coding*. Kasus ini ada dikarenakan sebenarnya kita mengulang struktur kode *test* yang sama, hanya fungsinya yang berbeda. Sebaiknya, tes jumlah *item* ditambah ke *file* yang sama, yaitu `CreateProductFunctionalTest.java` untuk menjaga prinsip *clean code*.