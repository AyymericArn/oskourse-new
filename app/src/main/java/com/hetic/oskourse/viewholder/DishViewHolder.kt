package com.hetic.oskourse.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import com.hetic.oskourse.R
import com.hetic.oskourse.services.Dish
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_dish.*

class DishViewHolder (itemView: View): FastAdapter.ViewHolder<DishItem>(itemView) {
    val nameTextView:TextView
    val areaTextView:TextView
    val imageView:ImageView
    var id:Int

    init {
        nameTextView = itemView.findViewById(R.id.nameTextView)
        areaTextView = itemView.findViewById(R.id.areaTextView)
        imageView = itemView.findViewById(R.id.imageView)
        id = 0
    }

    override fun bindView(item: DishItem, payloads: MutableList<Any>) {
        nameTextView.text = item.dish.strMeal
        areaTextView.text = item.dish.strArea
        id = item.dish.idMeal

        if (item.dish.strMeal.isNotEmpty()) {
            Picasso.get().load(item.dish.strMealThumb).into(imageView)
        } else {
            imageView.setImageBitmap(null)
        }
    }

    override fun unbindView(item: DishItem) {
        nameTextView.text = ""
        areaTextView.text = ""
        id = 0
        imageView.setImageBitmap(null)
    }
}

class DishItem (val dish: Dish): AbstractItem<DishItem, DishViewHolder>() {
    override fun getType() = R.id.item_dish

    override fun getViewHolder(v: View) = DishViewHolder(v)

    override fun getLayoutRes() = R.layout.row_dish
}