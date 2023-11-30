package com.onlyone.crm.workbench.web.controller;

import com.onlyone.crm.commons.contants.Contants;
import com.onlyone.crm.commons.domain.ReturnObject;
import com.onlyone.crm.commons.utils.DateUtil;
import com.onlyone.crm.commons.utils.HSSFUtil;
import com.onlyone.crm.commons.utils.UUIDUtil;
import com.onlyone.crm.settings.domain.User;
import com.onlyone.crm.settings.service.UserService;
import com.onlyone.crm.workbench.domain.Activity;
import com.onlyone.crm.workbench.domain.ActivityRemark;
import com.onlyone.crm.workbench.mapper.ActivityRemarkMapper;
import com.onlyone.crm.workbench.service.ActivityRemarkService;
import com.onlyone.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * @Author 临渊
 * @Date 2023-11-25 16:13
 */

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityRemarkService activityRemarkService;

    public ActivityController() throws FileNotFoundException {
    }

    @RequestMapping("/workbench/activity/index")
    public String index(HttpServletRequest request) {
        List<User> userList = userService.selectAllNoLockStateUser();
        request.setAttribute("userList", userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreatActivity")
    public @ResponseBody Object saveCreatActivity(Activity activity, HttpServletRequest request) {
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateUtil.formatDateTime(new Date()));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            int resultCount = activityService.saveCreateActivity(activity);
            if (resultCount == 1) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙请...稍后再试!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙请...稍后再试!");
        }
        return returnObject;
    }



    @RequestMapping("/workbench/activity/selectActivityByConditionOfPageAndCount")
    public @ResponseBody Object selectActivityByConditionOfPageAndCount(String name, String owner, String startDate, String endDate,
                                                                        Integer pageNo, Integer pageSize) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer startNo = ( pageNo - 1 ) * pageSize;
        map.put("startNo", startNo);
        map.put("pageSize", pageSize);
        List<Activity> activityList = activityService.selectActivityByConditionOfPage(map);
        Integer totalRows = activityService.selectActivityByConditionCount(map);
        Map<String, Object> returnObjectMap = new HashMap<String, Object>();
        returnObjectMap.put("activityList", activityList);
        returnObjectMap.put("totalRows", totalRows);
        return returnObjectMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds")
    public @ResponseBody Object deleteActivityByIds(String[] id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            Integer count = activityService.deleteActivityByIds(id);
            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙...请稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙...请稍后再试");
        }
        return returnObject;
    }


    @RequestMapping("/workbench/activity/selectActivityById")
    public @ResponseBody Object selectActivityById(String id) {
        return activityService.selectActivityById(id);
    }

    @RequestMapping("/workbench/activity/updateActivityById")
    public @ResponseBody Object updateActivityById(Activity activity, HttpServletRequest request) {
        activity.setEditTime(DateUtil.formatDateTime(new Date()));
        activity.setEditBy(((User) request.getSession().getAttribute(Contants.SESSION_USER)).getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            Integer count = activityService.updateActivityById(activity);
            if (count == 1) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙...请稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙...请稍后再试");
        }
        return returnObject;
    }



    @RequestMapping("/workbench/activity/exportAllActivities")
    public void exportAllActivities(HttpServletResponse response) throws IOException {
        List<Activity> activityList = activityService.selectAllActivities();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始时间");
        cell = row.createCell(4);
        cell.setCellValue("结束时间");
        cell = row.createCell(5);
        cell.setCellValue("费用");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");
        Activity activity = null;
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);
                row = sheet.createRow( i + 1);
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }
       /* // 获取类路径下的 file 目录的绝对路径
//        String filePath = getClass().getClassLoader().getResource("activityList.xls").getPath();
        OutputStream os = new FileOutputStream("C:\\programme\\JAVATEST\\TestProject\\SSM\\CRM-SSM\\code\\crm_project\\crm\\src\\main\\resources\\file\\activityList.xls");
//        FileOutputStream os = new FileOutputStream("activityList.xls");
        wb.write(os);
        os.close();
        wb.close();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=activityList.xls");
        OutputStream out = response.getOutputStream();
        InputStream is = new FileInputStream("C:\\programme\\JAVATEST\\TestProject\\SSM\\CRM-SSM\\code\\crm_project\\crm\\src\\main\\resources\\file\\activityList.xls");
//        InputStream is = new FileInputStream("activityList.xls");

        byte[] bytes = new byte[256];
        int len = 0;
        while ((len = is.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        is.close();
        out.flush();
        out.close();*/

        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=activityList.xls");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        wb.close();
        out.flush();
    }

    @RequestMapping("/workbench/activity/importActivityList")
    public @ResponseBody Object insertActivityList(MultipartFile activityFile, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        try {
            InputStream is = activityFile.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            List<Activity> activityList = new ArrayList<Activity>();
            User user = (User) session.getAttribute(Contants.SESSION_USER);
            String temp = "";
            for (int i = 1 ; i <= sheet.getLastRowNum() ; i++) {
                Activity activity = new Activity();
                activity.setId(UUIDUtil.getUUID());
                activity.setOwner(user.getId());
                row = sheet.getRow(i);
                cell = row.getCell(0);
                temp = HSSFUtil.getCellValueForString(cell);
                activity.setName(temp);
                cell = row.getCell(1);
                temp = HSSFUtil.getCellValueForString(cell);
                activity.setStartDate(temp);
                cell = row.getCell(2);
                temp = HSSFUtil.getCellValueForString(cell);
                activity.setEndDate(temp);
                cell = row.getCell(3);
                temp = HSSFUtil.getCellValueForString(cell);
                activity.setCost(temp);
                cell = row.getCell(4);
                temp = HSSFUtil.getCellValueForString(cell);
                activity.setDescription(temp);
                activity.setCreateTime(DateUtil.formatDateTime(new Date()));
                activity.setCreateBy(user.getId());
                activityList.add(activity);
            }
            int count = activityService.insertActivityList(activityList);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setMessage(String.valueOf(count));
            is.close();
            wb.close();

        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前网络繁忙...请稍后再试");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/detailActivity")
    public String detailActivity(String activityId, HttpServletRequest request) {
        Activity activity = activityService.selectActivityForDetailById(activityId);
        List<ActivityRemark> activityRemarkList = activityRemarkService.selectActivityRemarkListForDetailByActivityId(activityId);
        request.setAttribute("activity", activity);
        request.setAttribute("activityRemarkList", activityRemarkList);
        return "workbench/activity/detail";
    }

}
