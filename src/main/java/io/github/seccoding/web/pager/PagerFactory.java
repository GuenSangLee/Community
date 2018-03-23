package io.github.seccoding.web.pager;

public class PagerFactory {

	public static Pager getPager(boolean isDbOracle) {
		if ( isDbOracle ) {
			return new OraclePager();
		}
		else {
			return new OtherPager();
		}
	}
	
	public static Pager getPager(boolean isDbOracle, int printArticle, int printPage) {
		if ( isDbOracle ) {
			return new OraclePager(printArticle, printPage);
		}
		else {
			return new OtherPager(printArticle, printPage);
		}
	}

	public static Pager getPager(boolean isDBOracle, String pageNo) {
		Pager pager = getPager(isDBOracle);
		pager.setPageNumber(pageNo);
		return pager;
	}

	public static Pager getPager(boolean isDBOracle, String pageNo, int totalCount) {
		Pager pager = getPager(isDBOracle);
		pager.setPageNumber(pageNo);
		pager.setTotalArticleCount(totalCount);
		return pager;
	}

	public static Pager getPager(boolean isDBOracle, String pageNo, int printArticle, int printPage) {
		Pager pager = getPager(isDBOracle, printArticle, printPage);
		pager.setPageNumber(pageNo);
		return pager;
	}

	public static Pager getPager(boolean isDBOracle, String pageNo, int totalCount, int printArticle, int printPage) {
		Pager pager = getPager(isDBOracle, printArticle, printPage);
		pager.setPageNumber(pageNo);
		pager.setTotalArticleCount(totalCount);
		return pager;
	}
	
}
