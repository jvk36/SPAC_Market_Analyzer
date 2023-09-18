package com.example.myapplication.data

import com.example.myapplication.model.SPACTopDailyPriceChange
import org.json.JSONObject
import java.net.URL


class DataSourceTopDaily {

    fun loadSPACs(): List<SPACTopDailyPriceChange> {
        val list: MutableList<SPACTopDailyPriceChange> = mutableListOf()
        val jsonArray = URL("https://sheets.googleapis.com/v4/spreadsheets/1D61Q4V_LwTXVCOedHkg-IROuZKTiJ25wg_qL75XvWlc/values/Daily % Change!A2:I265?key=AIzaSyCZP2fBW638Gip01kDHMbHLaM84hWwU7uo").readText()
        val info = JSONObject(jsonArray).getJSONArray("values")

        val len = info.length() - 1

//        finalList.add(0, SPACLivePrices("TICKER", "LIVE PRICE", "COMPANY NAME"))

        for(i in 0..len) {
            val currentSPAC = info.getJSONArray(i)
            var company_name = currentSPAC.getString(4)
            if(company_name.length > 15){
                company_name = company_name.slice(IntRange(0,14)) + "..."
            }
            list.add(i, SPACTopDailyPriceChange(currentSPAC.getString(0),
                currentSPAC.getString(1),
                currentSPAC.getString(3).toFloatOrNull(),
                company_name,currentSPAC.getString(5),
                    currentSPAC.getString(6),
                    currentSPAC.getString(7),
                    currentSPAC.getString(8),
                    currentSPAC.getString(4)))
        }

        val finalList: MutableList<SPACTopDailyPriceChange> = mutableListOf()

        for(j in 0..len) {
            if (list[j].stringResourceId3 != null){
                list[j].stringResourceId3 = "%.${2}f".format(list[j].stringResourceId3?.times(100.0)?.toFloat()).toFloatOrNull()
                finalList.add(list[j])
            }
        }

        finalList.sortByDescending { SPACTopDailyPriceChange -> SPACTopDailyPriceChange.stringResourceId3 }

        return finalList.take(10)
    }
}