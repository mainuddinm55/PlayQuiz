package info.learncoding.playquiz.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import info.learncoding.playquiz.R
import info.learncoding.playquiz.databinding.FragmentMainBinding
import info.learncoding.playquiz.ui.base.BaseFragment

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    override val viewModel: MainViewModel by viewModels()

    override val layout: Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.viewModel = viewModel
    }

}