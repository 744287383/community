package com.community.jian.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOs;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private Integer page;
    private Integer endPage;
    private List<Integer> pages;
    private Integer size;
    public void initPage(Integer totalQuestion,Integer page,Integer size){
        this.size=size;
        pages=new ArrayList<>();

        endPage=0;
        if(totalQuestion%size==0){
            endPage=totalQuestion/size;
        }else {
            endPage=totalQuestion/size+1;
        }
        if (page<1){
            page=1;
        }
        if (page>endPage){
            page=endPage;
        }
        this.page=page;
        pages.add(page);
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
        }
        for (int i=1;i<=3;i++){
            if (page+i<=endPage){
                pages.add(page+i);
            }
        }

        if(this.page==1 || this.page==0){
            this.showPrevious=false;
        }else {
            this.showPrevious=true;
        }
        if (this.page==endPage){
            this.showNext=false;
        }else {
            this.showNext=true;
        }

        if ((this.page-3)>1){
            this.showFirstPage=true;
        }else {
            this.showFirstPage=false;
        }


        if((this.page+3)>=endPage){
            this.showEndPage=false;
        }else {
            this.showEndPage=true;
        }



    }

}
