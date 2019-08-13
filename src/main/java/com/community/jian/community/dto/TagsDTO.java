package com.community.jian.community.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class TagsDTO {
   @Value("${tags.language}")
   private String language;
   @Value("${tags.framework}")
   private String framework;
   @Value("${tags.server}")
   private String server;
   @Value("${tags.database}")
   private String database;
   @Value("${tags.tool}")
   private String tool;
}
