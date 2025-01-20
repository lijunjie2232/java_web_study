package com.li.hellospringmybatis.mapper;

import com.li.hellospringmybatis.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface EmpDynamicMapper {
    List<Emp> getEmpByNameAndSalary(
            @Param("name") String name,
            @Param("salaryMin") BigDecimal salaryMin,
            @Param("salaryMax") BigDecimal salaryMax
    );
    List<Emp> getEmpByNameAndSalaryByTrim(
            @Param("name") String name,
            @Param("salaryMin") BigDecimal salaryMin,
            @Param("salaryMax") BigDecimal salaryMax
    );
    List<Emp> getEmpByNameAndSalaryChoose(
            @Param("name") String name,
            @Param("salaryMin") BigDecimal salaryMin,
            @Param("salaryMax") BigDecimal salaryMax
    );

    void updateEmpByNameAndSalary(Emp emp);
}
