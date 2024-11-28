package com.example.codialapp.fragment.guruhlar

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.codialapp.Db.MyDbHelper
import com.example.codialapp.R
import com.example.codialapp.databinding.FragmentAddStudentBinding
import com.example.codialapp.models.Talabalar
import com.example.codialapp.utils.MyData
import java.util.Calendar

class AddStudentFragment : Fragment() {

    lateinit var myDbHelper: MyDbHelper
    private val binding by lazy { FragmentAddStudentBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDbHelper = MyDbHelper(requireContext())
        var date = ""
        binding.date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(requireContext(),0,
                { _, year, month, dayOfMonth ->
                    date = "$dayOfMonth.${month+1}.$year"
                    binding.date.text = date
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))


            datePickerDialog.show()
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).text = "Tanlash"

        }
        binding.btnSave.setOnClickListener {
            if (binding.ismi.text.isNotEmpty() && binding.familya.text.isNotEmpty() && binding.phoneNumber.text.isNotEmpty() && binding.date.text.isNotEmpty()) {
                myDbHelper.addStudent(
                    Talabalar(
                        binding.ismi.text.toString(),
                        binding.familya.text.toString(),
                        binding.phoneNumber.text.toString(),
                        date,
                        MyData.grupalar
                    )
                )
                requireActivity().supportFragmentManager.popBackStack()
            }else{
                Toast.makeText(requireContext(), "Bo'limlarni to'liq to'ldiring", Toast.LENGTH_SHORT).show()
            }
        }
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }
    }

