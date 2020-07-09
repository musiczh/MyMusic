package com.example.mymusic.network.netbean

data class SearchBean(
    val code: Int,
    val data: Datas,
    val message: String,
    val notice: String,
    val subcode: Int,
    val time: Int,
    val tips: String
)

data class Datas(
    val keyword: String,
    val priority: Int,
    val qc: List<Any>,
    val semantic: Semantic,
    val song: Song,
    val tab: Int,
    val taglist: List<Any>,
    val totaltime: Int,
    val zhida: Zhida
)

data class Semantic(
    val curnum: Int,
    val curpage: Int,
    val list: List<Any>,
    val totalnum: Int
)

data class Song(
    val curnum: Int,
    val curpage: Int,
    val list: List<Result>,
    val totalnum: Int
)

data class Zhida(
    val chinesesinger: Int,
    val type: Int
)

data class Result(
    val albumid: Int,
    val albummid: String,
    val albumname: String,
    val albumname_hilight: String,
    val alertid: Int,
    val belongCD: Int,
    val cdIdx: Int,
    val chinesesinger: Int,
    val docid: String,
    val format: String,
    val grp: List<Any>,
    val interval: Int,
    val isonly: Int,
    val lyric: String,
    val lyric_hilight: String,
    val media_mid: String,
    val msgid: Int,
    val newStatus: Int,
    val nt: Long,
    val pay: Pay,
    val preview: Preview,
    val pubtime: Int,
    val pure: Int,
    val singer: List<Singer>,
    val size128: Int,
    val size320: Int,
    val sizeape: Int,
    val sizeflac: Int,
    val sizeogg: Int,
    val songid: Int,
    val songmid: String,
    val songname: String,
    val songname_hilight: String,
    val songurl: String,
    val strMediaMid: String,
    val stream: Int,
    val switch: Int,
    val t: Int,
    val tag: Int,
    val type: Int,
    val ver: Int,
    val vid: String
)

data class Pay(
    val payalbum: Int,
    val payalbumprice: Int,
    val paydownload: Int,
    val payinfo: Int,
    val payplay: Int,
    val paytrackmouth: Int,
    val paytrackprice: Int
)

data class Preview(
    val trybegin: Int,
    val tryend: Int,
    val trysize: Int
)

data class Singer(
    val id: Int,
    val mid: String,
    val name: String,
    val name_hilight: String
)