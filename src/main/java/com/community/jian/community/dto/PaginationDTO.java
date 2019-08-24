package com.community.jian.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {

    private List<T> data;//页面数据
    private Integer page;//当前页面的码数
    private Integer endPage;//末尾页的码数！
    private List<Integer> pages;//显示出来的页码按钮的码数
    private Integer size;//一页里多少个数据

    private boolean showPrevious;//是否显示上一页按钮
    private boolean showNext;//是否显示下一页按钮
    private boolean showFirstPage;//是否显示首页按钮
    private boolean showEndPage;//是否显示末位页的按钮

    /**
     * @param totalQuestion 总的问题数
     * @param page          访问第几页的页码数
     * @param size          一页中多少行数据
     * @return 返回size*(pageNum-1)即offset，sql语句中的limit offset,sise：
     **/
    public int initPage(Integer totalQuestion, Integer page, Integer size) {
        //防止恶意传入数据总数量
        if (totalQuestion <= 0) {
            totalQuestion = 0;
        }
        //防止恶意传入size
        if (size <= 0) {
            this.size = 1;
        } else {
            this.size = size;//完成size初始化
        }

        countEndPage(totalQuestion, size);//完成endPage初始化

        //页面最小是1
        if (page < 1) {
            this.page = 1;
        } else {
            this.page = page;
        }

        //防止page大于endPage
        if (this.page > this.endPage) {
            this.page = this.endPage;
        }//完成page当前页初始化

        countPagesList(totalQuestion);//pages完成pages页码按钮列初始化

        initShowPrevious();
        initShowNext();
        initShouFirstPage();
        initShowEndPage();


        return this.size * (this.page - 1);//返回offset偏移量！
    }

    /**
     * @param totalQuestion 总共的数据量
     * @param size          页面数据量
     *                      计算出末尾页码，最后的数据不够一页时也要多加一页
     **/
    private void countEndPage(Integer totalQuestion, Integer size) {
        this.endPage = 0;
        if (totalQuestion % size == 0) {
            this.endPage = totalQuestion / size;
        } else {
            this.endPage = totalQuestion / size + 1;
        }
    }

    /**
     * 当前页码偏移3次后回到大于等于末尾页页码就不显示末尾按钮
     **/
    private void initShowEndPage() {
        if ((this.page + 3) >= endPage) {
            this.showEndPage = false;
        } else {
            this.showEndPage = true;
        }
    }

    /**
     * 当前页码偏移3次后回到小于等于第一页页码就不显示首页按钮
     **/
    private void initShouFirstPage() {
        if ((this.page - 3) <= 1) {
            this.showFirstPage = false;
        } else {
            this.showFirstPage = true;
        }
    }

    /**
     * 当前页码等于最后一页时就不显示下一页按钮
     **/
    private void initShowNext() {
        if (this.page == endPage) {
            this.showNext = false;
        } else {
            this.showNext = true;
        }
    }

    /**
     * 当前页面码数为1或者等于0的时候不显示上一页按钮
     **/
    private void initShowPrevious() {
        if (this.page == 1 || this.page == 0) {
            this.showPrevious = false;
        } else {
            this.showPrevious = true;
        }
    }

    /**
     * 获取当前页面左边三个页码 ，当前页码，右边变三个页码
     *
     * @param allNum 总的数据条数！
     **/
    private void countPagesList(Integer allNum) {
        pages = new ArrayList<>();
        if (allNum == 0) {
            return;
        }
        pages.add(this.page);
        for (int i = 1; i <= 3; i++) {
            if (this.page - i > 0) {
                pages.add(0, this.page - i);
            }
        }
        for (int i = 1; i <= 3; i++) {
            if (this.page + i <= endPage) {
                pages.add(this.page + i);
            }
        }
    }

}
