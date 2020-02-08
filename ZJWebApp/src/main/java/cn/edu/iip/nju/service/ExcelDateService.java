package cn.edu.iip.nju.service;

import cn.edu.iip.nju.dao.HospitalDataDao;
import cn.edu.iip.nju.model.HospitalData;
import cn.edu.iip.nju.model.vo.HospitalForm;
import cn.edu.iip.nju.util.WarningDegree;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;


@Service
public class ExcelDateService {

        public static List<HospitalData> getBankListByExcel(InputStream inputStream, String fileName) throws Exception {
            List<HospitalData> list = new ArrayList<>();
            Workbook workbook = null;
            try {
                workbook = WorkbookFactory.create(inputStream);
                inputStream.close();
                //工作表对象
                Sheet sheet = workbook.getSheetAt(0);
                //总行数
                int rowLength = sheet.getLastRowNum();
                System.out.println("总行数有多少行" + rowLength);
                //工作表的列
                Row row = sheet.getRow(0);

                //总列数
                int colLength = row.getLastCellNum();
                System.out.println("总列数有多少列" + colLength);
                //得到指定的单元格
                Cell cell = row.getCell(0);
                for (int j = 1; j <= rowLength; j++) {
                    HospitalData hospitalData = new HospitalData();
                    row = sheet.getRow(j);

                    if(row.getCell(3) != null)
                        hospitalData.setName(row.getCell(3).toString());

                    if(row.getCell(4) != null)
                    hospitalData.setGender(row.getCell(4).toString());

                    if(row.getCell(5) != null) {
                        int age = (int) Float.parseFloat(row.getCell(5).toString());
                        hospitalData.setAge(Integer.valueOf(age));
                    }

                    if(row.getCell(6) != null) {
                        if (row.getCell(6).toString().equals("1"))
                            hospitalData.setHuji("本市/县");
                        else if (row.getCell(6).toString().equals("2"))
                            hospitalData.setHuji("本省外地");
                        else if (row.getCell(6).toString().equals("3"))
                            hospitalData.setHuji("外省");
                        else if (row.getCell(6).toString().equals("4"))
                            hospitalData.setHuji("外籍");
                    }

                    if(row.getCell(7) != null) {
                        if (row.getCell(7).toString().equals("1"))
                            hospitalData.setEduDegree("未上学儿童");
                        else if (row.getCell(7).toString().equals("2"))
                            hospitalData.setEduDegree("文盲、半文盲");
                        else if (row.getCell(7).toString().equals("3"))
                            hospitalData.setEduDegree("小学");
                        else if (row.getCell(7).toString().equals("4"))
                            hospitalData.setEduDegree("初中");
                        else if (row.getCell(7).toString().equals("5"))
                            hospitalData.setEduDegree("高中或中专");
                        else if (row.getCell(7).toString().equals("6"))
                            hospitalData.setEduDegree("大专");
                        else if (row.getCell(7).toString().equals("7"))
                            hospitalData.setEduDegree("大学及以上");
                    }

                    if(row.getCell(8) != null) {
                        if (row.getCell(8).toString().equals("1"))
                            hospitalData.setVocation("学龄前儿童");
                        else if (row.getCell(8).toString().equals("2"))
                            hospitalData.setVocation("在校学生");
                        else if (row.getCell(8).toString().equals("3"))
                            hospitalData.setVocation("家务");
                        else if (row.getCell(8).toString().equals("4"))
                            hospitalData.setVocation("待业");
                        else if (row.getCell(8).toString().equals("5"))
                            hospitalData.setVocation("离退休人员");
                        else if (row.getCell(8).toString().equals("6"))
                            hospitalData.setVocation("专业技术人员");
                        else if (row.getCell(8).toString().equals("7"))
                            hospitalData.setVocation("办事人员和有关人员");
                        else if (row.getCell(8).toString().equals("8"))
                            hospitalData.setVocation("商业、服务业人员");
                        else if (row.getCell(8).toString().equals("9"))
                            hospitalData.setVocation("农牧渔水利业生产人员");
                        else if (row.getCell(8).toString().equals("10"))
                            hospitalData.setVocation("生产运输设备操作人员及有关人员");
                        else if (row.getCell(8).toString().equals("11"))
                            hospitalData.setVocation("军人");
                        else if (row.getCell(8).toString().equals("12"))
                            hospitalData.setVocation("其他/不清楚");
                    }

                    if(row.getCell(9) != null)
                        hospitalData.setInjureDate(Date.valueOf(row.getCell(9).toString().substring(0, 10)));
                    if(row.getCell(10) != null)
                        hospitalData.setVisDate(Date.valueOf(row.getCell(10).toString().substring(0, 10)));

                    if(row.getCell(11) != null) {
                        if (row.getCell(11).toString().equals("1"))
                            hospitalData.setInjureReason("机动车车祸");
                        else if (row.getCell(11).toString().equals("2"))
                            hospitalData.setInjureReason("非机动车车祸");
                        else if (row.getCell(11).toString().equals("3"))
                            hospitalData.setInjureReason("跌倒/坠落");
                        else if (row.getCell(11).toString().equals("4"))
                            hospitalData.setInjureReason("钝器伤");
                        else if (row.getCell(11).toString().equals("5"))
                            hospitalData.setInjureReason("火器伤");
                        else if (row.getCell(11).toString().equals("6"))
                            hospitalData.setInjureReason("刀/锐器伤");
                        else if (row.getCell(11).toString().equals("7"))
                            hospitalData.setInjureReason("烧烫伤");
                        else if (row.getCell(11).toString().equals("8"))
                            hospitalData.setInjureReason("窒息/悬吊");
                        else if (row.getCell(11).toString().equals("9"))
                            hospitalData.setInjureReason("溺水");
                        else if (row.getCell(11).toString().equals("10"))
                            hospitalData.setInjureReason("中毒");
                        else if (row.getCell(11).toString().equals("11"))
                            hospitalData.setInjureReason("动物伤");
                        else if (row.getCell(11).toString().equals("12"))
                            hospitalData.setInjureReason("性侵犯");
                        else if (row.getCell(11).toString().equals("13"))
                            hospitalData.setInjureReason("其他");
                        else if (row.getCell(11).toString().equals("14"))
                            hospitalData.setInjureReason("不清楚");
                    }

                    if(row.getCell(12) != null) {
                        if (row.getCell(12).toString().equals("1"))
                            hospitalData.setInjureLocation("家中");
                        else if (row.getCell(12).toString().equals("2"))
                            hospitalData.setInjureLocation("公共居住场所");
                        else if (row.getCell(12).toString().equals("3"))
                            hospitalData.setInjureLocation("学校与公共场所");
                        else if (row.getCell(12).toString().equals("4"))
                            hospitalData.setInjureLocation("体育和运动场所 ");
                        else if (row.getCell(12).toString().equals("5"))
                            hospitalData.setInjureLocation("公路/街道");
                        else if (row.getCell(12).toString().equals("6"))
                            hospitalData.setInjureLocation("贸易和服务场所");
                        else if (row.getCell(12).toString().equals("7"))
                            hospitalData.setInjureLocation("工业和建筑场所");
                        else if (row.getCell(12).toString().equals("8"))
                            hospitalData.setInjureLocation("农场/农田");
                        else if (row.getCell(12).toString().equals("9"))
                            hospitalData.setInjureLocation("其他");
                        else if (row.getCell(12).toString().equals("10"))
                            hospitalData.setInjureLocation("不清楚");
                    }

                    if(row.getCell(13) != null) {
                        if (row.getCell(13).toString().equals("1"))
                            hospitalData.setActivityWhenInjure("工作");
                        else if (row.getCell(13).toString().equals("2"))
                            hospitalData.setActivityWhenInjure("家务");
                        else if (row.getCell(13).toString().equals("3"))
                            hospitalData.setActivityWhenInjure("学习");
                        else if (row.getCell(13).toString().equals("4"))
                            hospitalData.setActivityWhenInjure("体育活动");
                        else if (row.getCell(13).toString().equals("5"))
                            hospitalData.setActivityWhenInjure("休闲活动");
                        else if (row.getCell(13).toString().equals("6"))
                            hospitalData.setActivityWhenInjure("生命活动");
                        else if (row.getCell(13).toString().equals("7"))
                            hospitalData.setActivityWhenInjure("驾乘交通工具");
                        else if (row.getCell(13).toString().equals("8"))
                            hospitalData.setActivityWhenInjure("步行");
                        else if (row.getCell(13).toString().equals("9"))
                            hospitalData.setActivityWhenInjure("其他");
                        else if (row.getCell(13).toString().equals("10"))
                            hospitalData.setActivityWhenInjure("不清楚");
                    }

                    if(row.getCell(14) != null) {
                        if (row.getCell(14).toString().equals("1"))
                            hospitalData.setIfIntentional("非故意（意外事故）");
                        else if (row.getCell(14).toString().equals("2"))
                            hospitalData.setIfIntentional("自残/自杀");
                        else if (row.getCell(14).toString().equals("3"))
                            hospitalData.setIfIntentional("故意（暴力、攻击）");
                        else if (row.getCell(14).toString().equals("4"))
                            hospitalData.setIfIntentional("不清楚");
                        else if (row.getCell(14).toString().equals("5"))
                            hospitalData.setIfIntentional("其他");
                    }

                    if(row.getCell(15) != null) {
                        if (row.getCell(15).toString().equals("1"))
                            hospitalData.setInjureType("骨折");
                        else if (row.getCell(15).toString().equals("2"))
                            hospitalData.setInjureType("扭伤/拉伤");
                        else if (row.getCell(15).toString().equals("3"))
                            hospitalData.setInjureType("锐器伤、咬伤、开放伤");
                        else if (row.getCell(15).toString().equals("4"))
                            hospitalData.setInjureType("挫伤、擦伤");
                        else if (row.getCell(15).toString().equals("5"))
                            hospitalData.setInjureType("烧烫伤");
                        else if (row.getCell(15).toString().equals("6"))
                            hospitalData.setInjureType("脑震荡、脑挫裂伤");
                        else if (row.getCell(15).toString().equals("7"))
                            hospitalData.setInjureType("内脏器官伤");
                        else if (row.getCell(15).toString().equals("8"))
                            hospitalData.setInjureType("其他");
                        else if (row.getCell(15).toString().equals("9"))
                            hospitalData.setInjureType("不清楚");
                    }

                    if(row.getCell(16) != null) {
                        if (row.getCell(16).toString().equals("1"))
                            hospitalData.setInjureSite("头部");
                        else if (row.getCell(16).toString().equals("2"))
                            hospitalData.setInjureSite("上肢");
                        else if (row.getCell(16).toString().equals("3"))
                            hospitalData.setInjureSite("下肢");
                        else if (row.getCell(16).toString().equals("4"))
                            hospitalData.setInjureSite("躯干");
                        else if (row.getCell(16).toString().equals("5"))
                            hospitalData.setInjureSite("多部位");
                        else if (row.getCell(16).toString().equals("6"))
                            hospitalData.setInjureSite("全身广泛受伤");
                        else if (row.getCell(16).toString().equals("7"))
                            hospitalData.setInjureSite("其他");
                        else if (row.getCell(16).toString().equals("8"))
                            hospitalData.setInjureSite("不清楚");
                    }

                    if(row.getCell(17) != null) {
                        if (row.getCell(17).toString().equals("1"))
                            hospitalData.setInjureDegree("轻度");
                        else if (row.getCell(17).toString().equals("2"))
                            hospitalData.setInjureDegree("中度");
                        else if (row.getCell(17).toString().equals("3"))
                            hospitalData.setInjureDegree("重度");
                    }

                    if(row.getCell(18) != null)
                        hospitalData.setInjurejudge(row.getCell(18).toString());

                    if(row.getCell(19) != null) {
                        if (row.getCell(19).toString().equals("1"))
                            hospitalData.setInjureResult("处理后离院");
                        else if (row.getCell(19).toString().equals("2"))
                            hospitalData.setInjureResult("留观");
                        else if (row.getCell(19).toString().equals("3"))
                            hospitalData.setInjureResult("转院");
                        else if (row.getCell(19).toString().equals("4"))
                            hospitalData.setInjureResult("住院");
                        else if (row.getCell(19).toString().equals("5"))
                            hospitalData.setInjureResult("死亡");
                        else if (row.getCell(19).toString().equals("6"))
                            hospitalData.setInjureResult("其他");
                    }

                    String product = null;
                    if(row.getCell(30) != null)
                        product = row.getCell(30).toString();
                    if(row.getCell(31) != null)
                        product = product + row.getCell(31).toString();
                    hospitalData.setProduct(product);

                    if(row.getCell(36) != null) {
                        if (row.getCell(36).toString().equals("1"))
                            hospitalData.setAlcohol("饮用");
                        else if (row.getCell(36).toString().equals("2"))
                            hospitalData.setAlcohol("未饮用");
                        else if (row.getCell(36).toString().equals("3"))
                            hospitalData.setAlcohol("不清楚");
                    }

                    if(row.getCell(37) != null) {
                        if (row.getCell(37).toString().equals("1"))
                            hospitalData.setInjureSystem("中枢神经系统");
                        else if (row.getCell(37).toString().equals("2"))
                            hospitalData.setInjureSystem("呼吸系统");
                        else if (row.getCell(37).toString().equals("3"))
                            hospitalData.setInjureSystem("消化系统");
                        else if (row.getCell(37).toString().equals("4"))
                            hospitalData.setInjureSystem("泌尿生殖系统");
                        else if (row.getCell(37).toString().equals("5"))
                            hospitalData.setInjureSystem("运动系统");
                        else if (row.getCell(37).toString().equals("6"))
                            hospitalData.setInjureSystem("多系统");
                        else if (row.getCell(37).toString().equals("7"))
                            hospitalData.setInjureSystem("其他");
                        else if (row.getCell(37).toString().equals("8"))
                            hospitalData.setInjureSystem("不清楚");
                    }

                    if(row.getCell(39) != null) {
                        if (row.getCell(39).toString().equals("1"))
                            hospitalData.setHowGetInjure("使用不当");
                        else if (row.getCell(39).toString().equals("2"))
                            hospitalData.setHowGetInjure("与产品质量有关");
                        else if (row.getCell(39).toString().equals("3"))
                            hospitalData.setHowGetInjure("像往常一样使用却突发事故");
                        else if (row.getCell(39).toString().equals("4"))
                            hospitalData.setHowGetInjure("不确定");
                        else if (row.getCell(39).toString().equals("5"))
                            hospitalData.setHowGetInjure("其他");
                    }

                    list.add(hospitalData);
                    //System.out.println(hospitalData);
                }
                workbook.close();
            }
            catch (Exception e) {
                System.out.println(e);
            }
            return list;
        }
}
