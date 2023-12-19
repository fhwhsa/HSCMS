/**
 * 
 */
layui.use(function(){
  var element = layui.element;
  
  // hash 地址定位
  var hashName = 'tabid'; // hash 名称
  var layid = location.hash.replace(new RegExp('^#'+ hashName + '='), ''); // 获取 lay-id 值
    
  // 初始切换
  element.tabChange('test-hash', layid);
  // 切换事件
  element.on('tab(test-hash)', function(obj){
    location.hash = hashName +'='+ this.getAttribute('lay-id');
  });
});