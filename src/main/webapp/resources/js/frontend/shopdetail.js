$(function() {
	var loading = false;
	// 分页允许返回的最大条数
	var maxItems = 20;
	var pageSize = 10;
	// 列出商品列表的url
	var listUrl = '/o2o/frontend/listproductsbyshop';
	// 默认的页码
	var pageNum = 1;
	// 从地址里获取shopId
	var shopId = getQueryString('shopId');
	var productCategoryId = '';
	var productName = '';
	// 获取本店铺信息以及商品类别信息列表的URL
	var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId=' + shopId;
	// 渲染出店铺基本信息以及商品类别列表以供搜索
	getSearchDivData();
	// 预先加载10条商品信息
	addItems(pageSize, pageNum);
	// 给兑换礼品的a标签赋值兑换礼品的URL，2.0讲解
	// $('#exchangelist').attr('href',
	// '/o2o/frontend/awardlist?shopId='+shopId);
	// 获取本店铺信息以及商品类别信息列表
	function getSearchDivData() {
				$.ajax({
					url : searchDivUrl,
					type : 'get',
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							var shop = data.shop;
							$('#shop-cover-pic').attr('src', shop.shopImg);
							$('#shop-update-time').html(
									new Date(shop.lastEditTime)
											.Format("yyyy-MM-dd"));
							$('#shop-name').html(shop.shopName);
							$('#shop-desc').html(shop.shopDesc);
							$('#shop-addr').html(shop.shopAddr);
							$('#shop-phone').html(shop.phone);
							// 获取后台返回的商品列表
							var productCategoryList = data.productCategoryList;
							var html = '';
							// 遍历商品列表，生成可以点击搜索相应商品类别下的商品的a标签
							productCategoryList
									.map(function(item, index) {
										html += '<a href="#" class="button" data-product-search-id='
												+ item.productCategoryId
												+ '>'
												+ item.productCategoryName
												+ '</a>';
									});
							// 将商品类别a标签绑定到相应的html组件中
							$('#shopdetail-button-div').html(html);
						}
					}
				});
	}
	/*
	 * 获取分页展示的商品列表信息
	 */
	function addItems(pageSize, pageIndex) {
		// 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productCategoryId=' + productCategoryId
				+ '&productName=' + productName + '&shopId=' + shopId;
		// 设定加载符，若还在后台加载数据则不能再次访问后台
		loading = true;
		// 访问后台获取相应查询条件下的商品列表
		$.ajax({
			url : url,
			type : 'get',
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					// 获取当前查询条件下的商品总数
					maxItems = data.count;
					var html = '';
					data.productList.map(function(item, index) {
						html += '' + '<div class="card" data-product-id='
								+ item.productId
								+ '>'
								+ '<div class="card-header">'
								+ item.productName
								+ '</div>'
								+ '<div class="card-content">'
								+ '<div class="list-block media-list">'
								+ '<ul>'
								+ '<li class="item-content">'
								+ '<div class="item-media">'
								+ '<img src="'
								+ item.imgAddr
								+ '" width="44">'
								+ '</div>'
								+ '<div class="item-inner">'
								+ '<div class="item-subtitle">'
								+ item.productDesc
								+ '</div>'
								+ '</div>'
								+ '</li>'
								+ '</ul>'
								+ '</div>'
								+ '</div>'
								+ '<div class="card-footer">'
								+ '<p class="color-gray">'
								+ new Date(item.lastEditTime)
										.Format("yyyy-MM-dd") + '更新</p>'
								+ '<span>点击查看</span>' + '</div>' + '</div>';
					});
					$('.list-div').append(html);
					// 获取目前为止显示的卡片总数
					var total = $('.list-div .card').length;
					if (total >= maxItems) {
						$('.infinite-scroll-preloader').hide();
					} else {
						$('.infinite-scroll-preloader').show();
					}
					pageNum += 1;
					loading = false;
					// 刷新页面，显示新加载的店铺
					$.refreshScroller();
				}
			}
		});
	}
	// 下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});
	// 选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
	$('#shopdetail-button-div').on(
			'click',
			'.button',
			function(e) {
				// 获取商品类别Id
				productCategoryId = e.target.dataset.productSearchId;
				if (productCategoryId) {
					// 若之前已选定了别的category,则移除其选定效果，改成选定新的
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						productCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				}
			});
	// 点击商品的卡片进入该商品的详情页
	$('.list-div').on(
			'click',
			'.card',
			function(e) {
				var productId = e.currentTarget.dataset.productId;
				window.location.href = '/o2o/frontend/productdetail?productId='
						+ productId;
			});
	// 需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，按照新的名字去查询
	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
	// 点击后打开右侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
});