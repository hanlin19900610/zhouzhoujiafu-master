package com.mufeng.libs.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.KeyboardUtils
import com.itxca.msa.IMsa
import com.itxca.msa.msa
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.mufeng.libs.utils.getVmClazz
import com.mufeng.libs.utils.inflateBindingWithGeneric

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment(),
    BaseActivity.MyOnTouchListener, IMsa by msa() {

    private val handler = Handler(Looper.myLooper()!!)

    protected lateinit var vm: VM
        private set

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    //是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mActivity: AppCompatActivity

    var loadingDialog: LoadingPopupView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBindingWithGeneric(layoutInflater, container, false)
        (requireActivity() as BaseActivity<*, *>).registerMyOnTouchListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as BaseActivity<*, *>).unregisterMyOnTouchListener(this)
        _binding = null
    }


    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initManageStartActivity()
        isFirst = true
        initArgument()
        vm = createViewModel()
        initView(savedInstanceState)
        startObserve()
        initData()
    }

    open fun initArgument() {}
    abstract fun initView(savedInstanceState: Bundle?)
    open fun startObserve() {}

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            handler.postDelayed({
                lazyLoadData()
                isFirst = false
            }, lazyLoadTime())
        }
    }

    /**
     * Fragment执行onCreate后触发的方法
     */
    open fun initData() {}

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 300
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    open fun initImmersionBar() {

    }

    open fun showLoading(msg: String = "加载中...") {
        if (loadingDialog == null) {
            loadingDialog = XPopup.Builder(context)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .hasShadowBg(false)
                .hasStatusBarShadow(true)
                .asLoading(msg)
        }
        if (loadingDialog?.isShow == true) {
            loadingDialog?.dismiss()
        }
        loadingDialog?.show()
    }

    open fun dismissLoading() {
        loadingDialog?.dismiss()
    }

    override fun onTouch(ev: MotionEvent?): Boolean {
        KeyboardUtils.hideSoftInput(mActivity)
        return true
    }

}