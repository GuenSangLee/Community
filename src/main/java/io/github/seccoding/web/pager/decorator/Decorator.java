package io.github.seccoding.web.pager.decorator;

public class Decorator {

	public StringBuffer makeForm(String formId, String link) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<script>");
		buffer.append("function movePage(pageNo) {");
		buffer.append("document.getElementById('" + formId + "')." + link + ".value=pageNo;");
		buffer.append("document.getElementById('" + formId + "').action='';");
		buffer.append("document.getElementById('" + formId + "').method='post';");
		buffer.append("document.getElementById('" + formId + "').submit();");
		buffer.append("}");
		buffer.append("</script>");

		buffer.append("<input type=\"hidden\" id=\""+link+"\" name=\""+link+"\" />");

		return buffer;
	}

	public String makePageNumber(String pageFormat, int i) {
		return pageFormat.replaceAll("@", i + "");
	}

	public String makeHighlightNowPageNumber(String pageNumber) {
		return "<b>" + pageNumber + "</b>";
	}

	public String makePrevGroup(int prevGroupPageNumber, String prevButtonName) {
		return "<a href=\"javascript:movePage('" + prevGroupPageNumber + "')\">" + prevButtonName + "</a>";
	}

	public String makeNextGroup(int nextGroupPageNumber, String nextButtonName) {
		return "<a href=\"javascript:movePage('" + nextGroupPageNumber + "')\">" + nextButtonName + "</a>";
	}

	public String makePageNumbers(int pageIndex, String pageNumber) {
		return "<a href=\"javascript:movePage('" + pageIndex + "')\">" + pageNumber + "</a>";
	}
	
}
