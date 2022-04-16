## Object的内容存储

Object的存储采用压缩算法，具体的是zlib压缩内容。
具体参考实现：ObjectParser.java


参考文档：
https://zhuanlan.zhihu.com/p/351557158
https://www.zhihu.com/column/c_1346239260224405504
https://www.bianchengquan.com/article/320620.html

## Index的存储格式

Index文件的压缩比较复杂，需要按照一个byte一个byte的解析
具体参考：IndexParser.java

参考：
https://zhuanlan.zhihu.com/p/351559228
https://git-scm.com/docs/index-format

## 为什么要理解Object和Index的格式
一般我们使用git的方式可能按照如下操作:
```
git init demo
cd demo
echo "hello" >> test.txt
git add test.txt
git commit -m 'add test.txt'
git push origin main
```
请问，上面的每一步对我们来说具体是做了什么？
- git init demo 
在当前目录下创建demo文件夹，并生成.git文件夹
- git add test.txt 
将test.txt 文件存入objects中，名称通过文件内容的sha1算法获取, 内容采用zlib压缩。并将test.txt文件信息写入index文件中(其中采用上面Index的格式存储)，此时test.txt文件状态未提交
- git commit -m 'add test.txt'
生成commit的对象存入object中，其中commit包含tree对象的指向，这样就能通过某个commit找到当前工作区的文件内容。
- git push origin main
将当前修改的信息同步至远程仓库，具体的后续再说
