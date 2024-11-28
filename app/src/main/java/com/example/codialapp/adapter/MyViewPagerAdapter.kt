package com.example.codialapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.codialapp.fragment.guruhlar.OchilayotganGuruhFragment
import com.example.codialapp.fragment.guruhlar.OchilganGuruhFragment

class MyViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        if (position==0){
            return OchilganGuruhFragment()
        }else return OchilayotganGuruhFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val list = arrayOf("Ochilgan guruhlar","Ochilayotgan guruhlar")
        return list[position]
    }
}