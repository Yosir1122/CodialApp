package com.example.codialapp.fragment.guruhlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.R
import com.example.codialapp.adapter.GrupalarAdapter
import com.example.codialapp.databinding.FragmentOchilganGuruhBinding
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Talabalar
import com.example.codialapp.utils.MyData


class OchilganGuruhFragment : Fragment() {
    private val binding by lazy { FragmentOchilganGuruhBinding.inflate(layoutInflater) }
    lateinit var grupalarAdapter: GrupalarAdapter
    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<Grupalar>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val myLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )

        binding.rvOchilgan.apply {
            layoutManager = myLayoutManager
            addItemDecoration(DividerItemDecoration(requireContext(), myLayoutManager.orientation))
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        myDbHelper = MyDbHelper(requireContext())
        list = ArrayList()
        for (grupalar in myDbHelper.showGroup()) {
            if (grupalar.mentor_id?.kurs_id?.id == MyData.kurslar?.id && grupalar.ochilganmi == 1) {
                list.add(grupalar)
            }
        }
        grupalarAdapter = GrupalarAdapter(object : GrupalarAdapter.RvAction5 {
            override fun viewClick(grupalar: Grupalar, position: Int) {
                findNavController().navigate(
                    R.id.studentFragment,
                    bundleOf("raqam" to 100, "key10" to grupalar.id)
                )
            }

            override fun editClick(grupalar: Grupalar, position: Int) {
                findNavController().navigate(
                    R.id.editGroupFragment,
                    bundleOf("edit" to grupalar.id)
                )
            }

            override fun deleteClick(grupalar: Grupalar, position: Int) {
                myDbHelper = MyDbHelper(requireContext())
                var talabalar: Talabalar? = null
                val listStudents = ArrayList<Talabalar>()
                myDbHelper.showStudents().forEach {
                    if (it.groupId?.id == grupalar.id) {
                        listStudents.add(it)
                        talabalar = it
                    }
                }
                if (listStudents.size != 0) {
                    myDbHelper.deleteStudent(talabalar!!)
                    myDbHelper.deleteGroup(grupalar)
                    grupalarAdapter.notifyItemChanged(position)
                } else {
                    Toast.makeText(context, "Guruh talabalar ro'yxati bo'sh!", Toast.LENGTH_SHORT)
                        .show()
                }
                onResume()
            }
        }, list, myDbHelper)

        binding.rvOchilgan.adapter = grupalarAdapter
    }
}

