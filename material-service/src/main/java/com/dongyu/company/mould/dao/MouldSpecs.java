package com.dongyu.company.mould.dao;

import com.dongyu.company.mould.domain.PurchaseMould;
import com.dongyu.company.mould.dto.MouldQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义模具采购查询
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
public class MouldSpecs {
    private static final String DY_CODE = "dyCode";
    private static final String PRODUCT_MODEL = "productModel";
    private static final String SUPPLIER = "supplier";
    private static final String AFFILIATED_CUSTOMER = "affiliatedCustomer";
    private static final String PURCHASE_DATE = "purchaseDate";

    public static Specification<PurchaseMould> mouldListQuerySpec(MouldQueryDTO mouldQueryDTO) {
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据DY编号模糊查询
            if (StringUtils.isNotBlank(mouldQueryDTO.getDyCode())) {
                list.add(builder.like(root.get(DY_CODE), "%" + mouldQueryDTO.getDyCode() + "%"));
            }
            //根据所属客户模糊查询
            if (StringUtils.isNotBlank(mouldQueryDTO.getAffiliatedCustomer())) {
                list.add(builder.like(root.get(AFFILIATED_CUSTOMER), "%" + mouldQueryDTO.getAffiliatedCustomer() + "%"));
            }

            //根据产品型号模糊查询
            if (StringUtils.isNotBlank(mouldQueryDTO.getProductModel())) {
                list.add(builder.like(root.get(PRODUCT_MODEL), "%" + mouldQueryDTO.getProductModel() + "%"));
            }

            //根据供应商模糊查询
            if (StringUtils.isNotBlank(mouldQueryDTO.getSupplier())) {
                list.add(builder.like(root.get(SUPPLIER), "%" + mouldQueryDTO.getSupplier() + "%"));
            }
            //根据采购日期开始查询
            if (StringUtils.isNotBlank(mouldQueryDTO.getPurchaseDateStart())) {
                //大于或等于传入时间
                list.add(builder.greaterThanOrEqualTo(root.get(PURCHASE_DATE).as(String.class), mouldQueryDTO.getPurchaseDateStart()));
            }
            //根据采购日期结束查询
            if (StringUtils.isNotBlank(mouldQueryDTO.getPurchaseDateEnd())) {
                //小于等于传入时间
                list.add(builder.lessThanOrEqualTo(root.get(PURCHASE_DATE).as(String.class), mouldQueryDTO.getPurchaseDateEnd()));
            }
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}