package com.nagi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.TimeZone;

@Controller
public class MyControllerWithResolver {
    // handlerAdapter传入timezone
    @RequestMapping("/zone")
    public String  zone(TimeZone timeZone) {
        return timeZone.toString();
    }

    /**
     * LocaleResolver用于解析客户端的locale实现国际化
     * 使用LocaleContextResolver或者TimeZoneAwareLocaleContext时不仅可以获得locale
     * 还可以通过可能包含timezone信息的LocaleContext获取timezone，未匹配到timezone则返回null
     */
    @RequestMapping("/myZone")
    public String myZone(HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocale(request);
        TimeZone timeZone = RequestContextUtils.getTimeZone(request);
        return "locale: " + locale + (timeZone == null ? "unknown" : timeZone.toString());
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "upload";
    }

    /**
     * 上传的文件会保存在请求参数里，参数名对应表单的INPUT的name属性
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        // 获取上传文件的名称
        String originalFilename = multipartFile.getOriginalFilename();
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/" + originalFilename))) {
            bufferedWriter.write(new String(multipartFile.getBytes()));
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return "upload failed!";
        }
        return "upload successfully!";
    }

    /**
     * 直接向response的OutputStream写入数据，也可以使用ResponseEntity将StreamingResponseBody作为body，
     * 实现自定义status和headers
     */
    @GetMapping("/download")
    public StreamingResponseBody download() {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                outputStream.write("hello world".getBytes(StandardCharsets.UTF_8));
            }
        };
    }
}
