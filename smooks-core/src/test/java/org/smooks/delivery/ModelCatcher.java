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
package org.smooks.delivery;

import org.smooks.delivery.sax.SAXVisitAfter;
import org.smooks.delivery.sax.SAXElement;
import org.smooks.delivery.dom.DOMVisitAfter;
import org.smooks.container.ExecutionContext;
import org.smooks.SmooksException;
import org.smooks.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

/**
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
*/
public class ModelCatcher implements SAXVisitAfter, DOMVisitAfter {

    public static List<Element> elements = new ArrayList<Element>();

    public void visitAfter(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
        addModelElement(element.getName().getLocalPart(), executionContext);
    }

    public void visitAfter(Element element, ExecutionContext executionContext) throws SmooksException {
        addModelElement(DomUtils.getName(element), executionContext);
    }

    private void addModelElement(String name, ExecutionContext executionContext) {
        DOMModel nodeModel = DOMModel.getModel(executionContext);
        elements.add(nodeModel.getModels().get(name));
    }
}
