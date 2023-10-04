package com.malbyte.mytale.data.appPref

import com.chibatching.kotpref.KotprefModel

object GlobalPref: KotprefModel() {
    var token by nullableStringPref()
}