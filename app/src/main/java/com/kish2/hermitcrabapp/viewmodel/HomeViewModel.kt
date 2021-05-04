package com.kish2.hermitcrabapp.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.kish2.hermitcrabapp.data.CampusInform
import com.kish2.hermitcrabapp.state.HomeState
import com.kish2.hermitcrabapp.state.LoadStatus

class HomeViewModel(initialState: HomeState) : MavericksViewModel<HomeState>(initialState) {

	fun fetchCampusMessageList() {
		// here to load data
		setState {
			copy(
				status = LoadStatus.Success,
				informList = mutableListOf(
					CampusInform(
						img = "http://cipp.ustb.edu.cn/attachment/0/20210429/FD1689EB591A4B8F82BDAFC395E4A82F_%E6%B5%B7%E6%8A%A5.jpg",
						activityDate = "2021年5月6日",
						title = "立言辩坛——“建党百年”主题辩论赛开幕式",
						address = "时代凌宇报告厅",
						weekDay = "星期四"
					),
					CampusInform(
						img = "http://cipp.ustb.edu.cn/attachment/0/20210423/E57C5AB35E8E42DB84E08195732DD7DA_%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20210423090722.jpg",
						activityDate = "2021年05月08日",
						title = "百年历史共传承,接力奔跑颂青春",
						address = "北京科技大学 校园",
						weekDay = "星期六"
					),
					CampusInform(
						img = "http://cipp.ustb.edu.cn/attachment/0/20210415/B97349B252684EFDAC00D7F740D0C4D3_b9bfc527adfb597780478b50f6e05ed.jpg",
						activityDate = "2021年05月11日",
						title = "托举行动——2021届毕业生春季空中双选会",
						address = "线上",
						weekDay = "星期二"
					),
					CampusInform(
						img = "http://cipp.ustb.edu.cn/attachment/0/20210422/C50216A034934D7A8AF5618962A3F6CE_cb571fb6d3c900fef1f2373104be689.jpg",
						activityDate = "2021年05月12日",
						title = "大学生职业生涯规划与发展辅导月系列活动",
						address = "体育馆、时代凌宇报告厅",
						weekDay = "星期三"
					),
					CampusInform(
						img = "http://cipp.ustb.edu.cn/attachment/0/20210422/D3800BEF32AB4EE098153CB1CBE0BCAC_shixishengshuangxuanhui.png",
						activityDate = "2021年05月14日",
						title = "北京科技大学2021年实习生专场双选会",
						address = "体育馆中心场地",
						weekDay = "星期五"
					),
					CampusInform(
						img = "http://cipp.ustb.edu.cn/attachment/0/20210422/DE77AABA83C2494E95E3938A74916FF0_01cfea66def3eaf509d3a668b88ea76.jpg",
						activityDate = "2021年05月14日",
						title = "预就业实习计划",
						address = "体育馆、时代凌宇报告厅",
						weekDay = "星期五"
					)
				)
			)
		}
	}

}