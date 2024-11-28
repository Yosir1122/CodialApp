package com.example.codialapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialapp.databinding.ItemMentorRvBinding
import com.example.codialapp.models.Mentorlar

class MentorlarAdapter(var list: ArrayList<Mentorlar>,var rvAction: RvAction) : RecyclerView.Adapter<MentorlarAdapter.Vh>() {
    inner class  Vh( var itemMentorRvBinding: ItemMentorRvBinding): RecyclerView.ViewHolder(itemMentorRvBinding.root) {
        fun onBind(mentorlar: Mentorlar) {
            itemMentorRvBinding.name.text=mentorlar.name
            itemMentorRvBinding.surname.text=mentorlar.number
            itemMentorRvBinding.edit.setOnClickListener {
                rvAction.edit(mentorlar)
            }
            itemMentorRvBinding.delete.setOnClickListener {
                rvAction.delete(mentorlar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemMentorRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
    interface RvAction{
        fun edit(mentor: Mentorlar)
        fun delete(mentor: Mentorlar)
    }
}