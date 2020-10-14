package com.kish2.hermitcrabapp.model.handler;

public class MessageForHandler {
    public static final char ADAPTER_INIT = 0;          // adapter 初始化完成，用于activity初始化fragment的adapter
    public static final char LOCAL_DATA_LOADING = 1;    // 本地数据加载中
    public static final char LOCAL_DATA_LOADED = 2;     // 本地数据加载完成
    public static final char DATA_LOADED = 3;           // 数据加载完成，用于请求网络数据
    public static final char DATA_LOADING = 4;          // 数据加载中
    public static final char NETWORK_FAILURE = 5;       // 网络故障
    public static final char SYSTEM_FAILURE = 6;        // public static final char = 系统故障
    public static final char SERVICE_FAILURE = 7;       // 服务端故障
    public static final char DATA_UPDATE = 8;
}
