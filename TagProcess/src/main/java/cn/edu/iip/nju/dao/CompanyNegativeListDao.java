package cn.edu.iip.nju.dao;

import cn.edu.iip.nju.model.CompanyNegativeList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xu on 2017/10/26.
 */
@Repository
public interface CompanyNegativeListDao extends JpaRepository<CompanyNegativeList,Integer>{
    Page<CompanyNegativeList> findAllByProvinceIsNull(Pageable pageable);
    Page<CompanyNegativeList> findAllByProvinceEquals(String province,Pageable pageable);
    List<CompanyNegativeList> findAllByProvinceEquals(String province);

    List<CompanyNegativeList> findAllByProvinceAndAndCompanyNameLike(String province,String companyName);
}
