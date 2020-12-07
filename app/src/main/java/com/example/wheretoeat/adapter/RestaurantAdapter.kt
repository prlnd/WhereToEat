package com.example.wheretoeat.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageButton
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.activityViewModels
//import androidx.recyclerview.widget.RecyclerView
//import com.example.wheretoeat.R
//import com.example.wheretoeat.data.SharedViewModel
//import com.example.wheretoeat.model.Restaurant
//import com.example.wheretoeat.network.USER_ID
//import kotlin.coroutines.coroutineContext
//
//class RestaurantAdapter(val viewModel: SharedViewModel) :
//    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
//    private var restaurantList = emptyList<Restaurant>()
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
//        val itemView =
//            LayoutInflater.from(parent.context).inflate(R.layout.list_item_row, parent, false)
//
//        return RestaurantViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
//        val currentItem = restaurantList[position]
//
//        holder.apply {
//            imageView.setImageResource(R.drawable.foodimage)
//            textViewName.text = currentItem.name
//            textViewAddress.text = currentItem.address
//            textViewPrice.text = "$".repeat(currentItem.price)
//            favorite.setOnClickListener {
////                Toast.makeText(this, "Added to favorites ${currentItem.id}", 2000)
//                viewModel.addFavorite(USER_ID, currentItem)
//            }
//        }
//    }
//
//    override fun getItemCount() = restaurantList.size
//
//    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
//        View.OnClickListener {
//        val imageView: ImageView = itemView.findViewById(R.id.image_view)
//        val textViewName: TextView = itemView.findViewById(R.id.text_view_name)
//        val textViewAddress: TextView = itemView.findViewById(R.id.text_view_address)
//        val textViewPrice: TextView = itemView.findViewById(R.id.text_view_price)
//        val favorite: ImageButton = itemView.findViewById(R.id.imageButton)
//
//        override fun onClick(v: View?) {
//            TODO("Not yet implemented")
//        }
//    }
//
//    fun setData(newList: List<Restaurant>) {
//        restaurantList = newList
//        notifyDataSetChanged()
//    }
//}