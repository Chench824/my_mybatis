package sise.cch.io;

import java.io.InputStream;

/**
 * @author Chench
 * @date 2021/8/31 10:27
 * @description
 */
public class Resources {

    // 根据配置文件的路径，将配置文件加载成字节输入流，存储在内存中
    public static InputStream getResourceAsSteam(String path){
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return inputStream;
    }
}
