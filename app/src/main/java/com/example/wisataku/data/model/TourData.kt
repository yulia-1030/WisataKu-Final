package com.example.wisataku.data.model

import com.example.wisataku.R

object TourData {
    private val tourName = arrayOf(
        "Kampung Korea Bandung",
        "Selasar Soenaryo Art Space",
        "Wisata Batu Kuda",
        "Ciwangun Indah Camp Official",
        "Curug Batu Templek",
        "Teras Cikapundung BBWS",
        "Bukit Moko",
        "Gunung Api Nglanggeran",
        "Nol Kilometer Jl.Malioboro",
        "Pantai Parangtritis",
        "Candi Prambanan",
        "Gembira Loka Zoo"
    )

    private val tourDescription = arrayOf(
        "Kampung Korea Bandung adalah suatu kawasan yang mengusung tema dan gaya arsitektur Korea yang unik di tengah kota Bandung, Jawa Barat. Tempat ini menawarkan pengalaman serba Korea dengan beragam fasilitas dan dekorasi yang terinspirasi dari budaya Korea.",
        "Selasar Soenaryo Art Space adalah galeri seni yang terletak di kota Bandung, Jawa Barat. Tempat ini didirikan oleh Soenaryo, seorang seniman dan kolektor seni Indonesia. Galeri seni ini menjadi wadah untuk memamerkan karya seni kontemporer dari seniman-seniman lokal maupun internasional.",
        "Wisata Batu Kuda merupakan destinasi wisata alam yang menarik di daerah Ciwidey, Bandung Selatan, Jawa Barat. Tempat ini terkenal dengan karakteristik batu besar yang menyerupai kepala kuda, menjadi daya tarik utama bagi pengunjung.",
        "Ciwangun Indah Camp Official adalah tempat wisata yang menawarkan pengalaman berkemah dan liburan alam di tengah keindahan alam Ciwidey, Bandung Selatan, Jawa Barat. Terletak di kawasan yang sejuk dengan udara segar pegunungan, Ciwangun Indah Camp Official menjadi destinasi favorit bagi para pengunjung yang mencari ketenangan dan kegiatan outdoor.",
        "Curug Batu Templek adalah sebuah air terjun yang menakjubkan terletak di kawasan Taman Nasional Gunung Gede Pangrango, Jawa Barat. Tersembunyi di antara pepohonan hijau yang rimbun, curug ini menawarkan pesona alam yang memukau dan udara segar yang menyegarkan.",
        "Teras Cikapundung BBWS (Balai Besar Wilayah Sungai) merupakan salah satu area yang diatur dan dikelola oleh BBWS Citarum-Cimanuk-Cisanggarung. Berlokasi di sepanjang Sungai Cikapundung, teras ini memiliki fungsi penting dalam pengelolaan dan pelestarian sungai serta sebagai pusat edukasi lingkungan",
        "Bukit Moko terletak di ketinggian sekitar 1.500 meter di atas permukaan laut, memberikan pengunjung pemandangan spektakuler Kota Bandung dan pegunungan sekitarnya. Tempat ini dikenal sebagai salah satu titik tertinggi yang dapat diakses dengan mudah di Bandung, sehingga menawarkan panorama yang menakjubkan dan udara sejuk.",
        "Gunung Nglanggeran adalah sebuah gunung di Daerah Istimewa Yogyakarta, Indonesia. Gunung ini merupakan suatu gunung api purba yang terbentuk sekitar 0,6-70 juta tahun yang lalu atau yang memiliki umur tersier (Oligo-Miosen). Gunung Nglanggeran memiliki batuan yang sangat khas karena didominasi oleh aglomerat dan breksi gunung api. Gunung ini terletak di Desa Nglanggeran, Kecamatan Patuk, Kabupaten Gunung Kidul yang berada pada deretan Pegunungan Baturagung.",
        "Nol Kilometer Jalan Malioboro adalah titik awal atau pusat kota Yogyakarta yang terletak di kawasan Malioboro, salah satu destinasi wisata utama di Yogyakarta, Jawa Tengah. Titik ini memiliki nilai simbolis sebagai pusat atau kilometer nol yang menandai titik pusat sekaligus awal perhitungan jarak ke berbagai destinasi di sekitar Yogyakarta.",
        "Pantai Parangtritis adalah destinasi pantai yang terkenal di Yogyakarta, Indonesia. Terletak sekitar 27 kilometer selatan kota Yogyakarta, pantai ini menawarkan pesona alam yang menakjubkan dengan garis pantai yang panjang, pasir putih yang lembut, dan ombak Samudera Hindia yang memukau.",
        "Candi Prambanan adalah kompleks candi Hindu yang megah dan sangat terkenal, terletak sekitar 17 kilometer timur laut Yogyakarta, Jawa Tengah, Indonesia. Candi ini dianggap sebagai salah satu warisan budaya terbesar di Indonesia dan merupakan situs yang diakui oleh UNESCO.",
        "Gembira Loka Zoo adalah kebun binatang yang terletak di Yogyakarta, Jawa Tengah, Indonesia. Didirikan pada tahun 1956, kebun binatang ini merupakan salah satu destinasi wisata populer yang menawarkan pengalaman mendalam dalam memahami dan mengamati keanekaragaman satwa."

    )

    private val tourPhoto = intArrayOf(
        R.drawable.tour_bandung_kampung_korea,
        R.drawable.tour_bandung_selasar_soenaryo,
        R.drawable.tour_bandung_wisata_batu_kuda,
        R.drawable.tour_bandung_siwangun_indah_camp,
        R.drawable.tour_bandung_curug_batu_templek,
        R.drawable.tour_bandung_teras_cikapundang,
        R.drawable.tour_bandung_bukit_moko,
        R.drawable.tour_jogja_gunung_nglanggeran,
        R.drawable.tour_jogja_nol_kilometer_malioboro,
        R.drawable.tour_jogja_pantai_parangtritis,
        R.drawable.tour_jogja_candi_prambanan,
        R.drawable.tour_jogja_gembiraloka
    )

    private val tourAddress = arrayOf(
        "Bandung",
        "Bandung",
        "Bandung",
        "Bandung",
        "Bandung",
        "Bandung",
        "Bandung",
        "Yogyakarta",
        "Yogyakarta",
        "Yogyakarta",
        "Yogyakarta",
        "Yogyakarta"
    )

    private val tourRating = arrayOf(
        "5/5",
        "4/5",
        "4/5",
        "2/5",
        "2/5",
        "3/5",
        "3/5",
        "4/5",
        "4/5",
        "5/5",
        "5/5",
        "4/5"
    )

    val listData: ArrayList<Tour>
        get() {
            val list = arrayListOf<Tour>()
            for (position in tourName.indices){
                val tour = Tour()

                tour.photo = tourPhoto[position]
                tour.name = tourName[position]
                tour.address = tourAddress[position]
                tour.description = tourDescription[position]
                tour.rating = tourRating[position]

                list.add(tour)
            }
            return list
        }
}