package com.williamv.debtmake.utils

import android.content.Context
import org.json.JSONArray

data class CountryCurrency(
    val country: String,
    val currency: String,
    val currencyName: String
)

object CurrencyCountryUtil {
    private var countryCurrencyList: List<CountryCurrency>? = null

    fun loadFromAssets(context: Context) {
        if (countryCurrencyList != null) return
        val inputStream = context.assets.open("country_currency.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)
        val list = mutableListOf<CountryCurrency>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            list.add(
                CountryCurrency(
                    country = obj.getString("country"),
                    currency = obj.getString("currency"),
                    currencyName = obj.getString("currencyName")
                )
            )
        }
        countryCurrencyList = list
    }

    fun getAll(): List<CountryCurrency> = countryCurrencyList ?: emptyList()
    fun search(q: String): List<CountryCurrency> = getAll().filter {
        it.country.contains(q, true) || it.currency.contains(q, true) || it.currencyName.contains(q, true)
    }
    fun getByCountry(country: String): CountryCurrency? = getAll().find { it.country == country }
    fun getByCurrency(currency: String): CountryCurrency? = getAll().find { it.currency == currency }
}
