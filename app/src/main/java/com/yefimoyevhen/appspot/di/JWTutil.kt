package com.yefimoyevhen.appspot.di

import android.util.Base64
import com.yefimoyevhen.appspot.BuildConfig
import org.jose4j.jws.AlgorithmIdentifiers
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwt.JwtClaims
import org.jose4j.keys.HmacKey

const val UID = "uid"
const val IDENTITY = "identity"
const val NAME = "typ"
const val TYPE = "JWT"

fun generateJWT(): String {
    /*val claims = JwtClaims().apply {
        setClaim(UID, "123")
        setClaim(IDENTITY, "1234")
    }

    val decode = Base64.decode(BuildConfig.API_SECRET_KEY, Base64.DEFAULT)
    val key = HmacKey(decode)
    val jwt = JsonWebSignature().apply {
        payload = claims.toJson()
        algorithmHeaderValue = AlgorithmIdentifiers.HMAC_SHA256
        setHeader(NAME, TYPE)
        this.key = key
        isDoKeyValidation = false
    }.compactSerialization

    return jwt*/
    //Only for test, not for production!!!
    return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjMiLCJpZGVudGl0eSI6IjEyMzQifQ.yOIx1ZozHSMy_ZndEEMXIH0YeGUkHH3idl_2WTI12gs"

}