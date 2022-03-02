package info.learncoding.playquiz.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<VM : ViewModel, ViewBinding : ViewDataBinding> : Fragment() {

    protected abstract val viewModel: VM
    protected abstract val layout: Int

    protected var binding: ViewBinding? = null
        private set

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding!!.root
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}











