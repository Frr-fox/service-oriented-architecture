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
    private int page = 1;

    /**
     * Максимальное количество элементов на странице
     * */
    @NonNull
    private int limit = 5;

    public PageDTO(@NonNull int page, @NonNull int limit) {
        this.page = page;
        this.limit = limit;
    }
}
