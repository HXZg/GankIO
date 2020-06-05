package com.hxz.gankio.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.hxz.baseui.util.LogUtils
import com.hxz.baseui.view.BaseFragment
import com.hxz.gankio.R
import com.hxz.gankio.adapter.ListAdapter
import com.hxz.gankio.viewmodel.ListFactory
import com.hxz.gankio.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment() {

    companion object {
        const val LIST_CATEGORY = "list_category"
        const val LIST_TYPE = "list_type"
        fun newListFragment(category: String,type: String) : ListFragment {
            return ListFragment().apply {
                arguments = Bundle().apply {
                    putString(LIST_CATEGORY,category)
                    putString(LIST_TYPE,type)
                }
            }
        }
    }

    private val viewModel by lazy { viewModels<ListViewModel>(factoryProducer = {ListFactory(category,type)}).value }

    private val category by lazy { arguments?.getString(LIST_CATEGORY) ?: "All" }
    private val type by lazy { arguments?.getString(LIST_TYPE) ?: "All" }

    private val adapter by lazy { ListAdapter() }

    override fun bindLayout(): Int = R.layout.fragment_list

    override fun initData() {
        rv_list.layoutManager = GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false)
        rv_list.adapter = adapter
        loadData(1)

        viewModel.listLive.observe(this, Observer {
            adapter.setNewData(it.data ?: arrayListOf())
        })
    }

    private fun loadData(page: Int) {
        viewModel.setPage(page)
    }
}