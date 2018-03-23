package io.github.seccoding.web.pager.explorer;

import io.github.seccoding.web.pager.Pager;
import io.github.seccoding.web.pager.decorator.Decorator;

public class ClassicPageExplorer extends PageExplorer {

	public ClassicPageExplorer(Pager pager) {
		this.pager = pager;
		this.decorator = new Decorator();
	}
	
	protected String generate(StringBuffer pagenation) {

        if (pager.nowGroupNumber > 0) {
        	pagenation.append(makePrevGroup(pager.prevGroupPageNumber));
        }

        int nextPrintPage = pager.groupStartPage + pager.printPage;
        if (nextPrintPage > pager.totalPage) {
            nextPrintPage = pager.totalPage + 1;
        }

        String pageNumber = "";

        for (int i = pager.groupStartPage; i < nextPrintPage; i++) {
        		
        	pageNumber = decorator.makePageNumber(pageFormat, i);
            
            if ((i-1) == pager.pageNo) {
            	pageNumber = makeHighlightNowPageNumber(pageNumber);
            }
            
            pagenation.append(makePageNumbers(i-1, pageNumber));
        }

        if (pager.nowGroupNumber < (pager.totalGroup - 1)) {
        	pagenation.append(makeNextGroup(pager.nextGroupPageNumber));
        }

        return pagenation.toString();
    }
	
}
