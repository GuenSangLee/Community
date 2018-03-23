package io.github.seccoding.web.pager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import io.github.seccoding.web.pager.annotations.EndRow;
import io.github.seccoding.web.pager.annotations.StartRow;
import io.github.seccoding.web.pager.explorer.PageExplorer;

public abstract class Pager {
	
	public static final boolean ORACLE = true;
	public static final boolean OTHER = false;
	//전체 게시글 개수
	private int totalArticleCount;
	//한 페이지당 나오는 개수
	//한 그룹당 나오는 페이지 개수
	protected int printArticle;
	public int printPage;
	//
	protected int startArticleNumber;
	protected int endArticleNumber;

	public int totalPage;
	public int totalGroup;
//현재 페이지네이션 그룹 번호
	public int nowGroupNumber;

//현재 페이지네이션 그룹의 첫번째 페이지.
	public int groupStartPage;

	public int nextGroupPageNumber;
	public int prevGroupPageNumber;

	public int pageNo;
	
	/**
	 * Paging 객체를 얻어온다.
	 * 한 페이지당 보여지는 게시글 수 10개
	 * 한 페이지당 보여지는 페이지 수 10개
	 * 로 기본 설정됨.
	 */
	public Pager() {
		this.printArticle = 10;
		this.printPage = 10;
	}
	
	public Pager(int printArticle, int printPage) {
		this.printArticle = printArticle;
		this.printPage = printPage;
	}
	
	public void setPageNumber(int pageNumber) {
		setPageNumber(pageNumber + "");
	}
	
	/**
	 * 요청된 페이지의 번호를 얻어온다.
	 * 1 페이지의 경우 0이 입력된다.
	 * 아무것도 입력하지 않았다면 0으로 기본 설정된다.
	 * @param pageNumber
	 */
	public void setPageNumber(String pageNumber) {
		this.pageNo = 0;
		try {
			this.pageNo = Integer.parseInt(pageNumber);
		} catch (NumberFormatException nfe) {
			this.pageNo = 0;
		}

		computeArticleNumbers();
		
		this.nowGroupNumber = this.pageNo / this.printPage;
		this.groupStartPage = (this.nowGroupNumber * this.printPage) + 1;

		this.nextGroupPageNumber = this.groupStartPage + this.printPage - 1;
		this.prevGroupPageNumber = this.groupStartPage - this.printPage - 1;
	}
	
	protected abstract void computeArticleNumbers();
	
	/**
	 * 조회하려는 조건(Query)의 총 게시물 수를 정의한다.
	 * @param count
	 */
	public void setTotalArticleCount(int count) {
		this.totalArticleCount = count;

		this.totalPage = (int) Math.ceil((double) this.totalArticleCount
				/ this.printArticle);
		this.totalGroup = (int) Math.ceil((double) this.totalPage
				/ this.printPage);
	}
	
	/**
	 * 조회하려는 조건(Query)의 총 게시물 수를 가져온다.
	 * @return
	 */
	public int getTotalArticleCount() {
		return this.totalArticleCount;
	}

	/**
	 * Query에서 사용될 탐색 시작 번호 
	 * Oracle의 경우 rownum의 시작 번호
	 * @return
	 */
	public int getStartArticleNumber() {
		return this.startArticleNumber;
	}
	
	/**
	 * Query에서 사용될 탐색 시작 번호를 정의한다.
	 * @param startArticleNumber
	 */
	public void setStartArticleNumber(int startArticleNumber) {
		this.startArticleNumber = startArticleNumber;
	}
	
	/**
	 * Query에서 사용될 탐색 끝 번호를 정의한다.
	 * @param endArticleNumber
	 */
	public abstract void setEndArticleNumber(int endArticleNumber);

	/**
	 * Query에서 사용될 탐색 마지막 번호
	 * Oracle의 경우 rownum의 마지막 번호
	 * @return
	 */
	public abstract int getEndArticleNumber();
	
	/**
	 * PageExplorer 만들기
	 * @param cls
	 * 		<ul>
	 * 			<li>ClassicPageExplorer.class</li>
	 * 			<li>ListPageExplorer.class</li>
	 * 		</ul>
	 * @return ClassicPageExplorer, ListPageExplorer
	 */
	public PageExplorer makePageExplorer(Class<? extends PageExplorer> cls) {
		try {
			PageExplorer pageExplorer = cls.getDeclaredConstructor(Pager.class).newInstance(this);
			pageExplorer.setTotalCount(this.totalArticleCount);

			return pageExplorer;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PageExplorer 만들기
	 * @param cls
	 * 		<ul>
	 * 			<li>ClassicPageExplorer.class</li>
	 * 			<li>ListPageExplorer.class</li>
	 * 		</ul>
	 * @param searchObject
	 *
	 * @return ClassicPageExplorer, ListPageExplorer
	 */
	public PageExplorer makePageExplorer(Class<? extends PageExplorer> cls, Object searchObject) {
		PageExplorer pageExplorer = makePageExplorer(cls);

		Field[] fields = searchObject.getClass().getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getDeclaredAnnotations();
			for (Annotation annotation : annotations) {
				if(annotation.annotationType() == EndRow.class) {
					try {
						field.setAccessible(true);
						field.set(searchObject, this.getEndArticleNumber());
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				else if(annotation.annotationType() == StartRow.class) {
					try {
						field.setAccessible(true);
						field.set(searchObject, this.getStartArticleNumber());
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return pageExplorer;
	}
}
