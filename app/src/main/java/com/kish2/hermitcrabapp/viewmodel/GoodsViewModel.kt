package com.kish2.hermitcrabapp.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.kish2.hermitcrabapp.data.ThirdPersonInfo
import com.kish2.hermitcrabapp.data.TradeGoods
import com.kish2.hermitcrabapp.state.GoodsState
import com.kish2.hermitcrabapp.state.LoadStatus

class GoodsViewModel(initialState: GoodsState) : MavericksViewModel<GoodsState>(initialState) {

	fun fetchGoodsList() {
		setState {
			copy(
				status = LoadStatus.Success,
				goodsList = listOf(
					TradeGoods(
						img = "https://img10.360buyimg.com/seckillcms/s250x250_jfs/t1/165159/36/20447/74138/6084f594E483ca8f0/770d63e4c3f510d6.jpg",
						tag = "电子产品",
						goodsName = "小米Redmi红米Note9 5G手机 云墨灰 全网通 8GB+128GB",
						price = 1419.00f,
						pubPeopleInfo = ThirdPersonInfo(
							"Kish2",
							"https://i2.hdslb.com/bfs/face/5590aa7f6eaaf1e824e2611efa3cf7956fc3a844.jpg@128w_128h_1o.webp"
						),
						desireDesc = ""
					),
					TradeGoods(
						img = "https://img11.360buyimg.com/seckillcms/s250x250_jfs/t1/164197/6/21771/96478/608a1753E2b9e331c/84f1e253e812bb31.jpg",
						tag = "毛绒玩具",
						goodsName = "圆梦筑成 阿狸天生一对盲盒迷你公仔手办家居摆件",
						price = 29.00f,
						pubPeopleInfo = ThirdPersonInfo(
							"Kish2",
							"https://i2.hdslb.com/bfs/face/5590aa7f6eaaf1e824e2611efa3cf7956fc3a844.jpg@128w_128h_1o.webp"
						),
						desireDesc = ""
					),
					TradeGoods(
						img = "https://img11.360buyimg.com/seckillcms/s250x250_jfs/t1/182617/16/1546/143365/608b6fddEeb3fbc36/211bfcec80cd0ed1.jpg",
						tag = "电子产品",
						goodsName = "漫步者（EDIFIER）MiniBuds 真无线蓝牙耳机 音乐耳机 迷你运动耳机 手机耳机 通用苹果安卓手机 雾霾蓝",
						price = 165f,
						pubPeopleInfo = ThirdPersonInfo(
							"Kish2",
							"https://i2.hdslb.com/bfs/face/5590aa7f6eaaf1e824e2611efa3cf7956fc3a844.jpg@128w_128h_1o.webp"
						),
						desireDesc = ""
					),
					TradeGoods(
						img = "https://img10.360buyimg.com/seckillcms/s200x200_jfs/t1/168659/17/20539/97430/608381bbE280cc7e7/d992edf9f22669f8.jpg!cc_200x200.webp",
						tag = "食品",
						goodsName = "猴头菇粗粮曲奇饼干 400g礼盒装 儿童零食饱腹早餐代餐饼干办公零食整箱 含糖400g",
						price = 9.9f,
						pubPeopleInfo = ThirdPersonInfo(
							"Kish2",
							"https://i2.hdslb.com/bfs/face/5590aa7f6eaaf1e824e2611efa3cf7956fc3a844.jpg@128w_128h_1o.webp"
						),
						desireDesc = ""
					),
					TradeGoods(
						img = "https://img20.360buyimg.com/seckillcms/s200x200_jfs/t1/163053/1/20757/183423/6082787eE7b8e0fe5/65f125de341631df.jpg!cc_200x200.webp",
						tag = "文具",
						goodsName = "铭昌泰大容量彩色手帐笔套装莫兰迪九色学生标记笔全针管彩绘中性笔0.5mm 北欧系9色",
						price = 9.90f,
						pubPeopleInfo = ThirdPersonInfo(
							"Kish2",
							"https://i2.hdslb.com/bfs/face/5590aa7f6eaaf1e824e2611efa3cf7956fc3a844.jpg@128w_128h_1o.webp"
						),
						desireDesc = ""
					)
				)
			)
		}
	}

}