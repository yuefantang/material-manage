package com.dongyu.company.order.dao;

import com.dongyu.company.order.domain.Order;
import com.dongyu.company.order.dto.OrderQueryDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 下单原生sql多表查询
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Repository
public class OrderSqlDao {
    @Autowired
    private EntityManager entityManager;

    public Map<String, Object> queryOrder(OrderQueryDTO orderQueryDTO, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        sql.append(" SELECT o.id,o.charge_opening,o.commissioning_code,o.commissioning_num,o.complete_state,o.completed_num,o.customer_order_code,o.deleted,o.delivery_date,o.hared_materials_num,o.mi_register_id,o.operation_state,o.order_date,o.order_dy_code,o.order_num,o.spare_parts_num,o.spare_parts_rate,o.square_num,o.surplus_pcs,o.surplus_pnl,o.surplus_remarks,o.surplus_treatment,o.uncompleted_num ");
        sql.append(" from t_order o ,t_mi_register m ");
        sql.append(" WHERE o.mi_register_id=m.id ");
        //根据客户型号模糊查询
        if (StringUtils.isNotBlank(orderQueryDTO.getCustomerModel())) {
            sql.append(" AND m.customer_model LIKE '%" + orderQueryDTO.getCustomerModel() + "%' ");
        }
        //根据客户名称模糊查询
        if (StringUtils.isNotBlank(orderQueryDTO.getCustomerName())) {
            sql.append(" AND m.customer_name LIKE '%" + orderQueryDTO.getCustomerName() + "%' ");
        }
        //根据投产单号模糊查询
        if (StringUtils.isNotBlank(orderQueryDTO.getCommissioningCode())) {
            sql.append(" AND o.commissioning_code LIKE '%" + orderQueryDTO.getCommissioningCode() + "%' ");
        }
        //根据DY编号模糊查询
        if (StringUtils.isNotBlank(orderQueryDTO.getOrderDyCode())) {
            sql.append(" AND o.order_dy_code LIKE '%" + orderQueryDTO.getOrderDyCode() + "%' ");
        }
        //订单是否完成
        if (orderQueryDTO.getCompleteState() != null) {
            sql.append(" AND o.complete_state='" + orderQueryDTO.getCompleteState() + "' ");
        }
        //根据下单是否删除查询
        if (orderQueryDTO.getDeleted() != null) {
            sql.append(" AND o.deleted='" + orderQueryDTO.getDeleted() + "' ");
        }
        //收费开单
        if (orderQueryDTO.getChargeOpening() != null) {
            sql.append(" AND o.charge_opening='" + orderQueryDTO.getChargeOpening() + "' ");
        }
        sql.append("ORDER BY o.create_time DESC");
        Query query = this.entityManager.createNativeQuery(sql.toString());
        //获取查询总数
        countSql.append("SELECT count(1) from (" + sql + ")oo");
        Query countQuery = this.entityManager.createNativeQuery(countSql.toString());
        Long count = Long.valueOf(String.valueOf(countQuery.getSingleResult()));
        //分页查询参数设置
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults((pageNumber + 1) * pageSize);
        List<Object[]> resultList = query.getResultList();
        if (entityManager != null) {
            entityManager.close();
        }
        List<Order> collect = null;
        if (CollectionUtils.isNotEmpty(resultList)) {
            collect = this.transf(resultList);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("resultList", collect);
        map.put("count", count);
        return map;
    }

    public List<Order> queryOrderList(OrderQueryDTO orderQueryDTO) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT o.id,o.charge_opening,o.commissioning_code,o.commissioning_num,o.complete_state,o.completed_num,o.customer_order_code,o.deleted,o.delivery_date,o.hared_materials_num,o.mi_register_id,o.operation_state,o.order_date,o.order_dy_code,o.order_num,o.spare_parts_num,o.spare_parts_rate,o.square_num,o.surplus_pcs,o.surplus_pnl,o.surplus_remarks,o.surplus_treatment,o.uncompleted_num ");
        sql.append(" from t_order o ,t_mi_register m ");
        sql.append(" WHERE o.mi_register_id=m.id ");
        //根据客户型号模糊查询
        if (StringUtils.isNotBlank(orderQueryDTO.getCustomerModel())) {
            sql.append(" AND m.customer_model LIKE '%" + orderQueryDTO.getCustomerModel() + "%' ");
        }
        //根据客户名称模糊查询
        if (StringUtils.isNotBlank(orderQueryDTO.getCustomerName())) {
            sql.append(" AND m.customer_name LIKE '%" + orderQueryDTO.getCustomerName() + "%' ");
        }
        //根据投产单号模糊查询
        if (StringUtils.isNotBlank(orderQueryDTO.getCommissioningCode())) {
            sql.append(" AND o.commissioning_code LIKE '%" + orderQueryDTO.getCommissioningCode() + "%' ");
        }
        //根据DY编号模糊查询
        if (StringUtils.isNotBlank(orderQueryDTO.getOrderDyCode())) {
            sql.append(" AND o.order_dy_code LIKE '%" + orderQueryDTO.getOrderDyCode() + "%' ");
        }
        //订单是否完成
        if (orderQueryDTO.getCompleteState() != null) {
            sql.append(" AND o.complete_state='" + orderQueryDTO.getCompleteState() + "' ");
        }
        //根据下单是否删除查询
        if (orderQueryDTO.getDeleted() != null) {
            sql.append(" AND o.deleted='" + orderQueryDTO.getDeleted() + "' ");
        }
        //收费开单
        if (orderQueryDTO.getChargeOpening() != null) {
            sql.append(" AND o.charge_opening='" + orderQueryDTO.getChargeOpening() + "' ");
        }
        sql.append("ORDER BY o.create_time DESC");
        Query query = this.entityManager.createNativeQuery(sql.toString());
        List<Object[]> resultList = query.getResultList();
        if (entityManager != null) {
            entityManager.close();
        }
        List<Order> collect = null;
        if (CollectionUtils.isNotEmpty(resultList)) {
            collect = this.transf(resultList);
        }
        return collect;
    }

