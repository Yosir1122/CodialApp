package com.example.codialapp.fragment.kurslar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.R
import com.example.codialapp.adapter.KursAdapter
import com.example.codialapp.databinding.FragmentKurslarBinding
import com.example.codialapp.databinding.ItemDialogBinding
import com.example.codialapp.models.Kurslar

class KurslarFragment : Fragment() {
    lateinit var kursAdapter: KursAdapter
    lateinit var myDb: MyDbHelper
    private val binding by lazy { FragmentKurslarBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDb = MyDbHelper(requireContext())
        binding.add.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext()).create()
            val itemDialogBinding= ItemDialogBinding.inflate(layoutInflater)
            itemDialogBinding.add.setOnClickListener {
                val kurslar = Kurslar(
                    1,
                    itemDialogBinding.name.text.toString(),
                    itemDialogBinding.info.text.toString()
                )
                myDb.addKurs(kurslar)
                onResume()
                dialog.cancel()
            }
            itemDialogBinding.back.setOnClickListener {
                dialog.cancel()
            }
            dialog.setView(itemDialogBinding.root)
            dialog.show()
        }
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val list = myDb.showKurs()
        kursAdapter= KursAdapter(requireContext(), list,object :KursAdapter.RvAction{
            override fun onClick(position: Int,kurslar: Kurslar) {
                findNavController().navigate(R.id.dataFragment, bundleOf("position" to position))
            }
        })
        binding.rv.adapter = kursAdapter
    }

}