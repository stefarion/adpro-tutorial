# Advanced Programming EShop
**Nama:**   &nbsp; Stefanus Tan Jaya<br>
**NPM:**    &nbsp;&ensp; 2306152456<br>
**Kelas:**  &nbsp; Pemrograman Lanjut A<br>

### Deployment Link
Check out the website here: [stefarion-adpro-tutorial-eshop.koyeb.app](https://stefarion-adpro-tutorial-eshop.koyeb.app)

### Module Reflection
- [Modul 1](#modul-1)
- [Modul 2](#modul-2)
- [Modul 3](#modul-3)

## Modul 1
### Reflection 1.1
Dalam mengerjakan modul ini, saya mempelajari ilmu baru mengenai implementasi *clean coding* dan *secure coding*. Dari *progress* yang sudah saya kerjakan (terhitung saat saya menulis refleksi ini), saya telah menerapkan prinsip *clean coding*, seperti  nama variabel dan *function* yang jelas dan deskriptif serta mengurangi komentar yang tidak perlu. Saya mencoba menggambarkan apa yang dikerjakan suatu *function* tanpa perlu menjelaskannya kembali dalam komentar. Prinsip lain yang sudah saya terapkan adalah memastikan satu *function* mengerjakan satu fungsi, seperti membuat fungsi masing-masing untuk membuat, mengedit, dan menghapus produk.

Saya sadar bahwa program EShop ini masih memiliki kekurangan. Dari segi *clean code*, saya belum menerapkan *error handling* secara menyeluruh. Dari segi *secure coding*, program masih belum dapat memvalidasi *input* dengan baik, contohnya ketika memasukkan bilangan *non-integer* pada kolom Quantity. Selain itu, bagian autentikasi dan autorisasi untuk *user* juga belum terimplementasi. Akibatnya, produk yang terbuat masih belum terikat dengan *user* pembuatnya dan Eshop masih rawan terhadap serangan siber. Fitur-fitur tersebut akan menjadi rencana pengembangan Eshop selanjutnya.

### Reflection 1.2
Sebelumnya, saya mohon izin *update* perkembangan EShop dari rencana sebelumnya. Saya sudah mengimplementasikan validasi *input* pada kolom Name dan Quantity. Hasilnya, kedua kolom tidak menerima nilai Null dan kolom Quantity wajib dalam *integer*. Selain itu, saya juga *encode* semua *output* yang memungkinkan dengan bawaan `Thymeleaf`. Tujuan *update* untuk menanggulangi serangan, seperti SQL Injection dan XSS. 

1. * Setelah menulis *unit test*, saya merasa terbantu karena dengan *unit test*, saya bisa mengecek jika kode saya masih meninggalkan *bug* saat dijalankan. Menurut saya, hal ini berguna untuk memastikan bahwa program yang akan di-*push* atau *deploy* bisa berfungsi dengan baik.
   * Menurut saya, tidak ada batasan dalam membuat *unit test*, namun sebaiknya dibuat secukupnya saja. *Unit test* difokuskan untuk menguji suatu *class*, baik positif maupun negatif serta *edge case* bila diperlukan.
   * Untuk mengetahui apakah semua *unit test* yang dibuat sudah cukup menjamin kode kita, mempelajari *code coverage* bisa menjadi solusi. *Code coverage* menyatakan persentase seberapa banyak baris kode yang dieksekusi saat menjalankan *unit test*. Namun, tingkat *code coverage* 100% tidak menjamin bahwa kode bebas dari *bug*. Bisa saja ada kemungkinan *edge case* yang terlewat. "All input is evil, until proven otherwise." ― Michael Howard & David LeBlanc, Writing Secure Code.
   <br><br>
2. Menurut saya, jika diminta membuat *file* baru dengan *class* untuk melakukan tes pada jumlah *item* di dalam Product List dengan *setup* dan *instance* variabel yang sama dengan `CreateProductFunctionalTest.java`, justru akan melanggar prinsip *clean coding*. Kasus ini ada dikarenakan sebenarnya kita mengulang struktur kode *test* yang sama, hanya fungsinya yang berbeda. Sebaiknya, tes jumlah *item* ditambah ke *file* yang sama, yaitu `CreateProductFunctionalTest.java` untuk menjaga prinsip *clean code*.

## Modul 2
1. Pada proses pengerjaan *exercise* modul 2 ini, saya mendapati beberapa *code quality issues*. Salah satunya adalah *return* pada Controller yang keliru terhadap nama tujuan *file* HTML. Contohnya pada `productListPage` yang sebelumnya return productList diganti menjadi ProductList disesuaikan dengan nama *file* HTML-nya. Selain itu, saya memberi komentar pada *method* `setUp()` di `ProductRepositoryTest` yang kosong karena belum terpakai untuk sementara.
   <br><br>
   Dalam *exercise* ini juga, saya menambahkan beberapa *unit tests* baru untuk mencapai 100% *code coverage*. Bagian utama yang diliputi adalah *unit tests* untuk `ProductService`, `ProductController`, dan `HomePageController` karena bagian tersebut tidak di-*cover* pada modul sebelumnya.
   <br><br>
2. Menurut saya, kode dalam program EShop ini sudah mengimplementasikan *workflow* CI/CD sesuai dengan definisinya. Kode saya akan melakukan Continuous Integration secara otomatis dengan GitHub Actions setelah *push*. Kode dianalisis juga dengan Scorecard dan SonarCloud. Pada bagian Continuous Deployment, saya menggunakan Koyeb sebagai PaaS. Koyeb sudah dihubungkan dengan repositori ini, sehingga setiap perubahan yang di-*push* akan dicatat dan Koyeb secara otomatis melakukan *deployment*.

## Modul 3
1) **Explain what principles you apply to your project!**
   * S (Single Responsibility Principle)<br>
   Pada awal pembuatan program ini, *class* `HomePage()` masih terletak pada `ProductController`. Hal ini melanggar SRP karena `HomePage()` memiliki tanggung jawab sendiri untuk menangani halaman *home*, bukan produk. Jadi, dibuat *file class* baru `HomePageController` khusus untuk *handle* *homepage*. Selain itu, sebelumnya terdapat *class* `CarController` yang merupakan *extends* dari `ProductController`. Seharusnya `CarController` dipisahkan karena memiliki tanggung jawab sendiri untuk menangani halaman *car* yang berbeda fungsionalitasnya dengan halaman *product*.<br><br>
   * O (Open/Closed Principle)<br>
   Saya membuat `RepositoryInterface` dan mengimplementasikannya dengan `ProductRepositroy` dan `CarRepository`. Tujuannya untuk memungkinkan saya membuat repositori baru pada *update* mendatang dengan kembali mengimplementasi *interface* repository tersebut tanpa perlu mengubah kode yang sudah ada sebelumnya.<br><br>
   * L (Liskov Substitution Principle)<br>
   Sebelumnya, `CarController` diposisikan sebagai *subclass* dari `ProductController`. Namun, kedua *controller* tersebut menjalankan fungsi yang berbeda. `CarController` juga tidak mampu menggantikan `ProductController`, sehingga keduanya harus dipisah dengan saya membuat *file class* `CarController`.<br><br>
   * I (Interface Segregation Principle)<br>
   *Interface* `CarService` dan `ProductService` hanya berisi *method class* yang memang menjalankan tugas tertentu  pada EShop ini. Maka, tiap *interface* memiliki tanggung jawab masing-masing tanpa ada pemaksaan untuk menggunakan semua *method class* yang dibuat. Perubahan selanjutnya yang saya lakukan adalah memisahkan `RepositoryInterface` menjadi `RepositoryRead` dan `RepositoryWrite` sesuai fungsinya masing-masing. Perubahan ini membantu saya dalam membuat repositori baru dengan hanya mengimplementasi salah satu fungsi tersebut tanpa perlu mengimplementasi fungsi yang tidak diperlukan.<br><br>
   * D (Dependency Inversions Principle)<br>
   Dalam `CarController`, *line* `private CarServiceImpl carService` diubah menjadi `private CarService carService`. Hal ini untuk menerapkan prinsip DIP di mana suatu modul harus bergantung pada *interface* atau *class* yang bersifat abstrak daripada bergantung pada *class* yang *concrete*. Selain itu, perubahan pemanggilan `ProductRepository` di `ProductServiceImpl` dan `CarRepository` di `CarServiceImpl` bertujuan untuk menghindari inisialisasi manual, yang juga bertentangan dengan DIP.<br><br>
2) **Explain the advantages of applying SOLID principles to your project with examples.**
   * Karena pada model `Product` berisi atribut-atribut dari *product* dan *instance* dari *product* diimplementasikan di `ProductRepository`, kode saya menjadi lebih mudah untuk di-*maintain*. *Unit test* telah dibuat tertuju `ProductRepository` untuk menguji logika *method* *instance*-nya dan tertuju model `Product` dengan menguji atributnya. Pemisahan ini juga membantu dalam membaca *bug*.
   * Implementasi `RepositoryInterface` dan pemisahan fungsinya antara *read* dan *write* membantu saya ketika menambah repositori baru tanpa harus memodifikasi kode sebelumnya. Saya juga dapat mengimplementasi salah satu fungsi dari *write* atau *read*
   * Kode saya menjadi lebih mudah untuk dibaca dan dipahami karena pemisahan *method*-*method* atas tugasnya masing-masing. Bisa dilihat pada bagian *controller* ada yang khusus menangani *homepage*, `Product`, dan `Car`.<br><br>
3) **Explain the disadvantages of not applying SOLID principles to your project with examples.**
   * Kode saya akan lebih sulit untuk di-*maintain*. Sebagai contoh, bila model `Product` dan `ProductRepository` digabung, model harus ikut diuji untuk logika *instance* dan *repository* harus ikut diuji untuk atribut produk. Hal ini akan mengakibatkan kode saya menjadi lebih kompleks dan meningkatkan potensi *bug* dan *error*.
   * Jika `RepositoryInterface` tidak diimplementasikan, akan menyulitkan pengembangan dan ekstensi *codebase*. Saya akan terpaksa memodifikasi kode yang sudah ada dan ada kemungkinan implementasi *method*-*method* yang tidak diperlukan jika saya membuat repositori baru. 
   * Jika modul `CarController` langsung bergantung pada *class* yang *concrete* seperti `CarServiceImpl`, saya bisa menemui kesulitan untuk mengembangkan *codebase* saya karena terjadi *tight coupling* antara *controller* dan implementasi.  