package cn.edu.iip.nju.crawler.fujian;


import org.assertj.core.util.Lists;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2017/5/7.
 */
public class Depress {

    private static List<String> fileList = new ArrayList<>();
    private static List<File> file = Lists.newArrayList();

    public static List<String> getFileList(File dir, String extension) {


        File[] files = dir.listFiles();
        if(files==null){
            return null;
        }
        for (File file : files) {
            if(file.isDirectory()){
                getFileList(file,extension);
            }else {
                if (file.getName().endsWith(extension)){
                    fileList.add(file.getAbsolutePath());
                }
            }
        }
        return fileList;
    }
    public static List<File> getFileList(File dir) {
        File[] files = dir.listFiles();
        if(files==null){
            return null;
        }
        for (File f : files) {
            if(f.isDirectory()){
                getFileList(f);
            }else {
                if (f.getName().endsWith("xlsx")){
                    file.add(f);
                }
            }
        }
        return file;
    }

//    public static void depressRarAndZip() throws Exception {
//
//        Resource resource = new FileSystemResource("C:\\Users\\63117\\IdeaProjects\\zhijianju\\src\\main\\resources\\files\\attachment");
//        File dir = resource.getFile();
//        List<String> rar = getFileList(dir, "rar");
//        List<String> zip = getFileList(dir, "zip");
//        for (String s : rar) {
//            RarZipUtils.deCompress(s,dir.getAbsolutePath()+"\\depress");
//        }
//        for (String s : zip) {
//            RarZipUtils.deCompress(s,dir.getAbsolutePath()+"\\depress");
//        }
//    }


}
