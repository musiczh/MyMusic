package com.example.mymusic.network.netbean

data class MusicUrlBean(
    val code: Int,
    val req_0: Req0,
    val start_ts: Long,
    val ts: Long
)

data class Req0(
    val code: Int,
    val data: Data
)

data class Data(
    val expiration: Int,
    val login_key: String,
    val midurlinfo: List<Midurlinfo>,
    val msg: String,
    val retcode: Int,
    val servercheck: String,
    val sip: List<String>,
    val testfile2g: String,
    val testfilewifi: String,
    val thirdip: List<String>,
    val uin: String,
    val verify_type: Int
)

data class Midurlinfo(
    val common_downfromtag: Int,
    val errtype: String,
    val filename: String,
    val flowfromtag: String,
    val flowurl: String,
    val hisbuy: Int,
    val hisdown: Int,
    val isbuy: Int,
    val isonly: Int,
    val onecan: Int,
    val opi128kurl: String,
    val opi192koggurl: String,
    val opi192kurl: String,
    val opi30surl: String,
    val opi48kurl: String,
    val opi96kurl: String,
    val opiflackurl: String,
    val p2pfromtag: Int,
    val pdl: Int,
    val pneed: Int,
    val pneedbuy: Int,
    val premain: Int,
    val purl: String,
    val qmdlfromtag: Int,
    val result: Int,
    val songmid: String,
    val tips: String,
    val uiAlert: Int,
    val vip_downfromtag: Int,
    val vkey: String,
    val wififromtag: String,
    val wifiurl: String
)