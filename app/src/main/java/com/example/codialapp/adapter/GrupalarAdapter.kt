package com.example.codialapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.databinding.ItemGroupBinding
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Talabalar

class GrupalarAdapter(var rvAction5: RvAction5, var list: ArrayList<Grupalar>, var myDbHelper: MyDbHelper): RecyclerView.Adapter<GrupalarAdapter.Vh>() {

    inner class Vh(var itemGroupBinding: ItemGroupBinding): RecyclerView.ViewHolder(itemGroupBinding.root){
        fun onBind(grupalar: Grupalar, position: Int){
            itemGroupBinding.name.text = grupalar.name
            itemGroupBinding.count.text = counter(grupalar, myDbHelper)

            itemGroupBinding.view.setOnClickListener {
                rvAction5.viewClick(grupalar, position)
            }
            itemGroupBinding.edit.setOnClickListener {
                rvAction5.editClick(grupalar, position)
            }
            itemGroupBinding.delete.setOnClickListener {
                rvAction5.deleteClick(grupalar, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction5 {
        fun viewClick(grupalar: Grupalar, position: Int)
        fun editClick(grupalar: Grupalar, position: Int)
        fun deleteClick(grupalar: Grupalar, position: Int)
    }

    fun counter(grupalar: Grupalar, myDbHelper1: MyDbHelper) : String{
        val list = ArrayList<Talabalar>()
        myDbHelper1.showStudents().forEach {
            if (it.groupId?.id == grupalar.id){
                list.add(it)
            }
        }
        return "O'quvchilar soni: ${list.size} ta"
    }
}