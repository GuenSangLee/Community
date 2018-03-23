package io.github.seccoding.web.pager.explorer;

import io.github.seccoding.web.pager.Pager;
import io.github.seccoding.web.pager.decorator.Decorator;

public class ListPageExplorer extends PageExplorer {

	public ListPageExplorer(Pager pager) {
		this.pager = pager;
		this.decorator = new Decorator();
	}
	
	public String generate(StringBuffer pagenation) {

		int centerPage = pager.printPage / 2;
		int startPageNumber = pager.pageNo - centerPage;
		if ( startPageNumber < 0 ) {
			startPageNumber = 0;
		}
		
		int endPageNumber = startPageNumber + pager.printPage;
		if ( endPageNumber > pager.totalPage ) {
			endPageNumber = pager.totalPage;
		}
		
		if ( endPageNumber - startPageNumber < pager.printPage ) {
			startPageNumber = startPageNumber - (pager.printPage - (endPageNumber - startPageNumber));
			if ( startPageNumber < 0 ) {
				startPageNumber = 0;
			}
		}
		
		String pageNumber = "";
		
		if ( startPageNumber > 0 ) {
			pagenation.append(makePrevGroup(pager.pageNo - 1));
		}
		
		for (int i = startPageNumber; i < endPageNumber; i++) {
			pageNumber = decorator.makePageNumber(pageFormat, i+1);
			if (i == pager.pageNo) {
				pageNumber = makeHighlightNowPageNumber(pageNumber);
			}
			
			pagenation.append(makePageNumbers(i, pageNumber));
		}
		
		if ( pager.pageNo < endPageNumber-1 ) {
			pagenation.append(makeNextGroup(pager.pageNo+1));
		}

		return pagenation.toString();
	}
	
}
