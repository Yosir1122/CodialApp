package com.example.codialapp.fragment.mentorlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.adapter.MentorlarAdapter
import com.example.codialapp.databinding.FragmentMentorAddBinding
import com.example.codialapp.databinding.ItemDialogMentorBinding
import com.example.codialapp.models.Kurslar
import com.example.codialapp.models.Mentorlar

class MentorAddFragment : Fragment() {
    lateinit var myDbHelper: MyDbHelper
    lateinit var mentorlarAdapter:MentorlarAdapter
    lateinit var list: ArrayList<Mentorlar>

    private val binding by lazy { FragmentMentorAddBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDbHelper = MyDbHelper(requireContext())
        val mentorId = arguments?.getSerializable("p") as Kurslar
        println("hi")
        println(mentorId)
        println("hi")
        onResume()
        binding.add.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext()).create()
            val itemDialogMentorBinding = ItemDialogMentorBinding.inflate(layoutInflater)
            itemDialogMentorBinding.add.setOnClickListener {
                if (itemDialogMentorBinding.name.text.isNotEmpty() && itemDialogMentorBinding.surname.text.isNotEmpty() && itemDialogMentorBinding.otasiniIsmi.text.isNotEmpty()) {
                    val mentor = Mentorlar(
                        1,
                        itemDialogMentorBinding.name.text.toString(),
                        itemDialogMentorBinding.surname.text.toString(),
                        itemDialogMentorBinding.otasiniIsmi.text.toString(),
                        mentorId
                    )
                    myDbHelper.addMentor(mentor)
                }else{
                    Toast.makeText(context, "Bo'limlarni to'liq to'ldiring", Toast.LENGTH_SHORT).show()
                }
                onResume()
                dialog.cancel()
            }

            itemDialogMentorBinding.back.setOnClickListener {
                dialog.cancel()
            }
            dialog.setView(itemDialogMentorBinding.root)
            dialog.show()

        }
        val myLayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        binding.rv.apply {
            layoutManager = myLayoutManager
            addItemDecoration(DividerItemDecoration(requireContext(), myLayoutManager.orientation))
        }
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        myDbHelper = MyDbHelper(requireContext())
        tartibla()
        mentorlarAdapter = MentorlarAdapter(list, object : MentorlarAdapter.RvAction {
            override fun edit(mentor: Mentorlar) {
                myDbHelper = MyDbHelper(requireContext())
                val mentorId = arguments?.getSerializable("p") as Kurslar
                val dialog = AlertDialog.Builder(requireContext())
                    .create()
                val itemDialogMentorBinding = ItemDialogMentorBinding.inflate(layoutInflater)
                itemDialogMentorBinding.name.setText(mentor.name)
                itemDialogMentorBinding.surname.setText(mentor.lastName)
                itemDialogMentorBinding.otasiniIsmi.setText(mentor.number)
                itemDialogMentorBinding.add.text = "O'zgartirish"
                itemDialogMentorBinding.add.setOnClickListener {
                    if (itemDialogMentorBinding.name.text.isNotEmpty() && itemDialogMentorBinding.surname.text.isNotEmpty() && itemDialogMentorBinding.otasiniIsmi.text.isNotEmpty()) {
                        val mentor = Mentorlar(
                            mentor.id,
                            itemDialogMentorBinding.name.text.toString(),
                            itemDialogMentorBinding.surname.text.toString(),
                            itemDialogMentorBinding.otasiniIsmi.text.toString(),
                            mentorId
                        )
                        myDbHelper.editMentor(mentor)
                    }else{
                        Toast.makeText(context, "Bo'limlarni to'liq to'ldiring", Toast.LENGTH_SHORT).show()
                    }
                    onResume()
                    dialog.cancel()
                }
                itemDialogMentorBinding.back.setOnClickListener {
                    dialog.cancel()
                }
                dialog.setView(itemDialogMentorBinding.root)
                dialog.show()
            }

            override fun delete(mentor: Mentorlar) {
                var a = true
                myDbHelper = MyDbHelper(requireContext())
                for (grupalar in myDbHelper.showGroup()) {
                    if (grupalar.mentor_id?.id==mentor.id){
                        a=false
                        Toast.makeText(context, "Oldin bu mentor guruhlarini yakunlang", Toast.LENGTH_SHORT).show()
                    }
                }
                if (a) {
                    myDbHelper.deleteMentor(mentor)
                    onResume()
                }
            }
        })
        binding.rv.adapter = mentorlarAdapter
    }

    fun tartibla(){
        val mentorId = arguments?.getSerializable("p") as Kurslar
        this.list = ArrayList()
        for (mentorlar in myDbHelper.showMentor()) {
            if (mentorlar.kurs_id?.id ==mentorId.id){
                list.add(mentorlar)
                println(list)
            }
        }
    }



}

