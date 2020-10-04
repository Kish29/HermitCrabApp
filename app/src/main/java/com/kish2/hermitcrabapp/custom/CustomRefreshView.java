package com.kish2.hermitcrabapp.custom;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.kish2.hermitcrabapp.R;

import static java.lang.Thread.sleep;

public class CustomRefreshView extends LinearLayout implements View.OnTouchListener {

    ProgressDialog progressDialog;

    /* 刷新头（根）视图 */
    View mRefreshHeader;
    /* 刷新头的布局参数 */
    MarginLayoutParams mRefreshHeaderLayoutParams;
    /* 隐藏的progressBar */
    ProgressBar mPrsBar;
    /* 旋转的图片 */
    ImageView mIndicateImg;
    /* 指示下拉和释放的箭头 */
    TextView mDescription;

    /* 子类视图
     * 可以是自定义视图 */
    View mSubView;

    /* 回调接口 */
    RefreshListener mListener;

    /* 是否已加载过一次Layout，这里onLayout中的初始化只需加载一次 */
    private boolean loadOnce = false;

    /* 是否进行高度的设置 */
    private boolean setHeight = false;

    /* 在被判定为滚动之前用户手指可以移动的最大值 */
    private static int touchSlop;
    /* 下拉头的高度 */
    private int hideHeaderHeight;

    /* 下拉头超出距离回滚一小部分的高度 */
    private static int ROLL_BACK_HEIGHT = 50;

    public static void setRollBackHeight(int rollBackHeight) {
        ROLL_BACK_HEIGHT = rollBackHeight;
    }

    /* 用户点击屏幕的第一个位置 */
    private float firstTouchY;

    /* 刷新状态 */
    private enum REFRESH_STATUS {
        DOWN_PULL_NOT_MEET_REFRESH,
        RELEASE_TO_REFRESH,
        REFRESHING,
        REFRESH_FINISHED
    }

    /* 当前状态 */
    private REFRESH_STATUS currentStatus = REFRESH_STATUS.REFRESH_FINISHED;

    /* 使用的刷新类型 */
    public enum REFRESH_TYPE {
        NORMAL_REFRESH,
        POP_REFRESH
    }

    public void setRefresh_type(REFRESH_TYPE refresh_type) {
        this.refresh_type = refresh_type;
    }

    /* 默认为普通刷新效果 */
    private REFRESH_TYPE refresh_type = REFRESH_TYPE.NORMAL_REFRESH;

    /* 构造函数会在使用该布局文件的activity的setContentView时进行调用
     * 并且传入的context是使用该布局文件的activity */

    /**
     * @param context        上下文
     * @param attrs          属性
     * @param firstChildView 第一个子视图，必须添加进来，推荐是自定义的ViewGroup
     */
    @SuppressLint("InflateParams")
    public CustomRefreshView(Context context, @Nullable AttributeSet attrs, ViewGroup firstChildView) {
        super(context, attrs);

        System.out.println("CustomRefreshView create and " + this + "and loadOnce is");


        /* 获取自定义的下拉刷新布局文件 */
        /* 如果attachToRoot=true,则布局文件将转化为View并绑定到root，然后返回root作为根节点的整个View
         * 如果attachToRoot=false,则布局文件转化为View但不绑定到root，返回以布局文件根节点为根节点的View
         * 所以这儿进行根节点的绑定 */
        mRefreshHeader = LayoutInflater.from(context).inflate(R.layout.ly_refresh_view, null, true);

        /* 获取刷新头的部件*/
        mPrsBar = mRefreshHeader.findViewById(R.id.progress_bar);
        mIndicateImg = mRefreshHeader.findViewById(R.id.arrow);
        mDescription = mRefreshHeader.findViewById(R.id.description);

        /* 注：touchSlop是系统所能识别出的被认为是滑动的最小距离.
         * 换个说法，当手指在屏幕上滑动时，如果两次滑动之间的距离小于这个常量，那么系统就不认为你是在进行滑动操作。
         * 滑动的距离太短，系统不认为它是滑动的。这是一个常量，和设备有关，在不同设备上这个值可能是不同的 */
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        /* 子视图赋值*/
        mSubView = firstChildView;

        /* 设置视图的排列方式，必须
         * 也可在xml中进行设置 */
        setOrientation(VERTICAL);

        /* addView都会提前调用onLayout函数 */
        /* 添加该刷新头(必须) */
        addView(mRefreshHeader, 0);

        /* 添加第一个子视图 */
        addView(mSubView, 1);

        /* 添加完之后*/
        setHeight = true;
    }

