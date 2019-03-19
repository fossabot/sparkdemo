package com.gehc.apps.fmk.sparkdemo.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post implements Validate {
    public enum PublicationStatus {

        DRAFT(1),
        VALIDATED(2),
        PUBLISHED(3),
        ARCHIVED(4);

        private int status=0;

        PublicationStatus(int i){
            this.status = i;
        }
        public int status(){
            return this.status;
        }

    }

    private UUID uuid;
    private String title;
    private String summary;
    private String content;
    private String author;
    private Date createdAt;
    private Date updatedAt;
    private PublicationStatus status;
    private String tags;
    private String categories;

    @Override
    public boolean isValide() {
        return (title!=null && !title.equals(""))
            && (content!=null && !content.equals(""))
            && (author!=null && !author.equals(""));
    }
}