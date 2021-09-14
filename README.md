## 柬埔寨电商

#### 发包相关
1、渠道信息存储在app/config.json文件中，打包调用指令  
   ```
   ./gradlew clean assembleReleaseChannels    # release 包
   ./gradlew clean assembleDebugChannels      # debug 包
   ```
   也可直接用walle-cli-all.jar结合历史打包方式打包，但切记更改包名，下载链接固定

   ！！！升级到3.5.1后，split要常设，输出release包时，需要将enable设置为false，开发状态下使用true
   
2、360渠道加固后，渠道信息都丢失，需要用walle-cli-all.jar对重新签名的包加入渠道信息  
3、vivo、meizu渠道包都是用副标题


# walle-cli

walle-cli是walle提供的命令行程序。

即：本目录下的 walle-cli-all.jar （github release中会及时更新，可到release中下载）=

## 使用示例

### 获取信息

显示当前apk中的渠道和额外信息：

```
java -jar walle-cli-all.jar show /Users/Meituan/app/build/outputs/apk/app.apk
```

### 写入信息

写入渠道

```
java -jar walle-cli-all.jar put -c meituan /Users/Meituan/Downloads/app.apk
```

写入额外信息，不提供渠道时不写入渠道

```
java -jar walle-cli-all.jar put -c meituan -e buildtime=20161212,hash=xxxxxxx /Users/xxx/Downloads/app.apk
```

指定输出文件，自定义名称。 不指定时默认与原apk包同目录。

```
java -jar walle-cli-all.jar put -c meituan /Users/Meituan/Downloads/app.apk /Users/xxx/Downloads/app-new-hahha.apk
```

#### 批量写入
##### 命令行指定渠道列表
```
java -jar walle-cli-all.jar batch -c meituan,meituan2,meituan3 /Users/walle/app/build/outputs/apk/app.apk
```

##### 指定渠道配置文件

```
java -jar walle-cli-all.jar batch -f /Users/Meituan/walle/app/channel  /Users/Meituan/walle/app/build/outputs/apk/app.apk
```

[配置文件示例](../app/channel) 支持使用#号添加注释

输出目录可指定，不指定时默认在原apk包同目录下。

##### 指定渠道&额外信息配置文件
```
java -jar walle-cli-all.jar batch2 -f /Users/Meituan/walle/app/config.json  /Users/Meituan/walle/app/build/outputs/apk/app.apk
```

[配置文件示例](app/config.json)

输出目录可指定，不指定时默认在原apk包同目录下。

## 更多用法

获取cli所有功能

```
java -jar walle-cli-all.jar -h
```

