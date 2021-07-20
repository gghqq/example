[arthas练习地址](https://arthas.aliyun.com/doc/arthas-tutorials.html?language=cn&id=case-web-console)  
[arthas文档官网](https://arthas.aliyun.com/doc/quick-start.html)   
##用户案例
```
1. 下载Arthas启动程序
wget https://arthas.aliyun.com/arthas-boot.jar  

2. 启动.根据序号选择
java -jar arthas-boot.jar --target-ip 0.0.0.0 

3. 反编译 
jad com.qds.manager.rpc.IOrganizeRPCService

4.查看当前jvm运行概况
dashboard

5. 退出当前的连接,arthas还会继续运行，端口会保持开放，下次可以直接连接
quit/exit

6. 完全退出
stop

7. 执行结果存日志 结果会异步保存在：{user.home}/logs/arthas-cache/result.log，请定期进行清理，以免占据磁盘空间.
options save-result true 

8. 排查接口异常
curl   http://localhost/user/0  访问 http://localhost/user/0 ，会返回500异常

9.排查函数调用异常,会用到Athas内置对象
watch com.example.demo.arthas.user.UserController * '{params, throwExp}'   其实就是监控该类方法调用的参数和异常
watch com.example.demo.arthas.user.UserController * '{params, throwExp}' -x 2  打印异常的详细参数
watch com.example.demo.arthas.user.UserController * returnObj 'params[0] > 100'  条件支持 例如参数>100
watch com.example.demo.arthas.user.UserController * "{params[0],throwExp}" -e 只捕获异常参数
watch com.example.demo.arthas.user.UserController * '{params, returnObj}' '#cost>200' 捕获耗时大于200毫秒的请求
Athas内置对象
loader,clazz,method,target,params,returnObj,throwExp,isBefore,isThrow,isReturn 内置对象

10.thread 所有线程信息
thread 16     查看具体线程的栈
thread -n 3  参数n用来指定最忙的前N个线程并打印堆栈
thread -b 参数b用来指定找出当前阻塞其他线程的线程
thread -n 3 -i 5000  查看5秒内的CPU使用率top n线程栈

11.查找类的ClassLoader 
sc -d com.example.demo.arthas.user.UserController | grep classLoaderHash
```  

