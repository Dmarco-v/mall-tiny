# mall商城开发日志

## 一、整合SpringBoot+MyBatis搭建项目框架

### 1.mysql数据库建库建表

版本：mysql 8

工具：SQLyog

创建数据库。此处是直接导入项目的数据库脚本。地址：[sql脚本](github.com/macrozheng/mall-learning/blob/master/document/sql/mall_tiny.sql )

实际开发过程：思维导图-->powerDesign-->数据库脚本

使用tiny版本，数据表部分包括会员管理模块和商品管理模块。

### 2.使用的框架

- SpringBoot。快速构建Web应用。
- PageHelper。MyBatis分页插件。
- Druid。阿里开源数据库连接池。
- MyBatis generator。MyBatis代码生成器。可以根据数据库生成model,mapper.xml,mapper接口和Example。 http://mybatis.org/generator/ 

### 3.搭建项目步骤

1. 初始化SpringBoot项目
2. pom.xml添加相关依赖。这里的依赖包括 pagehelper 、druid、MyBatis generator、MySQL数据库驱动
3. 修改 application.yml 配置文件。配置端口号，添加数据源配置和MyBatis路径。
4. 项目文件包结构
   - controller控制器
   - mbg存放MyBatis generator生成的代码。包括mapper和model
   - service & service.impl服务类。
   - config。配置java配置类。如MyBatis配置类。
   - common。存放通用类，包括工具类、通用返回结果。
   - component。存放组件类。
   - dao。自定义mapper
   - dto。存放自定义的传输对象。如请求参数和返回结果。
   - resources包下。包括application.yml（SpringBoot配置），generator.properties，generatorConfig.xml（mbg配置）。
5. 配置generator.properties，generatorConfig.xml，添加MyBatis的Java配置。运行Generator的main函数生成代码。
6. 写controller层。
7. 写service层。

### 4.问题解决

问题1： Could not autowire. No beans of ‘xxxx' type found 

```java
@Autowired
    private PmsBrandMapper brandMapper;
```

解决：一般可以忽略该问题，项目启动后会自动注入。

问题2：因mapper.xml中的语句出错，导致报了一系列错误。

```java
Cause: java.lang.IllegalArgumentException: Result Maps collection already contains value for com.dmarco.mall.tiny.mbg.mapper.PmsBrandMapper.BaseResultMap
//该原因导致的一连串报错
org.apache.ibatis.builder.BuilderException: Error parsing Mapper XML
org.springframework.core.NestedIOException: Failed to parse mapping resource//映射资源转换失败
org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.apache.ibatis.session.SqlSessionFactory]//启动sql事务工厂失败
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'sqlSessionFactory'
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'xxx'//创建bean失败
```

解决：mybatis 的xml sql 映射语句中已经存在相同的Mapper XML映射，ImageClassifyTrain.countImageClassifyTrain已经存在。应检查mapper.xml文件中的id是否唯一。最后发现是因为在mapper.xml文件生成过的情况下，又执行了一次Generator，从而导致mapper.xml文件中出现重复，导致报错。

解决以上问题后，项目正常启动。



 ## 二、整合Swagger-UI实现在线API文档

### 1.Swagger-UI介绍

Swagger-UI是HTML, Javascript, CSS的一个集合，可以动态地根据注解生成在线API文档。 https://swagger.io/tools/swagger-ui/ 

常用注解：

- @Api：用于修饰Controller类，生成Controller相关文档信息
- @ApiOperation：用于修饰Controller类中的方法，生成接口方法相关文档信息
- @ApiParam：用于修饰接口中的参数，生成接口参数相关文档信息
- @ApiModelProperty：用于修饰实体类的属性，当实体类是请求参数或返回结果时，直接生成相关文档信息

### 2.整合

1. 添加依赖。在pom.xml中引入Swagger-UI相关依赖
2. 添加Swagger-UI的配置。
3. 给Controller类添加Swagger注解。
4. 修改MyBatis Generator的自定义注释生成器。修改addFieldComment方法使其生成Swagger的@ApiModelProperty注解来取代原来的方法注释，添加addJavaFileComment方法，使其能在import中导入@ApiModelProperty。从而自动为Model字段添加注释。重新运行MyBatis Generator，可以看到model类中的都添加了@ApiModelProperty注解，自动根据数据库中的注释生成了model类中字段的注释。

### 3.访问接口文档

访问接口地址 http://localhost:8080/swagger-ui.html 即可查看到相应文档。







