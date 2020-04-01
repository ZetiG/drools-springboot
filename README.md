#规则引擎

###版本号
spring-boot-starter-parent.version  2.1.9.RELEASE  
drools-core.version                 7.24.0.Final  
kie-spring.version                  7.24.0.Final

###基本格式
```drools
package rules #虚拟包名，不一定需要与物理存在的包名一致(最好一致,否则启动会有warning)

import java.math.BigDecimal
import com.zeti.drools.entity.Commodity #导包,引入外部的类,还可引入静态方法

global org.slf4j.Logger logger #global 定义全局变量

dialect  "mvel" #方言  默认是java

rule "name" #规则名
salience 1 #执行优先级,数值越大 优先级越高
attributes  #属性部分
    when 
        LHS #条件部分
    Then
        RHS #结果部分
end
```


###规则介绍
####约束连接符
<pre>"&&"(and)  "||"(or)  ","(and)</pre>
<pre>优先级: '&&' > '||' > ','  </pre>
<pre>且:','不能和 '&&' '||'一起使用 </pre>


####比较符
`>、>=、<、<=、= =、!=、contains、not contains、memberof、not memberof、matches、not matches`

```drools
package rules

import java.math.BigDecimal
import com.zeti.drools.entity.Commodity

global org.slf4j.Logger logger 

rule "discount_01"
    when
        #条件语句, '$' 符号绑定变量名,可在下面语句使用该变量, 多条件使用(“&&”（and）、“||”(or)和“,”(and))
        #优先级(&& > || > ,) 
        $commodity : Commodity(price.compareTo(new BigDecimal("500.00")) < 0) 
    then
        BigDecimal discount = BigDecimal.ZERO;
        discountPrice(discount, $commodity); #调用function函数,也可调用外部Java方法,须在import导入
        logger.info("优惠金额: {}", discount);
end

#function函数,支持返回值,无法使用全局变量
function void discountPrice(BigDecimal discount, Commodity commodity) {
        BigDecimal price = commodity.getPrice();
        commodity.setDiscount(discount);
        commodity.setPrice(price.subtract(discount));
}

```
