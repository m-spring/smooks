/*
	Milyn - Copyright (C) 2006 - 2010

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License (version 2.1) as published by the Free Software
	Foundation.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

	See the GNU Lesser General Public License for more details:
	http://www.gnu.org/licenses/lgpl.txt
*/
package org.smooks.delivery.sax;

import org.smooks.SmooksException;
import org.smooks.cdr.annotation.ConfigParam;
import org.smooks.container.ExecutionContext;

import java.io.IOException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class SAXVisitBeforeVisitor implements SAXVisitBefore {

    public static boolean visited = false;
    public static String staticInjectedParam;

    @ConfigParam
    private String injectedParam;

    public void visitBefore(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
        visited = true;
        staticInjectedParam = injectedParam;
    }

	/**
	 * @param injectedParam the injectedParam to set
	 */
	public SAXVisitBeforeVisitor setInjectedParam(String injectedParam) {
		this.injectedParam = injectedParam;
		return this;
	}
}
