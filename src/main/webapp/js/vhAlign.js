/**
 * Created with PyCharm.
 * User: chenglin
 * Date: 15-10-5
 * Time: 下午8:16
 * To change this template use File | Settings | File Templates.
 */
	(function($){
						$.fn.vhAlign =  function(){
							return this.each(function(i){
							  //获取元素的内容高度
								var h = Math.ceil($(this).height());
								//outerHeight=padding+border+height
								var oh = Math.ceil($(this).outerHeight());
								//取得margin-top值
								var mt = Math.ceil(oh / 2);
								//取得元素宽度
								var w = Math.ceil($(this).width());
								//outerWidth=padding+border+width
								var ow = Math.ceil($(this).outerWidth());
								//取得margin-left
								var ml = Math.ceil(ow / 2);
								//实现元素居中效果
								$(this).css({
									"margin-top": "-" + mt + "px",
									"top": "50%",
									"margin-left": "-" + ml + "px",
									"left": "50%",
									"width":w,
									"height":h,
									"position": "absolute"
								});
							});
						};
					})(jQuery);
