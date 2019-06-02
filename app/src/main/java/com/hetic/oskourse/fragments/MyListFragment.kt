package com.hetic.oskourse.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.hetic.oskourse.R
import com.hetic.oskourse.viewholder.IngredientItem
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.fragment_my_list.*
import kotlinx.android.synthetic.main.row_ingredient.*

class MyListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layout = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )

        recyclerView.layoutManager = layout

        val itemAdapter = ItemAdapter<IItem<*, *>>()
        val fastAdapter = FastAdapter.with<IngredientItem, ItemAdapter<IItem<*, *>>>(itemAdapter)
        recyclerView.adapter = fastAdapter

        // enables actions on items when they are clicked

        fastAdapter.withOnClickListener { view, adapter, item, position ->

            item.getViewHolder(view!!).binButtonView.visibility = View.VISIBLE
            item.getViewHolder(view!!).eyeButtonView.visibility = View.VISIBLE

            item.getViewHolder(view!!).binButtonView.setOnClickListener {
                itemAdapter.remove(position)
            }

            true
        }

        // gets the old list of ingredients (local)

        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this.context)

        var ingredientString = sharedPreference.getString("ingredients", "no ingredients")
        val ingredientsList = ingredientString.split(",")

        Toast.makeText(context, ingredientsList.toString(), Toast.LENGTH_LONG).show()

        for (item in ingredientsList) {
            val ingredient = IngredientItem(item.toString())
            itemAdapter.add(ingredient)
        }
    }

}
