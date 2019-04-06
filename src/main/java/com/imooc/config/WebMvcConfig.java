package com.imooc.config;

import com.imooc.interceptor.MiniInterceptor;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/30 9:44
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("file:E:/imooc_videos_dev/");  //windows
//                .addResourceLocations("file:/imooc_videos_dev/"); //linux
    }

    @Bean(initMethod = "init")
    public ZKCuratorClient zkCuratorClient(){
        return new  ZKCuratorClient();
    }

    @Bean
    public MiniInterceptor miniInterceptor(){
        return new MiniInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
                                                  .addPathPatterns("/bgm/**")
                                                  .addPathPatterns("/video/userLike","/video/userUnLike","/video/saveComment")
                                                  .addPathPatterns("/video/upload","/video/uploadCover")
                                                  .excludePathPatterns("/user/queryPublisher");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    /*
    *
     * @功能描述 上传文件的大小限制
     * @创建日期 15:32 2019/4/6
     **/
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("10240KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("1024000KB");
        return factory.createMultipartConfig();
    }
}
