package model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class PageDTO implements Serializable {
    /**
     * Номер страницы
     * */
    @NonNull
    private Integer page = 1;

    /**
     * Максимальное количество элементов на странице
     * */
    @NonNull
    private Integer limit = 5;

    public PageDTO(@NonNull Integer page, @NonNull Integer limit) {
        this.page = page;
        this.limit = limit;
    }
}
