package com.hxz.gankio.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.baseui.view.BaseFragment
import com.hxz.gankio.R
import com.hxz.gankio.activity.DetailActivity
import com.hxz.gankio.activity.GirlDetailActivity
import com.hxz.gankio.adapter.BaseGankAdapter
import com.hxz.gankio.adapter.setGankManager
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

    private var currentPage = 1

    private val viewModel by lazy { viewModels<ListViewModel>(factoryProducer = {ListFactory(category,type)}).value }

    private val category by lazy { arguments?.getString(LIST_CATEGORY) ?: "All" }
    private val type by lazy { arguments?.getString(LIST_TYPE) ?: "All" }

    private val adapter by lazy { rv_list.adapter as BaseGankAdapter }

    override fun bindLayout(): Int = R.layout.fragment_list

    override fun initData() {
        rv_list.setGankManager()
        initRefresh()

        adapter.setClickInvoke { position, data ->
            if (adapter.getItemViewType(position) != 4) DetailActivity.startDetail(requireContext(),data._id)
            else {
                GirlDetailActivity.startGirlDetail(requireContext(),ArrayList(adapter.data),currentPage,position)
            }
        }

        viewModel.listLive.observe(this, Observer {
            currentPage = it.page
            if (it.page == 1) {
                smart_list.finishRefresh()
                adapter.setNewData(it.data ?: arrayListOf())
            }else {
                smart_list.finishLoadMore()
                adapter.loadDataList(it.data ?: arrayListOf())
            }
            if (it.page == it.page_count) {
                smart_list.setEnableLoadMore(false)
            }
        })
    }

    private fun initRefresh() {
        smart_list.setOnRefreshListener { loadData(1) }
        smart_list.setOnLoadMoreListener { loadData(++currentPage) }
        smart_list.autoRefresh()
    }

    private fun loadData(page: Int) {
        viewModel.setPage(page)
    }
}