package com.example.codialapp.fragment.guruhlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.R
import com.example.codialapp.adapter.StudentAdapter
import com.example.codialapp.databinding.FragmentStudentBinding
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Talabalar
import com.example.codialapp.utils.MyData

class StudentFragment : Fragment() {
    private val binding by lazy { FragmentStudentBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    lateinit var studentAdapter: StudentAdapter
    lateinit var list:ArrayList<Talabalar>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raqam = arguments?.getInt("raqam")

        val grupa_id = arguments?.getInt("key10")
        myDbHelper = MyDbHelper(requireContext())


        var name = ""
        var vaqt = ""
        myDbHelper.showGroup().forEach {
            if (it.id == grupa_id) {
                vaqt = it.time.toString()
                name = it.name.toString()
                MyData.grupalar = it
            }
        }
        binding.nameGroup.text = name
        binding.vaqti.text = vaqt
        val myLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rv.apply {
            layoutManager = myLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    myLayoutManager.orientation
                )
            )
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.addStudentFragment)
        }
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        if (raqam==100){
            binding.btnBoshlash.visibility = View.GONE
        }else {

            binding.btnBoshlash.setOnClickListener {
                val p = MyData.grupalar
                val grupalar = Grupalar(
                    p?.id,
                    p?.name,
                    p?.mentor_id,
                    p?.day,
                    p?.time,
                    1
                )
                myDbHelper.editGroup(grupalar)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        return binding.root
    }



    override fun onResume() {
        super.onResume()
        val grupa_id = arguments?.getInt("key10")
        myDbHelper = MyDbHelper(requireContext())
        list = ArrayList()

        for (student in myDbHelper.showStudents()) {
            if (student.groupId?.id == grupa_id) {
                list.add(student)
            }
        }
        binding.count.text = list.size.toString()
        studentAdapter = StudentAdapter(object :StudentAdapter.RvAction{
            override fun editClick(talabalar: Talabalar) {
                findNavController().navigate(R.id.editStudentFragment)
                MyData.talabalar = talabalar
            }

            override fun deleteClick(talabalar: Talabalar) {
                myDbHelper.deleteStudent(talabalar)
                onResume()
            }
        }, list)
        binding.rv.adapter = studentAdapter
    }

}