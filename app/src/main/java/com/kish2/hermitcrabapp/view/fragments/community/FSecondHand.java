package com.kish2.hermitcrabapp.view.fragments.community;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.RecyclerInformAdapter;
import com.kish2.hermitcrabapp.bean.Inform;
import com.kish2.hermitcrabapp.utils.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.IBaseFragment;
import com.kish2.hermitcrabapp.view.fragments.home.FLatest;
import com.kish2.hermitcrabapp.view.fragments.home.HomeFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FSecondHand extends FCommunityBase implements IBaseFragment {
    @BindView(R.id.srl_refresh_list)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_container_items)
    RecyclerView mInformList;

    RecyclerInformAdapter mInformListAdapter;

    private Handler mHandler;

    private float mFirstY;
    private float mTouchSlop;
    private float mCurrentY;

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1:
                        loadDataComplete();
                        break;
                    case 2:
                    case 3:
                    default:
                        break;
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("LatestFragment", "onCreateView run.");
        View view = inflater.inflate(R.layout.sub_fragment_content, container, false);
        ButterKnife.bind(this, view);
        /* 主线程 */
        getAndSetLayoutView();
        mRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getLayoutComponentsAttr();
            }
        });
        return view;
    }

    @Override
    protected void loadData() {
        registerViewComponentsAffairs();
        mRefreshLayout.setRefreshing(true);
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<Inform> informArrayList = new ArrayList<>();
                /* 测试，添加ListView，查看滑动效果 */
                for (int i = 0; i < 20; i++) {
                    Inform inform = new Inform();
                    inform.setTitle("这是标题");
                    inform.setDate(new Date());
                    int j = new Random().nextInt(11);
                    if (j <= 5)
                        inform.setImgSrc("yes");
                    else inform.setImgSrc("no");
                    informArrayList.add(inform);
                }
                System.out.println("data load complete");
                mInformListAdapter = new RecyclerInformAdapter(informArrayList, getContext());
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    @Override
    public void getLayoutComponentsAttr() {

    }

    @Override
    public void getAndSetLayoutView() {
        /* 父Fragment*/
        mCommunity = (CommunityFragment) requireParentFragment();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mInformList.setLayoutManager(layoutManager);
        /*StaggeredLayout不需要设置该项
        mInformList.setVerticalScrollBarEnabled(false);*/
    }

    @Override
    public void loadDataComplete() {
        mInformList.setAdapter(mInformListAdapter);
        mRefreshLayout.setRefreshing(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void registerViewComponentsAffairs() {
        /* 因为onScrollChangedListener的onScrolled方法是回调方法，要等到item停下来时才调用，所以这儿直接监听touch事件 */
        mInformList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();
                        if (mCurrentY - mFirstY > mTouchSlop) {
                            // 下滑 显示titleBar
                            mCommunity.bottomBarHide(false);
                        } else if (mFirstY - mCurrentY > mTouchSlop) {
                            // 上滑 隐藏titleBar
                            mCommunity.bottomBarHide(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                /*因为要让listView继续滑动，所以这儿返回false
                事件未被消费，向下传递，调用onTouchEvent*/
                return false;
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(getContext(), "刷新", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
