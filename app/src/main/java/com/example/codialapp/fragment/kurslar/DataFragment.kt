package com.example.codialapp.fragment.kurslar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.databinding.FragmentDataBinding

class DataFragment : Fragment() {
    private val binding by lazy { FragmentDataBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var id = arguments.let { it?.getInt("position", 0) }
        myDbHelper = MyDbHelper(requireContext())
        binding.nameInfo.text = myDbHelper.showKurs()[id!!].name
        binding.tvInfo.text = myDbHelper.showKurs()[id].about
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }
}