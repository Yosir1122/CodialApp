package com.example.codialapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialapp.databinding.ItemRvBinding
import com.example.codialapp.models.Kurslar


class KursAdapter(var context: Context, var list:List<Kurslar>, var rvAction:RvAction ) : RecyclerView.Adapter<KursAdapter.Vh>() {
    inner class  Vh( var itemRv: ItemRvBinding): RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(kurslar: Kurslar) {
            itemRv.tvName .text = kurslar.name
            itemRv.imgMalumot.setOnClickListener {
                rvAction.onClick(adapterPosition, kurslar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
    interface RvAction{
        fun onClick(position: Int, kurslar: Kurslar)
    }
}