package com.carrot97.vhr.utils;

import com.carrot97.vhr.model.*;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class POIUtils {

    public static ResponseEntity<byte[]> employee2Excel(List<Employee> allEmps) {
        /*1 创建文档*/
        HSSFWorkbook workbook = new HSSFWorkbook();
        /*2 创建文档信息*/
        workbook.createInformationProperties();
        /*3 获取并配置文档摘要信息*/
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        docInfo.setCategory("员工信息");
        docInfo.setManager("Carrot97");
        docInfo.setCompany("www.carrot97.com");
        /*4 获取并配置摘要信息*/
        SummaryInformation sumInfo = workbook.getSummaryInformation();
        sumInfo.setTitle("员工信息表");
        sumInfo.setAuthor("Carrot97");
        sumInfo.setComments("本文档由carrot97提供技术支持");

        /*5 创建表单*/
        HSSFSheet sheet = workbook.createSheet("员工信息表");
        /*6 设置单元长度*/
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 8 * 256);
        sheet.setColumnWidth(3, 12 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 12 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 16 * 256);
        sheet.setColumnWidth(9, 25 * 256);
        sheet.setColumnWidth(10, 20 * 256);
        sheet.setColumnWidth(11, 25 * 256);
        sheet.setColumnWidth(12, 12 * 256);
        sheet.setColumnWidth(13, 12 * 256);
        sheet.setColumnWidth(14, 12 * 256);
        sheet.setColumnWidth(15, 12 * 256);
        sheet.setColumnWidth(16, 8 * 256);
        sheet.setColumnWidth(17, 20 * 256);
        sheet.setColumnWidth(18, 20 * 256);
        sheet.setColumnWidth(19,  12* 256);
        sheet.setColumnWidth(20, 12 * 256);
        sheet.setColumnWidth(21, 12 * 256);
        sheet.setColumnWidth(22, 10 * 256);
        sheet.setColumnWidth(23, 12 * 256);
        sheet.setColumnWidth(24, 12 * 256);
        /*7 创建标题行*/
        /*创建样式*/
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HSSFCellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

        HSSFRow r0 = sheet.createRow(0);
        HSSFCell c0 = r0.createCell(0);
        c0.setCellStyle(headerStyle);
        c0.setCellValue("编号");
        HSSFCell c1 = r0.createCell(1);
        c1.setCellStyle(headerStyle);
        c1.setCellValue("姓名");
        HSSFCell c2 = r0.createCell(2);
        c2.setCellStyle(headerStyle);
        c2.setCellValue("性别");
        HSSFCell c3 = r0.createCell(3);
        c3.setCellStyle(headerStyle);
        c3.setCellValue("出生日期");
        HSSFCell c4 = r0.createCell(4);
        c4.setCellStyle(headerStyle);
        c4.setCellValue("身份证号");
        HSSFCell c5 = r0.createCell(5);
        c5.setCellStyle(headerStyle);
        c5.setCellValue("婚姻状态");
        HSSFCell c6 = r0.createCell(6);
        c6.setCellStyle(headerStyle);
        c6.setCellValue("名族");
        HSSFCell c7 = r0.createCell(7);
        c7.setCellStyle(headerStyle);
        c7.setCellValue("籍贯");
        HSSFCell c8 = r0.createCell(8);
        c8.setCellStyle(headerStyle);
        c8.setCellValue("政治面貌");
        HSSFCell c9 = r0.createCell(9);
        c9.setCellStyle(headerStyle);
        c9.setCellValue("电子邮箱");
        HSSFCell c10 = r0.createCell(10);
        c10.setCellStyle(headerStyle);
        c10.setCellValue("手机号码");
        HSSFCell c11 = r0.createCell(11);
        c11.setCellStyle(headerStyle);
        c11.setCellValue("住址");
        HSSFCell c12 = r0.createCell(12);
        c12.setCellStyle(headerStyle);
        c12.setCellValue("所属部门");
        HSSFCell c13 = r0.createCell(13);
        c13.setCellStyle(headerStyle);
        c13.setCellValue("职称");
        HSSFCell c14 = r0.createCell(14);
        c14.setCellStyle(headerStyle);
        c14.setCellValue("职位");
        HSSFCell c15 = r0.createCell(15);
        c15.setCellStyle(headerStyle);
        c15.setCellValue("雇佣形式");
        HSSFCell c16 = r0.createCell(16);
        c16.setCellStyle(headerStyle);
        c16.setCellValue("最高学历");
        HSSFCell c17 = r0.createCell(17);
        c17.setCellStyle(headerStyle);
        c17.setCellValue("专业名称");
        HSSFCell c18 = r0.createCell(18);
        c18.setCellStyle(headerStyle);
        c18.setCellValue("毕业院校");
        HSSFCell c19 = r0.createCell(19);
        c19.setCellStyle(headerStyle);
        c19.setCellValue("入职时间");
        HSSFCell c20 = r0.createCell(20);
        c20.setCellStyle(headerStyle);
        c20.setCellValue("工号");
        HSSFCell c21 = r0.createCell(21);
        c21.setCellStyle(headerStyle);
        c21.setCellValue("合同期限");
        HSSFCell c22 = r0.createCell(22);
        c22.setCellStyle(headerStyle);
        c22.setCellValue("转正日期");
        HSSFCell c23 = r0.createCell(23);
        c23.setCellStyle(headerStyle);
        c23.setCellValue("合同起始时间");
        HSSFCell c24 = r0.createCell(24);
        c24.setCellStyle(headerStyle);
        c24.setCellValue("合同终止时间");

        for (int i = 0; i < allEmps.size(); i++) {
            Employee employee = allEmps.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getName());
            row.createCell(2).setCellValue(employee.getGender());
            HSSFCell cell3 = row.createCell(3);
            cell3.setCellStyle(dateStyle);
            cell3.setCellValue(employee.getBirthday());
            row.createCell(4).setCellValue(employee.getIdCard());
            row.createCell(5).setCellValue(employee.getWedlock());
            row.createCell(6).setCellValue(employee.getNation().getName());
            row.createCell(7).setCellValue(employee.getNativePlace());
            row.createCell(8).setCellValue(employee.getPoliticsstatus().getName());
            row.createCell(9).setCellValue(employee.getEmail());
            row.createCell(10).setCellValue(employee.getPhone());
            row.createCell(11).setCellValue(employee.getAddress());
            row.createCell(12).setCellValue(employee.getDepartment().getName());
            row.createCell(13).setCellValue(employee.getJobLevel().getName());
            row.createCell(14).setCellValue(employee.getPosition().getName());
            row.createCell(15).setCellValue(employee.getEngageForm());
            row.createCell(16).setCellValue(employee.getTiptopDegree());
            row.createCell(17).setCellValue(employee.getSpecialty());
            row.createCell(18).setCellValue(employee.getSchool());
            HSSFCell cell19 = row.createCell(19);
            cell19.setCellStyle(dateStyle);
            cell19.setCellValue(employee.getBeginDate());
            row.createCell(20).setCellValue(employee.getWorkId());
            row.createCell(21).setCellValue(employee.getContractTerm());
            HSSFCell cell22 = row.createCell(22);
            cell22.setCellStyle(dateStyle);
            cell22.setCellValue(employee.getConversionTime());
            HSSFCell cell23 = row.createCell(23);
            cell23.setCellStyle(dateStyle);
            cell23.setCellValue(employee.getBeginContract());
            HSSFCell cell24 = row.createCell(24);
            cell24.setCellStyle(dateStyle);
            cell24.setCellValue(employee.getEndContract());
        }

        /*8 写出*/
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment",
                    new String("员工表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static ArrayList<Employee> excel2Employee(MultipartFile file, List<Nation> allNations, List<Politicsstatus> allPoliticsstatus,
                                                     List<JobLevel> allJobLevels, List<Position> allPositions, List<Department> allDepartments) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            HSSFSheet sheet = workbook.getSheetAt(0);
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            if (numberOfRows > 1) {
                for (int i = 1; i < numberOfRows; i++) {
                    HSSFRow row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    int numberOfCells = row.getPhysicalNumberOfCells();
                    Employee employee = new Employee();
                    for (int j = 1; j < numberOfCells ; j++) {
                        HSSFCell cell = row.getCell(j);
                        switch (j) {
                            case 1:
                                employee.setName(cell.getStringCellValue());
                                break;
                            case 2:
                                employee.setGender(cell.getStringCellValue());
                                break;
                            case 3:
                                employee.setBirthday(cell.getDateCellValue());
                                break;
                            case 4:
                                employee.setIdCard(cell.getStringCellValue());
                                break;
                            case 5:
                                employee.setWedlock(cell.getStringCellValue());
                                break;
                            case 6:
                                int nationIdx = allNations.indexOf(new Nation(cell.getStringCellValue()));
                                employee.setNationId(allNations.get(nationIdx).getId());
                                break;
                            case 7:
                                employee.setNativePlace(cell.getStringCellValue());
                                break;
                            case 8:
                                int PoliticIdx = allPoliticsstatus.indexOf(new Politicsstatus(cell.getStringCellValue()));
                                employee.setPoliticId(allPoliticsstatus.get(PoliticIdx).getId());
                                break;
                            case 9:
                                employee.setEmail(cell.getStringCellValue());
                                break;
                            case 10:
                                employee.setPhone(cell.getStringCellValue());
                                break;
                            case 11:
                                employee.setAddress(cell.getStringCellValue());
                                break;
                            case 12:
                                int departmentIdx = allDepartments.indexOf(new Department(cell.getStringCellValue()));
                                employee.setDepartmentId(allDepartments.get(departmentIdx).getId());
                                break;
                            case 13:
                                int jobLevelIdx = allJobLevels.indexOf(new JobLevel(cell.getStringCellValue()));
                                employee.setJobLevelId(allJobLevels.get(jobLevelIdx).getId());
                                break;
                            case 14:
                                int positionsIdx = allPositions.indexOf(new Position(cell.getStringCellValue()));
                                employee.setPosId(allPositions.get(positionsIdx).getId());
                                break;
                            case 15:
                                employee.setEngageForm(cell.getStringCellValue());
                                break;
                            case 16:
                                employee.setTiptopDegree(cell.getStringCellValue());
                                break;
                            case 17:
                                employee.setSpecialty(cell.getStringCellValue());
                                break;
                            case 18:
                                employee.setSchool(cell.getStringCellValue());
                                break;
                            case 19:
                                employee.setBeginDate(cell.getDateCellValue());
                                break;
                            case 20:
                                employee.setWorkId(cell.getStringCellValue());
                                break;
                            case 21:
                                employee.setContractTerm(cell.getNumericCellValue());
                                break;
                            case 22:
                                employee.setConversionTime(cell.getDateCellValue());
                                break;
                            case 23:
                                employee.setBeginContract(cell.getDateCellValue());
                                break;
                            case 24:
                                employee.setEndContract(cell.getDateCellValue());
                                break;
                        }
                    }
                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
