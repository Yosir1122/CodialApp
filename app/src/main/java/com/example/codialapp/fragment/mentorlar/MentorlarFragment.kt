package com.example.codialapp.fragment.mentorlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.R
import com.example.codialapp.adapter.KursAdapter
import com.example.codialapp.databinding.FragmentMentorlarBinding
import com.example.codialapp.models.Kurslar


class MentorlarFragment : Fragment() {
    lateinit var myDbHelper: MyDbHelper
    lateinit var kursAdapter: KursAdapter
    private val binding by lazy { FragmentMentorlarBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDbHelper = MyDbHelper(requireContext())
        val list = myDbHelper.showKurs()
        kursAdapter = KursAdapter(requireContext(), list, object : KursAdapter.RvAction {
            override fun onClick(position: Int, kurslar: Kurslar) {
                findNavController().navigate(R.id.mentorAddFragment, bundleOf("p" to kurslar))
            }
        })
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.rv.adapter = kursAdapter
        return binding.root
    }

}