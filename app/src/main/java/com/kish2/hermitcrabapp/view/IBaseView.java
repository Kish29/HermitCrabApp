package com.kish2.hermitcrabapp.view;

/* IBaseView 必须给子类给予约束 */
public interface IBaseView {

    /**
     * @子线程不可以改变view中主键的属性，但是能获取或者设置监听
     */
    /* 设置自定义视图部分或Fragment获取父容器组件，如布局、颜色、主题等*/
    // 放在主线程
    void getAndSetLayoutView();

    /* 获取视图组件的属性，如宽高*/
    /* 在View的onGlobalFocusChangeListener监听器中 */
    void getLayoutComponentsAttr();

    /* presenter或自己加载本地数据 */
    /* 首次加载数据 */
    void loadData();

    /* 数据的更新操作 */
    void refreshData();

    /* 注册控制事务 */
    void registerViewComponentsAffairs();

    /* 初始化presenter */
    void attachPresenter();

    void initHandler();

    /* 必须调用，detach连接的presenter，调用presenter的detachView方法
    public void detachPresenter();
    用lifeCycle，在presenter中detach*/
}
