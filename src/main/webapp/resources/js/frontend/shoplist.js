$(function(){
	var loading=false;
	//分页允许返回的最大条数
	var maxItems=999;
	//一页返回的最大条数
	var pageSize=10;
	//获取店铺列表的URL
	var listUrl='/o2o/frontend/listshops';
	//获取店铺类别列表以及区域列表的URL
	var searchDivUrl='/o2o/frontend/listshopspageinfo';
	//页码
	var pageNum=1;
	var parentId=getQueryString('parentId');
	var areaId='';
	var shopCategoryId='';
	var shopName='';
	//渲染出店铺类别列表及区域列表供查询
	getSearchDivData();
	//预先加载10条店铺信息
	addItems(pageSize,pageNum);
	/*
	 * 店铺类别列表及区域列表信息
	 */
	function getSearchDivData(){
		// 如果传入了parentId，则取出此一级类别下面的所有二级类别
		var url=searchDivUrl+'?'+'parentId='+parentId;
		$.ajax({
			url:url,
			type:'get',
			dataType:'json',
			success:function(data){
				if(data.success){
					//获取后台返回的店铺类别列表
					var shopCategoryList=data.shopCategoryList;
					var html='';
					html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
					// 遍历店铺类别列表，拼接出a标签集
					shopCategoryList.map(function(item, index) {
								html += '<a href="#" class="button" data-category-id='
										+ item.shopCategoryId
										+ '>'
										+ item.shopCategoryName
										+ '</a>';
							});
					// 将拼接好的类别标签嵌入前台的html组件里
					$('#shoplist-search-div').html(html);
					var selectOptions = '<option value="">全部街道</option>';
					// 获取后台返回过来的区域信息列表
					var areaList=data.areaList;
					// 遍历区域信息列表，拼接出option标签集
					areaList.map(function(item, index) {
						selectOptions += '<option value="'
								+ item.areaId + '">'
								+ item.areaName + '</option>';
					});
					//将标签集添加到area列表里
					$('#area-search').html(selectOptions);
				}
			}
		});
	}
	/*
	 * 获取分页展示的店铺列表信息
	 */
	function addItems(pageSize,pageIndex){
		// 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&parentId=' + parentId + '&areaId=' + areaId
				+ '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
		// 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
		loading=true;
		$.ajax({
			url:url,
			type:'get',
			dataType:'json',
			success:function(data){
				if(data.success){
					//获取当前查询条件下店铺总数
					maxItems=data.count;
					var html='';
					//遍历店铺列表拼接出卡片集合
					data.shopList.map(function(item, index) {
						html += '' + '<div class="card" data-shop-id="'
								+ item.shopId + '">' + '<div class="card-header">'
								+ item.shopName + '</div>'
								+ '<div class="card-content">'
								+ '<div class="list-block media-list">' + '<ul>'
								+ '<li class="item-content">'
								+ '<div class="item-media">' + '<img src="'
								+ item.shopImg + '" width="44">' + '</div>'
								+ '<div class="item-inner">'
								+ '<div class="item-subtitle">' + item.shopDesc
								+ '</div>' + '</div>' + '</li>' + '</ul>'
								+ '</div>' + '</div>' + '<div class="card-footer">'
								+ '<p class="color-gray">'
								+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
								+ '更新</p>' + '<span>点击查看</span>' + '</div>'
								+ '</div>';
					});
					$('.list-div').append(html);
					//获取目前为止显示的卡片总数
					var total=$('.list-div .card').length;
					if(total>=maxItems){
						// 隐藏提示符
						$('.infinite-scroll-preloader').hide();
					}else{
						$('.infinite-scroll-preloader').show();
					}
					//否则页码数加一
					pageNum+=1;
					//加载结束，可以再次加载了
					loading=false;
					//刷新页面显示加载的店铺
					$.refreshScroller();
				}
			}
		});
	}
	//下滑屏幕自动进行分页搜索
	$(document).on('infinite','.infinite-scroll-bottom',function(){
		if(loading){
			return;
		}
		addItems(pageSize,pageNum);
	});
	//点击店铺的卡片进入店铺详情页
	$('.shop-list').on('click','.card',function(e){
		var shopId=e.currentTarget.dataset.shopId;
		window.location.href='/o2o/frontend/shopdetail?shopId='+shopId;
	});
	//选择新的店铺类别之后重置页码，按照新的类别去查询
	$('#shoplist-search-div').on('click','.button',function(e){
		if(parentId){//如果传递过来的是父类下的子类
			shopCategoryId=e.target.dataset.categoryId;
			//若之前选定了别的category，则移除其选定效果，该选成新的
			if($(e.target).hasClass('button-fill')){
				$(e.target).removeClass('button-fill');
				shopCategoryId='';
			}else{
				$(e.target).addClass('button-fill').siblings().removeClass('button-fill');
			}
			//由于查询条件改变，清空列表再查询
			$('.list-div').empty();
			pageNum=1;
			addItems(pageSize,pageNum);
		}else{
			parentId=e.target.dataset.categoryId;
			if($(e.target).hasClass('button-fill')){
				$(e.target).removeClass('button-fill');
				parentId='';
			}else{
				$(e.target).addClass('button-fill').siblings().removeClass('button-fill');
			}
			//由于查询条件改变，清空列表再查询
			$('.list-div').empty();
			pageNum=1;
			addItems(pageSize,pageNum);
			parentId='';
		}
	});
	//需要查询的店铺名字发生改变后重置页码，按照新的类别去查询
	$('#search').on('change',function(e){
		shopName=e.target.value;
		$('.list-div').empty();
		pageNum=1;
		addItems(pageSize,pageNum);
	});
	//区域信息发生变化后，重置页码，按照新的类别去查询
	$('#area-search').on('change',function(){
		areaId=$('#area-search').val();
		$('.list-div').empty();
		pageNum=1;
		addItems(pageSize,pageNum);
	});
	//点击打开右侧栏
	$('#me').click(function(){
		$.openPanel('#panel-right-demo');
	});
	//初始化页面
	$.init();
});