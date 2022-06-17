package com.aran.ocrapp.helper

import com.google.gson.annotations.SerializedName
data class Responses(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

// login response
data class SignInResponse(

    @field:SerializedName("loginResult")
    val loginResult: SignInResult,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("status")
    val status: String
)

data class SignInResult(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)

data class PostResponse(

    @field:SerializedName("data")
    val data: DataResult,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,
)

data class DataResult(

    @field:SerializedName("NIK")
    val nik: String,

    @field:SerializedName("Nama")
    val nama: String,

    @field:SerializedName("Alamat")
    val alamat: String,

    @field:SerializedName("Provinsi")
    val provinsi: String,

    @field:SerializedName("Kabupaten/Kota")
    val kabupaten: String,

    @field:SerializedName("Agama")
    val agama: String,

    @field:SerializedName("Jenis kelamin")
    val jk: String,

    @field:SerializedName("Kecamatan")
    val kecamatan: String,

    @field:SerializedName("Kel/Desa")
    val kelurahan: String,

    @field:SerializedName("Pekerjaan")
    val pekerjaan: String,

    @field:SerializedName("RT/RW")
    val rt: String,

    @field:SerializedName("Tempat/tgl lahir")
    val ttl: String,

    @field:SerializedName("kewarganegaraan")
    val kewarganegaraan: String,

)