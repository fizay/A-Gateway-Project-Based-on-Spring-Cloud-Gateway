# A-Gateway-Project-Based-on-Spring-Cloud-Gateway
基于spring cloud gateway，实现网关登录、授权、限流等功能

需求：
1.身份验证，验证发出请求的客户端身份；
2.访问授权，验证客户端是否有权执行该特定操作可以基于RBAC模型；
3.速率限制，限制特定客户或所有客户端每秒的请求数和一天的请求总数；
4.缓存，缓存响应以减少对服务的请求数；
5.指标收集，收集有关api使用情况的指标，用于分析；
6.请求日志，记录请求历史。
7.把API网关项目打包封装成一个可以到处运行的可执行程序
8.对上述的代码实现进行单元测试

扩展：
9.ip白名单
10.邮件发送超标信息

问题：
1.前端页面jsp替换成html后${}不能从Model域取值
2.用户密码需使用Md5盐值加密，暂未实现


测试：
1.身份验证，验证发出请求的客户端身份
1.1浏览器访问http://localhost:3001/index?i=1	可以正常访问
1.2浏览器访问http://localhost:3001/provider-service/getInfo?username=abc&para=def	需要登录验证

2.访问授权，验证客户端是否有权执行该特定操作，可以基于RBAC模型
2.1浏览器访问/index后，点击“需要登录”跳转登录页面，使用ZHANGSAN登录可以访问
2.2浏览器访问/index后，点击“需要登录且授权”跳转登录页面，使用ZHANGSAN登录可以不访问，使用FUZIYAN登录可以访问

3.速率限制，限制特定客户或所有客户端每秒的请求数和一天的请求总数
3.0所有访问gateway的请求总次数限制为1000次
3.1jmeter设置请求头CMSOFT_TOKEN，{"id":1,"username":"FUZIYAN","salt":"root","email":"microfzy@outlook.com","phone":"15700756797","qpsLevel":100,"deptId":1,"note":"none note"}
    	设置访问路径为/provider-service/getInfo，参数为FUZIYAN，CMSOFT
	限制每秒并发量10次，最多可访问1000次
3.2jmeter设置请求头CMSOFT_TOKEN，{"id":2,"username":"ZHANGSAN","salt":"customer ","email":"jacob2014@126.com ","phone":"18717023757 ","qpsLevel":50,"deptId":1,"note":"none note"}
	设置访问路径为/provider-service/getInfo，参数为ZHANGSAN，CMSOFT
	限制每秒并发量1次，最多可访问10次

4.缓存，缓存响应以减少对服务的请求数
4.1在redis中输入命令Flushall，然后输入monitor
4.2访问http://localhost:3001/index?i=1，观察redis输出和console控制台输出

5.指标收集，收集有关api使用情况的指标，用于分析
5.1浏览器访问http://localhost:6001/profiler.html
5.2浏览器访问http://localhost:3001/index?i=1，观察profiler.html变化

6.请求日志，记录请求历史——yml配置tomcat记录日志
6.1本地文件夹C:\Java\SummerTraining\st-provider01，观察访问网关后的变化

7.ip白名单
7.1删除st-gateway中.properties文件主机ip地址
7.2浏览器访问http://localhost:3001/index?i=1，拒绝访问
