/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package com.dongyu.company.common.transform;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyMapImpl;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

/**
 * Result transformer that allows to transform a result to
 * a user specified class which will be populated via setter
 * methods or fields matching the alias names.
 * <p/>
 * <pre>
 * List resultWithAliasedBean = s.createCriteria(Enrolment.class)
 * 			.createAlias("student", "st")
 * 			.createAlias("course", "co")
 * 			.setProjection( Projections.projectionList()
 * 					.add( Projections.property("co.description"), "courseDescription" )
 * 			)
 * 			.setResultTransformer( new AliasToBeanResultTransformer(StudentDTO.class) )
 * 			.list();
 * <p/>
 *  StudentDTO dto = (StudentDTO)resultWithAliasedBean.get(0);
 * 	</pre>
 *
 * @author max
 */
public class OwnAliasToBeanResultTransformer extends AliasedTupleSubsetResultTransformer {

    // IMPL NOTE : due to the delayed population of setters (setters cached
    // 		for performance), we really cannot properly define equality for
    // 		this transformer

    private final Class resultClass;
    private boolean isInitialized;
    private String[] aliases;
    private Setter[] setters;

    public OwnAliasToBeanResultTransformer(Class resultClass) {
        if (resultClass == null) {
            throw new IllegalArgumentException("resultClass cannot be null");
        }
        isInitialized = false;
        this.resultClass = resultClass;
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Object result;

        try {
            if (!isInitialized) {
                initialize(aliases);
            } else {
                check(aliases);
            }

            result = resultClass.newInstance();

            for (int i = 0; i < aliases.length; i++) {
                if (setters[i] != null) {
                    Object param = tuple[i];
                    if (param instanceof BigInteger) {
                        param = ((BigInteger) param).longValue();
                    } else if (param instanceof Timestamp) {
                        param = (Date) param;
                    } else if (param instanceof Byte) {
                        param = ((Byte) param).intValue();
                    }
                    setters[i].set(result, param, null);
                }
            }
        } catch (InstantiationException e) {
            throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
        } catch (IllegalAccessException e) {
            throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
        }

        return result;
    }

    private void initialize(String[] aliases) {
        PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(PropertyAccessStrategyBasicImpl.INSTANCE, PropertyAccessStrategyFieldImpl.INSTANCE, PropertyAccessStrategyMapImpl.INSTANCE);
        this.aliases = new String[aliases.length];
        setters = new Setter[aliases.length];
        for (int i = 0; i < aliases.length; i++) {
            String alias = aliases[i];
            if (StringUtils.isNotBlank(alias)) {
                if (alias.contains("_")) {
                    alias = this.upperTable(alias);
                }
            }
            this.aliases[i] = alias;
            setters[i] = propertyAccessStrategy.buildPropertyAccess(resultClass, alias).getSetter();
            aliases[i] = alias;
        }
        isInitialized = true;
    }

    private void check(String[] aliases) {
        if (!Arrays.equals(aliases, this.aliases)) {
            throw new IllegalStateException("aliases are different from what is cached; aliases=" + Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OwnAliasToBeanResultTransformer that = (OwnAliasToBeanResultTransformer) o;

        if (!resultClass.equals(that.resultClass)) {
            return false;
        }
        if (!Arrays.equals(aliases, that.aliases)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = resultClass.hashCode();
        result = 31 * result + (aliases != null ? Arrays.hashCode(aliases) : 0);
        return result;
    }

    //转换驼峰test_tb_kkk_llll 转换为 testTbKkkLlll
    public static String upperTable(String str) {
        // 字符串缓冲区
        StringBuffer sbf = new StringBuffer();
        // 如果字符串包含 下划线
        if (str.contains("_")) {
            // 按下划线来切割字符串为数组
            String[] split = str.split("_");
            // 循环数组操作其中的字符串
            for (int i = 0, index = split.length; i < index; i++) {
                String upperTable = split[i];
                if (i == 0) {
                    sbf.append(upperTable);
                } else {
                    upperTable = upperTable.substring(0, 1).toUpperCase() + upperTable.substring(1);
                    // 添加到字符串缓冲区
                    sbf.append(upperTable);
                }
            }
        }
        return sbf.toString();
    }

}





