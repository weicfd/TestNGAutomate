# TestNGAutomate

## 文件结构
* pom.xml 添加了依赖包信息
* src 源程序
  * main 主程序
    * java JAVA代码
      * entity 实体类
      * fsm 数据操作自动机模块
      * generator 生成器模块
      * parser 解析器模块
      * Driver 示例驱动器
    * resources 资源文件夹
      * biscTest.vm 模板文件
  * test 测试文件
    * data 存放输入的数据文件
    * data_out 数据预加载时的生成临时文件
    * script 存放输入的脚本文件
    * out 存放输出的TestNG测试代码
    * java 针对TestNGAutomate的单元测试
    
## 运行
1. `data`文件夹和`script`文件夹里输入要运行的脚本和数据  
2. 在`generator`底下的`Config`配置相应的路径  
3. 运行`generator`底下`GenerateTest`的`main`函数  

## 项目构成
### 实体类 entity

#### Method 操作类
对应一个**操作**类型

##### 成员列表
* `methodName` 操作名（`String`）
* `mType` 操作的类型（`Int`） ：mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0
* `dataCodeMap` 操作参数半数据码映射表(`Hashmap`)  
key: 半数据码（`Long`），半数据码是指最后一位数据ID全为0的数据码  
value: 数据对应的参数名（`String`） 

**数据码的格式**

| 原始类型/复合类型 | 服务ID | 实体ID | 属性ID | 数据ID |
| ------------    | ------ | ----- | ------ | ----- |
| 1位             | 3位      | 3位  | 3位     | 3位   |


* `locationCode` location数据元素对应的值
 
#### Case 用例类
对应一个脚本的**操作序列**类型

##### 成员列表
* `methods` Method类列表（`ArrayList`）
* `serviceName` 服务名（`String`）
* `pat` 模式（`String`） 
String内存放的是`mType`中对应的数字的组合，如`124`为Add-Update-Find操作序列
* `oracle` 预期值半数据码映射表(`Hashmap`)
key: 半数据码 datacode （`Long`） 
value: 参数名param name （`String`）


### 生成器模块 generator
#### GenerateTest 测试生成器

主程序入口
* Step 1: 初始化dataMap
* Step 2: 对每一个服务，按照pat长度升序获取XML脚本
* Step 3: 调用TemplateWriter生成一个TestNG的测试代码

#### TemplateWriter 模板写入类

一个TemplateWriter对应一个TestNG的测试Java文件
* @param fileID 输出文件的ID
* @param methods Method列表
* @param serviceName 服务名
* @param caseNo 对应的case编号
* @param pat 模式
* @param target 预期数据码映射表
* @param dataMap 方法参数数据码映射表
* @param path 输入的XML文件的路径

文件的输出路径： `Config.generate_test_dir` + `serviceName` + '/' + `serviceName` + `fileID` + ".java"

#### Config公共配置项
以下部分显示的是公共配置项及对应的默认位置

    String data_xml_dir = "src/test/data/"; // 存放输入的数据文件
    String script_xml_dir = "src/test/script/"; // 存放输入的脚本文件
    public final static String generate_test_dir = "src/test/script/"; // 存放输出的TestNG测试代码
    String project_package_dir = "test"; // 生成的测试类的运行环境所在的包名称
    String project_root_url = "http://localhost:8080/GuangDa/"; // 需要测试人员根据被测系统的具体运行路径进行配置

### 解析器 parser
#### DataParser
解析所有数据文件，保存为数据码(Long)到数据值(String)的映射表，并将其存放一个ser文件中，调用getDataCode来获取

#### MethodsParser
解析一个脚本文件，保存为一个Case对象，使用getCase方法获取

### 数据操作自动机模块 fsm
#### DataFsm 数据生成自动机
单例模式，在`TemplateWriter`中调用来生成对应的数据
* 每次生成新的TestNG的测试Java文件前要调用`init`方法进行初始化
* 每次解析操作序列中的下一个操作时要调用`next`方法
* 调用`getData`输入半数据码来获取相应的完整数据码
* 调用`getOracle`输入半数据码来获取相应的完整数据码

#### DataCodeConverter 数据生成辅助类

提供两个辅助方法
* `castAttrToCode`方法：
     * 使用哈希函数将数据的信息转化为半数据码
     * @param mServiceName 服务名
     * @param attribute 数据的属性（实体名+参数名）
     * @return 半数据码
* `getCases`方法：
     * 根据Method列表来判断当前序列可以生成的用例编号（可扩展）
     * @param methods 操作序列列表
     * @return 用例标号的数组


### 驱动器 driver
驱动器，输出Java文件，配置文件，应置于单独的测试环境中。示例驱动器中提供了两个方法
* `httpConnect`方法
     * 使用HTTP协议将请求发送到被测系统并获取返回报文内容
     * @param url 需要发送请求的URL
     * @return 响应报文内容
* `getJsonValue`方法
     * 解析Json内容
     * @param JsonString 需要解析的JSON内容
     * @param JsonId 目标内容的JSON ID
     * @return JsonValue 返回JsonString中JsonId对应的Value