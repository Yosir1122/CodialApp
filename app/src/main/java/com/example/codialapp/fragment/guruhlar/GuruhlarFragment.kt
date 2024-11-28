package com.example.codialapp.fragment.guruhlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.R
import com.example.codialapp.adapter.KursAdapter
import com.example.codialapp.databinding.FragmentGuruhlarBinding
import com.example.codialapp.models.Kurslar
import com.example.codialapp.utils.MyData

class GuruhlarFragment : Fragment() {
    lateinit var myDbHelper: MyDbHelper
    lateinit var kursAdapter: KursAdapter
    private val binding by lazy { FragmentGuruhlarBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDbHelper = MyDbHelper(requireContext())
        val list = myDbHelper.showKurs()
        kursAdapter = KursAdapter(requireContext(), list, object : KursAdapter.RvAction {
            override fun onClick(position: Int, kurslar: Kurslar) {
                for (mentorlar in myDbHelper.showMentor()) {
                    if (mentorlar.kurs_id?.id == kurslar.id) {
                        findNavController().navigate(
                            R.id.addGroupFragment,
                            bundleOf("ali" to kurslar)
                        )
                        MyData.kurslar = kurslar
                    } else {
                        Toast.makeText(context, "Bu yo'nalishda hali mentor yo'q", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        })

        binding.rv.adapter = kursAdapter
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }
}

