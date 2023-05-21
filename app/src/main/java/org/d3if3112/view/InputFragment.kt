package org.d3if3112.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.d3if3112.databinding.FragmentAddfoodBinding

class InputFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var binding: FragmentAddfoodBinding =
            FragmentAddfoodBinding.inflate(inflater, container, false)

        return binding.root
    }
}