    /* onLayout函数用于布置子View，初始化子布局必须在这儿写 */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        /**
         * @注意这儿一定要加上对loadOnce的判断
         * @因为要在onLayout中进行setLayoutParams
         * @而setLayoutParams又会调用onLayout方法，造成死循环 */
        if (changed && !loadOnce) {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle(getResources().getString(R.string.refreshing));
            progressDialog.setMessage("冲冲冲！");

            /* 获取布局参数 */
            mRefreshHeaderLayoutParams = (MarginLayoutParams) mRefreshHeader.getLayoutParams();

            /* 获取刷新头高度
             * 注意负值 */
            hideHeaderHeight = -mRefreshHeader.getHeight();

            /* 用marginTop方法隐藏刷新头 */
            mRefreshHeaderLayoutParams.topMargin = hideHeaderHeight;
            /* 设置之后一定要设置布局参数才会生效 */
            mRefreshHeader.setLayoutParams(mRefreshHeaderLayoutParams);

            /* 获取子类视图 */
            mSubView = getChildAt(1);

            /* 注册子类视图的touch监听 */
            mSubView.setOnTouchListener(this);

            loadOnce = true;
        }
    }

    /* 下拉事件的各种逻辑 */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /* 不是刷新状态 */
        if (currentStatus != REFRESH_STATUS.REFRESHING) {
            /* switch屏幕事件 */
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    /* 获取点击时距离屏幕的纵坐标并记录
                     * 坐标原点在左上角 */
                    firstTouchY = event.getRawY();
                    break;
                /* 用户手指移动事件 */
                case MotionEvent.ACTION_MOVE:
                    /* 计算新的偏移量
                     * 用户的滑动事件一定会先走点击事件（ACTION_DOWN） */
                    int distance = (int) (event.getRawY() - firstTouchY);

                    /* 如果手指小于等于系统最小滑动判断 */
                    if (distance <= touchSlop) {
                        return false;
                    }
                    /* 经过上面的过滤，现在可以将当前状态设置为下拉但没达到释放刷新的程度 */
                    currentStatus = REFRESH_STATUS.DOWN_PULL_NOT_MEET_REFRESH;
                    /* 滑动距离超过header的高度 */
                    if (mRefreshHeaderLayoutParams.topMargin >= ROLL_BACK_HEIGHT) {
                        mDescription.setText("释放立即刷新");
                        /* 设置当前状态为释放即刷新 */
                        currentStatus = REFRESH_STATUS.RELEASE_TO_REFRESH;
                        return false;
                    }
                    /* 通过设置下拉头的topMargin值，来实现下拉效果 */
                    /* 如果用户手指一次性下拉距离过大，要给予限制 */
                    if (distance / 2 + hideHeaderHeight >= ROLL_BACK_HEIGHT)
                        distance = 2 * (ROLL_BACK_HEIGHT - hideHeaderHeight);
                    /* distance / 2 实现较为平滑的效果 */
                    mRefreshHeaderLayoutParams.topMargin = (distance / 2) + hideHeaderHeight;
                    mRefreshHeader.setLayoutParams(mRefreshHeaderLayoutParams);
                    break;
                /* 松手状态 */
                case MotionEvent.ACTION_UP:
                default:
                    /* 判断用户的下拉程度是否是刷新的程度 */
                    if (currentStatus == REFRESH_STATUS.RELEASE_TO_REFRESH) {
                        if (refresh_type == REFRESH_TYPE.NORMAL_REFRESH) {
                            new RefreshingTask().execute();
                        } else {
                            new HideHeaderTask().execute();
                            // 中间弹出刷新动画的效果
                            popRefreshingWidget(true);
                            /* 设置屏幕失去焦点不可交互(可选)
                            /* 执行刷新过程 */
                            new PopRefreshingTask().execute();
                        }
                    } else {
                        new HideHeaderTask().execute();
                    }
                    break;
            }
            /* 时刻记得更新下拉头中的信息
             * 并且让子元素失去焦点 */
            if (currentStatus == REFRESH_STATUS.RELEASE_TO_REFRESH) {
                /* 当前正处于下拉或释放状态，要让ListView失去焦点
                 * 否则被点击的那一项会一直处于选中状态 */
                mSubView.setPressed(false);
                mSubView.setFocusable(false);
                mSubView.setFocusableInTouchMode(false);
                return true;
            }
        }
        return false;
    }

    private void updateHeaderView() {
        switch (currentStatus) {
            case REFRESHING:
                mDescription.setText(getResources().getString(R.string.refreshing));
                mIndicateImg.clearAnimation();
                mIndicateImg.setVisibility(INVISIBLE);
                mPrsBar.setVisibility(VISIBLE);
                break;
            case REFRESH_FINISHED:
            case DOWN_PULL_NOT_MEET_REFRESH:
                mDescription.setText(getResources().getString(R.string.pull_to_refresh));
                mIndicateImg.setVisibility(VISIBLE);
                mPrsBar.setVisibility(INVISIBLE);
                break;
            case RELEASE_TO_REFRESH:
                mDescription.setText(getResources().getString(R.string.release_to_refresh));
                mIndicateImg.setVisibility(VISIBLE);
                mPrsBar.setVisibility(INVISIBLE);
                break;
        }
    }

    private void popRefreshingWidget(boolean isShow) {
        if (isShow) {
            progressDialog.show();
            //TODO
            /* 屏幕失去焦点 */
        } else {
            progressDialog.dismiss();
            //TODO
            /* 屏幕重获焦点 */
        }
    }

    @SuppressLint("StaticFieldLeak")
    class PopRefreshingTask extends AsyncTask<Void, Integer, Boolean> {

        /* 直接调用onRefresh方法 */
        @WorkerThread
        @Override
        protected Boolean doInBackground(Void... voids) {
            if (mListener != null) {
                mListener.onRefresh();
            }
            return true;
        }

        @SuppressLint("ShowToast")
        @Override
        protected void onPostExecute(Boolean succeed) {
            if (succeed) {
                Toast.makeText(getContext(), "我冲完了，你呢？", Toast.LENGTH_SHORT).show();
                popRefreshingWidget(false);
            }
            updateHeaderView();
        }
    }


    @SuppressLint("StaticFieldLeak")
    class RefreshingTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            /* 回到marginTop位置0处 */
            int topMargin = mRefreshHeaderLayoutParams.topMargin;
            while (true) {
                topMargin -= 20;
                if (topMargin <= 0) {
                    topMargin = 0;
                    break;
                }
                publishProgress(topMargin);
                /* 睡眠10毫秒营造平滑移动的效果 */
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /* 正在刷新 */
            currentStatus = REFRESH_STATUS.REFRESHING;
            publishProgress(0);
            /* 后台执行该线程的刷新过程，接口由使用该listener的类实现该接口 */
            if (mListener != null) {
                mListener.onRefresh();
            }
            return null;
        }

        /* 回到marginTop位置0处 */
        @MainThread
        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            updateHeaderView();
            mRefreshHeaderLayoutParams.topMargin = topMargin[0];
            mRefreshHeader.setLayoutParams(mRefreshHeaderLayoutParams);
        }
    }


    @SuppressLint("StaticFieldLeak")
    class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

        @MainThread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @WorkerThread
        @Override
        protected Integer doInBackground(Void... voids) {
            int topMargin = mRefreshHeaderLayoutParams.topMargin;
            while (true) {
                topMargin--;
                if (topMargin <= hideHeaderHeight) {
                    topMargin = hideHeaderHeight;
                    break;
                }
                /* 该方法将后台进度返回给onProgressUpdate函数*/
                publishProgress(topMargin);
            }
            /* 返回值返回给onPostExecute函数 */
            return topMargin;
        }

        @MainThread
        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            mRefreshHeaderLayoutParams.topMargin = topMargin[0];
            mRefreshHeader.setLayoutParams(mRefreshHeaderLayoutParams);
        }

        @MainThread
        @Override
        protected void onPostExecute(Integer topMargin) {
            mRefreshHeaderLayoutParams.topMargin = topMargin;
            mRefreshHeader.setLayoutParams(mRefreshHeaderLayoutParams);
            /* 更新信息 */
            updateHeaderView();
        }
    }

    /* 当刷新完成后应当调用该方法 */
    public void refreshComplete() {
        currentStatus = REFRESH_STATUS.REFRESH_FINISHED;
        /* 普通动画收回刷新头 */
        if (refresh_type == REFRESH_TYPE.NORMAL_REFRESH)
            new HideHeaderTask().execute();
    }

    /* 下拉刷新的监听器
     * 必须初始化一个新的监听器，以执行回调方法 */
    public void setOnRefreshListener(RefreshListener refreshListener) {
        this.mListener = refreshListener;
    }

    /* 下拉刷新的监听器，使用下拉刷新的地方注册此监听器来获取刷新的回调函数 */
    public interface RefreshListener {
        void onRefresh();
    }

}


