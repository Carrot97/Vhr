package com.carrot97.vhr.service;

import com.carrot97.vhr.mapper.EmployeeMapper;
import com.carrot97.vhr.model.Employee;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EmpBasicService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public final static Logger LOGGER = LoggerFactory.getLogger(EmpBasicService.class);

    SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthDateFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    private Double calTerm(Date beginDate, Date endDate) {
        double month = (Double.parseDouble(yearDateFormat.format(endDate)) - Double.parseDouble(yearDateFormat.format(beginDate))) * 12
                + Double.parseDouble(monthDateFormat.format(endDate)) - Double.parseDouble(monthDateFormat.format(beginDate));
        return Double.parseDouble(decimalFormat.format(month / 12));
    }

    public PageInfo<Employee> getEmpByPage(Integer pageNum, Integer pageSize, Employee employee, Date[] beginDateScope) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> employees = employeeMapper.getAllEmps(employee, beginDateScope);
        return new PageInfo<Employee>(employees);
    }

    public List<Employee> getAllEmps() {
        return employeeMapper.getAllEmps(null, null);
    }

    public Integer addEmp(Employee employee) {
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        employee.setContractTerm(calTerm(beginContract, endContract));
        int result = employeeMapper.insertSelective(employee);
        if (result == 1) {
            LOGGER.info(employee.toString());
            rabbitTemplate.convertAndSend("carrot97.mail.welcome", employee);
        }
        return result;
    }

    public Integer getMaxWorkId() {
        return employeeMapper.getMaxWorkId();
    }

    public Integer deleteById(Integer empId) {
        return employeeMapper.deleteByPrimaryKey(empId);
    }

    public Integer updateEmp(Employee employee) {
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        employee.setContractTerm(calTerm(beginContract, endContract));
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer batchAddEmps(List<Employee> employees) {
        return employeeMapper.BatchInsert(employees);
    }


}
