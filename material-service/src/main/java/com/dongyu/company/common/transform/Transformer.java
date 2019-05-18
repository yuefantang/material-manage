/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package com.dongyu.company.common.transform;


import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.ToListResultTransformer;

final public class Transformer {

	private Transformer() {}
	
	/**
	 * Each row of results is a <tt>Map</tt> from alias to values/entities
	 */
	public static final AliasToEntityMapResultTransformer ALIAS_TO_ENTITY_MAP =
			AliasToEntityMapResultTransformer.INSTANCE;

	/**
	 * Each row of results is a <tt>List</tt> 
	 */
	public static final ToListResultTransformer TO_LIST = ToListResultTransformer.INSTANCE;
	
	/**
	 * Creates a resulttransformer that will inject aliased values into 
	 * instances of Class via property methods or fields.
	 */
	public static ResultTransformer aliasToBean(Class target) {
		return new OwnAliasToBeanResultTransformer(target);
	}
	
}
