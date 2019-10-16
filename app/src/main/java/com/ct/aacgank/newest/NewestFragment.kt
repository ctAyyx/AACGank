package com.ct.aacgank.newest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ct.aacgank.AppDatabase
import com.ct.aacgank.classify.LinearItemDecoration
import com.ct.aacgank.classify.RoofItemDecoration
import com.ct.aacgank.classify.adapter.ClassifyAdapter
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.common.net.Status
import com.ct.aacgank.common.ui.BaseFragment
import com.ct.aacgank.common.util.e
import com.ct.aacgank.databinding.RefreshRecyclerBinding
import com.ct.aacgank.newest.adapter.NewestAdapter
import com.ct.aacgank.newest.decoration.NewestItemDecoration
import com.ct.aacgank.newest.decoration.NewestRoofItemDecoration
import com.ct.aacgank.newest.repository.NetWorkBoundResource
import com.ct.aacgank.newest.repository.NewestRepository
import com.ct.aacgank.newest.test.NewestBoundaryCallback
import com.ct.aacgank.newest.viewmodel.NewestViewModel
import com.ct.aacgank.newest.viewmodel.NewestViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

/**
 * 文件名 NewestFragment
 * 创建者  CT
 * 时 间  2019/9/20 11:41
 * TODO  干货最新数据
 *       1.RecyclerView添加头部布局
 *       2.使用ItemDecoration实现分组
 */
class NewestFragment : BaseFragment() {

    private lateinit var adapter: NewestAdapter


    private lateinit var binding: RefreshRecyclerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RefreshRecyclerBinding.inflate(inflater, container, false).apply {

        recyclerView.adapter = NewestAdapter().also { adapter = it }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.addItemDecoration(NewestItemDecoration())

        //未完成  实现悬浮吸顶
        // recyclerView.addItemDecoration(NewestRoofItemDecoration())

        binding = this


    }
        .root

    override fun onResume() {
        super.onResume()
         subscribeUi()
        //test()
        // test2()
        //test3()
    }


    private fun subscribeUi() {


        val viewModel = ViewModelProviders.of(
            this,
            NewestViewModelFactory(NewestRepository(), requireContext())
        )
            .get(NewestViewModel::class.java)

        viewModel.newestData.observe(this, Observer {
            if (!it.isNullOrEmpty())
                adapter.submitList(it)
        })


        viewModel.refreshState.observe(this, Observer {
            binding.swipeRefresh.isRefreshing = it.status == Status.RUNNING
        })


        viewModel.networkState.observe(this, Observer {
            if (it.status == Status.FAILED)
                Toast.makeText(requireContext(), it.msg ?: "网络请求失败", Toast.LENGTH_LONG).show()
        })


        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

    }

    private fun test2() {
        val viewModel = ViewModelProviders.of(
            this,
            NewestViewModelFactory(NewestRepository(), requireContext())
        )
            .get(NewestViewModel::class.java)

        viewModel.newestData.observe(this, Observer {
            if (!it.isNullOrEmpty())
                adapter.submitList(it)
        })

        viewModel.refreshState.observe(this, Observer {
            binding.swipeRefresh.isRefreshing = it.status == Status.RUNNING
        })


        viewModel.networkState.observe(this, Observer {
            if (it.status == Status.FAILED)
                Toast.makeText(requireContext(), it.msg ?: "网络请求失败", Toast.LENGTH_LONG).show()
        })


        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }


    /**
     * 使用数据库作为缓存
     * */
    private fun test() {

        object : NetWorkBoundResource<PagedList<ClassifyBean>>() {
            override fun loadFromDb(): LiveData<PagedList<ClassifyBean>> {

                return AppDatabase.getInstance(requireContext())
                    .classifyDao()
                    .getAllClassifies2()
                    .toLiveData(pageSize = 10)
            }

            override fun shouldFetch(data: PagedList<ClassifyBean>?): Boolean {
                Log.e("TAG", "是否需要从网络加载数据----${data.isNullOrEmpty()}")
                return false//data.isNullOrEmpty()
            }

            override fun saveCallResult(item: PagedList<ClassifyBean>) {

                runBlocking(Dispatchers.IO) {
                    AppDatabase.getInstance(requireContext())
                        .classifyDao()
                        .insertClassifies(item)
                }
                Log.e("TAG", "saveCallResult===")
                // adapter.submitList(item)
            }

            override fun createCall(): LiveData<PagedList<ClassifyBean>> =
                NewestRepository().getNewestData().pagedList

        }
            .asLiveData()
            .observe(this, Observer {
                Log.e("TAG", "数据改变-----》")
                adapter.submitList(it)
            })

    }


    //对MediatorLiveData的使用
    private fun test3(){

        val result = MediatorLiveData<List<ClassifyBean>>()

       val dbResource:LiveData<List<ClassifyBean>> = AppDatabase.getInstance(requireContext())
            .classifyDao()
            .getAllClassifies()
            //.toLiveData(10)
        dbResource.observe(this, Observer {
            Log.e("TAG","数据库获取的数据....${dbResource.value}")
        })


        result.addSource(dbResource){
            Log.e("TAG","当前数据库数据:$it")
        }


        (result as LiveData<PagedList<ClassifyBean>>)
            .observe(this, Observer {
                Log.e("TAG","==================")
            })
    }
}