package com.aran.ocrapp.helper

import com.google.gson.annotations.SerializedName
data class Responses(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class SignUpResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: UserEmail
)

data class UserEmail(
    @field:SerializedName("email")
    val email: String
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

data class getAllDataResponse(

    @field:SerializedName("data")
    val data: Resulttt,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,
)

data class Resulttt(

    @field:SerializedName("nik")
    val nik: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("provinsi")
    val provinsi: String,

    @field:SerializedName("kabupaten")
    val kabupaten: String,

    @field:SerializedName("agama")
    val agama: String,

    @field:SerializedName("jenis_kelamin")
    val jk: String,

    @field:SerializedName("kecamatan")
    val kecamatan: String,

    @field:SerializedName("kelurahan")
    val kelurahan: String,

    @field:SerializedName("pekerjaan")
    val pekerjaan: String,

    @field:SerializedName("rt")
    val rt: String,

    @field:SerializedName("ttl")
    val ttl: String,

    @field:SerializedName("kewarganegaraan")
    val kewarganegaraan: String,
)