/**
 * 
 */
// 当加载js文件时
$(function() {
	// 获取shopId
	var shopId = getQueryString('shopId');
	var isEdit = shopId ? true : false;
	var shopInfoUrl = "/o2o/shopadmin/getshopbyid?shopId=" + shopId;
	var editShopUrl = '/o2o/shopadmin/modifyshop';
	// 初始化一个url,用来获取店铺分类和区域信息
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	if (!isEdit) {
		getShopInitInfo();
	} else {
		getShopInfo(shopId);
	}
	function getShopInfo(shopId) {
		$.ajax({
			url : shopInfoUrl,
			type : 'GET',
			success : function(data) {
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '"selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled', 'disabled');
				$('#area').html(tempAreaHtml);
				$("#area option[data-id='" + shop.area.areaId + "']").attr(
						"selected", "selected");
			}
		});
	}
	// 获取店铺的基本信息
	function getShopInitInfo() {
		$.ajax({
			url : initUrl,
			type : 'GET',
			success : function(data) {
				var tempHtml = '';
				var tempAreaHtml = '';
				// 遍历返回的shopCategoryList
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				// 将获取到的信息返回给前端
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	}
	// 注册店铺
	var registerShopUrl = '/o2o/shopadmin/registershop';
	$('#submit').click(function() {
		var shop = {};
		if (isEdit) {
			shop.shopId = shopId;
		}
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		// 获取下拉列表里的数据
		// 双重否定等于肯定
		shop.shopCategory = {
			shopCategoryId : $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		shop.area = {
			areaId : $('#area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		// 获取图片的文件流
		var shopImg = $('#shop-img')[0].files[0];
		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		var verifyCodeActual = $('#j_kaptcha').val();
		if (!verifyCodeActual) {
			$.toast("请输入验证码");
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		// 提交到后台
		$.ajax({
			url : (isEdit ? editShopUrl : registerShopUrl),
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功');
				} else {
					$.toast('提交失败' + data.errMsg);
				}
				$('#kaptcha_img').click();
			}
		});
	});
});