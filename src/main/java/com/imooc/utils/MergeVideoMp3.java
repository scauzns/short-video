package com.imooc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/30 17:10
 */
public class MergeVideoMp3 {
    private String ffmpegEXE;

    public MergeVideoMp3(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    public void convertor(String videoInputPath, String mp3InputPath, double seconds, String videoOutputPath) throws IOException {
        //ffmpeg.exe -i dormitory.mp4 -i bgm.mp3 -t 6 -y 新的视频.mp4
        List<String> command = new ArrayList<String>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-i");
        command.add(mp3InputPath);
        command.add("-t");
        command.add(String.valueOf(seconds));

        command.add("-strict"); //linux环境下如果截图需要添加下面两个参数
        command.add("-2");

        command.add("-y");
        command.add(videoOutputPath);

        ProcessBuilder processBuilder =  new ProcessBuilder(command);
        Process process = processBuilder.start();
        //如果内存中太多流，会把当前线程卡住，需要清理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while((line=br.readLine()) != null){

        }
        if(br != null){
            br.close();
        }
        if(inputStreamReader != null){
            inputStreamReader.close();
        }
        if(errorStream != null){
            errorStream.close();
        }

    }

    public static void main(String[] args) {
        MergeVideoMp3 ffMpegTest = new MergeVideoMp3("E:\\ffmpeg\\ffmpeg\\bin\\ffmpeg.exe");
        try {
            ffMpegTest.convertor("E:\\imooc_videos_dev\\dormitory.mp4",
                    "E:\\imooc_videos_dev\\bgm.mp3",
                    6,
                    "E:\\imooc_videos_dev\\生成的视频.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
