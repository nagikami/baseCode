package com.nagi.controller;

import com.nagi.annotation.MyRequestMapping;
import com.nagi.model.Car;
import com.nagi.validator.MyConstraintValidator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * spring参数处理包括在请求数据与handler参数和Model之间的Validation, Data Binding, and Type Conversion
 * SessionAttributes将Model的attribute保存在session中，注意，直接返回response body会提交response，
 * 导致session创建失败，需要返回View name将response交由spring管理
 */
//@SessionAttributes("string")
/**
 * @RestController 是@Controller和@ResponseBody的复合注解，会直接将返回值写入到response body导致无法返回View
 */
//@RestController
/**
 * 如需代理controller注解的类，需要使用类级别代理，例如@EnableTransactionManagement(proxyTargetClass = true)
 * 如果通过实现接口的方式实现代理，在不启用类级别代理的情况下，需要在接口添加controller注解
 */
@Controller
public class MyController {
    /**
     * 可通过handler method的特定参数类型声明HandlerAdapter回调handler method时传入的参数
     */
    @GetMapping("/info")
    public String handle01(Locale locale) {
        return "hello nagi, current locale: " + locale.toString();
    }

    /**
     * ModelAttribute注解方法时，在RequestMapping注解的方法前处理，将返回值添加到Model
     * 和RequestMapping一起注解同一个方法时，方法的返回值被转换为Model，而不是View name
     */
    @ModelAttribute
    public String handle02() {
        return "hello attr";
    }

    /**
     * 在ModelAttribute注解的handle02执行后执行，spring将handle02返回的Model传递给handle03
     */
    @RequestMapping("/attr")
    public String handle03(Model model) {
        return "hello nagi " + model.getAttribute("string") + " " + model.getAttribute("global");
    }

    /**
     * ModelAttribute注解参数时绑定请求参数复合对象Car到Model，用于渲染View，Spring需要为每个Model对象分别创建BindingResult
     * BindingResult保存验证和绑定的结果以及可能发生的错误，需要紧跟在对应的Model对象（Car）后面
     * ResponseBody将返回值直接写到HTTP response body，不会放到Model或者作为View name解析
     * Spring使用HttpMessageConverter将HTTP request body转换为对象（ResponseBody）
     * 或者将对象转换为HTTP response body（RequestBody）
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/home")
    public String processSubmit(@ModelAttribute("car") Car car, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        return "car: type: " + car.getType() + " color: " + car.getColor();
    }

    /**
     * HttpEntity既可以和RequestBody/ResponseBody一样访问请求和响应的body，也可以访问请求和响应的header
     */
    @RequestMapping("/testEntity")
    public ResponseEntity<String> handle(HttpEntity<byte[]> requestEntity) throws UnsupportedEncodingException {
        String requestHeader = requestEntity.getHeaders().getFirst("MyHeader");
        byte[] body = requestEntity.getBody();
        // do something with request header and body
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyHeader", "nagi");
        return new ResponseEntity<>("OK, with request MyHeader: " + requestHeader + " request body: "
                + new String(body == null ? "empty".getBytes(StandardCharsets.UTF_8) : body), responseHeaders, HttpStatus.OK);
    }

    // 添加本地自定义Spring Validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators();
    }

    @RequestMapping("/validation")
    public String validation(@Valid @ModelAttribute("car") Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "data bind error";
        }
        return "OK, type: " + car.getType() + " color: " + car.getColor();
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * redirect：向浏览器返回一个response status让它去访问重定向的地址，属于客户端行为
     * spring mvc通过RedirectView直接返回重定向的地址和对应的response status
     * 若通过@ResponseStatus注解设置response status，则返回设置的response status而不是redirect的
     */
    @RequestMapping("/redirect")
    public String redirect() {
        return "redirect:/index";
    }

    /**
     * 服务端请求转发的地址，将response返回给客户端，属于服务端行为
     */
    @RequestMapping("/forward")
    public String forward() {
        return "forward:/index";
    }

    @MyRequestMapping(value = "/fake", fake = "POST")
    public String fake() {
        return "index";
    }
}
