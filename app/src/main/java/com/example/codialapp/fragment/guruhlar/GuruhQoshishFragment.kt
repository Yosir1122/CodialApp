package com.example.codialapp.fragment.guruhlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.databinding.FragmentGuruhQoshishBinding
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Kurslar
import com.example.codialapp.models.Mentorlar


class GuruhQoshishFragment : Fragment() {
    lateinit var mentorList: ArrayList<String>
    lateinit var myDbHelper: MyDbHelper
    lateinit var abs: Mentorlar
    private val binding by lazy { FragmentGuruhQoshishBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val p = arguments?.getSerializable("vali") as Kurslar
        myDbHelper = MyDbHelper(requireContext())
        vaqti()
        kunlari()
        mentorlar()
        binding.mentor.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mentorList)
        binding.vaqti.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, vaqti())
        binding.kunlari.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, kunlari())

        var vaqt = ""
        binding.vaqti.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                vaqt = vaqti()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

        var kun = ""
        binding.kunlari.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                kun = kunlari()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })
        binding.mentor.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (mentorlar in myDbHelper.showMentor()) {
                    if ("${mentorlar.name} ${mentorlar.lastName}" == mentorList[position]) {
                        abs = mentorlar
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })
        binding.btnSave.setOnClickListener {
            val grupalar = Grupalar(
                binding.nameGroup.text.toString(),
                abs,
                kun,
                vaqt,
                0
            )
            println(grupalar)
            myDbHelper.addGroup(grupalar)
            Toast.makeText(requireContext(), "Saqlandi", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.chiqish.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

        return binding.root
    }

    fun vaqti(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("8:00 dan 10:00 gacha")
        list.add("10:00 dan 12:00 gacha")
        list.add("14:00 dan 16:00 gacha")
        list.add("16:00 dan 20:00 gacha")
        return list
    }

    fun kunlari(): ArrayList<String> {
        val list = ArrayList<String>()
        println("ali")
        list.add("Dushanba, Chorshanba, Juma")
        list.add("Seshanba, Payshanba, Shanba")
        return list
    }

    fun mentorlar() {
        val p = arguments?.getSerializable("vali") as Kurslar
        mentorList = ArrayList()
        myDbHelper = MyDbHelper(requireContext())
        for (mentorlar in myDbHelper.showMentor()) {
            println(mentorlar)
            if (mentorlar.kurs_id?.id == p.id) {
                println(mentorlar)
                mentorList.add("${mentorlar.name} ${mentorlar.lastName}")
            }
        }
    }
}


