package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.PriceFunctions
import com.example.myapplication.R
import com.example.myapplication.model.SPACBottomMonthlyPriceChange

class ItemAdapterBottomMonthlyPriceChange (
        private val context: Context,
        private val dataset: List<SPACBottomMonthlyPriceChange>,
        private val preloidata: MutableList<Array<String>> = PriceFunctions.getdata("Pre+LOI", context),
        private val definitiveagreementdata: MutableList<Array<String>> = PriceFunctions.getdata("Definitive+Agreement", context)
    ) : RecyclerView.Adapter<ItemAdapterBottomMonthlyPriceChange.ItemViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just an SPACLivePrices object.
        class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            val textView1: TextView = view.findViewById(R.id.item_title1)
            val textView2: TextView = view.findViewById(R.id.item_title2)
            val textView3: TextView = view.findViewById(R.id.item_title3)
            val textView4: TextView = view.findViewById(R.id.item_title4)
        }

        /**
         * Create new views (invoked by the layout manager)
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            // create a new view
            val adapterLayout = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_bottom_monthly_spac, parent, false)

            return ItemViewHolder(adapterLayout)
        }

        /**
         * Replace the contents of a view (invoked by the layout manager)
         */
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val item = dataset[position]
            holder.textView1.text = item.stringResourceId1
            holder.textView2.text = item.stringResourceId2
            holder.textView3.text = item.stringResourceId3.toString().plus("%")
            holder.textView4.text = item.stringResourceId4
            var icon = R.drawable.price_up
            if(item.stringResourceId3?.toFloat()!! < 0){icon = R.drawable.price_down}
            holder.textView3.setCompoundDrawablesWithIntrinsicBounds(null, null,ResourcesCompat.getDrawable(context.resources, icon, null), null)

            //Determine whether it belongs to PreLoi or DefAgreement. If not, list[0] = SPAC NOT FOUND
            val thisdatapreloi = PriceFunctions.getSPACdata(preloidata, holder.textView1.text.toString())
            val thisdatadefagreement = PriceFunctions.getSPACdata(definitiveagreementdata, holder.textView1.text.toString())
            if (thisdatapreloi[0] != "SPAC NOT FOUND") {
                PriceFunctions.onclicksetter_bottommonthly(holder, "Pre LOI", thisdatapreloi, context)
            } else if (thisdatadefagreement[0] != "SPAC NOT FOUND") {
                PriceFunctions.onclicksetter_bottommonthly(holder, "Definitive Agreement", thisdatadefagreement, context)
            } else {
                val unfoundarray = arrayOf(
                        item.stringResourceId1,
                        item.stringResourceId3.toString(),
                        item.stringResourceId2,
                        item.MarketCap,
                        item.EstTrustValue,
                        item.CurrentVolume,
                        item.AverageVolume,
                        item.FullName
                )
                PriceFunctions.onclicksetter_bottommonthly(holder, "NOT_FOUND", unfoundarray, context)
            }
        }

        /**
         * Return the size of your dataset (invoked by the layout manager)
         */
        override fun getItemCount() = dataset.size
    }