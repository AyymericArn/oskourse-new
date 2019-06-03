package com.hetic.oskourse.viewholder

import android.view.View
import android.widget.ImageView
// import android.content.ClipData
import android.widget.TextView
import androidx.core.content.edit
import com.hetic.oskourse.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.hetic.oskourse.fragments.MyListFragment

class IngredientViewHolder (itemView: View) : FastAdapter.ViewHolder<IngredientItem>(itemView) {
    val ingredientNameTextView: TextView
    val eyeButtonView: ImageView
    val binButtonView: ImageView

    init {
        ingredientNameTextView = itemView.findViewById(R.id.ingredientNameTextView)
        eyeButtonView = itemView.findViewById(R.id.eyeButton)
        binButtonView = itemView.findViewById(R.id.binButton)
    }

    override fun bindView(item: IngredientItem, payloads: MutableList<Any>) {
        ingredientNameTextView.text = item.ingredient

        binButtonView.setOnClickListener {

            // val sharedPreference = PreferenceManager.getDefaultSharedPreferences()

//            val ingredientsString = sharedPreference.getString("ingredients", "no ingredients")
//            val ingredientsList = ingredientsString.split(",").toMutableList()
//            ingredientsList.removeAt(position)
//            sharedPreference.edit {
//                putString("ingredients", ingredientsList.toString().replace("[", "").replace("]", ""))
//            }
        }

        // binButtonView.setOnClickListener {
           // this.itemId.removeRange(adapterPosition)
        // }
    }

    override fun unbindView(item: IngredientItem) {
        ingredientNameTextView.text = ""
    }
}

class IngredientItem(val ingredient: String) : AbstractItem<IngredientItem, IngredientViewHolder>() {

    override fun getType() = R.id.item_ingredient

    override fun getViewHolder(v: View) = IngredientViewHolder(v)

    override fun getLayoutRes() = R.layout.row_ingredient

}