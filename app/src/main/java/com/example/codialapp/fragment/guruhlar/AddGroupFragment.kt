package com.example.codialapp.fragment.guruhlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.R
import com.example.codialapp.adapter.MyViewPagerAdapter
import com.example.codialapp.databinding.FragmentAddGroupBinding
import com.example.codialapp.models.Kurslar

@Suppress("UNREACHABLE_CODE")
class AddGroupFragment : Fragment() {
    lateinit var myDbHelper: MyDbHelper
    lateinit var myViewPagerAdapter: MyViewPagerAdapter
    private val binding by lazy { FragmentAddGroupBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val p = arguments?.getSerializable("ali") as Kurslar
        myViewPagerAdapter = MyViewPagerAdapter(childFragmentManager)
        binding.pager.adapter = myViewPagerAdapter
        binding.tablayout.setupWithViewPager(binding.pager)
        val myDbHelper = MyDbHelper(requireContext())
        binding.name.text = p.name
        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 0) binding.add.visibility =
                    View.INVISIBLE else binding.add.visibility = View.VISIBLE
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.guruhQoshishFragment, bundleOf("vali" to p))

        }
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }
}


