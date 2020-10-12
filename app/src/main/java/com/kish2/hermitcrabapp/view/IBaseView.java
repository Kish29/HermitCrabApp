package com.kish2.hermitcrabapp.view;

import android.content.Context;

/* IBaseView 必须给子类给予约束 */
public interface IBaseView {

    /**
     * @子线程不可以改变view中主键的属性，但是能获取或者设置监听
     */

    /* 获取视图组件的属性，如宽高*/
    /* 在View的onGlobalFocusChangeListener监听器中 */
    public void getLayoutComponentsAttr();

    /* 设置自定义视图部分或Fragment获取父容器组件，如布局、颜色、主题等*/
    // 放在主线程
    public void getAndSetLayoutView();

    /* 数据加载完成后设置相应组件的数据显示 */
    public void loadDataComplete();

    /* 注册控制事务 */
    /* 子线程 */
    public void registerViewComponentsAffairs();

    /* 初始化presenter */
    public void attachPresenter();

    /* 必须调用，detach连接的presenter，调用presenter的detachView方法*/
    public void detachPresenter();

}
