package cn.edu.iip.nju.web;

import cn.edu.iip.nju.dao.HospitalDataDao;
import cn.edu.iip.nju.model.HospitalData;
import cn.edu.iip.nju.service.ExcelDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by xu on 2018/1/18.
 */
@Controller
@RequestMapping("/dataSource")
public class DataSourceController {
    @Autowired
    HospitalDataDao hospitalDataDao;

    @PostMapping("/hosUp")
    public String uploadHosFile(@RequestPart("file") MultipartFile file, RedirectAttributes falshModel, Model model) {
        if (file == null||file.isEmpty()) {
            model.addAttribute("msg","未选择任何文件");
            return "setting";
        }
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        System.out.println(fileType);
        if (!(".xls".equals(fileType) || ".xlsx".equals(fileType))) {
            //上传文件不是excel文件
            model.addAttribute("msg", "上传文件格式错误！！");
            return "setting";
        }
        try {
            //ClassPathResource resource = new ClassPathResource("hospital");
            //System.out.println(resource.getFilename());
            //SysteSystem.out.println(lo);m.out.println(resource.getFile().getPath());
            //File dir = resource.getFile();
            InputStream inputStream = file.getInputStream();
            List<HospitalData> list = ExcelDateService.getBankListByExcel(inputStream, file.getOriginalFilename());
            int count = hospitalDataDao.getCount();
            System.out.println(count);
            System.out.println(list.size());
            for (int i = 0; i < list.size(); i++) {
                HospitalData hospitalData = list.get(i);
                //hospitalData.setId(Integer.valueOf(count+i+1));
                hospitalDataDao.save(hospitalData);
                //System.out.println(hospitalData);
            }

            File dir = new File("/home/kjb/zhijianju/hospital");
            if (dir.isDirectory()) {
                System.out.println(dir + "/" + file.getOriginalFilename());
                file.transferTo(new File(dir + "/" + file.getOriginalFilename()));
            }
            falshModel.addFlashAttribute("msg", "上传成功！");
            return "redirect:/setting";
        } catch (IOException e) {
            System.out.println(e);
            model.addAttribute("msg", "文件上传失败，请重试！");
        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("msg", "文件导入数据库失败，请重试！");
        }
        return "setting";
    }
}
