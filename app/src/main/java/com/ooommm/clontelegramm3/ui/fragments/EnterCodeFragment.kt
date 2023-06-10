package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.databinding.FragmentEnterCodeBinding

class EnterCodeFragment : Fragment() {
    private lateinit var binding: FragmentEnterCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.etRegisterInputCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("TAG1", s.toString())

                val string = binding.etRegisterInputCode.text.toString()
                if (string.length == 6) {
                    verifiCode()
                    Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun verifiCode() {
        Toast.makeText(activity, "OK", Toast.LENGTH_SHORT).show()
        Log.d("TAG1", "OK")

    }


}