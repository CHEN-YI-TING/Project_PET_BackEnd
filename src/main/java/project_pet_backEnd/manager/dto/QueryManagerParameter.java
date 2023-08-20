package project_pet_backEnd.manager.dto;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

@Data
public class QueryManagerParameter {
    private  String search;
    private Integer page;//當前頁數
    private Integer size;//該頁顯示筆數
}