    private List<Order> transf(List<Object[]> resultList) {
        return resultList.stream().map(obj -> {
            Order order = new Order();
            order.setId(obj[0] == null ? null : Long.valueOf(obj[0].toString()));
            order.setChargeOpening(obj[1] == null ? null : Integer.valueOf(obj[1].toString()));
            order.setCommissioningCode(obj[2] == null ? null : obj[2].toString());
            order.setCommissioningNum(obj[3] == null ? null : obj[3].toString());
            order.setCompleteState(obj[4] == null ? null : Integer.valueOf(obj[4].toString()));
            order.setCompletedNum(obj[5] == null ? null : obj[5].toString());
            order.setCustomerOrderCode(obj[6] == null ? null : obj[6].toString());
            order.setDeleted(obj[7] == null ? null : Integer.valueOf(obj[7].toString()));
            order.setDeliveryDate(obj[8] == null ? null : (Date) obj[8]);
            order.setHaredMaterialsNum(obj[9] == null ? null : obj[9].toString());
            order.setMiRegisterId(obj[10] == null ? null : Long.valueOf(obj[10].toString()));
            order.setOperationState(obj[11] == null ? null : Integer.valueOf(obj[11].toString()));
            order.setOrderDate(obj[12] == null ? null : (Date) obj[12]);
            order.setOrderDyCode(obj[13] == null ? null : obj[13].toString());
            order.setOrderNum(obj[14] == null ? null : obj[14].toString());
            order.setSparePartsNum(obj[15] == null ? null : obj[15].toString());
            order.setSparePartsRate(obj[16] == null ? null : obj[16].toString());
            order.setSquareNum(obj[17] == null ? null : obj[17].toString());
            order.setSurplusPcs(obj[18] == null ? null : obj[18].toString());
            order.setSurplusPnl(obj[19] == null ? null : obj[19].toString());
            order.setSurplusRemarks(obj[20] == null ? null : obj[20].toString());
            order.setSurplusTreatment(obj[21] == null ? null : obj[21].toString());
            order.setUncompletedNum(obj[22] == null ? null : obj[22].toString());
            return order;
        }).collect(Collectors.toList());
    }

}
